"""手动迁移最新正式版的旧 ZIP：保留原 JAR 字节，全部核实后才移除旧附件。"""
import hashlib
import json
from pathlib import Path
import re
import subprocess
import tempfile
import zipfile
import os

repository = os.environ['GITHUB_REPOSITORY']
if repository != 'fxjcangku/yiyiaddon':
    raise RuntimeError('只允许维护指定公开仓库')

def gh(*args):
    return subprocess.check_output(['gh', *args], text=True, encoding='utf-8')

release = json.loads(gh('api', f'repos/{repository}/releases/latest'))
if release['draft'] or release['prerelease']:
    raise RuntimeError('只迁移真实最新正式版')
tag = release['tag_name']
version = tag.removeprefix('v')
if not re.fullmatch(r'\d+(?:\.\d+){1,2}', version):
    raise RuntimeError('该版本号不在本次旧包迁移范围')
games = ['26.1.2', '26.2', '26.3']
old_assets = [a for a in release['assets'] if a['name'] in [f'yiyiaddon-{version}-{g}.zip' for g in games]]
if not old_assets:
    print('没有待迁移 ZIP，不修改附件。')
    raise SystemExit(0)

with tempfile.TemporaryDirectory(prefix='yiyiaddon-jar-migration-') as directory:
    folder = Path(directory)
    ready = []
    for asset in old_assets:
        name = asset['name']
        gh('release', 'download', tag, '--repo', repository, '--pattern', name, '--dir', str(folder))
        original = folder / name
        digest = hashlib.sha256(original.read_bytes()).hexdigest()
        if asset.get('digest') != f'sha256:{digest}':
            raise RuntimeError(f'公开 ZIP 摘要不符：{name}')
        jar_name = name.removesuffix('.zip') + '.jar'
        with zipfile.ZipFile(original) as archive:
            matches = [item for item in archive.infolist() if Path(item.filename).name == jar_name]
            if len(matches) != 1:
                raise RuntimeError(f'ZIP 内模组 JAR 不唯一：{name}')
            jar_bytes = archive.read(matches[0])
        target = folder / jar_name
        target.write_bytes(jar_bytes)
        jar_hash = hashlib.sha256(jar_bytes).hexdigest()
        current = json.loads(gh('api', f'repos/{repository}/releases/{release["id"]}'))
        existing = next((a for a in current['assets'] if a['name'] == jar_name), None)
        if existing:
            if existing.get('digest') != f'sha256:{jar_hash}' or existing['size'] != len(jar_bytes):
                raise RuntimeError(f'同名 JAR 已存在且内容不同，拒绝覆盖：{jar_name}')
        else:
            gh('release', 'upload', tag, str(target), '--repo', repository)
        current = json.loads(gh('api', f'repos/{repository}/releases/{release["id"]}'))
        uploaded = next((a for a in current['assets'] if a['name'] == jar_name), None)
        if not uploaded or uploaded['state'] != 'uploaded' or uploaded['size'] != len(jar_bytes) or uploaded.get('digest') != f'sha256:{jar_hash}':
            raise RuntimeError(f'上传后 JAR 核对失败：{jar_name}')
        print(json.dumps({'jar': jar_name, 'sha256': jar_hash, 'bytes': len(jar_bytes)}, ensure_ascii=False))
        ready.append((asset, uploaded))
    # 所有 JAR 先核实，才替换下载正文与移除已迁移的 ZIP；任何中途失败都保留原下载。
    current = json.loads(gh('api', f'repos/{repository}/releases/{release["id"]}'))
    body = current['body']
    for old, new in ready:
        body = body.replace(old['browser_download_url'], new['browser_download_url'])
    body = body.replace('下载 ZIP', '下载 JAR')
    notes = folder / 'notes.md'
    notes.write_text(body, encoding='utf-8')
    gh('release', 'edit', tag, '--repo', repository, '--notes-file', str(notes))
    for old, new in ready:
        gh('release', 'delete-asset', tag, old['name'], '--repo', repository, '--yes')
    verified = json.loads(gh('api', f'repos/{repository}/releases/{release["id"]}'))
    if any(a['name'] == old['name'] for a in verified['assets'] for old, new in ready):
        raise RuntimeError('旧 ZIP 仍在公开附件中')
    if verified['body'] != body:
        raise RuntimeError('发布正文回读不一致')
    print('旧 ZIP 已迁移为字节相同的混淆 JAR，发布正文与附件回读通过。')

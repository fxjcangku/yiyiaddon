// 从同一份 Markdown 生成本地预览，避免展示稿与仓库正文分别维护。
import fs from 'node:fs';
import { fileURLToPath, pathToFileURL } from 'node:url';
import { resolve } from 'node:path';
// 可从命令行指定现有 marked 模块，避免把机器绝对路径固定进设计稿。
const { marked } = await import(process.argv[2] ? pathToFileURL(resolve(process.argv[2])).href : 'marked');
const root = fileURLToPath(new URL('../', import.meta.url));
function render(file) {
  return marked.parse(fs.readFileSync(file, 'utf8'))
    .replaceAll('src="展示资源/', 'src="仓库首页/展示资源/')
    .replaceAll('src="' + encodeURIComponent('展示资源') + '/', 'src="仓库首页/展示资源/')
    .replace(/<h2>(.*?)<\/h2>/g, (_, title) => `<h2 id="${title.replace(/<[^>]*>/g, '')}">${title}</h2>`)
    .replace(/<table>/g, '<div class="table-scroll"><table>').replace(/<\/table>/g, '</table></div>');
}
const styles = fs.readFileSync(root + '/工具/预览样式.css', 'utf8');
const home = render(root + '/仓库首页/README.md');
const release = render(root + '/版本说明草稿.md');
const toc = [...home.matchAll(/<h2 id="([^"]*)">/g)].map(([, title]) => `<a href="#${title}">${title}</a>`).join('');
const html = `<!doctype html>
<html lang="zh-CN" data-theme="dark"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>yiyiaddon · 发布页面设计预览</title>
<style>${styles}</style></head><body>
<header class="top"><div class="top-inner"><span class="brand"><i></i>yiyiaddon</span><span class="label">方块世界 · 发布预览</span><div class="controls"><button id="home-button" aria-pressed="true">仓库首页</button><button id="release-button" aria-pressed="false">版本说明</button><button id="theme-button" aria-pressed="false">切换浅色</button></div></div></header>
<div class="layout"><aside><div class="eyebrow">功能栏</div><nav>${toc}</nav><div class="note">Minecraft 26.1.2<br>Beta 公开测试版<br><br>图形为功能概念示意<br>实际界面以游戏为准</div></aside><main><div class="filebar"><span id="filename">README.md</span><span>1.0-beta1 · 公开测试版</span></div><article class="markdown" id="home">${home}</article><article class="markdown" id="release" hidden>${release}</article><div class="foot">正文来自发布草稿 · GitHub 实际排版以平台渲染为准</div></main></div>
<script>
// 切换仅改变本地预览，不执行上传、下载或任何发布操作。
const homeButton=document.getElementById('home-button'),releaseButton=document.getElementById('release-button');
function select(release){document.getElementById('home').hidden=release;document.getElementById('release').hidden=!release;homeButton.setAttribute('aria-pressed',String(!release));releaseButton.setAttribute('aria-pressed',String(release));document.getElementById('filename').textContent=release?'版本说明草稿.md':'README.md';window.scrollTo(0,0)}
homeButton.onclick=()=>select(false);releaseButton.onclick=()=>select(true);
document.querySelectorAll('aside a').forEach(a=>a.addEventListener('click',()=>select(false)));
document.getElementById('theme-button').onclick=function(){const light=document.documentElement.dataset.theme==='dark';document.documentElement.dataset.theme=light?'light':'dark';this.textContent=light?'切换深色':'切换浅色';this.setAttribute('aria-pressed',String(light))};
</script></body></html>`;
fs.writeFileSync(root + '/页面预览.html', html);
console.log('预览已生成：' + root + '/页面预览.html');

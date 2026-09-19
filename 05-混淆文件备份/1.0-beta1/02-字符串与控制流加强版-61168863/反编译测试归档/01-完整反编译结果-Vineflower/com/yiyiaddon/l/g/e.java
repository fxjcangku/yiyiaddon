package com.yiyiaddon.l.g;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.textures.GpuTexture;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageInfo;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.types.Rect;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class e {
   private static final Logger v = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s1il1lsppeom34","Kpy0oILQTRPLVVWsO9Ev0MCPdgr5QZzDZFbQLlU5B18kuCj4domKjasGTOKa/UhPwraeMmIBfxkeavHYykwRabu2IXVekA==",1589429430333110296,2800695611274221565,-1667604776179047025,-899036909839443232>()
   );
   private static final float kc = 0.125F;
   private static final float kd = 0.625F;
   private static final int tQ = 65536;
   private static final int tR = 256;
   private static final Map<Identifier, Image> br = new HashMap<>();
   private static final Set<Identifier> aZ = new HashSet<>();

   private e() {
   }

   public static boolean a(Canvas var0, PlayerSkin var1, float var2, float var3, float var4) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1cun5gperz8bb","dgeQdfwsXc/j0QJ2nxWZLkMBkQy7O9Hsk7Pv0MXYZMk=",-3497379086144844653,-7166981358091615594,6426725822452909551,2136766848581641350>()) {
            case -1414749454:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s393fplmv9eg70","/+XLBGJztzWCMnbMm0W3p2no/zlY2NZThtOeijTqnpo=",8582662499908538065,1628002654734201166,3434863835297998291,-8185238327320098128>()) {
                     case -549206126:
                        if (!(var4 <= 0.0F)) {
                           Image var5 = a(var1);
                           if (var5 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2a4pbkulgowp8","B1fYj8+ad/GeyGEIvekl12MX7IrMozv/TlSbe6+8bA8=",-1017796588821749156,-7272770420056122179,-423897911518947015,4377664191192321982>()) {
                                 case 1472600730:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           float var6 = var5.getWidth();
                           float var7 = var5.getHeight();
                           float var8 = Math.min(var6, var7) * 0.125F;
                           if (var8 < 1.0F) {
                              switch ((int)com.yiyiaddon.m.b.a<"sgssffz8rkivx","TTTcPHHvDAFF1EgMnGboBpT/vH2ycV9I2pBrNhqWR+g=",371555261030510048,4352120461563587966,-6599754443728749499,-9015709624113802788>()) {
                                 case 2009538674:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           var0.drawImageRect(
                              var5,
                              Rect.makeXYWH(var6 * 0.125F, var7 * 0.125F, var8, var8),
                              Rect.makeXYWH(var2, var3, var4, var4),
                              SamplingMode.DEFAULT,
                              null,
                              true
                           );
                           var0.drawImageRect(
                              var5,
                              Rect.makeXYWH(var6 * 0.625F, var7 * 0.125F, var8, var8),
                              Rect.makeXYWH(var2, var3, var4, var4),
                              SamplingMode.DEFAULT,
                              null,
                              true
                           );
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s28xfpddqztkls","nYx8f76FWIwTZ3sMpPypZVc/MHnCCUEGiIzVYv77+Iw=",-6766663904312369553,168292797879249780,1910419666436792100,6785658872777434050>()) {
                           case -912008090:
                              return false;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   public static void b() {
      Iterator var0 = br.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1vunqp8jknbrt","KQH0WEZftYFanF5diXlzy9mccnoTx4s2OnoBt6gHJ7o=",5645197795550314891,5672186995552945164,-1280162507655237640,-3379614537021009586>()) {
         case 1174891156:
            while (var0.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"scdsg8dafmt2k","NDhUZfKrdwpOBlnMIsfQbnVuIqmoB3BAkF3e4+Nss1w=",1902382283619044930,-6927778315188435347,-804490656804970350,-2937996361513915850>()) {
                  case -825411163:
                     Image var1 = (Image)var0.next();
                     var1.close();
                     switch ((int)com.yiyiaddon.m.b.a<"s171i5tm8ef5nj","tYDTYoIfyBzVe8NUucGDPo4FBTBG39lr0eMo3hgitr4=",-9110653893637680626,2270541568456481299,3931205998877565511,8333263852355975858>()) {
                        case -383363040:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            br.clear();
            aZ.clear();
            return;
         default:
            throw null;
      }
   }

   private static Image a(PlayerSkin var0) {
      Identifier var1 = var0.body().texturePath();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n6uf8h8qe5h","HS0J/UEvDy5249kVRUJZE70B6ylc5DMFRqQGBvgWtnE=",1032957137880039733,-4208708173038930925,-2493586934761278089,4197908198114293688>()) {
            case -1336696004:
               return null;
            default:
               throw null;
         }
      } else {
         Image var2 = br.get(var1);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2zqwtpy3y6ab2","v7D07iZB83jaaS06urL+NeroskSR0FTAbIzz9BvPX68=",-1823472483843040753,-4673365453436772533,7595527114147344561,-7339533795900582137>()) {
               case -900291103:
                  return var2;
               default:
                  throw null;
            }
         } else if (aZ.contains(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s1bssxv2ckhtpg","qiH9+CHh8WQBcw26Gut7niF46Z7D02JWEhUtvdT18rc=",8197778743725445840,9038784902972038025,-5431502756008732431,-3990164876397533248>()) {
               case 1473043126:
                  return null;
               default:
                  throw null;
            }
         } else {
            Image var3 = a(var1);
            if (var3 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2to5gwg9vs1l0","dJ9kclEfRATS/diAiS8mN80Fej/oqDYbGJ1C7W48qBs=",-1505304260345520184,5774594724656007942,6780070498866875944,-3690709033117728146>()) {
                  case -258793111:
                     aZ.add(var1);
                     return null;
                  default:
                     throw null;
               }
            } else {
               if (br.size() >= 256) {
                  label33:
                  switch ((int)com.yiyiaddon.m.b.a<"s3nvf7m3uau5kh","jHAfmMgi7XDv+2Zy/sv0epGa7iJXHdCq+Pv9PiR0tR4=",3094764746340868301,7046365615637933612,3725084711900603309,-6883477870916602373>()) {
                     case -90178874:
                        b();
                        switch ((int)com.yiyiaddon.m.b.a<"s1y02ph4wromtx","8pn8FNWcmGGJ8KSMAPBmuzzQUe+Xu9L/z4E59Pc1uL0=",-7519059660112868981,-8471900980477039446,706953667495463291,-8475675765352855740>()) {
                           case 1962144137:
                              break label33;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               br.put(var1, var3);
               return var3;
            }
         }
      }
   }

   private static Image a(Identifier var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         return null;
      }

      AbstractTexture var2;
      try {
         var2 = var1.getTextureManager().getTexture(var0);
      } catch (RuntimeException var10) {
         return null;
      }

      if (var2 == null) {
         return null;
      }

      GpuTexture var3 = var2.getTexture();
      if (var3 instanceof GlTexture var4) {
         int var5 = var3.getWidth(0);
         int var6 = var3.getHeight(0);
         if (var5 > 0 && var6 > 0 && var5 * var6 <= 65536) {
            byte[] var7 = a(var4.glId(), var5, var6);
            if (var7 == null) {
               return null;
            }

            try {
               return Image.makeRasterFromBytes(new ImageInfo(var5, var6, ColorType.RGBA_8888, ColorAlphaType.PREMUL, ColorSpace.getSRGB()), a(var7), var5 * 4);
            } catch (RuntimeException var9) {
               v.warn(
                  (String)com.yiyiaddon.m.b.a<"s3srxx7ssivuoi","+hoHvgsScakZa9s4KBzgqzOZXBJQS+w3L7wdRWxczrUZGessgLL+pgCM5B8M6lGfDXS3j429",1536014522721400172,-1987138373672312634,-756499215876600321,-7785996617161542217>(),
                  var0,
                  var9
               );
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static byte[] a(byte[] var0) {
      byte var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1qmvkatqnglac","9pT34TX9b5qmyVJrwBn+qv6Kz7AWGYxSyh9IGxUaiWc=",-1884735924279239352,7900432432699772654,-583478063350037748,3563572487997298458>()) {
         case 937696142:
            while (var1 < var0.length) {
               switch ((int)com.yiyiaddon.m.b.a<"s3lbxau9l8ull6","6oe7A7uUyvo6upSnwKyHyjuqAgK4f7YW8LrozIwaDX4=",8851939674609289974,-8214160624056895815,5980250211126333407,-5962334733345569223>()) {
                  case -159571510:
                     int var2 = var0[var1 + 3] & 255;
                     if (var2 == 255) {
                        label36:
                        switch ((int)com.yiyiaddon.m.b.a<"s3embs2vh68j22","U2GQhwC0t3FaFla7pf+3zxkFMQ8UmhpK78FfkAgcCiE=",-2315091033383672532,7049246457250055855,-8003918220285608143,8035955527611029874>()) {
                           case 1005326624:
                              switch ((int)com.yiyiaddon.m.b.a<"s25dlmzy1vzpcv","PY/QKGGqmztENI+mnpPe218BpZoLesLVmDwUSDqcpKg=",1768669619909504542,5520570786636052624,7898915546459971156,6761090604481177593>()) {
                                 case -344799713:
                                    break label36;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var2 == 0) {
                        label32:
                        switch ((int)com.yiyiaddon.m.b.a<"scie8bskkrkfj","g9zHnE9D1xVjkKRvNYLIK8g0V1NWGgPU7z38PtS/ilU=",317665497456492378,4355002613816172215,4346115304531063182,-7781792058383608014>()) {
                           case -2005886107:
                              var0[var1] = 0;
                              var0[var1 + 1] = 0;
                              var0[var1 + 2] = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"sqcli7ldfbmke","hpWaFZ/80/y9+WSVQiTtPOwZy/i68eeZpLXY3IpnEgg=",-309966317311749066,-6659539606510801030,-8239910247890628359,-2317507756493511679>()) {
                                 case 543030262:
                                    break label32;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var0[var1] = (byte)(((var0[var1] & 255) * var2 + 127) / 255);
                        var0[var1 + 1] = (byte)(((var0[var1 + 1] & 255) * var2 + 127) / 255);
                        var0[var1 + 2] = (byte)(((var0[var1 + 2] & 255) * var2 + 127) / 255);
                        switch ((int)com.yiyiaddon.m.b.a<"sy2y20l8iw3q0","kMHh2MnbsXa5PrBTyJUjKlch6V6McdLVWHSOONRYyoU=",3538921689165363904,507056114229034942,-8849946008753434143,380588399217475889>()) {
                           case 117605678:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var1 += 4;
                     switch ((int)com.yiyiaddon.m.b.a<"sd3tzpprm3lr4","wam4RReyEIFH0b3O9c62YUR1xbqPZMbPDDw7ws7DMMA=",5688679124144947460,8672379027794896943,680609837024102188,9061994272765583804>()) {
                        case 2106224960:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var0;
         default:
            throw null;
      }
   }

   private static byte[] a(int var0, int var1, int var2) {
      int[] var3 = new int[1];
      GL11.glGetIntegerv(32873, var3);

      try {
         GL11.glBindTexture(3553, var0);
         ByteBuffer var4 = BufferUtils.createByteBuffer(var1 * var2 * 4);
         GL11.glGetTexImage(3553, 0, 6408, 5121, var4);
         byte[] var5 = new byte[var1 * var2 * 4];
         var4.get(var5);
         return var5;
      } catch (RuntimeException var10) {
         return null;
      } finally {
         GL11.glBindTexture(3553, var3[0]);
      }
   }
}

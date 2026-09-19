package com.yiyiaddon.l.g;

import com.yiyiaddon.l.b.q;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class b {
   private static EditBox a;
   private static Screen d;
   private static volatile String FX;

   private b() {
   }

   public static void bv(String var0) {
      String var10000;
      label29: {
         if (var0 != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s3i9higp6eautx","aPcFXOndBQl7jWNAJpsE4neWcQSA6Ocy3/4UNkjwVP0=",-5171023541241693167,-7958540532599875858,-4630476178975000871,7044801470529991534>()) {
               case 1209646199:
                  if (!var0.isEmpty()) {
                     var10000 = var0;
                     switch ((int)com.yiyiaddon.m.b.a<"s39u2zmv8b17o5","l6L9E4YfIyM7XT7VDeab+t7b7hizbw6X4GgCUR1x3nQ=",-7487798011225013021,-2446991646121296517,-4967743100486438203,1147187293720758151>()) {
                        case -2068706635:
                           break label29;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2setf470aa0lw","2hDF2RRces9slKmi9DtfccEHKF/J8DVB7MQjJqJhKLc=",-5627114858035924073,-1786806626325688527,2809345849998747521,2512778656041521023>()) {
                     case -521019789:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = null;
         switch ((int)com.yiyiaddon.m.b.a<"ss4hqm5r95q2v","1d74REasl4t6+EgNp7/ehF3PO3DSz/yzcaB6euvFPNc=",-822067207034206701,-4703139826110092424,-7573193047786844066,3081414038513581792>()) {
            case -1514501863:
               break;
            default:
               throw null;
         }
      }

      FX = var10000;
   }

   public static void kf() {
      FX = null;
   }

   public static String gP() {
      return FX;
   }

   public static void a(Screen var0, float var1, float var2, float var3, float var4) {
      Minecraft var5 = Minecraft.getInstance();
      if (var5 != null) {
         label37:
         switch ((int)com.yiyiaddon.m.b.a<"s1jqece2oqpjhc","lWC56AXojGJ9bppc4lqGG9aHvQHM56sYC2/sTx3JRuY=",5291578771378293424,1669888976763837692,-465391171372762390,3859051445262825642>()) {
            case 2004959370:
               if (var0 instanceof i) {
                  if (d != var0) {
                     label30:
                     switch ((int)com.yiyiaddon.m.b.a<"s3vpjbzykyex2r","RPQsKQ79ss+4N6wfJSDmELwEZ1Ac+AXH2NzApnLuI5Y=",-1924068193986069017,-2621903148334458458,-2993573982210075941,-7304458625315997334>()) {
                        case -920270138:
                           kh();
                           d = var0;
                           switch ((int)com.yiyiaddon.m.b.a<"skr5qmjzz0uzp","3Xfe6yBzYKtJ6VXMPdJ6gDA2U7Uglm1qRFog1qjP170=",-4809958200627176639,1454850987372162801,-7250996872147658073,-8672306576224266425>()) {
                              case -75262736:
                                 break label30;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (a == null) {
                     label25:
                     switch ((int)com.yiyiaddon.m.b.a<"s2w7sfgo9y3w6b","6Tzpr2YtCtLeIfda9MSUiRPoDcDc9WzovfE+FV1M9KQ=",9101460765394848839,3224008496382161597,-4543172393342825486,9097311200209695670>()) {
                        case -2125046525:
                           a = new EditBox(
                              var5.font,
                              0,
                              0,
                              1,
                              1,
                              Component.literal(
                                 (String)com.yiyiaddon.m.b.a<"s1lm9xvu8vxyhu","fothGXUhUrU32BAq89bmPHFl/WaUQrTVIu7GAsACh+B8tuJXfzLOizFl5yjhWF+IazB8vaRf",4998303300988395295,-3629294100412210925,3567896432299297237,-8738413346323054797>()
                              )
                           );
                           a.setBordered(false);
                           a.setTextColor(0);
                           a.setValue(
                              (String)com.yiyiaddon.m.b.a<"s22h7pwad90rvs","1TLquSa9KZqqUt6XG3ps7PK643oV3CjOhNFWAw==",-4484726844992334020,7769850678303553464,1154789311873531307,-8234611412936963785>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s28xw8ji0h5nv8","CIZopMZJ5P/exp0iVreQ/2kle6tH0/ShP/tluZpk27s=",5377345884813320024,-4567926889201159961,2738392249781387247,4272095835371664733>()) {
                              case -875785522:
                                 break label25;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  a(var1, var2, var3, var4);
                  a.setFocused(true);
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2kp15w83m9dqy","md5PQdM6GCONJOs4Zt5ZJiEli4sHxw2NGKFB6+4QzAE=",8745746223920938369,-6121507884933013619,8696665305772535848,268723314735442460>()) {
                  case 1327566709:
                     break label37;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      kg();
   }

   public static void a(float var0, float var1, float var2, float var3) {
      if (a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33iggyl19cd4b","/1nyT2+kU2IwRmL4t7FaG79UUS3Nxm78alZMuowNuJ8=",-4649710577204678577,4860402942450385300,-7889179680660262817,8109204652781432046>()) {
            case -345394431:
               return;
            default:
               throw null;
         }
      } else {
         Screen var5 = d;
         if (var5 instanceof i) {
            label47:
            switch ((int)com.yiyiaddon.m.b.a<"s37o9txe59ts1x","FiOlIMoo9N4f4da1Ne7qhvqgDfAryDdlAOEdZXcY5qU=",9006368535742663576,1749227481352442438,-1978282681292267846,3107232635998140101>()) {
               case 400892551:
                  i var4 = (i)var5;
                  var4.a(a);
                  switch ((int)com.yiyiaddon.m.b.a<"s4p24p4ym5r0l","CRw+/5mlYzw1fb3oLfHRQhtLiaJMi7kLPsekkq9Q5qE=",-2590143684262976480,4811403977139523867,-6978978404504433233,-4347073461723931831>()) {
                     case 1671801007:
                        break label47;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         q var9 = q.a();
         if (var9 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1m4yq3c5sn8qt","KBPxfWRBf/s9oaXktqxzowwwR2tbSsRNDmlmXAI179o=",1379844057966965069,8688567939619134648,5271324078291527422,-7416768993288726154>()) {
               case 156804770:
                  return;
               default:
                  throw null;
            }
         } else {
            int var10 = Math.round(var9.i(var0));
            int var6 = Math.round(var9.j(var1));
            int var7 = Math.max(1, Math.round(var9.k(var2)));
            int var8 = Math.max(1, Math.round(var9.k(var3)));
            if (a.getX() == var10) {
               switch ((int)com.yiyiaddon.m.b.a<"s34bkeyf1h1h02","QeoH8d6mTRgyCMDC8VItNfEU1yMRIQ3qKUKYYzCuH7Y=",6220885748838302651,2321927096230780034,4748369015921125962,-6636090376494999606>()) {
                  case 846532137:
                     if (a.getY() == var6) {
                        switch ((int)com.yiyiaddon.m.b.a<"s368w5nqtuk6cx","H+X7wmu1oK1NOkBT0Xb/DzXJW1Y/fdciRhNFLYDwJnA=",-1323198945810786097,-5845298948584724718,333560304962002306,2755033287828615887>()) {
                           case 1850805602:
                              if (a.getWidth() == var7) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1lipxduil3c74","XqqsCEdEjhUJUtWBnoFWl7ZNmlQlv0xiYxi/4o1lUIU=",5611370177505898930,-6993468770199669804,3781155072447620143,1887695967879804374>()) {
                                    case -1144171683:
                                       if (a.getHeight() == var8) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2uqa54kyvtel4","byaSZZg6GFIZ9xCT0yK7bR2u34jBtm+halgKMWWrLPA=",-8193561731836350166,-8861117049546119898,38591690045889645,5485428607046122162>()) {
                                             case 281323972:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            a.setX(var10);
            a.setY(var6);
            a.setWidth(var7);
            a.setHeight(var8);
         }
      }
   }

   public static void kg() {
      kh();
   }

   public static void f() {
      kh();
   }

   private static void kh() {
      if (a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3j5e4od54u0mk","nP84rGxMe6cHuHJMxtAqJR06HKyFWAW9r6E5EgBwY3Y=",-9208319485509581475,4074645110336123909,-4342534551398895326,9078158099267956672>()) {
            case 836247247:
               a.setFocused(false);
               Screen var1 = d;
               if (var1 instanceof i) {
                  label16:
                  switch ((int)com.yiyiaddon.m.b.a<"s3teg4ju1vwsch","fGkjuuDVMn0XIqhWhRWDCkKLHdvnsc5rhVgdaerOHiI=",4308656641739019256,-2245135117980755689,-5076722466310386041,6171223133456037492>()) {
                     case 1838989201:
                        i var0 = (i)var1;
                        var0.b(a);
                        switch ((int)com.yiyiaddon.m.b.a<"s3n55fo5xqou9n","7dRPD7pmOzlTTfjMBfKXMGv2uZw3UV+Mhw1/gjLpwL0=",7656453043544014607,-5676575144590838639,-4969003431544395977,6295852862512606438>()) {
                           case -1914981132:
                              break label16;
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

      d = null;
      FX = null;
   }
}

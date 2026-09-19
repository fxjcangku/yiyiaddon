package com.yiyiaddon.e.o.b;

import com.yiyiaddon.d.a.g;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class c {
   private static final String wI = (String)com.yiyiaddon.m.b.a<"s31lfgv995c6e7","TlNh+UXjZnG+ZFRzjQ2D9EIQEildo6fp2sTyr+WdL5jnow/ADHAhCVoassnlOmmb+Qib4tCT",29957636146952234,3071510914175154425,-3825619287817208103,-2345505361814145374>();
   private static final Minecraft aj = Minecraft.getInstance();
   private static final long an = 2000L;
   private static final long ao = 10000L;
   private static final int ps = 20;
   private static final double aQ = 18.0;
   private static volatile String wJ = (String)com.yiyiaddon.m.b.a<"sid30caz6y0pv","bVqi/LJAxC8HRaNd6rJW7ELibQ5HEkc90wP5AiOpqOQ=",7402282808926185966,-7494001125545896617,8028283614090758006,2273574691659658532>();
   private static volatile String wK = (String)com.yiyiaddon.m.b.a<"sid30caz6y0pv","bVqi/LJAxC8HRaNd6rJW7ELibQ5HEkc90wP5AiOpqOQ=",7402282808926185966,-7494001125545896617,8028283614090758006,2273574691659658532>();
   private static volatile boolean eL = false;
   private static volatile long ap = 0L;
   private static volatile boolean eM = false;
   private static volatile double aR = 20.0;
   private static int pt = 0;
   private static volatile int pu = 0;
   private static volatile int pv = 0;
   private static volatile long aq = 0L;
   private static volatile long ar = 0L;
   private static volatile int pw = 0;
   private static volatile long as = 0L;
   private static final List<c.a> ce = new CopyOnWriteArrayList<>();
   private static boolean h;

   private c() {
   }

   public static void init() {
      if (h) {
         switch ((int)com.yiyiaddon.m.b.a<"s3r2gvromw0jkd","J8hCNkyTyGH89pl7i4kWSC7Opjymht2HAi4fcTsTtJ4=",-5329818218311517980,1723127419606776965,-8504725252168725210,-7525246258459495216>()) {
            case 1144188589:
               return;
            default:
               throw null;
         }
      } else {
         h = true;
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s31lfgv995c6e7","TlNh+UXjZnG+ZFRzjQ2D9EIQEildo6fp2sTyr+WdL5jnow/ADHAhCVoassnlOmmb+Qib4tCT",29957636146952234,3071510914175154425,-3825619287817208103,-2345505361814145374>(),
            com.yiyiaddon.d.a.c.TICK,
            c::q
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s31lfgv995c6e7","TlNh+UXjZnG+ZFRzjQ2D9EIQEildo6fp2sTyr+WdL5jnow/ADHAhCVoassnlOmmb+Qib4tCT",29957636146952234,3071510914175154425,-3825619287817208103,-2345505361814145374>(),
            com.yiyiaddon.d.a.c.JOIN_SERVER,
            var0 -> ii()
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s31lfgv995c6e7","TlNh+UXjZnG+ZFRzjQ2D9EIQEildo6fp2sTyr+WdL5jnow/ADHAhCVoassnlOmmb+Qib4tCT",29957636146952234,3071510914175154425,-3825619287817208103,-2345505361814145374>(),
            com.yiyiaddon.d.a.c.DISCONNECT,
            var0 -> ii()
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s31lfgv995c6e7","TlNh+UXjZnG+ZFRzjQ2D9EIQEildo6fp2sTyr+WdL5jnow/ADHAhCVoassnlOmmb+Qib4tCT",29957636146952234,3071510914175154425,-3825619287817208103,-2345505361814145374>(),
            com.yiyiaddon.d.a.c.SERVER_POSITION,
            c::p
         );
      }
   }

   public static void a(c.a var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1amtfdqzg4fkz","6JSt6i9cHEWcBbncZqA/qv0hf9jpAFyJ2URwlRlDO6Q=",-6899808613460844059,3989799363756086318,-9058165578806913682,2947176448082924551>()) {
            case 904481197:
               if (!ce.contains(var0)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2kmz63qkicg3h","PP1mBnnvFrDS6vntaYbb7l2VmRnR+PLCwgtzkkC6TnI=",5008617314407230230,4436210157010568207,8713422090432055509,-5812827885324262124>()) {
                     case 26210841:
                        ce.add(var0);
                        switch ((int)com.yiyiaddon.m.b.a<"s2r57za0v6248s","5oVrebpaL1gEID59DRFJfcCTp8IV8fv0kQA4yAnbkVA=",2001560661765371472,-4620661803210138953,-3067748947980002169,-6139468213258158753>()) {
                           case 295302344:
                              return;
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
   }

   public static void b(c.a var0) {
      ce.remove(var0);
   }

   public static String eT() {
      return wJ;
   }

   public static String eU() {
      return wK;
   }

   public static boolean eF() {
      return eL;
   }

   public static boolean eG() {
      return eL;
   }

   public static boolean eH() {
      if (ap == 0L) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n1qhq6vjjnxz","sDChMvhE4xzVx4IbROEHc5KoXMP6QDxjFo92BMwwM2w=",3972114576653769529,-2920617862306596047,2587702057163873727,-5568849067434020140>()) {
            case -1065940804:
               return false;
            default:
               throw null;
         }
      } else if (System.currentTimeMillis() >= ap) {
         switch ((int)com.yiyiaddon.m.b.a<"s1udfuvzfkwy2f","3hHAAiAoB+Ad0aCfUFYc0fYS8wktolUMUKE1UtnmNcA=",5224001086391580185,-3636299337703301671,-8799601899363672759,-8744049159378972758>()) {
            case 591903061:
               ap = 0L;
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   public static boolean eI() {
      return eM;
   }

   public static double g() {
      return aR;
   }

   public static int cU() {
      return pu;
   }

   public static long r() {
      return ar;
   }

   public static void a(long var0, String var2, String var3) {
      if (var0 != ar) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x6uy2d1ki4i3","R00q0Kq/vAgGQwNXVKb+snIrQbmwy4iGQioJNAP7v8k=",-7606796167663580266,2135804323827335299,6485232446439835020,8722573790987477475>()) {
            case 772538206:
               return;
            default:
               throw null;
         }
      } else {
         if (var2 != null) {
            label71:
            switch ((int)com.yiyiaddon.m.b.a<"s2kl2jv7o2vul2","ktiKgP9K3V/82IzKRfukabJlEYvK5nTLcvrLX4gbrV0=",5604740930008289294,-5220954834659477397,6473562803554647817,-4232051175932472672>()) {
               case -891305778:
                  wJ = var2;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ztkzuk3u9wnz","RhOWmVgqFdI8czx8N7MI3x2+fjLpOpIXtNNgIP2Xc5o=",-4817449828574745262,5291644525365125984,6938477835973333943,-7023654617923111092>()) {
                     case -1226458110:
                        break label71;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3s9kwl9gj29u1","8878rfsl5BK/03RXGyMZx45XNrlndMs8N/dazmNWlgk=",-1227886237135509863,-1675030963050382928,-2725684017685492824,-999816027494664973>()) {
               case -943631873:
                  return;
               default:
                  throw null;
            }
         } else {
            if ((String)com.yiyiaddon.m.b.a<"s2fdv7f91sil4q","aP9V1veMjdRx+nKpeCtSNs5qHYSNFLA4Sh1BHB9lyfnFlA==",2717495985320296718,4234099321970020379,8370854159451090543,-4120564358914646507>()
               .equals(var3)) {
               switch ((int)com.yiyiaddon.m.b.a<"s3qkux429aw1sn","6DG/SdWgHv7DSA/qgE4lBhE3GVcQC1fSZINdCc4GEQc=",-4945383267918052221,1330282276586084291,2966276542803183339,-8732890994934611791>()) {
                  case 403006938:
                     if (eL) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3m900ado67hu4","0DH90YwpcP0gFEDsNVt2gmLhWuyj3442aVTwrPOaglY=",4207654032084997599,221108589190402168,7452622637064170781,8690727100551404253>()) {
                           case -204332931:
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

            boolean var10000;
            if (!var3.equals(wK)) {
               label58:
               switch ((int)com.yiyiaddon.m.b.a<"s2ib3dyo1sob12","PMyOVCsVXpOER6Fnc5B60ztLqzTlC/ToolVxmh7LCP0=",-3116399197697684607,-6011718865084066805,-4011176502908336234,4057055451841914716>()) {
                  case 1399491643:
                     var10000 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s1s7d5yppv0hg5","SC98GSnLg+tPeLQwjM0NLoW5G/xRzFg2KqhfxTzYs1E=",-7904507183799366643,-5939095256036298670,4850555986919372888,756099691938878688>()) {
                        case 651570943:
                           break label58;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s3vq5g256cipq","Vdb32SkWgG00A63WnfiLzv1eOhFUuuNiWNz2v7hJuA0=",-5348289950382029349,-9100003438590836777,-2382552397092234960,-4136127198922483359>()) {
                  case 541794909:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var4 = var10000;
            wK = var3;
            eL = b.aw(var3);
            if (var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s28s1kb1hitsxh","sKlvCsCEkKcn9hsdK5Gs4zer+oF3VjtjUB/iXlRIW1w=",-3973567779628437272,8404947965772517795,-5439920773027644951,-718765557932086596>()) {
                  case 236421309:
                     if (!(String)com.yiyiaddon.m.b.a<"s2s1jh7d20o8a7","ARBWI339qJt+kAjUv6kTut4g6+r//kOLPzL/yu6fdqHFKw==",5006568019627042646,6201276130084065076,-3390491532599777766,-2485581955797539692>()
                        .equals(var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2iazhwza5ur1w","H8HtR/aFy3E/YwvHiHlz9KJEO6UEPCnvYodI66mcq+o=",1488448407832336479,7528084014203559777,-7132330638641285465,-119000670215893481>()) {
                           case 1072121142:
                              if (!(String)com.yiyiaddon.m.b.a<"s2fdv7f91sil4q","aP9V1veMjdRx+nKpeCtSNs5qHYSNFLA4Sh1BHB9lyfnFlA==",2717495985320296718,4234099321970020379,8370854159451090543,-4120564358914646507>()
                                 .equals(var3)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kvqmr8x0peo2","M7OXeKUj5bLUYe+YPmYpRbNU9OuEHT6d+v2ACLghhI4=",9161037291553472054,8772755307791934996,-1541338834412835727,-3969892235929678230>()) {
                                    case 1762653647:
                                       aK(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s24wi9hfscam3d","4QiH3h1ypewB4hdda0y9QLMKErOjZl6EdkPJznpeCpU=",7870687543853112202,5049188655308089583,2525061292575924830,1747072690193621029>()) {
                                          case 55138489:
                                             return;
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
                     break;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public static com.yiyiaddon.e.o.b.a.a a(com.yiyiaddon.e.o.b.a.b var0, boolean var1, int var2) {
      if (eH()) {
         switch ((int)com.yiyiaddon.m.b.a<"s4zkikq2wylml","GVFWDheVtSCTQgXQ20jCvH0XYbKoDHz1O+rffE5IW9s=",-7034512328022595803,-9153335357044636317,-4838079705832188970,1179144664601829987>()) {
            case -1733004049:
               return new com.yiyiaddon.e.o.b.a.a(var0, com.yiyiaddon.e.o.b.a.c.COOLDOWN);
            default:
               throw null;
         }
      } else {
         long var3 = System.currentTimeMillis();
         if (pw > 0) {
            switch ((int)com.yiyiaddon.m.b.a<"ssmjrixbobdwd","JfXZ9bOCBOio/bpflE8Bj/5aJWpQWwvAh7CE/qdxoU0=",8013179183244124372,3453030167467885797,7043648333137759063,121994290156761042>()) {
               case 97709193:
                  if (var3 - aq > 10000L) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3nxzozz6gh1ge","ovxIRD26ANkXRuF1PYbGZFDKo6Jt19AVT8OyKz6uFQY=",-7367390434522898049,5431903151618542255,-6112979381991053312,-6337904175548817791>()) {
                        case -1083257915:
                           if (var3 - as > 10000L) {
                              label96:
                              switch ((int)com.yiyiaddon.m.b.a<"s86z8rtxecc1o","JxUhbalPck2cIA8nBZHohZGjdNWcpnwuqKWKkLz1nPc=",7666694810587866901,7070394309662621460,-2632450784855810555,4439738155080935064>()) {
                                 case 215316601:
                                    pw--;
                                    as = var3;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2l17iqbhl2ef8","hn8oN74iBS9QVtVUvHm+c35cfF1dJHNFThLbn81PkvY=",7728993607547992632,-6073716578379485587,1694724889852860822,-6765507864520399319>()) {
                                       case -780358831:
                                          break label96;
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
                  break;
               default:
                  throw null;
            }
         }

         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1o7yoe1rgpjwq","iIYbTERYJhKL631Iyk97Z1VEwToqze6u4luydIqID2E=",-4568063492229380074,4344560345466153854,-862116323076285089,545462422275126817>()) {
               case -640857586:
                  if (pv >= var2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2eze7ifu8hae8","fcTLXFbcrf8tveo841qlypyO/bH01L4HEnOKmUR4fMs=",6038743456969241486,701842855859781355,-7951768880313157012,-5091707831502049862>()) {
                        case -143792039:
                           if (pw < 2) {
                              label87:
                              switch ((int)com.yiyiaddon.m.b.a<"szqxebdufobkm","3f/D9n+IZqCcDyGF6xDfAzmct0vuCSIf5gPPhQZxFQ0=",4019337245043336993,-6017095281626390493,-2109986438176987591,4035513180762601022>()) {
                                 case 290894370:
                                    pw++;
                                    as = var3;
                                    pv = 0;
                                    switch ((int)com.yiyiaddon.m.b.a<"s22n50a4wm4jsw","LKMDrjI4h354KZVaMM+K/iICLx8TjFqn7tdtYUVjC6c=",-4685455428919409853,3666738863632197082,5537789885658010274,7909257529083998711>()) {
                                       case 1986048218:
                                          break label87;
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
                  break;
               default:
                  throw null;
            }
         }

         com.yiyiaddon.e.o.b.a.b var5 = var0;
         int var6 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"spyab3l1b4nv8","nbW8+BBMMoOMCbwKYlMqRvprT5Ph3a13QCoSHQUyvxM=",-1234403742668579881,8438025259155836208,2429742261707813399,489767396974863972>()) {
            case 799197623:
               label128:
               while (var6 < pw) {
                  switch ((int)com.yiyiaddon.m.b.a<"s34c079srkcbtr","IwsmkELRsKIlljlf99tPBMPFjOqY2T6gcnCKVurjj5Q=",8435161543585443723,-926558400477150885,-1870837516461546918,-1984454023136579564>()) {
                     case -1392715478:
                        com.yiyiaddon.e.o.b.a.b var7 = a(var5);
                        if (var7 == var5) {
                           switch ((int)com.yiyiaddon.m.b.a<"s29v1l8ckwl9wf","fqXiGe3v8wOPRMOL64Q2ZOSZPXdIoQMexvQRPb1W13k=",4802831395031017053,-6097038417628501948,-8649925715136537911,2838123691506132934>()) {
                              case 136809392:
                                 switch ((int)com.yiyiaddon.m.b.a<"s5w6soay9shcr","Y+/cmL5yGQekoKrviPaPOX7mi1XLZ5Ah6NMyce0M9aU=",7721388004654629086,7851190627810620063,5860964248415380366,-6381568589963632763>()) {
                                    case 1740480757:
                                       break label128;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5 = var7;
                        var6++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2zcqzrl1daz1g","zrGeR0z+JgfLm185GBAgOfpUEDPHuHqE9AXroOG2NTw=",4943537428813058402,-7053390769837506307,4664785639466042604,-5726698482351298060>()) {
                           case -689685440:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var5 != var0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3d5yb26y1b34w","yGBO7nje45QX5+IqRDEnvkSObUXAa+woqc5lkBgjnCs=",-4018383650021745362,-8723616831565429598,-452411936978442780,1173485874341200740>()) {
                     case 1485406368:
                        return new com.yiyiaddon.e.o.b.a.a(var5, com.yiyiaddon.e.o.b.a.c.DEGRADED);
                     default:
                        throw null;
                  }
               } else {
                  if (var0 == com.yiyiaddon.e.o.b.a.b.PACKET_FLY) {
                     switch ((int)com.yiyiaddon.m.b.a<"snzqm1enf1o9q","jT90qKsPC00hOKt6L23hGpzk0m4WeaqQMnw7K1V/ZI0=",4200153391778059505,3837009940660834646,803716825825882896,-1022865616052448820>()) {
                        case -1939465903:
                           if (eL) {
                              switch ((int)com.yiyiaddon.m.b.a<"s35k1jthch9uab","CFzeMt41vsnqONbgMUFhaAs1lO+bpGrdhu97mCjIwLo=",-8993393849995654166,5467042600860523733,-2673059240078600873,-3668028033036048321>()) {
                                 case -1386622953:
                                    return new com.yiyiaddon.e.o.b.a.a(a(var0), com.yiyiaddon.e.o.b.a.c.HIGH_RISK_AC);
                                 default:
                                    throw null;
                              }
                           }

                           LocalPlayer var8 = aj.player;
                           if (var8 == null) {
                              return new com.yiyiaddon.e.o.b.a.a(a(var0), com.yiyiaddon.e.o.b.a.c.NO_FLY_ABILITY);
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s397w14gchyd70","5DKBEQgKXwIJBDxpgVxuEJyK8OwfUAHHnWsiWqaP4CA=",-8861292753700943689,-291659227159168047,-7442951351904367255,-5367790476989702496>()) {
                              case 703212147:
                                 if (!var8.getAbilities().flying) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3a7iz6smhwq8j","3/5C79YdrHEOUZqrAteg1WT/ncu8D7mpSJGfLSIpXVw=",684092881111494127,2047192524783874522,5896694462557493045,2125152200911674508>()) {
                                       case 119264714:
                                          if (!var8.getAbilities().mayfly) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3j6er6mo69v6y","WToNB9dghg8S7VsmIg7B1k/+TpxSNoefeGu+cwebXic=",-7507974112427416076,-4383449097268611142,7480046784478781506,-3940790702769545151>()) {
                                                case 1968448610:
                                                   return new com.yiyiaddon.e.o.b.a.a(a(var0), com.yiyiaddon.e.o.b.a.c.NO_FLY_ABILITY);
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return new com.yiyiaddon.e.o.b.a.a(var0, com.yiyiaddon.e.o.b.a.c.GRANTED);
                                       default:
                                          throw null;
                                    }
                                 }

                                 return new com.yiyiaddon.e.o.b.a.a(var0, com.yiyiaddon.e.o.b.a.c.GRANTED);
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return new com.yiyiaddon.e.o.b.a.a(var0, com.yiyiaddon.e.o.b.a.c.GRANTED);
               }
            default:
               throw null;
         }
      }
   }

   private static com.yiyiaddon.e.o.b.a.b a(com.yiyiaddon.e.o.b.a.b var0) {
      com.yiyiaddon.e.o.b.a.b var10000;
      label32:
      switch (var0) {
         case PACKET_FLY:
         case FIREWORK_BOOST:
            var10000 = com.yiyiaddon.e.o.b.a.b.SAFE_GLIDE;
            switch ((int)com.yiyiaddon.m.b.a<"s3cjceb893c8zt","nrXadAduhmRVLM3TvohiLtYiixyqigbXRI65TpLlqUA=",-2105079958386297225,-845655267335264189,7410365274928800350,-8466924677729723551>()) {
               case -1001820379:
                  break label32;
               default:
                  throw null;
            }
         case SAFE_GLIDE:
         case SEQUENCE_SCAFFOLD:
            var10000 = com.yiyiaddon.e.o.b.a.b.VANILLA_MIMIC;
            switch ((int)com.yiyiaddon.m.b.a<"s35rlx255jqt4u","MDCQARDvjF6UslyaSnkEtnAYTaND9tGYAAlWE8Eg3Oo=",2872043085739445013,-1848594249925304766,-848075981318459498,-1110427775147768326>()) {
               case 549147589:
                  break label32;
               default:
                  throw null;
            }
         case VANILLA_MIMIC:
            var10000 = com.yiyiaddon.e.o.b.a.b.VANILLA_MIMIC;
            switch ((int)com.yiyiaddon.m.b.a<"s3nu5i46yscg0x","uy6cnslau+KNj40+9ZTP13ebnlnypGgFHcJ9/Xpckkw=",-8088465970413775069,6602362360461321339,4324202761474203865,-4126372654116270691>()) {
               case -1967102461:
                  break label32;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }

      com.yiyiaddon.e.o.b.a.b var1 = var10000;
      if (var1 == com.yiyiaddon.e.o.b.a.b.SAFE_GLIDE) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gcnln24c4cr5","5xwAnQJzuM9D5nPF3SfKFeB6lr75zQx6BMC2fVMvvM0=",8682472013860327627,-3943464980886719794,5934376038576224361,7049710690297998872>()) {
            case -750531574:
               if (!eJ()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2094w152ry36e","xs0K1eabwJdEp1rCD94FSlqaZvmFl1H/oq73iMJ+T1U=",-6649571049288883790,-3168298916934623436,84125038542291289,4209801364950600245>()) {
                     case -786801010:
                        return com.yiyiaddon.e.o.b.a.b.VANILLA_MIMIC;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return var1;
   }

   private static boolean eJ() {
      LocalPlayer var0 = aj.player;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1n5evyok8jxsq","cd10OeJKx+rc9vPDdo8M4oIiSTf7cA6WGomrchpQrTI=",6540364201388925991,-2662800945223558198,-9084689264205435677,508138263369284337>()) {
            case -1335775051:
               return false;
            default:
               throw null;
         }
      } else {
         Inventory var1 = var0.getInventory();
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s207g9e4uowoji","bioRY5CzRPWpGTvhCnvvjXzpOitR3ensvjFqrLaRFmQ=",6289601668498261054,-4125030969470489802,1344192816015835452,-2566401984329552961>()) {
            case -2018830629:
               while (var2 < var1.getContainerSize()) {
                  switch ((int)com.yiyiaddon.m.b.a<"smoa607u78czp","maa9oXg60J5iZjY8/tBpIHYjYi7nbqWQpNi8wESQmJU=",2619421833133880646,7451742059624345292,-8219427914360737807,8960167319889490707>()) {
                     case 422995897:
                        ItemStack var3 = var1.getItem(var2);
                        if (var3.getItem() == Items.ELYTRA) {
                           switch ((int)com.yiyiaddon.m.b.a<"s396vq59avg2xt","jdEqWg478CUkstl8o7yrrM3kUJ3HcCxaYSm5n+KRhp8=",-7872919071340432841,-8642703264769413605,-6231743042304157578,2406640420977934084>()) {
                              case -387998196:
                                 if (!var3.nextDamageWillBreak()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2mygnip74w4kg","KHNj3G9ox6R7KHgR4wqnu7ydRI2kpVyMdjzcr1nSJbM=",6208787359748028359,3779427574028526440,7319729788666502889,190094386291030369>()) {
                                       case -492253270:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2q90fxuj6eisy","zfucN8X4o68XDXlZjaIDnyvnoMK4JA9sOFJu5TUtkDk=",8897986258211557243,6005188153590570052,1949802877282301108,-1178736240037000455>()) {
                           case -599549156:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   public static void f() {
      ii();
   }

   private static void ii() {
      ar++;
      wJ = (String)com.yiyiaddon.m.b.a<"sid30caz6y0pv","bVqi/LJAxC8HRaNd6rJW7ELibQ5HEkc90wP5AiOpqOQ=",7402282808926185966,-7494001125545896617,8028283614090758006,2273574691659658532>();
      wK = (String)com.yiyiaddon.m.b.a<"sid30caz6y0pv","bVqi/LJAxC8HRaNd6rJW7ELibQ5HEkc90wP5AiOpqOQ=",7402282808926185966,-7494001125545896617,8028283614090758006,2273574691659658532>();
      eL = false;
      ap = 0L;
      eM = false;
      aR = 20.0;
      pt = 0;
      pu = 0;
      pv = 0;
      aq = 0L;
      pw = 0;
      as = 0L;
   }

   private static void p(com.yiyiaddon.d.a.a var0) {
      g var1 = var0.a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3p0vtf10f3h0n","QM/AOnWQyLtOW/7/kLMxPEgoMGjXXMYG31fbXuvBltg=",161818628873980398,6191142293386602939,-791477717211400411,-4019864810855857861>()) {
            case 1611952603:
               return;
            default:
               throw null;
         }
      } else if (var1.f()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1nr8v6nqluubw","0ixMn9sTMKlNO+5l9n2WGMiFFJtU+dlJDRh6lMDn6y0=",-2247528758006314117,1451470851216877030,-3457877695000972554,-2538416134125819569>()) {
            case -1147442145:
               return;
            default:
               throw null;
         }
      } else if (aj.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sd1ggjxpyh8u2","nLMhKZtEIekjkOtwTXmrGwAALZ7N75gWr9X2IIqLFvA=",-5270285552469089980,-6925955411579767803,4241757670821463975,2700725525731579419>()) {
            case -203047547:
               return;
            default:
               throw null;
         }
      } else {
         double var2 = var1.a();
         if (var2 < 1.0E-4) {
            switch ((int)com.yiyiaddon.m.b.a<"s36jly467limn5","zZM/Gd3KGB2A+BbIGTlzbqqYNjBvekEg3lYNp/NEXbc=",3069081540446504343,-3383996675108230228,-7316289264391214015,-7912060595293981986>()) {
               case -369677116:
                  return;
               default:
                  throw null;
            }
         } else if (var2 > 10.0) {
            switch ((int)com.yiyiaddon.m.b.a<"s66fppz02yxq3","kB1hXgg23+pCqUvmHjry7GqnuVrxeG1CX2DmDp1k1C8=",-3677899725992515988,7122583786686901836,4956647115161476650,-5472956223893536383>()) {
               case -1174067619:
                  return;
               default:
                  throw null;
            }
         } else {
            long var4 = ar;
            b(var4);
         }
      }
   }

   private static void b(long var0) {
      if (var0 == ar) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dh3wxx40ukgu","Aj9c2DOmRTr11XIidPnYwCg6qs/VF6lb9UhLDpzZX+M=",-2841929534445895220,273886353865131963,-5961704725411862384,7074171368225817680>()) {
            case -20124954:
               if (aj.player != null) {
                  long var2 = System.currentTimeMillis();
                  ap = var2 + 2000L;
                  pu++;
                  if (var2 - aq > 10000L) {
                     label39:
                     switch ((int)com.yiyiaddon.m.b.a<"s1h4podqvm2z68","4qFV67lwSsrrELW+cVh/zyBto04edrj8Jcc0CrpAhbY=",4169312485232507938,1234531999994386039,-9172314217993111929,-5357509529401791387>()) {
                        case 1327544227:
                           pv = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s6of1umowa5qn","TBa9LLptXojbCZ0kVK/NGAONpnCU+CgCByHl+M0ESBM=",-7971755511070920042,-2725042947563724041,-7458908438142944236,9173626055297544096>()) {
                              case 551979525:
                                 break label39;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  aq = var2;
                  pv++;
                  if (pu == 3) {
                     label35:
                     switch ((int)com.yiyiaddon.m.b.a<"s24mf8amtdxw59","K7f4iPLs2mFOCaQz240GJ3Gun+W5Rv3X7NurpfMZZhY=",-5811197219747974598,-900439077423199674,4942042317521823184,583473535814781726>()) {
                        case 616865671:
                           if (!(String)com.yiyiaddon.m.b.a<"sid30caz6y0pv","bVqi/LJAxC8HRaNd6rJW7ELibQ5HEkc90wP5AiOpqOQ=",7402282808926185966,-7494001125545896617,8028283614090758006,2273574691659658532>()
                              .equals(wK)) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"sp2glpkysleu6","6JroLx20J6RinZTs6fLKEJH4/hPDCxLtbVqkL+oLH4k=",7466410061769343965,6383144984371584949,-3319306150696410965,9081472095075199043>()) {
                                 case 829204962:
                                    if (!(String)com.yiyiaddon.m.b.a<"s2fdv7f91sil4q","aP9V1veMjdRx+nKpeCtSNs5qHYSNFLA4Sh1BHB9lyfnFlA==",2717495985320296718,4234099321970020379,8370854159451090543,-4120564358914646507>()
                                       .equals(wK)) {
                                       break label35;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2qdh2rafwv697","cGS/qjHLNROIGkHWavdx/1fgDtAcm1623UtZUaYI2fs=",-3959961154671144280,-7495965175724296023,-5595884049778306667,8085065150590439383>()) {
                                       case 356872604:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           wK = (String)com.yiyiaddon.m.b.a<"s3o532aeav13ue","0vjbLCbfdmTQO4/4fbr4cKAlvwyYEnAQpaqnAiYTQPzyE67NbWhegtArN+ua1K6Cy3vkLjfdAW/vuJ3YVP37ZEc4FazSOA==",-8446519706250905967,-3635181441638585776,-6589322968052638572,6434774998605789033>();
                           eL = true;
                           aK(wK);
                           switch ((int)com.yiyiaddon.m.b.a<"s1rc7mrdfyl1bl","8cHr3IxQRpaB4JyWYXX+hA6qwX+3DvwdEtTWHzvYZVQ=",-3302512157382380965,-8399022996791917127,-8634011849530138906,3232402758717381208>()) {
                              case 186748950:
                                 break label35;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  ij();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s7ky1tptcudb5","frMEoFOGxrE4UWL0QjCkQaEoAVckmsiwt2DnMUCpuss=",-8971098237383399805,1483215561057210090,-3821582242043566998,140013925158578644>()) {
                     case -104910716:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static void q(com.yiyiaddon.d.a.a var0) {
      if (aj.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vticjp68kuy8","ei/qljn9Wf8hGPf56rIjF+5Ai97KZRT+DvRtFTH806Y=",-5041109990159365277,-7144922822872643360,6480977758580542234,4635227756911511422>()) {
            case 1746886107:
               return;
            default:
               throw null;
         }
      } else if (++pt < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s1b93pr4ba555a","xJ9vaN6Q5JPGwpRN0sd/keO2CS/pYenP4/aWS2rC12k=",-8716125159066162514,1269081521575423102,-7466109614756537808,335126146794831171>()) {
            case 1360925996:
               return;
            default:
               throw null;
         }
      } else {
         pt = 0;
         float var1 = com.yiyiaddon.d.g.a();
         if (var1 <= 0.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s3he5lgwp9j28","zhhp6cs3XUFsfhtLhwnU6Yjt3DlKoq9B4+u/bxx/D7k=",-5805576370928816822,4964111171220228414,934596726372049201,-4361044138624057472>()) {
               case -1970472756:
                  return;
               default:
                  throw null;
            }
         } else {
            aR = var1;
            boolean var10000;
            if (var1 < 18.0) {
               label30:
               switch ((int)com.yiyiaddon.m.b.a<"stguxm9e191oo","PDpQPQANDv7YsVcVQwXL3lPde6X8HNESnkP+K+zi/fw=",5911125164423100476,423627448490226656,7516590055053271921,-4676442702735379221>()) {
                  case 955095546:
                     var10000 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s3ny20j5c6rmgi","8FGLZ/zSy8ru3svj8+fFXn+xa0CuBGzXFs1eFdLzrqI=",-8085944377377425757,-6263231597177127515,-1791111709679614118,-8927661610733459725>()) {
                        case -1503480684:
                           break label30;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"sntz9rh9wvk7s","j4Qi/kxJNiXwVkzRAlvfNS1mYyzyMmUSaQIFXfo58Bg=",3430059009173131024,8302603906670038275,-7502613691691546440,-90908721509447619>()) {
                  case 1337586283:
                     break;
                  default:
                     throw null;
               }
            }

            eM = var10000;
         }
      }
   }

   private static void aK(String var0) {
      for (c.a var2 : ce) {
         try {
            var2.F(var0);
         } catch (Throwable var4) {
         }
      }
   }

   private static void ij() {
      for (c.a var1 : ce) {
         try {
            var1.hS();
         } catch (Throwable var3) {
         }
      }
   }

   public interface a {
      default void hS() {
      }

      default void F(String var1) {
      }
   }
}

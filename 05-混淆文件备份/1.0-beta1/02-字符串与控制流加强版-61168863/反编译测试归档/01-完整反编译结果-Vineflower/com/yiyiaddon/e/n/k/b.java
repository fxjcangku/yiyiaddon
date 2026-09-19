package com.yiyiaddon.e.n.k;

import com.yiyiaddon.e.n.s.c;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.l.g.a.l;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResult.Fail;
import net.minecraft.world.InteractionResult.Pass;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public final class b {
   private static final String tK = (String)com.yiyiaddon.m.b.a<"s2acm3wmmsd242","zADlxCenGc7M3hxIKdC74gRDgaB3lPw0eMvR3jfbB9Gi/HvzWVEIfwv5K2bRxmY7J/6IRHDASZ9EOA8Auq5IYK1wtHICqw==",7799406731722991806,-2635643434036750280,883817680011485394,3815342342799761071>();
   private static final float ca = 1.5F;
   private static final d z = new d(65380, 200);
   private static final d A = new d(51455, 40);
   private static final d B = new d(51455, 160);
   private static b b;
   private static boolean K;
   private final Minecraft aa = Minecraft.getInstance();
   private final com.yiyiaddon.e.n.b a;
   private boolean L;
   private String qO;
   private String tI;
   private BlockPos f;
   private BlockPos G;

   public b(com.yiyiaddon.e.n.b var1) {
      this.a = var1;
      b = this;
      if (!K) {
         K = true;
         AttackBlockCallback.EVENT
            .register(
               (var0, var1x, var2, var3, var4) -> {
                  b var5 = b;
                  if (var5 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2lvc07reiqlhq","try6lf+fjIUrb2zrkShGQc9ACbMPRQJ9i+QBfdIIvvI=",8352547250669336285,7594911308397062939,-6583713942312916106,9100000580939942902>()) {
                        case 1668184888:
                           if (var5.a(var0, var2)) {
                              if (var5.dx()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2rd1jlg5zoc6p","2WQRvbSi2VE2sDSBayWMZuy/9oI9yphmwL9FOsUMm7U=",548856981739869387,6596445759012451933,3897047272866989447,1951136273033719142>()) {
                                    case -1967593115:
                                       var5.t(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2085zx0shy9pb","JIH0qJBXFdmXLdVhh79evCYAjTa/YX8xnm1o2Wy80PM=",-6168496375102787817,7106979000728347525,1799983389230221318,672234699154596363>()) {
                                          case -33572995:
                                             return InteractionResult.FAIL;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return InteractionResult.FAIL;
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s1drxyo18vddg6","GDjVjzEQuB4bm07nKngm6K9j4l/bCk9Vk7SiHj1ZCGU=",875930353858737903,471052681569698575,-7024812935208093471,6761269381906496670>()) {
                                 case -2082990984:
                                    return InteractionResult.PASS;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     return InteractionResult.PASS;
                  }
               }
            );
         UseBlockCallback.EVENT
            .register(
               (var0, var1x, var2, var3) -> {
                  b var4 = b;
                  if (var4 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1cu1fccfkxep1","BzacCWJITfYHWWFFvkUPhGntVkdznYYrRuGtPwkdyo8=",7070123896123467464,4996506112669122140,4410248832313790219,33243283716925498>()) {
                        case -412470770:
                           if (var4.a(var0, var2)) {
                              if (var4.dx()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2022lb3da77ar","wn70bP+OgExe9M93B0WWPvTQ0vfj+65XdIlzajr7iSg=",437011790531292979,-6059751502638120829,-3208491348241957124,4547685329747537754>()) {
                                    case 1694176789:
                                       var4.b(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3o0zih98cg5kl","KoS2kMjs+jHa92F4A/yskkKhsygDn5K/Qs/SpMdUp7c=",-2144824001210226062,-3016844137483929316,2973925597605010995,-4780201335673023480>()) {
                                          case -246634215:
                                             return InteractionResult.FAIL;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return InteractionResult.FAIL;
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s203xn9gqym95j","aRvoGAJ6x7L0QLixvsqFKANjNKaZLueP+C6ZI0XE3uw=",-5926247903128733899,1263478192022234124,2948676325151498830,-6570032118906429272>()) {
                                 case -381458136:
                                    return InteractionResult.PASS;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     return InteractionResult.PASS;
                  }
               }
            );
         UseItemCallback.EVENT
            .register(
               (var0, var1x, var2) -> {
                  b var3 = b;
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sg11jd6tibm5s","GaBvbV1walz7Yv5fGNHK1ocOc75713SAPoZ5HKFF/Jg=",-669278842619031123,1216128610072180302,6680281561224898767,3390072682506575947>()) {
                        case -860999498:
                           if (var3.a(var0, var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1c21yfje6dy90","fuVuCCL1gYBYZXQZ8wMaQLyXNAuTA6zX4g8dllOIO4c=",-462535259551354388,-1408454226841302049,8314815599558427280,-4698846924985042331>()) {
                                 case 265278973:
                                    Fail var4 = InteractionResult.FAIL;
                                    switch ((int)com.yiyiaddon.m.b.a<"szh14ain3tobl","LdjD2Z5oRB/N/Wh/BkZS3QmoGoCKuIeoMbGiEboCXq0=",-42693471879363765,4322970083311797459,6272418412361413792,2649628273814460678>()) {
                                       case -1599010145:
                                          return var4;
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

                  Pass var10000 = InteractionResult.PASS;
                  switch ((int)com.yiyiaddon.m.b.a<"s1zlj3z5e9asgv","wUYQ7jvWoepOtMZKZ+rXe0CJNc+RoS4GZ8ugYFTYeYo=",-4734927067746720361,5941183751417068835,984885354403968208,8297086907795473730>()) {
                     case 1259593608:
                        return var10000;
                     default:
                        throw null;
                  }
               }
            );
         AttackEntityCallback.EVENT
            .register(
               (var0, var1x, var2, var3, var4) -> {
                  b var5 = b;
                  if (var5 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3331bke2j6yja","x9zfr/beUeWobnoIMhqRoW/884Gj3dDLl000whq59rE=",-8013875731675602340,-6892335193540732792,-7286014332588820179,5683451394051598925>()) {
                        case -794749200:
                           if (var5.a(var0, var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3hz7j29wkzjyw","hV5fLDBZUq+17JREhgmsYRpgnG8mYRn8IaKwaTW4jZQ=",6139548412962141016,9073525963660171102,7825447562896883351,-4970359167010686752>()) {
                                 case -526911604:
                                    Fail var6 = InteractionResult.FAIL;
                                    switch ((int)com.yiyiaddon.m.b.a<"sq7qpq0c3iqjv","DatCA1H8rJTY7pBQoK06VduS9DoyRbFOWnIiLOTtgQU=",-6432358339906410664,5304861958065126113,-6784629888838464361,5943670692638047978>()) {
                                       case -1569388513:
                                          return var6;
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

                  Pass var10000 = InteractionResult.PASS;
                  switch ((int)com.yiyiaddon.m.b.a<"s39g9692ne0i8x","q5effd4yIbLtJKmj0Qu4QfdzKqAMMK/E3us1wk/SJQA=",1758658058962145692,-2082922227742917872,8530874219602668075,-823303234140511741>()) {
                     case -1736668427:
                        return var10000;
                     default:
                        throw null;
                  }
               }
            );
         ClientPreAttackCallback.EVENT
            .register(
               (var0, var1x, var2) -> {
                  b var3 = b;
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s25x86402oq639","CgOieuSukJ66r4QRtbMQM26YmhOcbnUgRgvhPPaJZr8=",-658847183297945671,5633146186935810203,-2044114650762297068,-3300646383636261796>()) {
                        case 1735448053:
                           if (var3.a(var1x, InteractionHand.MAIN_HAND)) {
                              if (var3.dx()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s39by5iw54pz2u","eV4tj+xA++8lhiWXiQCmNBRhrY24r4gCfE+wihw1+rM=",2875723332971016564,-4998231823171995628,73397473841650792,-8223050819220739704>()) {
                                    case 2014755819:
                                       BlockPos var4 = var3.b();
                                       if (var4 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s31egqf6gtug9r","GjRMcZMdk6gUef19Vz1r87oGKDzoRdoRXqnXBbFVa1E=",6852252250223685747,8079415639805322718,-7353679275499906416,-2838159723563227913>()) {
                                             case -1695999925:
                                                var3.t(var4);
                                                switch ((int)com.yiyiaddon.m.b.a<"s1yid0pun13csb","D7jTgsb7V0i0OaseHDgS3yVFraasd2yGQVSrB2O77PQ=",-8898454713704980349,-1920707083456623038,-5735636405232697622,-9193589156616288522>()) {
                                                   case 46304506:
                                                      return true;
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

                              return true;
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"syz0hsnpwjanw","JTnM9LDDyZyzntUPhU2v+Vj7KA5w2di5bEp9aG40m0o=",7156088872626628055,8315765976892811600,-5742648771524975836,-6577222126499958325>()) {
                                 case -984438250:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     return false;
                  }
               }
            );
      }
   }

   public boolean isActive() {
      return this.L;
   }

   public String ef() {
      return this.eg();
   }

   public boolean dw() {
      if (this.f != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ngw70my9ophq","frzwANqmtGzgSir5DeGH4Frs2sZhn6Z+b/vv8g3wKis=",7166327351210381881,-6290442414402668186,1962759148327604475,-7697252116416919196>()) {
            case -263901235:
               switch ((int)com.yiyiaddon.m.b.a<"sclepfwc3bw2","+GcVBej4qCoh/5wvYUTaLKUmt9vQW5fiFbhanSCDhts=",3595534858303795376,9043058667333196161,8387640837713870386,6438795441005729795>()) {
                  case -1485283089:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1thmtxdubmvwx","nMs8AuoXlHlCfHQoN7RniBhRPiNi08iaH2kV+HtEOWc=",6041580105674706494,7960285687238319102,1788664262581784319,7443564387525112301>()) {
            case 1492585433:
               return false;
            default:
               throw null;
         }
      }
   }

   private String eg() {
      if (this.tI == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s24h8x5f4ysnc4","aYvXpUgGUHDJgS3xY9wPat4u0xGGB54cb+yALIaky7c=",5375769295323858611,9167676304379283653,2616799507225281877,-4944222831325077947>()) {
            case 805196127:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s3qps1xnb2dr3z","Tv8d7w3/QR+kD086P3u/YmMFavnQfJHnQOqBoYq4RKyOX/cH",-4115320181370620739,2519147398010217608,-8911772493068890236,-6360305305461411396>();
               switch ((int)com.yiyiaddon.m.b.a<"szh0n9jnfo1ga","L4IHxWgMvFqeDHZ5Q5ZmRtLSGlvlEFScV3O+O8d8i2k=",-2540414446654475963,-5085743829927077970,-7301155444214797650,2726788960907475068>()) {
                  case -987345919:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = this.tI;
         switch ((int)com.yiyiaddon.m.b.a<"s3ccpijc6x44ud","Oas03aGp5Xp260GhIf63tJuSUG+yhCY/zh3K24moCac=",3993939584808960277,3699440386939069876,8519403998146460277,4715907567272245457>()) {
            case -2067686762:
               return var1;
            default:
               throw null;
         }
      }
   }

   public String j(String var1, String var2) {
      this.L = true;
      this.qO = var1;
      this.tI = var2;
      this.f = null;
      this.G = null;
      l.a(
         (String)com.yiyiaddon.m.b.a<"s2acm3wmmsd242","zADlxCenGc7M3hxIKdC74gRDgaB3lPw0eMvR3jfbB9Gi/HvzWVEIfwv5K2bRxmY7J/6IRHDASZ9EOA8Auq5IYK1wtHICqw==",7799406731722991806,-2635643434036750280,883817680011485394,3815342342799761071>(),
         this::render
      );
      com.yiyiaddon.d.d var10000 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"s3l4qfem9xrejv","wSWQ3NiNg4NxgHeKIeeA2Vt9poy0rHb99/XZA2r+hAYCfzLNzXY=",-8944995853935291702,6757260945677126356,951131470815809867,1964007560164529079>(),
            this.eg() + ""
         )
         .b(
            (String)com.yiyiaddon.m.b.a<"s2nv6xd1bvgj6k","k79aABgaV3biYCMBUNMq3yYfuzkdBHrRzooXWJ9SeXXpZQ==",3079129415855258891,-1662837914835389589,2755190016376937122,6763732099886433699>(),
            this.a.cW()
         )
         .b(
            (String)com.yiyiaddon.m.b.a<"syhmoibpqvncz","vcV9qZflsFKKPSGDZgrLmV7S7tBFEoRu1cCQIZwdIQc=",1200339637273129849,-5942175225677946513,-2111235427882571420,-1599028016258333429>(),
            (String)com.yiyiaddon.m.b.a<"s1s27wpa8zfqtn","lPhpcjs9lJZadsUe9uWOHl/8LlvRWu4lsqyuzkHE0ck4oVIUYpWOQNgRtD69dXjmv7CBCAd9Ia1cH+MQ",5302917587599462508,1460775596508896782,2150813283732849267,3997605706897049614>()
         );
      com.yiyiaddon.d.d.a var10001 = com.yiyiaddon.d.d.a.SUCCESS;
      String var10002;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1tlacbrowhv2z","1fQ4lP57dBfGueOy45cNXOdYeblqfvv7LE9rJSeDzho=",-4857613892582371424,-5253836669128074076,-6832979779525809239,-4262969108306072472>()) {
            case -1775582200:
               var10002 = (String)com.yiyiaddon.m.b.a<"szkkegv9v1jt6","u2/m7eWwkgeZq8+GnUHDsRg3tvpfvzPdGA54qghNxORd1NEDOoGAGF9MZCDh3jxQOFQtGfThoDc=",448267209348172288,3198681832785372519,3645603546819741753,4297326886771699220>();
               switch ((int)com.yiyiaddon.m.b.a<"s2w3kq7ynoifgi","wypNMsRSpJcK7zBqmdNj2+nlcrE9jhvrbxqhACVjPLI=",-8020250290542137230,-1259695046620572668,4138796077690284845,6832176692487700024>()) {
                  case 1027240448:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = (String)com.yiyiaddon.m.b.a<"s34ybg1mbi0yig","lirt2EUymbxKnaLy1Kf0YVopTfkbTYr944Cv/GVG3iILl1n/6GQ=",7114801516758347215,-1312598839839050168,-3456698425720338288,-5422336870354331247>();
         switch ((int)com.yiyiaddon.m.b.a<"stxz9e27aiy2c","dBQE4aeDPiwiVYV5K5fYSwgBiu2nqZ7RObHXHU5UpRc=",3512688544238728180,92194878385387735,-3289215134213858065,7392816507898955090>()) {
            case -952022102:
               break;
            default:
               throw null;
         }
      }

      var10000.a(var10001, var10002).g();
      return null;
   }

   public void d(boolean var1) {
      boolean var2 = this.isActive();
      this.L = false;
      this.qO = null;
      this.tI = null;
      this.f = null;
      this.G = null;
      l.l(
         (String)com.yiyiaddon.m.b.a<"s2acm3wmmsd242","zADlxCenGc7M3hxIKdC74gRDgaB3lPw0eMvR3jfbB9Gi/HvzWVEIfwv5K2bRxmY7J/6IRHDASZ9EOA8Auq5IYK1wtHICqw==",7799406731722991806,-2635643434036750280,883817680011485394,3815342342799761071>()
      );
      if (!var1) {
         switch ((int)com.yiyiaddon.m.b.a<"sb4uoktd3zref","U4jALgmMexauL7o/A4HOf1FWS3639eZxKN4qtaXqvic=",-41193989055170327,-5415646499980443653,516579678478857596,-7732247976693062340>()) {
            case 910161447:
               if (var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"spv1eibwbdtqq","2YTuxt4tC4XTqwB7n48QTU24rdIgJOD6HXOrH8Ogmzc=",4865268858961399457,3053357022385958262,5705036313251498702,3064595507074811451>()) {
                     case 429683846:
                        com.yiyiaddon.d.d.a(
                              (String)com.yiyiaddon.m.b.a<"s3l4qfem9xrejv","wSWQ3NiNg4NxgHeKIeeA2Vt9poy0rHb99/XZA2r+hAYCfzLNzXY=",-8944995853935291702,6757260945677126356,951131470815809867,1964007560164529079>(),
                              (String)com.yiyiaddon.m.b.a<"ss9jx4383snde","7r37WdiwxLHc39/xwYYVKw3kbH0rhn83eWlZGv5yRsdZCZUYH82jaQe6",-7789994014407945207,-2050768271321158990,-9148630217157663437,-8285091831143291800>()
                           )
                           .a(
                              com.yiyiaddon.d.d.a.SUCCESS,
                              (String)com.yiyiaddon.m.b.a<"s2w2ingvo1tetx","V8BDQQnexYxOYoWTyvlGcOfwC7/jTutGdBT4aPuYLQsp7P1xrF6JJcwwPbQ0gDA7UHnpzoKsMxRhbQ==",6864318522688127587,-331100289116536411,6374494921229993055,-4688720902648360039>()
                           )
                           .g();
                        switch ((int)com.yiyiaddon.m.b.a<"s3pvev21u10bo8","eF0poQp3pZNDfpLCXpofM5s+Z/bwdBVMLyp3VxVmp/Y=",6271680010534801477,-5924742413750665766,6445173733789242462,1629582150414169054>()) {
                           case 472770433:
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

   private void t(BlockPos var1) {
      BlockPos var2 = com.yiyiaddon.e.n.m.a.d(var1);
      if (var2.equals(this.f)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rsrupur1o5gh","63Fdb47TIVALpKLEWiAa0ouuydPQngjdsVsb9dzELJg=",5053745279251148238,-8736388151430389601,4507847148173286083,-5620559358743432103>()) {
            case 723724989:
               return;
            default:
               throw null;
         }
      } else {
         this.f = var2.immutable();
         com.yiyiaddon.d.d.a(
               (String)com.yiyiaddon.m.b.a<"s3l4qfem9xrejv","wSWQ3NiNg4NxgHeKIeeA2Vt9poy0rHb99/XZA2r+hAYCfzLNzXY=",-8944995853935291702,6757260945677126356,951131470815809867,1964007560164529079>(),
               (String)com.yiyiaddon.m.b.a<"s4gnvcugfmfmf","NBYf5dyrJpH6P8TctCXrO/IsaYjjObIaPbYgDuvoReGtLYsE+mMML/dY",-4967315554513125348,-1764555986840549762,-8722391935960665357,6679492534654282739>()
            )
            .b(
               (String)com.yiyiaddon.m.b.a<"s9ewn5wwob4rj","KwRXImAZghVxUQ8vxO/QvQ90rSHuShWPkH7NRDI+6IFQNg==",8776836907830472736,-6729675056460700700,-7588755351061073972,-1622700903229006137>(),
               this.eg()
            )
            .a(var2.getX(), var2.getY(), var2.getZ())
            .a(com.yiyiaddon.d.d.a.SUCCESS, this.a.cX())
            .g();
      }
   }

   private void b(BlockHitResult var1) {
      BlockPos var2 = com.yiyiaddon.e.n.m.a.d(var1.getBlockPos());
      if (this.f == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dz3c107u8oln","KKGDyQN+ZBmi7+czL9Pb1VPSS6vCPvd9cMDT7mYyic8=",-3261700211910484571,-6219164556809337180,8140680637119778206,1485278224019456278>()) {
            case 318716732:
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"s3l4qfem9xrejv","wSWQ3NiNg4NxgHeKIeeA2Vt9poy0rHb99/XZA2r+hAYCfzLNzXY=",-8944995853935291702,6757260945677126356,951131470815809867,1964007560164529079>(),
                     (String)com.yiyiaddon.m.b.a<"s3cw02bm79homt","VxOylS0CZQ+FxXytomg2ddCL2cziPWqc/pMHNB+KwSeEwR64Uh3pcF7geis=",-7124350329271793932,-4697638187304128154,-6160122696901385201,3777045772002918547>()
                  )
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s2nv6xd1bvgj6k","k79aABgaV3biYCMBUNMq3yYfuzkdBHrRzooXWJ9SeXXpZQ==",3079129415855258891,-1662837914835389589,2755190016376937122,6763732099886433699>(),
                     this.a.cW()
                  )
                  .a(
                     com.yiyiaddon.d.d.a.FAILURE,
                     (String)com.yiyiaddon.m.b.a<"sszq5q5w1xc3y","q2QmDDF+qbAdFpMe2Qu0ZdFJYAh7M4dqGhxsLwR1S8oGLJws491pzaUpwIAw1A==",1635642522305963577,-2764748207686726296,-8719796987411122249,7069888660594148230>()
                  )
                  .g();
               return;
            default:
               throw null;
         }
      } else if (this.qO == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sa4ushk1n3dsc","gNZ9Tfz1yXlV+wsK5gLpnRDFYwQ3iYsigtQgiAqrjQE=",7650594929000486139,-1379952308033625254,-8634900660583434515,-5490209485264140110>()) {
            case 1767232028:
               this.G = var2.immutable();
               this.gI();
               return;
            default:
               throw null;
         }
      } else {
         this.a(this.qO, this.tI, var2);
      }
   }

   private void gI() {
      if (this.aa.screen != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hcoiwalw10m2","1lCeRQcyFpQtcNKsVu933W4yfRYe9218AJqB56au3J0=",8527602327437083356,-8105549622919714308,3901583452150098953,-7057368332556302421>()) {
            case -2010865824:
               return;
            default:
               throw null;
         }
      } else {
         this.aa.setScreen(new c(null, this.a, this.f, this.G, (var1, var2) -> this.a(var1, var2, this.G), () -> this.d(false)));
      }
   }

   private void a(String var1, String var2, BlockPos var3) {
      String var4 = this.a.a(var1, var2, this.f, var3);
      if (var4 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28qsaukg0y7wr","Np57FbUiqo3Nwo4qDHA7z1wwrlIwYX2HzR9qdyNpIVQ=",-7499822166647327915,-5898996312719701069,1473190030342549632,-4972956618540755350>()) {
            case 1313086709:
               this.G = null;
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"s3l4qfem9xrejv","wSWQ3NiNg4NxgHeKIeeA2Vt9poy0rHb99/XZA2r+hAYCfzLNzXY=",-8944995853935291702,6757260945677126356,951131470815809867,1964007560164529079>(),
                     (String)com.yiyiaddon.m.b.a<"s1a572mec7g1oy","rDsS44dx8783XV068Dqfy/7L+C1IFOzzDw589FVtTsuzTSkiNpwQaYSYZ2s=",8162767301013485133,-3418993201448949004,2380524466109040086,8977482549672478228>()
                  )
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s9ewn5wwob4rj","KwRXImAZghVxUQ8vxO/QvQ90rSHuShWPkH7NRDI+6IFQNg==",8776836907830472736,-6729675056460700700,-7588755351061073972,-1622700903229006137>(),
                     var2
                  )
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s1u5gz9ubjp5ib","Xm460MdLhiNinfSg8Kyo15k9VdL3n66asQN45tlyWSI=",7243258603907143668,3459981780775160990,2889725225587660632,5741524776999064406>(),
                     var4
                  )
                  .a(
                     com.yiyiaddon.d.d.a.FAILURE,
                     (String)com.yiyiaddon.m.b.a<"syn239das0iwi","tqTwd8NHkI65PI1MPfpPWeFAQGT4NMK7+GnvhzoYD9rP1A==",-5757838127386014672,4174250734904265794,-2171049806895373573,8369294978977617274>()
                  )
                  .g();
               return;
            default:
               throw null;
         }
      } else {
         this.d(true);
      }
   }

   private boolean a(Player var1, InteractionHand var2) {
      if (!this.isActive()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1eksrgv9gojtx","uMhYwdG34mHf34mIYNX8eP5x7pWJXlUhndFdYiRLTQw=",6779587372279362021,5990384811393852902,1404597138122327191,1321569769558131133>()) {
            case -806014715:
               return false;
            default:
               throw null;
         }
      } else if (this.aa.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s20xzfs0th1vn5","kNmNo3qmGUb+JjDllpfqGSMf1T15+e9tZLMLy1AfSWk=",8142156679286458799,-8251427563006332057,-7869877899911138136,-71434357847705955>()) {
            case -391048796:
               if (this.aa.level != null) {
                  if (var1 == this.aa.player) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1atg3x57ebrrg","cfnKiFjB7skWsBAPf7O5060QKtAwg9GRURvPaZCcLcQ=",-8659441643336862297,-3199934595814775750,2500686375721997082,-7098668011728726011>()) {
                        case 1105911130:
                           if (var2 == InteractionHand.MAIN_HAND) {
                              if (this.aa.screen == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s5wuuqe3gkgjl","/nG+OLCDpEvt3XbQ1GBjnVT6wqtTlFweLRnMJpG4xIY=",5981679992234902256,5146859389617193737,7273940011087195404,-6562655739761747218>()) {
                                    case 513102948:
                                       switch ((int)com.yiyiaddon.m.b.a<"sk2bk7no1rggr","p8GC5B1MYtHhZwFuo3qIvDQfyJM3iLNdW/EKR+Hn2AA=",-7440785540776679746,-6354188534570899588,-5542967457235568817,-3196882383837177022>()) {
                                          case -296318020:
                                             return true;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 switch ((int)com.yiyiaddon.m.b.a<"sd7mnmuylz7oj","jeFvFTPeY44BmnV12/+Hxe4Sfuu8D2ovZZQTb26W4pE=",9185491788972254566,-2969932492546998079,-6061451664613438493,3350211208868618362>()) {
                                    case 1373838932:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s129mnkiz2k5yn","rcSckGwi7WzT124UT/cPNTH/qPXinU72w0lYEaopiAk=",2429403051296580237,-540430470128473437,-6935967055887258577,2176936038639343279>()) {
                              case 1165078067:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ybin8nixycr1","puNZpjvLjuxPXXbeGDyJY1tZjwS+NURpJgb7qK0fZA4=",-4717842101989779216,-1508387019208385259,6369811985732190059,2742214975238613233>()) {
                     case -792775994:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private boolean dx() {
      return this.a.cO();
   }

   private void render(f var1) {
      if (this.isActive()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bygcfnvywsxr","XnyQiNH48eROmBKokXxyOM7bPyzsaKdA2jJCoBI4uwU=",-647414469291925804,2996691734971134111,1481655247730228505,6020065458792969234>()) {
            case 1238944832:
               if (this.f != null) {
                  if (this.aa.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"szajwc090kn6d","4cqRlwJsBelRB6M+2WHmoXOE0nTzbb/6tCJeCcn9DhQ=",8964344040103304238,4125331998607757518,4516019079495028421,4467721700044118911>()) {
                        case 317818335:
                           if (this.aa.level != null) {
                              BlockPos var2 = com.yiyiaddon.e.n.m.a.d(this.b());
                              if (var2 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1b4thcgpwc3d","wTalWF1sMMNbJKlSTXsbU2UcQtmxLxqfo1zkLEV1Qfc=",7379952052174841894,4064666654916002141,-3622085879504645028,7503666396147678434>()) {
                                    case -1064834486:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              var1.a(a(this.f, var2), A, B, j.Lines, 1.5F);
                              var1.a(this.f.getX(), this.f.getY(), this.f.getZ(), z, z, j.Lines, 1.5F);
                              int var3 = Math.abs(this.f.getX() - var2.getX()) + 1;
                              int var4 = Math.abs(this.f.getZ() - var2.getZ()) + 1;
                              var1.a(
                                 "" + var3 + var4 + this.eg(),
                                 (Math.min(this.f.getX(), var2.getX()) + Math.max(this.f.getX(), var2.getX())) / 2.0 + 0.5,
                                 Math.min(this.f.getY(), var2.getY()) + 1.6,
                                 (Math.min(this.f.getZ(), var2.getZ()) + Math.max(this.f.getZ(), var2.getZ())) / 2.0 + 0.5,
                                 this.a.bL(),
                                 B,
                                 1.0F,
                                 true
                              );
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2s4fsvuipgmw2","72dPbmenEvYa4fkDNdho/fhgiy1l1M3JkkgIxSAdTiQ=",6011916299445581360,2248958654458189319,6786647535401506624,-8036763741113139041>()) {
                              case 941370437:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1gtf6d2dw3zj3","SP29lyLX/hXBs3SI3FMlVozIoAZTPOL5mzuOPFUSZpk=",2728633499443266629,2206231392377668196,-8619400316465182897,-4571128350685200705>()) {
                     case 684498650:
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

   static AABB a(BlockPos var0, BlockPos var1) {
      int var2 = Math.min(var0.getX(), var1.getX());
      int var3 = Math.max(var0.getX(), var1.getX());
      int var4 = Math.min(var0.getY(), var1.getY());
      int var5 = Math.min(var0.getZ(), var1.getZ());
      int var6 = Math.max(var0.getZ(), var1.getZ());
      return new AABB(var2, var4, var5, var3 + 1.0, var4 + 1.0, var6 + 1.0);
   }

   private BlockPos b() {
      HitResult var1 = this.aa.hitResult;
      if (var1 instanceof BlockHitResult) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ougfk8ne4bkd","LqWdcpZPDp3Zfo8bYf/mvxe/uFxyBuRCsMQ1i3NwBQU=",225902560984046217,7157671334635164064,-8342428663690362996,-3215415351076043755>()) {
            case -1645196621:
               BlockHitResult var2 = (BlockHitResult)var1;
               if (var1.getType() == Type.BLOCK) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2figa6waxns2n","Yb+AIsE/xel2YeYjYOf69yrv+6V7omyRh1hIo98P/vo=",1999265909450619435,1866686440085267681,-294568912297909831,2280509346821959761>()) {
                     case 932307317:
                        BlockPos var10000 = var2.getBlockPos();
                        switch ((int)com.yiyiaddon.m.b.a<"sipcwlvlxrk74","8CEifoduka46KfOeh2tLSRXNo+mqZLXRzm4Qr+Ednec=",-7101332194123819226,-4891827915804727382,2070590130877949980,-1418248369540018637>()) {
                           case 895662267:
                              return var10000;
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

      switch ((int)com.yiyiaddon.m.b.a<"s63e8xnaf83nl","8ez8zGpsYaGkxxFFOTcnq3vMoQXzvvvB3oSufRzi15o=",-5108001195537901634,6627824668417623354,5092723862719929444,-5093244094854465564>()) {
         case 1146777751:
            return null;
         default:
            throw null;
      }
   }
}

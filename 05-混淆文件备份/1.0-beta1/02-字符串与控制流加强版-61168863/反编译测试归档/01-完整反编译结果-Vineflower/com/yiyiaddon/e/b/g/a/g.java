package com.yiyiaddon.e.b.g.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.j.k;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class g {
   private static final String bK = (String)com.yiyiaddon.m.b.a<"s1ydp322cactjl","tWjm/W3b2rCzX8y3nalTT9uZPrTUOjr5zqSaK7/jwMyNxTsm4obrJloKUFKo02IYlQL3pYYVb6OyQ/1KjOxaxFunupiJXceyZD4beRAEVxJGReRfFb4LmzTUAipC/lu9MKjgEKTAm9g+RwVEv0lwTnA73WBgQRew5nZA55igiy1ABEOFAhdNpsr2C+X3zA==",-6092650551470951456,-8238360952829398064,3358018776396840097,-6749656699306529657>();
   private static final String bL = (String)com.yiyiaddon.m.b.a<"sq77uo2rst129","lJBKtk5BPR4/fjF9y69IYmTJk0zWmlgbsrUkBp1YaQFq4ONNmt5zIsGZUbE9AZSioq5pGHX1XkCrrB8fEWGp81miXq96l7+tIx6wEWVVTtOnT0RaQD4xEhHQkDFDQMwxeSMc/caBzvA=",-3374950028788728513,-56618874378409919,-3368081188308809609,1359312108064340838>();
   private static final String bM = (String)com.yiyiaddon.m.b.a<"sc12e05y99vh4","Iy3q3O+s25Dbo3Vqule8ls+yGfd7VXMsWnWFKwFQ4x+l7XcxbCVUeIOBAhmKKCnB9ldYXou9Z9wP8nWZXhLDSxqS2bZ7yMEHLF24byYOyAU=",3035861801262119530,6047360335867461290,5857746499853905640,-3097999346718506561>();
   private static final String bN = (String)com.yiyiaddon.m.b.a<"s1f7yooer6rsmw","6nn7I6dCxOZtkaf7LMnmFLJmHe/nF5V789cQXS7pxSCXti/0MU8cKsWdKzl0t5Fhew5uM3NiatVxhUxmJHD49g==",-5373487861101920152,-8411637287381982847,-1054343805913365325,-4134483756227720397>();
   private static final List<String> l = List.of(com.yiyiaddon.g.a.g.TARGET_COUNT.m(), com.yiyiaddon.g.a.g.TARGET_EMPTY.m(), com.yiyiaddon.g.a.g.TAKE_ALL.m());
   private static final com.yiyiaddon.e.b.b.a j = new com.yiyiaddon.e.b.b.a();
   private final com.yiyiaddon.e.b.g.a i;
   private final com.yiyiaddon.e.b.a l;

   public g(com.yiyiaddon.e.b.g.a var1, com.yiyiaddon.e.b.a var2) {
      this.i = var1;
      this.l = var2;
   }

   public void d(i var1) {
      com.yiyiaddon.e.b.b.a var2 = this.l.a();
      if (this.x()) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"sz4lu3rqbukx6","ru0T0eBOF8MfxuiDCajciieswHY2kGjdwjV5r5Sl0Xs=",5522525387434603099,-1695325363153463025,8062623374635765337,6583206094715534475>()) {
            case -1688662517:
               var1.a(
                  this.a(
                     (String)com.yiyiaddon.m.b.a<"s2jsopbzxem382","rtPIKrvjXQ+hmM3DPUvpqOw9lTDQWqrKClfdqMoUlLbeRVq3",326188690328514113,1150139305041767723,4246462986035246709,3061245622840261704>()
                  )
               );
               var1.a(
                  new com.yiyiaddon.l.c.f.b(
                     this.i,
                     () -> (String)com.yiyiaddon.m.b.a<"s2jsopbzxem382","rtPIKrvjXQ+hmM3DPUvpqOw9lTDQWqrKClfdqMoUlLbeRVq3",326188690328514113,1150139305041767723,4246462986035246709,3061245622840261704>(),
                     (String)com.yiyiaddon.m.b.a<"sq77uo2rst129","lJBKtk5BPR4/fjF9y69IYmTJk0zWmlgbsrUkBp1YaQFq4ONNmt5zIsGZUbE9AZSioq5pGHX1XkCrrB8fEWGp81miXq96l7+tIx6wEWVVTtOnT0RaQD4xEhHQkDFDQMwxeSMc/caBzvA=",-3374950028788728513,-56618874378409919,-3368081188308809609,1359312108064340838>(),
                     null,
                     List.of(
                        new com.yiyiaddon.l.c.f.c(
                           new com.yiyiaddon.l.j.a(
                              (String)com.yiyiaddon.m.b.a<"sos7lwjmqg2mf","Sgz6CyV83ey5tgS5eGAVTBREhz1EZbZQX512QoiZVFLBngwy6cCijQ==",7238752187589810464,-5840909746643924687,7835056974498200646,-6910688655384489374>(),
                              this::al
                           )
                        )
                     )
                  )
               );
               var1.a(
                  new com.yiyiaddon.l.c.f.b(
                     this.i,
                     this::Z,
                     null,
                     null,
                     List.of(
                        new com.yiyiaddon.l.c.f.c(
                           new com.yiyiaddon.l.j.b(
                              (String)com.yiyiaddon.m.b.a<"s2p5r3218l3fi0","qqOdmtMn59XNFV0ZkckcfUL8YiWMzG6Y3mzBl4CK",-5814403940980185573,-1044979600160689828,1282765382722243307,7092314763413372741>(),
                              () -> {
                                 com.yiyiaddon.c.a.a.f();
                                 this.i.C();
                              }
                           ),
                           (String)com.yiyiaddon.m.b.a<"s3q7dsxhxcq9dj","rlKIEDb71ljIPuk9cl6fxSqTCgqPEm9Ufzwk9pzlOlBRfAeGYz3tndvkiBJ82lUd",-9093230966459587641,1633387597595675472,1477530860290894743,-2703250934672722278>()
                        )
                     )
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s271yz1etvh5hm","XeuKz+FXsG3DSeVX5X1dHzt5R0zX7l8rcjfnN+8ZWjg=",1479590761499688797,-2007039429814031269,8243517846714786913,-7252648379915479114>()) {
                  case 1348085071:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.a(
         this.a(
            (String)com.yiyiaddon.m.b.a<"s2gbid0ks7flda","oO3ePbj7IEhBCzusNriwQ41Mw6d0NkKvW4LKjkzMv88=",2765348098409376670,-4045096489120245777,-4309344874380457293,-7643267561546832809>()
         )
      );
      var1.a(
         new com.yiyiaddon.l.c.f.b(
            this.i,
            () -> (String)com.yiyiaddon.m.b.a<"s30w08aq43i045","du82wJM7ORv4jajsLyrrIZKAhxW1PoyaEvJsCvuGcAP+XY6r",7289876283958105247,6073931851970940436,623901420779640252,-1421989811407979772>(),
            (String)com.yiyiaddon.m.b.a<"s1ydp322cactjl","tWjm/W3b2rCzX8y3nalTT9uZPrTUOjr5zqSaK7/jwMyNxTsm4obrJloKUFKo02IYlQL3pYYVb6OyQ/1KjOxaxFunupiJXceyZD4beRAEVxJGReRfFb4LmzTUAipC/lu9MKjgEKTAm9g+RwVEv0lwTnA73WBgQRew5nZA55igiy1ABEOFAhdNpsr2C+X3zA==",-6092650551470951456,-8238360952829398064,3358018776396840097,-6749656699306529657>(),
            null,
            List.of(
               new com.yiyiaddon.l.c.f.c(new k(l, () -> var2.a.ordinal(), this.b())),
               com.yiyiaddon.l.c.f.b(
                  () -> {
                     var2.a = j.a;
                     this.l.L();
                     this.i.C();
                  },
                  (String)com.yiyiaddon.m.b.a<"s30w08aq43i045","du82wJM7ORv4jajsLyrrIZKAhxW1PoyaEvJsCvuGcAP+XY6r",7289876283958105247,6073931851970940436,623901420779640252,-1421989811407979772>()
               )
            )
         )
      );
      if (var2.a == com.yiyiaddon.g.a.g.TARGET_COUNT) {
         label18:
         switch ((int)com.yiyiaddon.m.b.a<"s2ly2tzvjksrfs","AYF/emhhw4aeiDKCcyprjdR8TqVRMFpHycBBCo6XRn0=",7578551208258778123,5003909526603785765,-2724748201082771813,8602834772959671490>()) {
            case -826048573:
               var1.a(
                  new com.yiyiaddon.l.c.f.b(
                     this.i,
                     () -> (String)com.yiyiaddon.m.b.a<"s346lze615yooi","RJVZEGFXlsHG/eymt3w8v1Hf7HL2KfiEJR8iL9A0CczRXiV15rIBaA==",9162758534018157903,-8429180634003438143,-4574633250167654121,-1607478445055378953>(),
                     (String)com.yiyiaddon.m.b.a<"sc12e05y99vh4","Iy3q3O+s25Dbo3Vqule8ls+yGfd7VXMsWnWFKwFQ4x+l7XcxbCVUeIOBAhmKKCnB9ldYXou9Z9wP8nWZXhLDSxqS2bZ7yMEHLF24byYOyAU=",3035861801262119530,6047360335867461290,5857746499853905640,-3097999346718506561>(),
                     null,
                     List.of(
                        new com.yiyiaddon.l.c.f.c(
                           new com.yiyiaddon.l.j.a(
                              (String)com.yiyiaddon.m.b.a<"s159a2mo3e2lfu","3NzAqNiGBYGAnBtD8sUPpvNo9L4KW4ehLhAybLsERKV6G8oQfMxU0ZA7lbM=",-6190135085961089061,-2307097902453886244,-7037405401459210348,4380116037691506338>(),
                              () -> this.b(new com.yiyiaddon.e.b.g.d(this.i, this.l))
                           )
                        )
                     )
                  )
               );
               var1.a(
                  new com.yiyiaddon.l.c.f.b(
                     this.i,
                     this::ac,
                     null,
                     null,
                     List.of(
                        new com.yiyiaddon.l.c.f.c(
                           new com.yiyiaddon.l.j.b(
                              (String)com.yiyiaddon.m.b.a<"s2p5r3218l3fi0","qqOdmtMn59XNFV0ZkckcfUL8YiWMzG6Y3mzBl4CK",-5814403940980185573,-1044979600160689828,1282765382722243307,7092314763413372741>(),
                              () -> {
                                 var2.ad();
                                 this.l.L();
                                 this.i.C();
                              }
                           ),
                           (String)com.yiyiaddon.m.b.a<"s20r6wxhq34enx","UQYBO4gqoniPBtvA61pubo6k7uO6M/vV0Q9Z0Ca7pH3nuNWASW4uIcL7Fus=",-3213854075633393624,-1807314474189277525,4868703477457817374,-3893514767900873501>()
                        )
                     )
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1tsx2rb8509z8","YPFLHPh4IWVu2KDF94MZD1QK9hpEEBDRWN2/YlQCZNw=",-3743789293575644683,-8397921435853758850,561241150149192703,4748044487024619444>()) {
                  case -654835335:
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.a(
         new com.yiyiaddon.l.c.f.b(
            this.i,
            () -> (String)com.yiyiaddon.m.b.a<"s2x9wvnrzt5rgq","+HV1ieCpwOTcaX4iF88xqtqF8xoODPX0DOCf174jJV91otO4",-9216551592328157703,5990031755132421546,8163852495315294488,-5079890993251203256>(),
            (String)com.yiyiaddon.m.b.a<"s1f7yooer6rsmw","6nn7I6dCxOZtkaf7LMnmFLJmHe/nF5V789cQXS7pxSCXti/0MU8cKsWdKzl0t5Fhew5uM3NiatVxhUxmJHD49g==",-5373487861101920152,-8411637287381982847,-1054343805913365325,-4134483756227720397>(),
            null,
            List.of(
               new com.yiyiaddon.l.c.f.c(this.a(1, Integer.MAX_VALUE, () -> var2.P, var1x -> var2.P = var1x)),
               com.yiyiaddon.l.c.f.b(
                  () -> {
                     var2.P = j.P;
                     this.l.L();
                     this.i.C();
                  },
                  (String)com.yiyiaddon.m.b.a<"s2x9wvnrzt5rgq","+HV1ieCpwOTcaX4iF88xqtqF8xoODPX0DOCf174jJV91otO4",-9216551592328157703,5990031755132421546,8163852495315294488,-5079890993251203256>()
               )
            )
         )
      );
   }

   private com.yiyiaddon.l.c.f.e a(String var1) {
      return new com.yiyiaddon.l.c.f.e(this.i, var1 + "", null, 24.0F, 12.0F);
   }

   private boolean x() {
      if (this.l.a().a != com.yiyiaddon.g.a.g.TAKE_ALL) {
         switch ((int)com.yiyiaddon.m.b.a<"s391szaf6y966","pNQCtB8WuVCFHGP8D/OU5hODuet5uu6cr5+nxAmVhlw=",7040500672013495078,6083426619729131811,-4864236274608728961,-7269640631594984744>()) {
            case -567264271:
               switch ((int)com.yiyiaddon.m.b.a<"s1j5g9ebl5rxk9","UjiV/7RouigH0uzN+G7ezC5GlLRdXepl29RWq71D200=",3428122709484822963,3850561861416670308,7249466271182186021,-2470563561668262993>()) {
                  case 1998474617:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1z8fbp8vw5fqz","QXYS/A/2Fo6x8Nm7GJtmG0WB0cliR0bOww94BsuFqYI=",4040726608242953705,3004919228299061871,-8817235511882431671,3664006998514318386>()) {
            case -536651054:
               return false;
            default:
               throw null;
         }
      }
   }

   private Consumer<Integer> b() {
      return var1 -> {
         com.yiyiaddon.g.a.g[] var2 = com.yiyiaddon.g.a.g.values();
         if (var1 >= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1xqhd1pdu146n","4AUXyMHSZwOQDJvR0Qxvo1aWVN/fZ+HvSLdDweWzZZ0=",-1183687272866147935,-5377832834530555062,-7535933552746632860,-6744742055770361136>()) {
               case -1663336250:
                  if (var1 < var2.length) {
                     this.l.a().a = var2[var1];
                     this.l.L();
                     this.i.C();
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3zhqyj9h4114","colNlVrJwNAjMTWzLO5/iR9sQQkb+7aGqxACA4/pL8c=",2032108135764762015,-3107509094132322210,-5633866069181648722,-8867087964563007079>()) {
                        case 23056184:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }
      };
   }

   private void al() {
      Minecraft var1 = this.i.a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s172upwq2p3e3b","XmjuKau7pTRwzGFIN7WSFVz5ilB3eE+v2ICBm6n0cDQ=",-7485295753717884665,-3656149797777673536,-3100598684705950754,3003614979731458067>()) {
            case -525906658:
               return;
            default:
               throw null;
         }
      } else {
         ArrayList var2 = new ArrayList();
         ArrayList var3 = new ArrayList();
         Iterator var4 = com.yiyiaddon.k.b.a.a().B().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s28845aswtg8fr","k/IRK0KYNO7tkfaB6AN8m04jlTX0dyBoGC2NULarZF4=",-200350749658057657,-9119059399064643249,-6835390553718390902,4678142243975238871>()) {
            case 1515758039:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3pi0c8iib42dg","r+7B/PCEKeNcNxLCxtWlnxx82A5roZEwK9/UaCxX9TI=",2830190208938822893,-3092222409309588350,-6892402526060505555,-6415111129774276025>()) {
                     case -1803374118:
                        com.yiyiaddon.g.c.d var5 = (com.yiyiaddon.g.c.d)var4.next();
                        if (var5.fg()) {
                           label48:
                           switch ((int)com.yiyiaddon.m.b.a<"s25xdywb85xx7m","2pygSfp1tchl2e0cg6uQ81IU8QrNFixKa11ACCnii6A=",4360612128291906566,8448544718253475415,4908192900035312439,-105432640524780640>()) {
                              case 1713919701:
                                 var2.add(new g.a(var5, true));
                                 switch ((int)com.yiyiaddon.m.b.a<"sn53wzxwdulir","W2aMezUQxlblunCGdy0gvIcLLgKUPpGTIr/O/7rqRQs=",7703452955586989024,3689717132244101865,-8988884103498989203,6771429990083094947>()) {
                                    case 1065782724:
                                       break label48;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var3.add(var5);
                           switch ((int)com.yiyiaddon.m.b.a<"s2bx5xsmg4hjq4","phu1h1I3Go2HRAFAEkRHWMJ6TPu0GITAlskzQkIZwxo=",7128681576868673367,-1121719944966375753,-8703388818163799052,7770664268462790888>()) {
                              case -444788295:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3fwjnv442yjuk","wsYw2ER/dCUbvd43cp+H9n0CbQ2VnLHGDU/XwCg7t6k=",8586081987135721464,4705924026214629853,-7053866229712609085,-3974304437352392074>()) {
                           case 1000064107:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var4 = var3.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s1v5mbxragjscu","Nkkc+igjA2RwuJ4Hwq3lhIDV/ldtsIGgdHMhBo10HPk=",-6997915598802416438,5220326445381743318,-1076556762018963174,-3236060611333203703>()) {
                  case 2144355102:
                     while (var4.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s32hbbcffcjaf7","Sou0P0DjxZukaemGh1Ya62nyIAd47y78Tf35oPxE1ok=",7340915305573118185,-1952468613584042729,-5433440986415176550,2298115137999025356>()) {
                           case -489380880:
                              com.yiyiaddon.g.c.d var7 = (com.yiyiaddon.g.c.d)var4.next();
                              var2.add(new g.a(var7, false));
                              switch ((int)com.yiyiaddon.m.b.a<"sakwyhucoftt1","cW2fBtL2oWFK1GkQRCaPFLoySYlFVaJ6TUugDIi2J8w=",2467858865329493971,-7896301760679550406,5384793250211086666,8691445272462970355>()) {
                                 case -390069466:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var1.setScreen(
                        new com.yiyiaddon.l.h.g(
                           (String)com.yiyiaddon.m.b.a<"s2jsopbzxem382","rtPIKrvjXQ+hmM3DPUvpqOw9lTDQWqrKClfdqMoUlLbeRVq3",326188690328514113,1150139305041767723,4246462986035246709,3061245622840261704>(),
                           var1.screen,
                           var2,
                           () -> new ArrayList<>(com.yiyiaddon.c.a.a.b()),
                           var0 -> com.yiyiaddon.c.a.a.b(var0, true),
                           var0 -> com.yiyiaddon.c.a.a.b(var0, false)
                        )
                     );
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void b(Screen var1) {
      if (this.i.a() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vk5ckdm647o6","aKqGHZ2VLQfifxgZMEpIA0MjfoJ7xohyLzJYKsx/Er8=",4682880787050847484,-789919854580356099,-4312805140522165972,2146562068139745541>()) {
            case 2051154436:
               if (var1 != null) {
                  this.i.a().setScreen(var1);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2b0tfw2ukqkdf","Wyurbugs9JhCIz18Budz+wOkogNShsOtKq14fjUfKx0=",-4172678512036608740,-3656157184482236724,-656741517096039201,-8201313736131730424>()) {
                     case -1843752591:
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

   private String Z() {
      int var1 = com.yiyiaddon.k.b.a.a().dH();
      int var2 = com.yiyiaddon.c.a.a.b().size();
      if (var2 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s336p4rn0b7et9","fm81N0nLfFq0i+TSrRjdJLfjIjt5uot4k/zI4rn8uOw=",850500218197435752,6315505781458318195,4239536330951928116,-2751357506280383614>()) {
            case 586738658:
               return var1 + "";
            default:
               throw null;
         }
      } else {
         return "" + var2 + var1;
      }
   }

   private String ac() {
      return "" + this.l.a().p() + com.yiyiaddon.c.a.a.b().size();
   }

   private com.yiyiaddon.l.j.i a(int var1, int var2, Supplier<Integer> var3, Consumer<Integer> var4) {
      return new com.yiyiaddon.l.j.i(
         var1,
         var2,
         1.0,
         (String)com.yiyiaddon.m.b.a<"s1gcc8d9jlxyz5","LXlbgPC5XWiZsTW7z/ia/LSxClPI5if5bhjFW4DLLNwtX1LP",9150240688633088565,3482583345731181118,2045862697989120817,1198717370893159359>(),
         () -> (double)((Integer)var3.get()).intValue(),
         var2x -> {
            var4.accept((int)Math.round(var2x));
            this.l.L();
         }
      );
   }

   private static final class a implements com.yiyiaddon.l.h.g.b {
      private final com.yiyiaddon.g.c.d b;
      private final boolean z;

      private a(com.yiyiaddon.g.c.d var1, boolean var2) {
         this.b = var1;
         this.z = var2;
      }

      @Override
      public String L() {
         return this.b.dF();
      }

      @Override
      public String D() {
         return this.b.m() + "";
      }

      @Override
      public String M() {
         if (this.z) {
            switch ((int)com.yiyiaddon.m.b.a<"s1kidsnn1ohham","UZhy+7BLEYkGaNi2y4NyKM2g7SpIeXm6imU+cnYdMEM=",1708834940136389383,3598335362553386632,1887310130311522841,-8115668423235200666>()) {
               case 394675616:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"sqox0nscr5thg","781vWftHzoV/8WtlNHYj/tEyLkGPAulJS1/z0ghJTB803OaSwNI9x3Geac11CQEQ",7095866709852438677,2702419221782596116,-5675976720556924381,-2261218808861443495>();
                  switch ((int)com.yiyiaddon.m.b.a<"s284vjuvqxtc8d","ZfODRHiz7X9Bst/TAbm7vAq8KZoOkqTyRfosu1KeSJ0=",-4377633712580334765,-8905846159172570845,18771999632262444,4331511911659940054>()) {
                     case 373723486:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s2sd20qfp8mtv6","TXQb77fsqpQo8jVp0z8OgrRR3exrzJVU+wLPnm1M6HCfAEg6QYIOkHKzHba+YG6DGvI=",-7951834356934531566,-4593407744393567012,8241961568475608844,2092757539902660115>();
            switch ((int)com.yiyiaddon.m.b.a<"s95uuo9fvb9k5","i2OcLfNgZAyLfvtKW6x12E3/y+b/Gh7khzUxnqbJMsY=",-5407284663907648959,8294711445923370396,-5340911275063175524,7795991092865776344>()) {
               case -2103680038:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         Item var5 = a(this.b.be());
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s7llakanpmfsd","ePBpi9TohtetwGWkJCjVYz6Zh1EKj3IfD9YLdBff264=",2445680147807565913,3969507210679962924,6247430118766913757,-6084634755196788814>()) {
               case -1421642450:
                  if (com.yiyiaddon.l.g.c.a().a(var1, var5.getDefaultInstance(), var2, var3, var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1gu8ahefgao21","RZtXzExO7+QXxv1lnK2a1rQAOfw3t+wKjH3lliPcRo0=",-8144979351440998920,-3638088250499121846,-7996804030487402905,8783888248234426081>()) {
                        case 155767949:
                           switch ((int)com.yiyiaddon.m.b.a<"sfvwqorxu8le8","9quWBMexlupBTjF7pSd4878hi/Ydto2XnJ3bOTmGkdc=",5175397987850099210,3451387616147156458,-8084455103411019047,5420824035445829960>()) {
                              case 1189401319:
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

         switch ((int)com.yiyiaddon.m.b.a<"s32fwjasug5am5","DCtUTdh4o37ouWG8qB7mD6931mOHolvzFqGHUzcwnVo=",492915656915300344,-2555379403358508331,-4389242281572427933,-6822951698803439021>()) {
            case -2069299252:
               return false;
            default:
               throw null;
         }
      }

      @Override
      public ItemStack a() {
         Item var1 = a(this.b.be());
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3bfkqls4vcgl8","nsNxOvmAHmXsV5wYscHI8jtxFboozEWI3D8Ldy62PAw=",-8063551510793371079,-7966867324345374069,9008256375694865132,-5680988612746673147>()) {
               case -855041887:
                  switch ((int)com.yiyiaddon.m.b.a<"sy7vw4597ttby","6ebKwRu8HpR5jdHTbrld5roFtRpgCidvHpvxIRvZJnY=",3218264560250562785,-5033442638679654103,9128305958595434291,5778797706725796323>()) {
                     case 937664475:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            ItemStack var10000 = var1.getDefaultInstance();
            switch ((int)com.yiyiaddon.m.b.a<"s1hzxh4yc9uhgq","VcRU03TMQij6MjLq22qrzlg1s9iHw7nk9xH7TI4qV40=",6191514247434283041,685744870039052500,-8658098008517945647,-3436773193088417965>()) {
               case 76735171:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      private static Item a(String var0) {
         Identifier var1 = Identifier.tryParse(var0);
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2kwaa7cjssf0h","7iqtRsi6b4etspn7BZtDWwpEAzq+2J8+vZteNspW4J8=",3335374155603933659,-3501177692813142731,-3577347755360217288,6095864659138469360>()) {
               case 1542755881:
                  return null;
               default:
                  throw null;
            }
         } else {
            Item var2 = BuiltInRegistries.ITEM.getValue(var1);
            if (var2 != null) {
               label27:
               switch ((int)com.yiyiaddon.m.b.a<"s3edlvkiudj9cl","LVgOjAPQGGrqHKpg6Kmgkt0a8iSf87ZpFp6/vXtCBTM=",-2600005243750595707,-558344525306070824,4003658885192051429,-3480232575946786517>()) {
                  case -2091294321:
                     if (var2 != Items.AIR) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2t4dzbn6vqyhq","g+5EgvLp0StL7+QtFUolxuxIAA0Q/B7GGm0lF1MdGJE=",-3396364628871329768,366152447427028507,-2060407533146952620,-3041454515010788363>()) {
                           case -356331737:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"suvd2q6pwdnre","4Hs6hnf19cQBIaNLehvt5CU826WM9TJ3gY9PnS4OkCk=",7580019137315599760,-3675676808147850534,8179461889805605095,5080270946050703880>()) {
                        case -440173096:
                           break label27;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s1tty2vc7fxphs","NOdRS8MKvMcOG3KQQu/jc5OK4Wvjq/8pkBHEizmLgTg=",-6372546265333810548,-8954066150254552608,-2560691956469847397,-4298809664335702878>()) {
               case -1136077341:
                  return null;
               default:
                  throw null;
            }
         }
      }
   }
}

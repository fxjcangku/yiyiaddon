package com.yiyiaddon.e.h.e;

import com.yiyiaddon.l.b.n;
import com.yiyiaddon.l.b.o;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.j.m;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class a extends com.yiyiaddon.l.h.f {
   public static final String kQ = "添加物品";
   private static final String kR = (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>();
   private static final float aM = 420.0F;
   private static final int eK = 128;
   private String kS = (String)com.yiyiaddon.m.b.a<"s1dob00ktst428","7WYomPoNszGqHLXjRCs1yDG3FU50HDjM/dElpw==",898605294817565867,4841214698379084895,-189754033092623828,8426402056646762093>();
   private String kT = (String)com.yiyiaddon.m.b.a<"s1dob00ktst428","7WYomPoNszGqHLXjRCs1yDG3FU50HDjM/dElpw==",898605294817565867,4841214698379084895,-189754033092623828,8426402056646762093>();
   private String kU = (String)com.yiyiaddon.m.b.a<"s1dob00ktst428","7WYomPoNszGqHLXjRCs1yDG3FU50HDjM/dElpw==",898605294817565867,4841214698379084895,-189754033092623828,8426402056646762093>();
   private String kV = (String)com.yiyiaddon.m.b.a<"s1dob00ktst428","7WYomPoNszGqHLXjRCs1yDG3FU50HDjM/dElpw==",898605294817565867,4841214698379084895,-189754033092623828,8426402056646762093>();
   private final o a = new o();

   public a(Screen var1) {
      super(
         (String)com.yiyiaddon.m.b.a<"s264mjki4n1eia","EXb3SSZ4l9Fm3FLYBjDW/7aIpK57WlkKsdnXzpbZRGipnKVl",-4859721230221686122,-3701895273457130024,-3392081706592212642,5636910182802289500>(),
         var1
      );
      this.kQ();
      this.dm();
   }

   private void dm() {
      this.d()
         .a(
            new w(
               (String)com.yiyiaddon.m.b.a<"s1sfywa7hwtrxe","MeowHB4I0yOtq60CG4qYh20dmXMWt2/OQPlbH+6QfYxuh6E8L2KgA70Cs5pEdAqJ3K7XyMfTpSuj8q0+9tnKC49jEyem4O9UBWKaWaAwA4hd+Yk6urwdJB4L9PLhkdDiYahf8b5m9u5SSwce+LJHuHcMWxdNrGI+/v+zNpNrewyC43TwCiY=",-6887986381946250192,-3824998829926909363,-8124727327760762131,7892468801177465751>()
            )
         );
      this.d().a(this.a(() -> this.kS, var1 -> this.kS = var1));
      this.d()
         .a(
            new w(
               (String)com.yiyiaddon.m.b.a<"s1sx6c2ysx0jn5","EdqW0vgD8hBE9JMaQ/vEyK2/Ierxl/uAbK3A7ulgARx50FLVRSOs+bN3ql8e8+Rz6mp5iPnhsG4y9Ln3aafTCJKDuDNr+deM03QPraUenZi+1iKeD026jfTiXU7xSOOQ",-7063035761188776124,3602348711072385010,-8969758221464732465,-4763947771852195358>()
            )
         );
      this.d().a(this.a(() -> this.kT, var1 -> this.kT = var1));
      this.d()
         .a(
            new w(
               (String)com.yiyiaddon.m.b.a<"s3b21avyj7erop","B0qGLrq4ulXMl3c3DLednnRL3Es7u4B/Oe0wiBcC4WPA3hRDrWUGOeh3Ikkzt+zuAM7X6vyc09EVKbD6/lEDi/qo6r7oqEguDnwDCWxkBOT+ZMJRargWy3BaTDxxX+HdT85z9VIcCIh385dcKE+zD4ugEWvpicz0KL199j8ij9cfFuExDHhP4CQl",1589699088686772727,-7348269880259325823,-3108922761392981831,-5948998453995405798>()
            )
         );
      this.d().a(this.a(() -> this.kU, var1 -> this.kU = var1));
      this.d()
         .a(
            new w(
               (String)com.yiyiaddon.m.b.a<"s3d8aqb8b63vz6","7m6cjmg4h+VBKulO5XESUsEWToRDJgNGwceYWMkluiGpLxPJQyuluUnZVVJwzlK5LvHLZPB5FZz19cbU/xt5GVM6I2La+68lVb/rBQvGHjSEjq2KVR31GAUm+l+rxLw1HdKKARsacbb0yVxc",-2697520911065670606,8239533006567160809,5748008409118581934,6305475595530102359>()
            )
         );
      this.d().a(this.a(() -> this.kV, var1 -> this.kV = var1));
      this.kN();
      this.a(
         new com.yiyiaddon.l.j.a(
            (String)com.yiyiaddon.m.b.a<"s3iw25i4wf7y2w","3CI4Equwaa9ZwTgmG5QnZn/Bi5Grij1ZUV5AF96mml2YI4u4MTbHxP+m",-2639343535473145382,800692889946159283,286292805455704977,6933778673977876119>(),
            this::dn
         ),
         new com.yiyiaddon.l.j.a(
            (String)com.yiyiaddon.m.b.a<"s2ltp0l3nxk52q","f9VI2EQbnRU3lmGtRytibl5X7B49kAhCjHBdyBYB22NiggD6",-5578060413975456912,3551355314132818163,-1475238807303945558,7934905245434354822>(),
            () -> this.kR()
         )
      );
      this.kN();
      this.d().a(this.a);
   }

   private com.yiyiaddon.l.b.h a(Supplier<String> var1, Consumer<String> var2) {
      return new com.yiyiaddon.l.b.h(
         (String)com.yiyiaddon.m.b.a<"s1dob00ktst428","7WYomPoNszGqHLXjRCs1yDG3FU50HDjM/dElpw==",898605294817565867,4841214698379084895,-189754033092623828,8426402056646762093>(),
         new m(var1, var2, 128).a(420.0F)
      );
   }

   private void dn() {
      String var1 = this.kS.trim();
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uykd6f3jdvao","SYUAxxUxxxWOM+CHTVU5ZLTMeXYKU79KEKsYAPXzS78=",-5112465995340835617,7433466101907542420,7330572356570267013,-5806135410563653794>()) {
            case 766758873:
               this.x(
                  (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>(),
                  (String)com.yiyiaddon.m.b.a<"s26j6r7woe66jp","q93QkoeSiHxYXyhD9OcEonpx4t79bYAnAxNMRf55HJiljtbY+C52/EygrYUeg0qSWJx7+Q2JnAS0YVU7Gn+NGw==",-8261771803981592106,8896539771929827118,-670805518300839563,4345768492241478846>()
               );
               return;
            default:
               throw null;
         }
      } else {
         Item var2 = b(var1);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sci7rw5fqtfjo","8GjBkeAEXrpMLGKIHKJrmTHanU8bDn+9ll8USm5ekYk=",9100974375472974697,-4939905010534882867,-7992964677813255489,-5134251496471304716>()) {
               case 1175467341:
                  this.a(var2);
                  return;
               default:
                  throw null;
            }
         } else {
            Iterator var3 = com.yiyiaddon.k.b.a.a().B().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s3g2erqbmehpol","xP+OXo1KC2EE3ibs3WECM3Oi021cjQ5WcNMw4UF66ho=",-4609572735112329361,-4741142833062848060,2535923382982048051,5849068828008324650>()) {
               case -1411964178:
                  while (var3.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1bl3hpa9fuif9","4US1lTDwD7exWYV15gB/Hhk/gHYQsvyBrMXKy4xUBz0=",-45395732757426857,1281580748351344505,5070138920270740054,-1620912326634240600>()) {
                        case 1034555416:
                           com.yiyiaddon.g.c.d var4 = (com.yiyiaddon.g.c.d)var3.next();
                           if (var4.m().equals(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"stnvxhundsbbj","XDq57rHQtAGt8lW2OlJOI0+qvlrX8mV1fxOdt1d1tws=",2685455461798720766,594712174978021153,-8216209477643586492,-4119185235594800506>()) {
                                 case -100182191:
                                    this.b(var4);
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3kayswzcdd7qn","6c6GDi26yFg16DJ8qz8DoLhEVPzEtIiNp6aRoOn64jg=",1873898201890170635,-3995906362273433886,8546593845828880462,-4238724284061089036>()) {
                              case -699080985:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  List var5 = com.yiyiaddon.i.b.c.l(var1);
                  if (var5.size() == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s79hnvm069206","eQBlwKBg6hd3nLx6tA1As9Z41aDJx1TpilYh99gZqx8=",7311875145623254307,-7212123111715840593,-3472728707929917057,694552295142543054>()) {
                        case -1287899583:
                           this.a((Item)var5.get(0));
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (var5.size() > 1) {
                        switch ((int)com.yiyiaddon.m.b.a<"svxtprj1hzwr1","M6xS2mgPUWxb9tAtj5a46/LgR3f9TvuTmfmIrWzli1Q=",9139680604714995991,-2845294810361666806,-1928395636889518056,2799523849199625580>()) {
                           case 1499122800:
                              this.g(var5);
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.x(
                        (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>(),
                        (String)com.yiyiaddon.m.b.a<"s471ls2iiuf3y","HutCyeOhmvMA88EFUmps2EMT+rHv0f3oCYTerfIpC918XvuF4p0aJyyTZSMlGRqS+qyOGvY6viNWunfcbj+4/RNUIf5zG4uOU/GGGPsdQAKmkpSocrMY+bnbM4gxcABQr1nhuRgZr8lU/biacb+s3yxm7xSbwRZFEqiK5J6sSGk4CwFEPcRJ6Q==",4724195968305225344,-4589253304156079636,353162682542664123,-8590936756781974315>()
                     );
                     return;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private void a(Item var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jprr9mj226i","NaQV2Kynn9hNrhOgNtNQyFIt5pR8qME9cAYNBpfjI0Y=",7833031168264856918,9072128812749623541,-1585694358836592030,1648028421047922473>()) {
            case -2081371497:
               this.x(
                  (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>(),
                  (String)com.yiyiaddon.m.b.a<"sqpu3mazhzbrz","7IWUcB5wihTlB0uGGLdEgmZg8Yl95xyDvtUnA+Th47TGP2OPUh8L9FRk9WnEq8B4yVTINP/+tCbBGyR1INeuwKhV4gp3LSfRoMMbGl6HcCj9tUyP+/7SQScyuJYdSg==",-4291028996118800533,764123728265616796,5423798321886737240,3841081286032257377>()
               );
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.c.d var5;
         label62: {
            String var2 = this.kT.trim();
            String var3 = this.kU.trim();
            String var4 = this.kV.trim();
            if (var3.isEmpty()) {
               label50:
               switch ((int)com.yiyiaddon.m.b.a<"sg616otd8tu09","DZ8vJ3OoZMk14iqdyArBJmvBkVBFADCjDn4ieDpfWMo=",-2949694944429376884,6747161737182335943,3918065033583925770,8464808173049586816>()) {
                  case -617802130:
                     if (var4.isEmpty()) {
                        if (!var2.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s370xrsswsmlzk","l9eB25iL88XoRZdhLyOj91TcWZLvzdchN6R0mFdPCvk=",-4893928064037048871,-5132830961791298806,4832300219948477204,2626143304845279768>()) {
                              case 735064885:
                                 var5 = com.yiyiaddon.i.b.c.a(var1, var2);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3qlsila9gbz9w","DHmcLPNO240jqL05x5CI7L+Pr5ggVXG9OrflQkSg9/E=",8386513161660923493,-3131043118352548965,-7683315157526551317,-4272021241797559989>()) {
                                    case 1693467284:
                                       break label62;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var5 = com.yiyiaddon.i.b.c.a(BuiltInRegistries.ITEM.getKey(var1).toString());
                           switch ((int)com.yiyiaddon.m.b.a<"s1ec5le7rx6wwf","X46/AIBTrsmOxKQS1so7pPz+rdsnGn/9Rcy8eOVJF+E=",873631206020111464,-3875278719345963309,-6525893038004758107,-9148229648347471944>()) {
                              case 333362987:
                                 break label62;
                              default:
                                 throw null;
                           }
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s27wfgvzbepz5l","cStWKETS9ctSUG5f/rE2RYIMuQ/MidDDPf4X7ZLKjCA=",9078621888210793223,-2827508017973544632,3456768811283093978,-8810382641263126009>()) {
                        case -1357516126:
                           break label50;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            String var10000;
            if (var2.isEmpty()) {
               label43:
               switch ((int)com.yiyiaddon.m.b.a<"s30uvm3sr1spm8","cZ8SKoi2MgJzxMo5kD1oXja85L3YLy9F3qxTMUqefLo=",-8272863658899023908,-1350378946723019188,6973691490267069671,4204843635044524743>()) {
                  case 1282475689:
                     var10000 = var1.getDefaultInstance().getHoverName().getString();
                     switch ((int)com.yiyiaddon.m.b.a<"s194sicsf61zon","thZmEF7duVil+eUX61OtAa5hWxsacUfwU12619kJ7Vg=",6051246787748976161,-614720839114709431,1177723995265682930,-6837770447215140160>()) {
                        case 91217041:
                           break label43;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = var2;
               switch ((int)com.yiyiaddon.m.b.a<"s1ajfk1l306p5z","o+G3NEMz8Oxa5uvK1U6+JExwhm9ZL7X2iWVsZm4w2tw=",-1485107653513035510,-422120165078766924,-1650725039992817431,3850169744850507962>()) {
                  case 1503031984:
                     break;
                  default:
                     throw null;
               }
            }

            String var6 = var10000;
            var5 = com.yiyiaddon.i.b.c.a(var1, var6, var3, var4);
            switch ((int)com.yiyiaddon.m.b.a<"s1nrudamx1vpq1","9wkr70opVy5T0KThzsCbVZvgue0CJsKX24tULB3kUVM=",2462453293137004862,4787110178664981169,-5898516706416364473,-1445456714583564703>()) {
               case -101901021:
                  break;
               default:
                  throw null;
            }
         }

         this.b(var5);
      }
   }

   private void g(List<Item> var1) {
      ArrayList var2 = new ArrayList();
      var2.add(
         new w(
               (String)com.yiyiaddon.m.b.a<"s1xcj9soh261g0","O+ukfM4I54whj3HFlPJmbTFKK2zfPuuk0gWguBRWmoHCzuo5BdKT5a4oL3rQFcduRNmaZ79Hog0HKMj1R4U=",-3995807450726167103,-2598929682697376089,3147217585360685334,-5742276751602777473>()
            )
            .a(24.0F)
            .b(12.0F)
            .a(true)
      );
      Iterator var3 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"stqfcdvpxruci","coW5ZOacjS006dDFhq+7UIugjTP3BX3B4hYjiii63k0=",-5659077666199663667,-6240163123826448010,-3239571257247915029,-7833481177280032423>()) {
         case 1016007196:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ymd6adwk5lj1","+FBuk/FaBUw0QfBEfJd6/y6MuPJ3nmhkuE2JcH5BHGQ=",3006834758283104172,7466093386660787443,-5894993988141092674,3508526337048501641>()) {
                  case 1789036377:
                     Item var4 = (Item)var3.next();
                     String var5 = BuiltInRegistries.ITEM.getKey(var4).toString();
                     String var6 = var4.getDefaultInstance().getHoverName().getString();
                     var2.add(
                        new n(var6 + "")
                           .a((var1x, var2x, var3x, var4x) -> com.yiyiaddon.l.g.c.a().a(var1x, var4.getDefaultInstance(), var2x, var3x, var4x))
                           .a(() -> var5 + "")
                           .a(() -> this.a(var4))
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s1val6mtfvfbs4","bV2IMhlWdsDnvw2IzlSnmePqvWmPp0QVm6C7Nz7ZvIs=",4295486939725194656,5318519631864440130,-8610189327375283765,-483721346056086945>()) {
                        case 1825753190:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.a.s(var2);
            return;
         default:
            throw null;
      }
   }

   private void b(com.yiyiaddon.g.c.d var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2eprktc4xfl6s","uqPHgqW6UPET8xSH+QgSGBbx6bcbzUouAZJ5Omb1JO8=",-3950815901305225356,7555592804340051047,1872745698146254879,4477864591425146920>()) {
            case -1326252277:
               this.x(
                  (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>(),
                  (String)com.yiyiaddon.m.b.a<"sqpu3mazhzbrz","7IWUcB5wihTlB0uGGLdEgmZg8Yl95xyDvtUnA+Th47TGP2OPUh8L9FRk9WnEq8B4yVTINP/+tCbBGyR1INeuwKhV4gp3LSfRoMMbGl6HcCj9tUyP+/7SQScyuJYdSg==",-4291028996118800533,764123728265616796,5423798321886737240,3841081286032257377>()
               );
               return;
            default:
               throw null;
         }
      } else {
         String var2 = com.yiyiaddon.k.b.a.a().f(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s6skjvx870jst","wlimE+ugV8DYqYAU051v/ifqRZQLYOh0GplIAhMBV2c=",6029275161612000695,-174007682049152054,2587260746574547957,3444701264239549699>()) {
               case -1917793302:
                  this.x(
                     (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>(),
                     var1.m() + ""
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            String var10001 = (String)com.yiyiaddon.m.b.a<"s2f4t5j8fj9nnx","9PVNkETmcdNp8dwvoN9ICnqYcKGaeF+U533iEMlQgZXPEXP1ZVqXNg==",-4713918325281559827,-7522303382894603273,-3164615645074988888,-1070384647825736664>();
            String var10002 = var1.m();
            String var10003 = var1.be();
            String var10004;
            if (var1.fe()) {
               label25:
               switch ((int)com.yiyiaddon.m.b.a<"s2umykupw3dz6p","uVO0rSo8e/i6eusrHoODuYdvjH5g4IpIummsjs37GdE=",-1619160904118258855,2702139314539094997,-6745583331792290329,2512285169033547064>()) {
                  case -1119866070:
                     var10004 = (String)com.yiyiaddon.m.b.a<"s3jsmjugl4ahf8","6MMCe5LZpv3qU3hT2vC8Ane9amwM+pumYzsRwrXjFXpZFjmLOP7pfSbQQTLhCYDp",-4719314552104582947,-7732010625549321569,-8527542840270123141,72818638468560147>();
                     switch ((int)com.yiyiaddon.m.b.a<"sdeen01qsmeqe","twgDRZ+sTnRFPq1UXMu37VdKTMap52ElhfB/jpPxq1o=",-5462040647180683806,4000218586136888479,7652724404596993599,1267645900783830344>()) {
                        case 1616764063:
                           break label25;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10004 = (String)com.yiyiaddon.m.b.a<"s1dob00ktst428","7WYomPoNszGqHLXjRCs1yDG3FU50HDjM/dElpw==",898605294817565867,4841214698379084895,-189754033092623828,8426402056646762093>();
               switch ((int)com.yiyiaddon.m.b.a<"s3kjj2n1cjbfqy","P49pchJsAtwyQEXTQcQqqvVKgEFFZ6FdYnbqQRGExh4=",5035915276627300574,8943298305559541822,4099782476245909538,4884343269930566758>()) {
                  case -1411069119:
                     break;
                  default:
                     throw null;
               }
            }

            this.x(var10001, var10002 + var10003 + var10004);
            this.kR();
         }
      }
   }

   private static Item b(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"scgdl9mxf3vr0","Ga8oDiEyGYvKFro4evo2Afhupk0AjXWiuonP5MjQ/tY=",166407618350106371,973298027875168286,3046697070758042314,2561277856447293845>()) {
            case -975228100:
               return null;
            default:
               throw null;
         }
      } else {
         Item var2 = BuiltInRegistries.ITEM.getValue(var1);
         if (var2 != null) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s1y5scp77hxots","WHDiNh9sTEbAj0zaho1PEAjjRLm47eS3w1YQPgqT03A=",-2931556320476455519,-8625718284502219923,-6072426015006807327,-4591998826603062484>()) {
               case 385074755:
                  if (var2 != Items.AIR) {
                     switch ((int)com.yiyiaddon.m.b.a<"s137g5vxuq6q8","tKcweeoN+xr/ijmXSCacbufJDSnoE7oLwBVA4pI788E=",5689858969301245448,-2653494355527796104,-5222656475092315575,-1672021213851618217>()) {
                        case 2060679616:
                           return var2;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s378r1tx7pbaj9","8w7SdY2e0isZkKoVF4OoNFfskQzjijV3LO9M/COixAU=",3499430009451310181,-1453347651075631064,-3467831368545830211,1120707384307957652>()) {
                     case -6149362:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s1y16739w890ap","jzF02/07knQbFB3FUNUWNx6WWwcju+gIyYSIzn/owfM=",-6477296295451701846,8623594867848217339,5469301379959525399,3408321943989240763>()) {
            case -1796284414:
               return null;
            default:
               throw null;
         }
      }
   }
}

package com.yiyiaddon.e.p.c;

import com.yiyiaddon.e.p.e.c;
import com.yiyiaddon.e.p.e.d;
import com.yiyiaddon.e.p.e.e;
import com.yiyiaddon.e.p.e.f;
import com.yiyiaddon.m.b;
import java.util.Locale;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public final class a {
   private com.yiyiaddon.e.p.e.a a;
   private com.yiyiaddon.e.p.e.a b;
   private d a = d.IDLE;
   private long av = -1L;
   private final com.yiyiaddon.e.p.c.a.a a;

   public a(com.yiyiaddon.e.p.c.a.a var1) {
      this.a = var1;
   }

   public boolean dP() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wg769m9tha84","fFTP2N8zHiFS+R5mHcYpieMZ1974vqvQVdUjFiD5134=",5557003294875340458,-1216200144768818476,-665417190160054820,-5465005300682581254>()) {
            case -1910923888:
               switch ((int)com.yiyiaddon.m.b.a<"s2al0ubq6u6ku9","ifz4cY+kD34ISj5qvu/uLYAHU/PKKgySUEyNcqtKDmE=",7026976091494856848,-2890945801025318953,-6835721581495479385,-3933255581842098943>()) {
                  case -1372545268:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"si9cynp5emtin","Gufn0D8upL+3/5eTSjJdDIbwcuefYlQwNBaL5m45yo0=",3166159976817420492,5132263252003127755,-2572137993430454,-9154289479036575831>()) {
            case 52519886:
               return false;
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.e.p.e.a a() {
      return this.b;
   }

   public void a(LocalPlayer var1, ClientLevel var2, c var3) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ijx5x4pcufvz","69elggRm3Ei7rr7EpIlcNDTKiQZk9yrdnJALeSDeGwQ=",-6336180577114806010,-8830519135011782706,-106089867939440770,2758942207897647718>()) {
            case -1448952163:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1sn7m7qkn1756","f1vQNtJhfh+f2S3kLFgp4BstG6Oqhlz1S8BdmDKg/yI=",2827933405228381765,-6384500848711515557,-4957231654278547232,-9129651932498611861>()) {
                     case 433890668:
                        if (var3 != null) {
                           if (this.a != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sfemp1m0k1lhc","qMHktwC8wsbHr1g0dzYlZBt+ye705AFbW0jOgMLG0i4=",-2331922125687731206,3720372512970642980,-1460793594975414390,-7771394706085444796>()) {
                                 case 270224653:
                                    long var7 = var2.getGameTime();
                                    if (var7 - this.av > 20L) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sow7u5xxwugc","BWTD5zw8f6ToaCJCK1UjRUp+Pc/5q1PNh5Edbo6MBZQ=",3839676041996114667,-360572918145428231,3440109154566989655,1166528592614525125>()) {
                                          case 607938592:
                                             this.av = var7;
                                             this.az(
                                                (String)com.yiyiaddon.m.b.a<"s3k5m8f6u0bclg","EOBwghhZSvFA62KodJwVD4oUoiabZr31aGNJ56unFSmMmpFQHYttTf++q5zk8RZ8XDslLvrqWnEIxgvHk6PAIQZv",-1669724872499170428,-1030898475834411615,-5291141737887210267,2952729189201196868>()
                                             );
                                             switch ((int)com.yiyiaddon.m.b.a<"s33dcfnsaculpr","LFtswMkV+MhGbZPVF/RbtI3Xz0t28QXmknWl6jbfEF4=",7757366685729502886,858559758803271691,5982673569164972257,8413216216558438844>()) {
                                                case -1138631668:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (!var1.isAlive()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s299yzugly72ca","3IGd2vNy7eJoJVI6D9OpD5Vy/1qBwU/+nyhgU5Buf4c=",213822535216493497,201872639805500968,-1989324396024303531,-2104228240918577109>()) {
                                 case -1717886176:
                                    this.az(
                                       (String)com.yiyiaddon.m.b.a<"s9aahcavrq4v3","VvOzb/yWJq7zQyWRKNewcHYzlF+sqt/nAoeAyQ54CBjIYowqrNJtA+DiNMe5sQ00Umc6rPdZi5UCwdsf",8597313526901733812,4303638715083453556,8829905862833797255,7013568513500361214>()
                                    );
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           this.a = d.OBSERVE;
                           com.yiyiaddon.e.p.e.a var4 = new com.yiyiaddon.e.p.e.a();
                           var4.a = var3.a;
                           var4.e = var1.position();
                           var4.a = var2.dimension();
                           var4.f = var3.f;
                           var4.g = var3.g;
                           var4.bg = var3.ba;
                           var4.bc = var3.bc;
                           var4.ay = var3.ay;
                           var4.eN = var3.eN;
                           var4.D(d.OBSERVE.af() + "");
                           this.a = var4;
                           this.a = d.DECIDE;
                           var4.D(d.DECIDE.af() + var3.a);
                           String var5 = this.a(var1, var2, var4, var3);
                           if (var5 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3n0wa3let59l4","q0CGBzTHA3OO1DHe83hDpvcC2QCmc/pXMtBzV1cKXy0=",-6878172580767174493,2299790590464816971,5930783242803229891,-971456520543892293>()) {
                                 case 767764487:
                                    this.A(var5);
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (var4.eT) {
                              switch ((int)com.yiyiaddon.m.b.a<"swjq1kfom7j9l","ZF7sHkFk2fIjdpNobnDPgTWKaLA6CJycN4d0ZXlsrn8=",8867213013044852069,-39082967883994878,7788799944063060568,4270577575924449528>()) {
                                 case -296606940:
                                    this.ip();
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           String var6 = this.a(var1, var2, var4);
                           if (var6 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2bzshflou9ivc","lKFZFGzH2p+lmSLPpfg0vN8b1jn6wQzBipihdHQopB4=",-7606775168210482131,7802421961104247718,5285838516137527735,2634766493250195462>()) {
                                 case -1703551010:
                                    this.A(var6);
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           this.a = d.EXECUTE;
                           var4.D(d.EXECUTE.af() + "");
                           this.az(this.a.bn(var3.a.toString()) + this.a.bp(var4.a.eZ()));
                           var5 = com.yiyiaddon.e.p.f.a.b(var1, var2, var4);
                           if (var5 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2d60um4hawv3q","CRWoreQDTPydBU5Zgm22VQhFJnzN7yBHYi5RJwHHJ7A=",5998034042618886726,-5426718816884897152,6870992301641765109,-6903520542060744729>()) {
                                 case -564634533:
                                    this.A(var5);
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           this.a = d.VERIFY;
                           var4.ax = var2.getGameTime() + var4.ay;
                           var4.D(d.VERIFY.af() + var4.ay);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1fpu3nc50h54z","6chf3MLV1EEY7hXpPQH82VNWYksivZJbPnJuHtAIDcI=",4928124816203817716,-8935549036942776271,8136232291474696110,6101944673509093632>()) {
                           case -1458607865:
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

   public void b(LocalPlayer var1, ClientLevel var2) {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sa5s3i1wn6cfb","k0rdfQqh7xxafCOq1upYDo8bjV1IR2yXfXm7QoyFG9Q=",517182185366112406,-6035264381083939899,-3127591945576788145,469188832971430947>()) {
            case 1192366673:
               return;
            default:
               throw null;
         }
      } else {
         if (this.a != d.EXECUTE) {
            switch ((int)com.yiyiaddon.m.b.a<"s2tvgigarq7al3","o+YxHpW9SesnMBSJMo51qzYS+IV+5iNT65LLTUCKZsg=",-7327569906145691202,8241049947062697734,3350981552360781040,-7075723180528685089>()) {
               case 1465011315:
                  if (this.a != d.VERIFY) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2u3agvznw0t2w","I1vZwQsCEiXSk69QismZfhh1ucd7dashTzYRxpSu79o=",316142752703194819,-3512844513452617349,1662543299928162877,6846825040964743434>()) {
                        case 346602317:
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

         if (!this.a.a.equals(var2.dimension())) {
            switch ((int)com.yiyiaddon.m.b.a<"spv07vh6bmjxu","w8BQPES4rQonb1hn30MjmGsC0OOyEoVF/i0sxC8wWAk=",9152460047344856948,-1008739498233404695,4142358370027763872,-1999170536942517584>()) {
               case 1759695519:
                  this.A(
                     (String)com.yiyiaddon.m.b.a<"sp7n2n0fh1isd","JA9VoNnHobWoC0B5W/vqt7GPVd+r1a5+Hl9SOuKhCh4JDg9NHUslSfKxwaCESt25z+GI3w==",-1485495037638396622,4650930014647046910,238087396687404979,-6380764142963037535>()
                  );
                  return;
               default:
                  throw null;
            }
         } else if (!var1.isAlive()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1azcj99rhozj3","TY3IYfMO0T9KLYxbc/W6s/YwsrB6ucm6Bg6WZpguye4=",5849258159597975104,-1937503851197523576,-1503839299439251885,-7769143502044203294>()) {
               case -1034756826:
                  this.A(
                     (String)com.yiyiaddon.m.b.a<"s15odiv0n51lng","/QWnt81C97OUQlOJTEAM1mkduXg3w8WpKVLuf5WRdDXmsilHQgyt8DTOl4l5Pw3xtwwIFg==",-4185865976068166329,-1458852734358459510,-4217988531403258605,-7410758868053534896>()
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.a.a != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2s3iq37mudoor","tZn7Jntl/EMHRuOdP7aNsVHUAtlv73WimjhkE96E2Tk=",6569516088543126395,3306760700848618359,1301318095699300433,-3152436202795289246>()) {
                  case -926999160:
                     if (var1.getRootVehicle() != this.a.a) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2c0g2ch7cqiah","P4FremTOMTP27EHzai1GGN9t4hAgMsKEJIlSJIo3iso=",-1833836040747579574,8039479883370773069,-7852613913404616013,-8981677404939971469>()) {
                           case 676132897:
                              this.A(
                                 (String)com.yiyiaddon.m.b.a<"s3sowsfjmzjgyz","3xILcvrHWVGZJjKQ/QgQfo/2O3lVUkOw68iA0hLR6bZHu2TbIQx00OTGtFDsr/1jWNESNIaT8bA=",7529699805360044182,6114834833937195959,-4721563101269634192,1581731975638000233>()
                              );
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

            if (this.a == d.VERIFY) {
               switch ((int)com.yiyiaddon.m.b.a<"s13x6lz8k95g68","vJVmUUckSuRJ7UhfgGMhTz12wQAml2gVOr9eq08x7DA=",8626667572141600487,6792257755847733186,2082543989460931341,8431736154169863968>()) {
                  case -2031471914:
                     if (var2.getGameTime() >= this.a.ax) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1lvmle0ghga0c","8ZYFo+Vv3TKKOxH+SacV6ewYHQfNiHs5XNJIlZJN6qk=",-7696135103271415804,1501363928234731118,3052842376832634176,-7304402924353473786>()) {
                           case -196612342:
                              this.io();
                              switch ((int)com.yiyiaddon.m.b.a<"s9oubkacu983w","YgsRK6f01IkWEn/712VJ+3clPxnjIh8hmHuahLFa3zY=",-4491611526103396873,7259848106262612924,-6960885329700836588,-2549333705970609963>()) {
                                 case 1346246976:
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
      }
   }

   public void a(Vec3 var1, boolean var2, LocalPlayer var3) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2yow0lq6cfxoa","El0qc+boG14qURiwS9FBvVNr5+syFoSGrn8p8mLr3hM=",5624090559983129489,1503565215764004896,-1975326862581115507,5632917583046387247>()) {
            case 1486761981:
               if (this.a == d.VERIFY) {
                  switch ((int)com.yiyiaddon.m.b.a<"sxwmvc89j3441","n08Ki+NTn3Lm9c/1TL54btSehgyV0SuAq6b9cQvRXek=",-1860498699318100091,6541071014100317239,-8602471403708695318,-4263228343922488762>()) {
                     case 206967074:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3vtweuecwycmp","FCY+fVjFu0ullIhV6PWXHRzE5CN+Jd2qDCoI6amMOdA=",5363007840317433441,-2758253873163750904,-9049113641342423030,-4165322636235797349>()) {
                              case -2001336398:
                                 if (var3 != null) {
                                    int var4 = com.yiyiaddon.e.p.j.a.a(var1, var2, this.a);
                                    if (var4 == 1) {
                                       switch ((int)com.yiyiaddon.m.b.a<"srwi2dsnvfaus","s+Qx1NfA/o2wvP0xzQ9fKFD+NwpuddrQNKyxJO7EBrk=",-5673816797372452847,6981105928358995166,7449979018398653047,-4157929694197031417>()) {
                                          case 403788388:
                                             this.io();
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ffgqxu1x0xdz","oQz2Ra/nuZ0uiGv7xx0UMLyBPHtlyIify4FV6rE5cP8=",-647735381304219312,6023790326881220436,8279960861732241274,3634251064644757413>()) {
                                                case 780174229:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (var4 == 2) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1j8hjjt84hljx","OKPXZJ4wO7K5HUMLHxhD1okW4IVMgRc+pOhko/sMiuI=",-2951018234798150541,-427487713924328466,4718045087726607462,1830718873303111714>()) {
                                             case -6337777:
                                                this.iq();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1ssablrghin8t","bgPYCPSbMTgH+v1uR4XbnCCtet/b7rAJfTf2o1gdx3w=",-3347442431313870739,-1587433744161671526,36703336589196111,-8090119461175823744>()) {
                                                   case 1078928566:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       return;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2mef7an4v0nu8","g+6Hmu4JUBg9iJbE18pvh/JwIk8as5L/aHaL8woy2d0=",-5149564781620893733,7484168311554376684,-8787991814590367930,-828812886692136329>()) {
                                    case 406484138:
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

   public void aO(String var1) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2awt1atmzhq79","b5b03KuPZf6Vwa/j/+VRlbnDruYXi6rZnMg/xxf1NFQ=",3724950411994433336,-5821279878657461399,-3802805640827410947,1557996641193903069>()) {
            case -1198656453:
               this.A(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s11sjc9u4da7nm","8VsRIcd+/vYPAzRp0O2HVhSrk4bXOaUp/Nac6vQglbQ=",-2682653892778275234,1189684309139079457,-6937546426042541632,-4907857616324625583>()) {
                  case 1911214201:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void b() {
      this.a = null;
      this.b = null;
      this.a = d.IDLE;
   }

   private String a(LocalPlayer var1, ClientLevel var2, com.yiyiaddon.e.p.e.a var3, c var4) {
      e var5;
      var5 = e.a(var1);
      label146:
      switch (var4.a) {
         case GROUND:
            com.yiyiaddon.e.p.d.a.a var11 = com.yiyiaddon.e.p.d.a.a(var2, var5, var1.position(), var4.pP);
            int var14 = var11.pW;
            String var15;
            if (var11.a == null) {
               label134:
               switch ((int)com.yiyiaddon.m.b.a<"s2um6swahfwu4","EDsk2B5d+FRr8gBOKBWuUKiq0BnNsbeJQtaw0R3WSz4=",4791005270724566819,-6544247188358864497,-7480910302297738291,-1820270016601689604>()) {
                  case -1870541416:
                     var15 = var11.yQ + "";
                     switch ((int)com.yiyiaddon.m.b.a<"sn173cfra4ufr","0vnH4aV4tJWHAaj5sM8V+BSwtTc+6Gwvicm5XiIcyik=",8387393536764986816,4350615842241126134,-5200392602385418908,-3275904756740459434>()) {
                        case 496147402:
                           break label134;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var15 = (String)com.yiyiaddon.m.b.a<"s3bn7jnklepybv","GCwWoRBG1AnZK6vDc9xLgjFUI1CLgwxkYeQsUw==",-1093232795967913658,-7787265492586350082,7882079293848538312,3417097140749959150>();
               switch ((int)com.yiyiaddon.m.b.a<"siw7536r2a5jb","Vp0SY2ys5JBltNFEuVRbDBh+UK/kZvGej1HG5vvHil4=",-4126136252914507265,-5854824497688615971,-6983786802145081606,5740307842509693288>()) {
                  case -1987797491:
                     break;
                  default:
                     throw null;
               }
            }

            var3.D(var14 + var15);
            if (var11.a == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1au52a3cnu0h1","EOfMq29G9nEbS2lIhqiN85aNTpgqjlsRePrxoz2Enwg=",5097497034650728180,1340077160967342177,6756081031716678655,-8224619619726149437>()) {
                  case 1358528748:
                     return var11.yQ;
                  default:
                     throw null;
               }
            }

            var3.a = var11.a;
            var3.pY = var11.pW;
            if (var11.pW == 0) {
               label127:
               switch ((int)com.yiyiaddon.m.b.a<"szll5pmcx5osz","zYSFJI49Ly3jvhEOf+PAeBCFcTLNrz/i9MaqgUP2ZX8=",-829618141231613366,3214311964373635342,-4254558895997468083,-2426974227628694977>()) {
                  case -120432216:
                     var3.eT = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s17wyh8jsigvxw","eDdepCxQWSkfHYGpaOKf1S0X57LmrbNmQZgX/XISklE=",-7216081353169255778,8222206886922592950,-8554195875028517749,2790613191244316338>()) {
                        case -910047943:
                           break label127;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s1y1z5yr195kh5","YOuaiovhwTf4+Fgw/azom6s8G7YHlaSxLuYx50ZBQeA=",5230994049238457269,-6729431648947911120,-4981327630190447058,-8784996189909356989>()) {
               case -1615734682:
                  break label146;
               default:
                  throw null;
            }
         case WALL:
            if (var4.f != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1707m8488ty6s","iZJ/dnF0aF7/3GUerVXD53qaiHY4qBC8cbhoJUM/7t8=",-5431470061755002635,-8379574088868185031,2036843183659228708,-3047245723628258499>()) {
                  case 1342559089:
                     if (var4.g != null) {
                        com.yiyiaddon.e.p.d.b.a var10 = com.yiyiaddon.e.p.d.b.a(var2, var5, var4.f, var4.g, var4.bi, var4.ba, var4.pQ, var3.ch);
                        var3.pZ = var10.pX;
                        int var10001 = var10.pX;
                        String var10002;
                        if (var10.a != null) {
                           label118:
                           switch ((int)com.yiyiaddon.m.b.a<"s3q4trtpw1fb4x","FQRQJGt4vV76WTVTY8wz13UAwKKTFKjE/3BZvsz7MOA=",-6567241132977577354,-1315424408580225740,8295323264652359221,-333265861224045019>()) {
                              case 233547526:
                                 var10002 = (String)com.yiyiaddon.m.b.a<"s14hqzormje9xy","RBazmgQvC7r7AElyCwYSVFXbhzcP2afwFwMs7zIL7jFgBRFkfFGKpqvNh9AYStNxk/uG46Tb5As=",-4743445696680951147,470115620046030876,9030054929114589281,-6788987012743665917>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1yr3glbx9z40d","3tn6W7YzhQuG8BeR2IZLqSxZvyXmHzl+MyIN36t0A8w=",-4443705172175304578,2671956952477191376,-1844479806450836922,833129532310971899>()) {
                                    case -495963960:
                                       break label118;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10002 = (String)com.yiyiaddon.m.b.a<"s2febviy97y4lr","4KlQAVFsOo3P5nbfftngl/A2yvfUKW2Pa4eQV6WTL+cZHQtBwdPO6vJQgQ8=",-7181963961799275377,-5216463153681876567,-6680194914882773827,4968684607150044957>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1kjo2idrayt16","eJMOinjKOcvERRGVi00lRvpCfTZvE4jcXRvZqSTws/Q=",-4536282618562675835,-8646855145072545352,-2833685721416737132,1688755010387443293>()) {
                              case -1197410271:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var3.D(var10001 + var10002);
                        if (var10.a != null) {
                           label103:
                           switch ((int)com.yiyiaddon.m.b.a<"s36f36s8ltr2ly","xwZo7+JWGDz3M6YeievUaKeCzWdLOS+9ceQqXtrqQMI=",5406838363656678742,-4574061096180726558,-8201762926860255500,7773313054538771757>()) {
                              case -161821671:
                                 var3.a = var10.a;
                                 switch ((int)com.yiyiaddon.m.b.a<"s338j8ntn93crg","wWiezRBAw80V//edNPdzepeeOETqhCWQ8I3zgfooN1Y=",1949700778661771999,-6536211241548308411,6669941419720515256,-2322873752395413078>()) {
                                    case 1139631198:
                                       break label103;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var10.eQ) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3dhlljvb375y3","SvpLtBVAJxAByO8jTWdzBw6/1YJ67MWnH51vbPOe9tw=",7912508026765129245,7822031897093920372,6206329281605783611,9117737671862484088>()) {
                                 case -110826175:
                                    return (String)com.yiyiaddon.m.b.a<"s394olx3wk9be3","bFCv3bxyRD4uM1SC/XRzuXWNmZcqNA/H7UlfJtJ5kXOKHSawYiBlvLXUIPIP4cf5ytxrQUZ/firb5SHo4an9HzOy",-6200368679472346772,-1227166518281573306,9032669786865678762,-7642673030378749787>();
                                 default:
                                    throw null;
                              }
                           }

                           if (var10.eP) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1963y6q1jqd16","PmFHeP9exlpENKDvvJQuhMbEfhZg7cJ6LFXOZf78zFk=",5361380047088213302,1686631608837647413,-8689358125849920122,-6286716328719153803>()) {
                                 case -1303108885:
                                    return (String)com.yiyiaddon.m.b.a<"s1af5vf4kf1tlf","L+b9T3jKU2cmJMAEHKretEbbz2N0mukGnfjoSz2W9WWLCB7ngLOcjw1IQ4hI5LIhpUmDi3EP",-455718600930494528,6106715862665017904,6302437316915186051,8370058774768430217>();
                                 default:
                                    throw null;
                              }
                           }

                           com.yiyiaddon.e.p.h.b.a var12 = new com.yiyiaddon.e.p.h.b.a();
                           var12.m = var10.d;
                           var12.al = (int)Math.ceil(var4.bb);
                           var12.f = var4.f;
                           var12.g = var4.g;
                           var12.bb = var4.bb;
                           var12.bl = 2.0;
                           var12.ch = var3.ch;
                           f var13 = com.yiyiaddon.e.p.h.b.a(var2, var5, var12);
                           if (var13 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29pequl3nzhyn","3X1w4WVZPGwSdgphfOYxc/jxanKfSb7JbNd/9XHA6Dw=",2341073389855626377,5147311492884289091,-8345439891619140986,6363717525414698982>()) {
                                 case 364822960:
                                    return (String)com.yiyiaddon.m.b.a<"s2oj8urbekeex5","RZM6A5aAWODuh8jCDQYmif/EW54v1sQbtMeL3TBLssF+BjeY7+smUbPtI0H+QSZTcFV+fpdGnNSmY8W5yhs+hbPZ",9033574567621149568,2200263891799076167,234143477486764878,-5387526771459633050>();
                                 default:
                                    throw null;
                              }
                           }

                           var3.D(
                              (String)com.yiyiaddon.m.b.a<"s2ujv0salqn99c","LoZMLpuaRaJ0THubZvnZWGL9Shq4sOFMa5iV8aW1iM9bTkt/k+EJ8B/qIKxxGM2I2tlJZXdNxbwcuCaKnKsIOdnHYSxN1gMFRsn6xA==",5852008801395091351,3284314325705145211,-7478002137526822353,1258828970498586451>()
                           );
                           var3.a = var13;
                           switch ((int)com.yiyiaddon.m.b.a<"s7x42u73up2ll","/N2LLOawUUCcRV0zD1kLvDIR1erShvstn6uFjpbT4sA=",-1419790074761994137,7447478127420869331,1255795731398738605,2387320107872771414>()) {
                              case 1329016077:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1m9ifu99leqbo","TKbn3Sx9SMCHqtWBAagCet4SQBkpwZ5Agu7uPERf0jA=",-8708376339100948898,5203437757179440805,-5302315924994301365,-409990827132602431>()) {
                           case 782997520:
                              break label146;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3vgjdf85ozpj3","fae4d1Wew+0EHQA9TTT8KNPwETfeySJVZKOlon0u+Z4=",-2739323865040589257,-3617454306284645084,-1530869649088732191,-2292452908199182222>()) {
                        case -202650:
                           return (String)com.yiyiaddon.m.b.a<"s2sqme7x4378ss","74Z2EZ81Ttb0LU3uQk2Z5z3v7wIDZU/JdE10oqbnzTPk09v+Z/+BQLCIJ6xlkON9",7272014570753673964,3646970430606069205,-5750786675598385688,-8782759072791321976>();
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return (String)com.yiyiaddon.m.b.a<"s2sqme7x4378ss","74Z2EZ81Ttb0LU3uQk2Z5z3v7wIDZU/JdE10oqbnzTPk09v+Z/+BQLCIJ6xlkON9",7272014570753673964,3646970430606069205,-5750786675598385688,-8782759072791321976>();
         case COORD:
            Vec3 var6 = var4.j;
            if (var6 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"sxpw8xhfzkvm2","uc+eCLnWQgrVEVPmteM6GuUrGLMPnCrJ2+0DzwRLF0Q=",1051933126213548217,2879313021083438966,1323064765391324102,-7157711986551184902>()) {
                  case -180006308:
                     return (String)com.yiyiaddon.m.b.a<"sy2fcmhuu7e6o","vCc/85hoY3wsSB8eGvr2tf1Mde7WFPfmAQrIlDreYHIrUuiTWPFGdBDZ",1698631958442563907,-9050939138494781708,7382560263504189551,8452844471140776763>();
                  default:
                     throw null;
               }
            }

            String var7 = var5.a(var2, var6.x(), var6.y(), var6.z());
            if (var7 != null) {
               label92:
               switch ((int)com.yiyiaddon.m.b.a<"s1exbuy7u956pp","4mlRLbSq7rG/hwlr7wJRP47Fg0EnnUir9UT25slMfhU=",2789549946557462771,-3281430590292317797,-4933913656690196355,-8306389857179454488>()) {
                  case 1859248759:
                     var3.D(var7 + "");
                     var3.eS = true;
                     com.yiyiaddon.e.p.h.b.a var8 = new com.yiyiaddon.e.p.h.b.a();
                     var8.m = var6;
                     var8.al = var4.pU;
                     var8.ch = var3.ch;
                     f var9 = com.yiyiaddon.e.p.h.b.a(var2, var5, var8);
                     if (var9 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ednqv27p67kh","I7B2m2vks+DJBwTxjic29mnm/0v7BVReDhJyRvRWA/g=",7123724374312978608,3859064413648727507,-8702480426669122965,-5677231235464070351>()) {
                           case 439007332:
                              return (String)com.yiyiaddon.m.b.a<"s13mf40qdd6cc9","AsDH5jGpwtEz9xXoQ1DNYZrfweej2U6or02uaWVYA42HIxGJ59Mtg5HtaHMG3VQuuYeH+ifgEeaRvVCw2cQ=",-6885842503401860920,1271693256770549367,5267244655562108849,-9158746005876206705>();
                           default:
                              throw null;
                        }
                     }

                     var3.a = var9;
                     switch ((int)com.yiyiaddon.m.b.a<"s28izbm0vx2ryh","VGzsMqq23ikyKCGIRIOJYxOyz37efK3XwN+imynwZFY=",8455181480752127155,-1040552482770394516,1816555425268122632,5555015706447765955>()) {
                        case -849855729:
                           break label92;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var3.a = new f(var6, a(var6), 0.0);
               switch ((int)com.yiyiaddon.m.b.a<"s2gwqzdja00h8x","ciVvQJrI5xu+1tS6O0zb1A49OTPzdZjFHacMSGgjvfY=",-7188777034013081874,-7339030991120974835,2730899903399518144,7335519814307587420>()) {
                  case 619063847:
                     break;
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s22ou0zt3j14kz","R4fc9Lg63WxJOlAcdslJZtjokBb/+sfphs1DZr6hUvQ=",8293271122091205726,429971339864174274,-8105693412755255300,4362852814615800895>()) {
               case -1123655677:
                  break label146;
               default:
                  throw null;
            }
         default:
            return (String)com.yiyiaddon.m.b.a<"s2eorczj7zvd3i","J7zEs/WkXFmY4l1TenjRMKaNvIiN2QtVodS83nfXu8PtQMWCqXYiPw==",5303042757041958031,-4171160787380950185,7713110769248830047,-614543274482148877>();
      }

      var3.ci = var5.a(var3.a.b().x(), var3.a.b().y(), var3.a.b().z());
      return null;
   }

   private String a(LocalPlayer var1, ClientLevel var2, com.yiyiaddon.e.p.e.a var3) {
      if (!var3.a.equals(var2.dimension())) {
         switch ((int)com.yiyiaddon.m.b.a<"stjc72rjlhsil","K/tzZeJUDaPFcT0irMeQnWzt/IKpIjYO8t1CoXZ9OyY=",-5609905897071085811,-3412003121777081327,3213758841234709533,-3429555421557617739>()) {
            case -66869283:
               return (String)com.yiyiaddon.m.b.a<"s3pyz0gvkj3ntv","osGyp/uHwLEDcHLKHH+YCFWKXAtbxUqpbQgqeaEjeZYvR+VXZNR/PuYgj9e6/EkZCj8KRmxyOZA=",2446707779685877279,2524505464677487363,-2853199181420316315,-3577731422783813302>();
            default:
               throw null;
         }
      } else if (!var1.isAlive()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rfqn81ulu577","fF3N+fbSQ2DHEFJwXJ1+t+1p5tLrz5af8Gz9xCe3Ots=",-627000317706035803,1995472099550567200,2306990039979651152,4877561463518901562>()) {
            case 1174825988:
               return (String)com.yiyiaddon.m.b.a<"s1cft7zcxc46r2","xqUhwrr7Mr4GEVXhLQ1OEeJvD1gB84B/5QB1iMvNOmZj4qeHxo87/HudXjt4LNvhsbCCt1+feK8F2w==",758286066999862869,6088057562245849683,2821561357536573033,7945664469658952135>();
            default:
               throw null;
         }
      } else if (var3.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s233pflygn8m0y","TqWQ8yZceUvV7Atv+pGW1jupx49sW8E3c5qzH4qTGN4=",8813340255205583684,7954818083838648341,8604872228256010266,-5122053183562908195>()) {
            case -2137484208:
               return (String)com.yiyiaddon.m.b.a<"sq0s23zr0gi82","OmSN7y/TARkF+K0sav2SQjNkP6fvD73Cv5K4HXxhuM7klJRqVD2Bb1RBxi4vsZ8H",6194496421089447857,-3108205476978555893,-3684298560797626985,-7203448634310558868>();
            default:
               throw null;
         }
      } else {
         e var4 = e.a(var1);
         String var5 = var4.a(var2, var3.a.b().x(), var3.a.b().y(), var3.a.b().z());
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1yx9yeh86ofpr","XKpT93Twro17YSQk00HeZnhahYWn0kgSjx5p/IH0KAk=",-5485332860498736525,949575742776578714,-2821107018921202410,2280210745857641950>()) {
               case -1266964515:
                  return var5 + "";
               default:
                  throw null;
            }
         } else {
            var3.ci = var4.a(var3.a.b().x(), var3.a.b().y(), var3.a.b().z());
            return null;
         }
      }
   }

   private void io() {
      com.yiyiaddon.e.p.e.a var1 = this.a;
      this.a = d.SUCCESS;
      var1.D(d.SUCCESS.af() + "");
      StringBuilder var2 = new StringBuilder(
         (String)com.yiyiaddon.m.b.a<"s1m886s1t9acsm","xfUiRG6oes0LoPrwOUWw4jkkISJwshrl2djTwlfu+90Ag2EBYknppTcBy+0h666hHUU=",648548932494015308,-1342621632801146125,-7311726542278952614,4894132062891708160>()
      );
      var2.append(this.a.bp(var1.a.eZ()));
      var2.append(
            (String)com.yiyiaddon.m.b.a<"s2zzao9w0qoyae","vJJPKcbYtt/kN5SscSJgfWgUoah+eBO+GJLccimLtXtehzyyA51y2A==",-2721837884544812370,7387491245224821676,3591925768175677055,3594537016926896269>()
         )
         .append(
            this.a
               .bo(
                  String.format(
                     Locale.ROOT,
                     (String)com.yiyiaddon.m.b.a<"s18tucf0l34z8","oZ1ET8Di4DYzXbO3B5pdfrMSOHkWXP1dCyg8XX8OoA+FUrQt",-153524032152280704,7292797466785939532,-8445894467222076546,2081666573551382795>(),
                     var1.e.distanceTo(var1.a.b())
                  )
               )
         )
         .append(
            (String)com.yiyiaddon.m.b.a<"s2m6p8xz7g5y8e","5R1Zlstvep9us74BX/KrEGyZ9INU+bZqIeTgEJJel7g=",-7450515290677418997,4405062715065363320,-8994642106248642587,-2588062107718185557>()
         );
      if (var1.a == com.yiyiaddon.e.p.e.b.GROUND) {
         label67:
         switch ((int)com.yiyiaddon.m.b.a<"sy04mc456kwe5","MTODVjegY2MDl2p6+CkqA9uF4n5oy6Ltjsy2AMHzdp0=",5871369098311356799,811711923908712631,6313280696368979038,-7553271019349245350>()) {
            case 754830679:
               StringBuilder var10000 = var2.append(
                  (String)com.yiyiaddon.m.b.a<"s1nmnbll0rxkm2","9JXD7wxNNWeYXxLEmwNLIBvWSBPhAY/mSu3Vpe4r4ffHpQ==",5741957808126754366,-3621564658332627215,6752889355639951655,3151462738245864716>()
               );
               String var10001;
               if (var1.pY >= 0) {
                  label64:
                  switch ((int)com.yiyiaddon.m.b.a<"ss82mf58pz68e","FZK4uOax/lH3vy8LgwgbiDJkqwBlvlZVXTtpFvFyouY=",-1352314481190823870,-1909236490577677848,-5075917743923553522,-3734111482115181355>()) {
                     case -1687535263:
                        var10001 = (String)com.yiyiaddon.m.b.a<"sqogr0fk685ms","gvD9VLHxV2HsooTZh0bzFPFi/XIkiz4wz8fOKMZsv2TqbA==",1552242110907157331,-8810646043128676442,7575727707683460039,-3551814999239604863>();
                        switch ((int)com.yiyiaddon.m.b.a<"s2jgas36tn5t01","/AJGlUQMHWiBiQgpra+RwM+l8UoiqMDSpAYTxtp7T+Y=",7506117397098812624,-3522641749961510029,4226080358201179518,5083590132395989399>()) {
                           case -120352613:
                              break label64;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = (String)com.yiyiaddon.m.b.a<"s16av4wqny5kaa","ZY8MVa322c6iAAQkfT0CzQuaz0V2IXnODpZauSNqjOQ8qw==",-5677860184775697668,-3896563861094099211,7525893021557921891,6026544423098672473>();
                  switch ((int)com.yiyiaddon.m.b.a<"s21idx42pzrlsy","P6OUkAUwiSDClxHGA71mHVseleV26pIb06rCEOxokVo=",-3313862407449150257,-4500487363148913727,3070364401455332113,4642820936344815826>()) {
                     case 137478367:
                        break;
                     default:
                        throw null;
                  }
               }

               var10000.append(var10001).append(this.a.bo(Math.abs(var1.pY) + ""));
               switch ((int)com.yiyiaddon.m.b.a<"s1vqp4id2zvvq4","Fru0uMQG+jWSgIBOG+3T9v+ejSRqVPwMtXdeHCxAfVA=",4327779101193147693,1580513078985262240,2760940750219192854,-3861666243278593082>()) {
                  case 1647205594:
                     break label67;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1.a == com.yiyiaddon.e.p.e.b.WALL) {
         label55:
         switch ((int)com.yiyiaddon.m.b.a<"s25gzujhvjm8n","zj/oie0Eq7Uh9+Y3yvScjWyyAKRI1L43twGRmxooc2A=",-1553497782420465385,5837315961412833315,7179792911862806751,8710536784293802392>()) {
            case 968521576:
               if (var1.pZ > 0) {
                  label52:
                  switch ((int)com.yiyiaddon.m.b.a<"s3bjjaxshajhuk","4ZqOyLFm+9mInloNAjL2yswtMa2/HR01XNURnbX7ENU=",8173733237323643029,-349055723115668028,8143438829288178395,2709024143823020882>()) {
                     case 1776720810:
                        var2.append(
                              (String)com.yiyiaddon.m.b.a<"seyzk1y33cgu7","nJumbRstELydmj37C3F95/r57ffgmE7c8BYv9O0K3RlQMV9n/KLWZw==",-4876236730458542131,-4554596812933508754,-3299621241303904678,-6682860937461253294>()
                           )
                           .append(this.a.bo(var1.pZ + ""));
                        switch ((int)com.yiyiaddon.m.b.a<"s2yzqwomh8357s","Yebyp7MMCvG/wiwnrT1Zb10mOvl6WWbu2MnAm+7Z6yw=",3477267841088765498,-3088612162147013552,-7934348000417707529,-5818124504112148277>()) {
                           case 381494773:
                              break label52;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var1.a.h() > 0.5) {
                  switch ((int)com.yiyiaddon.m.b.a<"szhzedw5art7m","TfmRfArYOaPKBOxyigUOIqfnBKvnX/GUnhFRiJ798GU=",643004831757325226,-8543712185996740568,-670815031363283874,-4370930451504655195>()) {
                     case -1476708844:
                        var2.append(
                              (String)com.yiyiaddon.m.b.a<"s2gs2lqtmb1oq3","3efhzG47PfWQGWS5KQMqz8QP/ypsQWMBPaheR72YqpEFd5GFMZ2/Hb6awE4=",2629539005795981450,3771617006410149790,-8764413110125456343,-1250499190147300305>()
                           )
                           .append(
                              this.a
                                 .bo(
                                    String.format(
                                       Locale.ROOT,
                                       (String)com.yiyiaddon.m.b.a<"s18tucf0l34z8","oZ1ET8Di4DYzXbO3B5pdfrMSOHkWXP1dCyg8XX8OoA+FUrQt",-153524032152280704,7292797466785939532,-8445894467222076546,2081666573551382795>(),
                                       var1.a.h()
                                    )
                                 )
                           )
                           .append(
                              (String)com.yiyiaddon.m.b.a<"s2m6p8xz7g5y8e","5R1Zlstvep9us74BX/KrEGyZ9INU+bZqIeTgEJJel7g=",-7450515290677418997,4405062715065363320,-8994642106248642587,-2588062107718185557>()
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s207sy4iy2412l","twwIpw9gYa+BeLF2AzF5hJFjloW4+M4m4rr8o/Hhd58=",-6744918881150307109,-5880928703706582082,-8832316584318572183,-6551543171483091454>()) {
                           case 727110662:
                              break label55;
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

      if (var1.a == com.yiyiaddon.e.p.e.b.COORD) {
         switch ((int)com.yiyiaddon.m.b.a<"s11niej3i6pon","JyZ4j3Rj9AuLT2PkmighycQGjlWYbf73AqNcMbog4ww=",1016881303841966453,6392870895366797176,-7212550001588539665,-809251788808772407>()) {
            case 238853416:
               if (var1.eS) {
                  label41:
                  switch ((int)com.yiyiaddon.m.b.a<"s3e5kf1rofwenx","1WtKwv9aN7z0hYdM1p4u5QadwFcIEYD+if3qLdBOrz4=",5192133798630061170,-3288027037694574558,3172214566001784161,-7003144883652066328>()) {
                     case -1500744771:
                        var2.append(
                           (String)com.yiyiaddon.m.b.a<"s2die9t0iq3p0","rkNNlCaBdPFnwCp3tLXaOXNyvO5y8pof1oNBLWNvAj2wlBNWzXEd1AXmZnGtxZ9lWxnUNQ==",3896236159410454602,1382646468118473167,-5415700283224731461,4048689436733489182>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s3l04b7fq68rdl","lZwSucfFCV3ZGc/Qv2NEbU4IUMnFfjczK0aTQldpoHo=",7099736970249448164,-3235218420249520281,-6428269058883029264,5119290259587324998>()) {
                           case -1805438306:
                              break label41;
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

      this.az(var2.toString());
      this.fb();
   }

   private void ip() {
      this.a = d.SUCCESS;
      this.az(
         (String)com.yiyiaddon.m.b.a<"s2leh9doceewjf","pytmNQQmsIt7RqC3WLsaeJJGksbQb5nkhzn/gbpcwjC6oOFJLskQuJPqjyt78kp2YT/+exhSMTnbsj65d1I=",-4506270967623937327,7341053766622342145,7930712047419739571,6712090007683329644>()
      );
      this.fb();
   }

   private void iq() {
      com.yiyiaddon.e.p.e.a var1 = this.a;
      this.a = d.FAILED;
      var1.D(d.FAILED.af() + "");
      this.az(
         this.a
               .bo(
                  String.format(
                     Locale.ROOT,
                     (String)com.yiyiaddon.m.b.a<"s18tucf0l34z8","oZ1ET8Di4DYzXbO3B5pdfrMSOHkWXP1dCyg8XX8OoA+FUrQt",-153524032152280704,7292797466785939532,-8445894467222076546,2081666573551382795>(),
                     var1.bh
                  )
               )
            + ""
      );
      this.fb();
   }

   private void A(String var1) {
      this.a = d.FAILED;
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2d3ggajohjovz","COnkLSWtxOcMqDHY2wTCKkTg/LcbZWpIQbantS37YyI=",4326491943682770168,6779087032515786570,-4376018763689382740,-241856476325288451>()) {
            case -913368824:
               this.a.D(d.FAILED.af() + var1);
               switch ((int)com.yiyiaddon.m.b.a<"s9j4pn4hmw6fb","22mUP4mJYZMEiSeglJlOux7klwNXyT9xaeNIJ+mai58=",3253907635421137420,-9183822032701032307,-5234021631513655920,-1800556991183084529>()) {
                  case -1124903835:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.az(this.a.u(var1) + "");
      this.fb();
   }

   private void fb() {
      this.b = this.a;
      this.a = null;
   }

   private void az(String var1) {
      this.a.az(var1);
   }

   private static BlockPos a(Vec3 var0) {
      return new BlockPos((int)Math.floor(var0.x()), (int)Math.floor(var0.y()), (int)Math.floor(var0.z()));
   }

   public interface a {
      void az(String var1);

      String u(String var1);

      String bn(String var1);

      String bo(String var1);

      String bp(String var1);
   }
}

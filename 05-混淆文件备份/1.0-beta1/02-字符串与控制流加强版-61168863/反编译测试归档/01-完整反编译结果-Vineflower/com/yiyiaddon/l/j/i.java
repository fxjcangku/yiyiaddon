package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class i extends m {
   private static final float nN = 17.0F;
   private static final float nO = 60.0F;
   private static final float nP = 3.0F;
   private static final float nQ = 7.0F;
   private static final int vP = 16;
   private final double bU;
   private final double bV;
   private final double bW;
   private final String GZ;
   private final Supplier<Double> I;
   private final Consumer<Double> u;
   private final com.yiyiaddon.l.a.b k = new com.yiyiaddon.l.a.b();
   private final com.yiyiaddon.l.a.b l = new com.yiyiaddon.l.a.b();
   private final Paint N = new Paint().setAntiAlias(true);
   private String Ha;
   private boolean gI;
   private float nR = -1.0F;

   public i(double var1, double var3, double var5, String var7, Supplier<Double> var8, Consumer<Double> var9) {
      super(
         () -> (String)com.yiyiaddon.m.b.a<"s36vim6zo5frvh","WgiVQPjoYrXzlJELdtGtYTZMRx2ji8x/KKkZfQ==",-3256075664446157448,-5993099738033231209,-5315421568278295170,-8683226110973650389>(),
         var0 -> {},
         16
      );
      this.bU = var1;
      this.bV = var3;
      this.bW = var5;
      this.GZ = var7;
      this.I = var8;
      this.u = var9;
   }

   private static float aA() {
      return 100.0F;
   }

   @Override
   public float c() {
      if (this.nR > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lml781njwjxg","KUzPTCd6IPoqXgtba/BqXHjRzAkADxLCUQMjSI8G3VA=",1605334257271918346,-2416622837764497304,6432644605633331636,-476702908108550566>()) {
            case 824514969:
               float var10000 = this.nR;
               switch ((int)com.yiyiaddon.m.b.a<"s26i1r7lu8ykkk","LLZCPrcNsKKocFAXq/pvfroi46U5J/kC0OxQ23byorQ=",2648957820596181774,6158487543610769389,4341005686000688064,-2853754150008539894>()) {
                  case 994824911:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var1 = aA();
         switch ((int)com.yiyiaddon.m.b.a<"s2u7h62rdhkmax","FUVYO+8gu9yWwLQlIKblCwDwGK/TnyBrjUQIIioLnyI=",7142231983982321240,-656322464997979722,-4522193590872410081,330951118135511636>()) {
            case -1186553884:
               return var1;
            default:
               throw null;
         }
      }
   }

   @Override
   public float d() {
      return 24.0F;
   }

   private void la() {
      boolean var10000;
      if (d == this) {
         label49:
         switch ((int)com.yiyiaddon.m.b.a<"s3f1ne68iu7lh2","6v0hVW+NBTU6/QBOulOpfX69Qbe6VaHYTg9ouR5B72g=",6891073303474079410,-4712302296670493171,2270136132760218199,7745423939452616304>()) {
            case 705626020:
               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s3d9j71wrl5vmy","cwQdQzy6vWIcDdEt8oTEvv1Wki2UqA2l98RkgqnQOp4=",7285021227154490658,4594934417871948487,-5011133418226616941,-5576149725113177642>()) {
                  case 686086756:
                     break label49;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s1jt2bplh7r5ud","pMxTWlYEx0C0WbCfCdA+rVX66cpqigTUuxuJ+CQ2EcA=",2998455845696415042,8857393280040379464,68495873468116031,-7138239954543436859>()) {
            case 1312484704:
               break;
            default:
               throw null;
         }
      }

      boolean var1 = var10000;
      if (var1 == this.gI) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vnsyq05mxkot","NUSyYvla9qcY1BX1YJcodQgmYq2PP1rV+8LMYzTyJLw=",5322015066756094963,-1622455334353637467,1362830426706628452,-5885082549666615118>()) {
            case -1183466682:
               return;
            default:
               throw null;
         }
      } else {
         this.gI = var1;
         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ay587jlszkrn","Qub9LVFN6NrIs8QHFOBVFtKXsEAQsOzachhpF/Z8UKc=",-6506834693838462723,8558228702529019703,-3651566147135481553,-4142747767944532180>()) {
               case -655551652:
                  this.Ha = this.b(this.v());
                  return;
               default:
                  throw null;
            }
         } else if (this.Ha != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2qmnz604wj5h6","yojBuWds/2j/bB/eIYgKvM4hYKImAldxKqFfO0cot0Q=",6264053008767469973,3489722014295094207,1405831394622363078,-5998694138969650116>()) {
               case 2128158611:
                  Double var2 = e(this.Ha);
                  if (var2 != null) {
                     label37:
                     switch ((int)com.yiyiaddon.m.b.a<"s2p4d9j4miciae","KijpARcfk+PYQIr7L5ejooaZSDT79A6kqkdr51ow7mM=",3281146229567913184,3111121829103046844,-1590842657550067574,-8104703513805078198>()) {
                        case -2146131272:
                           this.u.accept(this.c(var2));
                           switch ((int)com.yiyiaddon.m.b.a<"s2yk0ie1m8ppsd","E9jnIdCHs39cqzIv9M3tnvhSoQrgMAcMqDYUAz9A4jU=",4388773077189874833,-955365995005204972,6721579668935478987,8491371821581440666>()) {
                              case -774063079:
                                 break label37;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.Ha = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s2jjlq0hxzijm2","sQvUtcEzy48m0c74mebJNqdy1Msx3lzePNtqb0ItIeg=",3352386010642199684,-913225236220953752,3744828119383690105,6048375049422908969>()) {
                     case -1274201787:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private double v() {
      Double var1 = this.I.get();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1upjca37dmzyq","dyUNwk/bJYr8Uq88v5Xttq/k5qa5mIY9Xk6Xv/DYmYE=",-8337997826490392284,2895755680764198029,1092527354505536943,4405161803773148191>()) {
            case -429502468:
               double var10000 = this.bU;
               switch ((int)com.yiyiaddon.m.b.a<"sgtfr219tuatr","o5J4OUfVMuMCikxQCycCnrS/txE7zd7bKYhmFLYDdlk=",6699899795577619126,1504133135969660166,-2342575649517817047,-15165669581987337>()) {
                  case 692135726:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         double var2 = this.c(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s2a71zyzqrt263","3DlSZg9GqXWYkjW+WYknOfzB6B0KCVBVAhNqUWwoMi8=",-6805047758336546341,-7996508395060018982,-4320512647493103343,-2519809735868794407>()) {
            case -1904455286:
               return var2;
            default:
               throw null;
         }
      }
   }

   private double c(double var1) {
      return Math.max(this.bU, Math.min(this.bV, var1));
   }

   private String b(double var1) {
      return String.format(Locale.ROOT, this.GZ, this.c(var1));
   }

   private String gT() {
      if (this.Ha != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dq9wbgvc5oo0","7V+78Ik+rgS/VpVXfhY5xXDDHgPYl+HedvBZlejAPTc=",-5802911266916584884,-3430697903096439792,-3254574913676077694,-2803315354808239137>()) {
            case 134065500:
               String var10000 = this.Ha;
               switch ((int)com.yiyiaddon.m.b.a<"s3m72r4vsc3i1w","WuCgcfTu0Rkb5tTM8W0x9fCzdSiv7W0HX+zw74XlY4U=",6743694893811907130,6744709053426305651,-1236052680889481562,6709020411671172739>()) {
                  case 2077632642:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = this.b(this.v());
         switch ((int)com.yiyiaddon.m.b.a<"sq89q7xxq6h1h","x7NlmIjueL6m06E534iNP7T9VzkP//ys3UP3aBjlpME=",2355961039398357089,-7156951747259620666,7515250664659719790,2938929918987705907>()) {
            case -254647177:
               return var1;
            default:
               throw null;
         }
      }
   }

   @Override
   protected String gU() {
      return this.gT();
   }

   @Override
   protected String gV() {
      return this.gT();
   }

   @Override
   protected void bB(String var1) {
      String var10002;
      if (var1 == null) {
         label25:
         switch ((int)com.yiyiaddon.m.b.a<"s2cokbjxxx38v","EQaAz9Mv79zYwtZwaRgBQynYbf7xFX6Xby6Hg8rBbn0=",5759396775385270889,6694909387422668104,5989121328456541494,1730292965300953860>()) {
            case -1913757788:
               var10002 = (String)com.yiyiaddon.m.b.a<"s36vim6zo5frvh","WgiVQPjoYrXzlJELdtGtYTZMRx2ji8x/KKkZfQ==",-3256075664446157448,-5993099738033231209,-5315421568278295170,-8683226110973650389>();
               switch ((int)com.yiyiaddon.m.b.a<"s3afdqpe2uc4au","Kk7gTlRNhmhRVzp8FKFPDjmbfzeqF68+bxsmU+bVv4I=",8451853931543587444,2379609631142066225,590334995389498502,5685034641545832628>()) {
                  case 1288423965:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s1ms9ifhmunxpn","VTRZ+s6Uhs1EayEq7m1BtIKtjzWB7hz3obUemQ6xGso=",6759736445775456949,-1283272637880363853,-3012995394748438476,6084303122932431875>()) {
            case 1682222847:
               break;
            default:
               throw null;
         }
      }

      this.Ha = this.cp(var10002);
      Double var2 = e(this.Ha);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s20envdbddgblf","g+rEwejQ6aJhqvlaWh3epHtjcKyiB6y1wB764jEgGxk=",7158152751453781110,1740128556750334500,-3178222395810662820,-8020170396105838989>()) {
            case -38893587:
               this.u.accept(this.c(var2));
               switch ((int)com.yiyiaddon.m.b.a<"s6drf3hbibr60","YKSQi19jdQ+5Np9UvZELLlV1YIt9IPTEEPxh0rQig2g=",-6807069097876223485,-8674326900696633148,8841297114995059214,6828488124215614042>()) {
                  case 916559308:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static Double e(String var0) {
      if (var0 == null) {
         return null;
      }

      String var1 = var0.strip();
      if (var1.isEmpty()) {
         return null;
      }

      int var2 = 0;
      boolean var3 = false;
      boolean var4 = false;

      for (int var5 = 0; var5 < var1.length(); var5++) {
         char var6 = var1.charAt(var5);
         if (var5 != 0 || var6 != '+' && var6 != '-') {
            if (var6 >= '0' && var6 <= '9') {
               var4 = true;
               var2 = var5 + 1;
            } else {
               if (var6 != '.' || var3) {
                  break;
               }

               var3 = true;
               var2 = var5 + 1;
            }
         } else {
            var2 = 1;
         }
      }

      if (!var4) {
         return null;
      }

      try {
         return Double.parseDouble(var1.substring(0, var2));
      } catch (NumberFormatException var7) {
         return null;
      }
   }

   private void m(int var1) {
      double var2 = this.c(this.v() + var1 * this.bW);
      this.u.accept(var2);
      if (d == this) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nu2hb0aam9sb","HKBkk7vftmYBuqEll14PnA1hxy19IAEXhwZvWaBKKo4=",-8338709564957034166,-7446079478152690691,704335243063646241,3105133967835469076>()) {
            case 1257259210:
               this.Ha = this.b(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s28sc35wp00qo2","l6f88zV1o9dj4IXTEJ4pNxWld5dBlTzXvuBzqIK5l3U=",3006369851216321816,559221956660515104,986432640389776092,-5251338802764646281>()) {
                  case -269361603:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      this.la();
      com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
      this.a(
         var1,
         var2,
         var3,
         var4,
         (String)com.yiyiaddon.m.b.a<"s9dkjskeqflm1","iTM6NgjDZEFKMywtoWbyHsR9baO0eXN2a/8snhXO",6750311099180233814,-5562606433789284488,7956531068821715143,5682135107749761289>(),
         this.k,
         var5
      );
      this.a(
         var1,
         var2 + aA() - 17.0F,
         var3,
         var4,
         (String)com.yiyiaddon.m.b.a<"swmxnl67h2xm2","YCDocA81GSPFiknpvTyOWtsyhy+nPJ8SVebVx1QO",-4103706219810830798,254179548735583238,4067624686697876192,276599175274891129>(),
         this.l,
         var5
      );
      this.nR = 60.0F;

      try {
         var1.save();
         var1.translate(var2 + 17.0F + 3.0F, var3);

         try {
            super.b(var1, 0.0F, 0.0F, var4);
         } finally {
            var1.restore();
         }
      } finally {
         this.nR = -1.0F;
      }
   }

   private void a(Canvas var1, float var2, float var3, float var4, String var5, com.yiyiaddon.l.a.b var6, com.yiyiaddon.l.i.c var7) {
      this.N.setColor(a(var7.vk, com.yiyiaddon.l.i.c.y(var4)));
      boolean var8 = var6.a(var1, var2, var3, 17.0F, this.d());
      var1.drawRRect(RRect.makeXYWH(var2, var3, 17.0F, this.d(), 7.0F), this.N);
      float var9 = com.yiyiaddon.l.g.a.a(var5, 12.0F);
      com.yiyiaddon.l.g.a.c(var1, var5, var2 + (17.0F - var9) * 0.5F, var3 + 16.0F, 12.0F, a(var7.vh, var4));
      if (var8) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mazs7b2q0wjm","/TWmsFUCPPksPCkHnXXb8OvXH+lQnySWe92qaiYKCA0=",7778055605673405934,-479241857904012511,-5191778685063659430,-7253451837693363514>()) {
            case -711824295:
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s1ewve45ku5an8","dF/0O5FgukLLlWAFWax7MdcECa9oPFo+gZXvK5eEoow=",-6938645016499996520,-9193907999820177683,-2869934737036203906,340460905221245065>()) {
                  case -1660414977:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s11zhf7mgbusja","lvkoJ3ZECyThvQ45vz7GSiOI4CRxH3kWntyuAWHBoNk=",-3591032986920758543,1686196251673040179,6930502412617217091,2707348218497665884>()) {
            case 471984088:
               return false;
            default:
               throw null;
         }
      } else if (!(var2 < var4)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1k0mhrvtwozz7","o6CzxlOKlzKdGPAlkzDti7DBcpv/Dx4Gtky/o3f1K7g=",-4925939501117110831,-1947547428468321859,-5199120462605091292,-6305715328421840834>()) {
            case 781945956:
               if (!(var2 > var4 + this.d())) {
                  if (var1 >= var3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3m8hnwvg3jn3","CuDg99Vt23rnTw5SY8KuAhGWvtIhS5RfQpzXwEnMi+g=",978285580821942773,8825550462991499590,-6117811441588403090,-8903517201235598130>()) {
                        case 1738652373:
                           if (var1 <= var3 + 17.0F) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2g362i2a29bm","HU9yj+2OxrWSlbTBOpvccYJFWjQ7yNyuCATPOqztnvY=",-8728505820305962055,148334758703791130,-5784660636796968518,2685489636780239710>()) {
                                 case 1691763366:
                                    this.k.jL();
                                    this.m(-1);
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

                  float var6 = var3 + 17.0F + 3.0F;
                  if (var1 >= var6) {
                     switch ((int)com.yiyiaddon.m.b.a<"snq5onweyj1vw","OihxMnvRvM2t2x3ZrZHtBAHElOfwobx2StSB8ywQ0cc=",2831763723768678152,-3040592507163120250,1896606248425305407,9024399629499168486>()) {
                        case -1674754308:
                           if (var1 <= var6 + 60.0F) {
                              switch ((int)com.yiyiaddon.m.b.a<"s242cjdxh0yxn1","lhr+00Sbffcu0BcFjPpPIrQu9V2RBpPzZcrxMkhJzHY=",5401907673735069343,-8458024993804266206,-3329237188588231938,-9072379718476330714>()) {
                                 case -289627849:
                                    return super.a(var1, var2, var6, var4, var5);
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (var1 >= var6 + 60.0F + 3.0F) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3limekbbvoanz","PXfiExuc3qEc2UsRflJz3qEY/atmP96nZ7NGQpk+CX8=",-6798313025589006393,2048173807331325186,-351646877832534119,-1852982580595535659>()) {
                        case 1520358759:
                           if (var1 <= var3 + aA()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2fd5nyumaia8e","Q6+ipbN1cNvzGcbgFxfNEZ/yp2wG4lNzNankIni14oc=",-6155172943020966319,-1280860334303273564,-6615046012660461113,-8561354332216707940>()) {
                                 case 691763342:
                                    this.l.jL();
                                    this.m(1);
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

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ogrhfxtvm2wa","NTnTljjn/vsy8exq5TuT/e9HrO/Y3zbyQDIyzoYratQ=",-1868661518544814399,3269801690698319721,3153968855955216278,-4469613678445417179>()) {
                     case 1774363340:
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

   @Override
   public void a(float var1) {
      super.a(var1);
      this.k.a(var1);
      this.l.a(var1);
   }

   @Override
   public boolean cB() {
      if (!super.cB()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qcwynmlh7vac","nGN4jrzRXLo8vOgMCx2/UnwvhSFOmowjjIr095r72gM=",-4024308083277089280,1436890243540729912,-3990284843014307675,5450929430118472477>()) {
            case -741025361:
               if (this.k.fP()) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s3r4ckmyc0md20","yRyObTxuzv/v5MZd0PGNqqSo0+Ndoz7D9Keorwyb84Y=",5513045406105100477,-9047054840703165050,8258410189401074696,4613119795002198896>()) {
                     case 19067290:
                        if (this.l.fP()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2z0xq37iqfquu","4ltNq6rYKNNJf9zsAoIhvie7r/iHRUsGHOfwsVfu92k=",-7452429013053606477,-1875205052234545169,-6653624173008108980,-190659624150570246>()) {
                              case 2030148292:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3kc91jbd4amfp","19GlXD7OfoTLYR93YCBc9KkjkIghp7lQWEpgq9Lx+KE=",-6516547641097961554,1726427165510157133,-1711264350494940720,-3968391695671872280>()) {
                           case 1360830400:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"s38b46hoolh5r5","iB0pSoz9NTZDWVrs/6J9GAa+kEQGyFBWxhOxRJ+Sd98=",-2626134119313785634,-3139796797694261122,-3998507432197916656,-8412343481735953488>()) {
         case -947066817:
            return true;
         default:
            throw null;
      }
   }
}

package com.yiyiaddon.e.d.c;

import net.minecraft.client.Minecraft;

public final class h {
   private final Minecraft p;
   private final com.yiyiaddon.e.d.a.a d;
   private final Runnable b;
   private com.yiyiaddon.e.d.b.a a;
   private int cu;
   private boolean L;

   public h(Minecraft var1, com.yiyiaddon.e.d.a.a var2, Runnable var3) {
      this.p = var1;
      this.d = var2;
      this.b = var3;
   }

   public void f() {
      this.a = null;
      this.cu = 0;
      this.L = false;
   }

   public void a(com.yiyiaddon.e.d.b.a var1) {
      this.a = var1;
      this.cu = 0;
      this.L = true;
   }

   public void ae() {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s29ihz1vdlny07","x8Fl+360mcIM633fkvxiZ+acGdCdA6wyLEJlEEIz1fg=",7929924809160723266,3216651307164994832,7386672998459754700,7920317492860134217>()) {
            case 595270907:
               if (this.a != null) {
                  this.cu++;
                  if (this.cu >= this.d.cc) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1y3fktrumso9t","EdALiWIb9Uma6kyhnjvuwQaVJTMeRGdE+0KYSFRlmQA=",6018714790070521954,2078525103107649244,-445918213694449615,8800914604963010482>()) {
                        case 1157441627:
                           String var1 = this.d.cm;
                           if (!var1.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxdba5n4mqye5","4du6LGC4MqBbn6BqsqAOlKBZZZ5/xX5sE9MjVf1rbSM=",-3524588081557636988,-2412778497570185972,-4727691473828323570,7290757474607649505>()) {
                                 case -1617311693:
                                    String var2 = this.a.t(var1);
                                    if (this.p.getConnection() != null) {
                                       label30:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2i7bcai6bqsoh","Bh2/I8pnINUG1ja/Ly4EkGZKZ7ghzI0Idurf4jk048Q=",-4092644832379217452,-7043376921481972992,3537166999787845150,-5489586582152284368>()) {
                                          case 487343855:
                                             this.p.getConnection().sendCommand(var2);
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ra92i8uugsgx","vF1W1u4Sg0G7PP0q28T2RDMiTRnzmU2AL39wraF1A74=",-849811883295841826,-5362348943407649507,-7361259325611146590,-1450658639806479372>()) {
                                                case 1346153895:
                                                   break label30;
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

                           this.L = false;
                           this.b.run();
                           switch ((int)com.yiyiaddon.m.b.a<"s11ogitfq893pv","o23uEosKLHte+OwXmeb0f11RcW/6XjsrFILCZvY5ALA=",-8638120110634462699,-4980978244407813648,-4183104887470707315,-1490740219894148031>()) {
                              case -481504859:
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
                  switch ((int)com.yiyiaddon.m.b.a<"saz9avfebwysl","XH2WDQhKmZX7kGvOg7Yjr4d6QXD+PTOLtOezu2J+hDw=",8994567152823613047,8261713108633967739,1536068500859519573,5181756602867033984>()) {
                     case -1363038476:
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

   public boolean isActive() {
      return this.L;
   }
}

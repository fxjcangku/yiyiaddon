package com.yiyiaddon.e.d.c;

import net.minecraft.client.Minecraft;

public final class j {
   private final Minecraft q;
   private final com.yiyiaddon.e.d.a.a f;
   private final Runnable c;
   private com.yiyiaddon.e.d.b.a b;
   private int cu;
   private boolean L;

   public j(Minecraft var1, com.yiyiaddon.e.d.a.a var2, Runnable var3) {
      this.q = var1;
      this.f = var2;
      this.c = var3;
   }

   public void f() {
      this.b = null;
      this.cu = 0;
      this.L = false;
   }

   public void a(com.yiyiaddon.e.d.b.a var1) {
      this.b = var1;
      this.cu = 0;
      this.L = true;
   }

   public void ae() {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"sz5ujfgh91sly","jgtOhUA12b8RLy03eabXuE0q7GCvvXvTvHHtPM99qYg=",4745252005999408736,4889085573363900754,-8282704884515657584,6877784226927100579>()) {
            case 1516270722:
               if (this.b != null) {
                  this.cu++;
                  if (this.cu >= this.f.cd) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1o31mdnxkxy5a","4UeaSfOub7jau2jsrz5sUIhxKgaM4HO0bC7T9YaJYH4=",3277925463531988261,-291040060266676244,-123748678014904358,-7413332311859755885>()) {
                        case 1156254556:
                           String var1 = this.f.cn;
                           if (!var1.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"skaszav7z2lu2","TE0z/HAIDQuHZ6qRti8BNQQGC6ZsjL/L+jE3tUYeip4=",6767571981439008732,-2201249491985723181,-4157875798511416431,-6244807486764264143>()) {
                                 case -1942309540:
                                    String var2 = this.b.t(var1);
                                    if (this.q.getConnection() != null) {
                                       label30:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2u1ib2lumo6pp","nB1bDP+VK0M9vNQT1biHexpGllWVIz/0Im2ewJ4vnAk=",6266866822221644467,8432716319498058123,-1771921665709214150,-527932648871621106>()) {
                                          case 729624092:
                                             this.q.getConnection().sendCommand(var2);
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ngxzb8dhia3g","XnGUR0ac4NdOKl4doBYpT1QhPLZMUG/gETd+QRcc1I8=",2639439431122396982,-4153307057081967748,-7959710045080502797,5626531557706623902>()) {
                                                case -1469795556:
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
                           this.c.run();
                           switch ((int)com.yiyiaddon.m.b.a<"s177fmjjb2kucy","iKNivQ3xX/FNC7AvwonXz4Pu71t3koAWw17KQwKDX9A=",-2879645501709544013,-4696686903330001567,2958817583274090186,1328115165541684>()) {
                              case -58061781:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s1i6jcgh35qawn","kI+nVj05rogetasXvhKN9rlXZN6vR/fo9/ZUWKfIyoo=",2963388816203811345,-2446583476434901346,3207088113460825395,449973956322927999>()) {
                     case 1611566037:
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

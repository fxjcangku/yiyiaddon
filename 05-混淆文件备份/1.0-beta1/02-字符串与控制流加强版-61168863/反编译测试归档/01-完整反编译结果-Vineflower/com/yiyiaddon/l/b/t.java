package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.function.Supplier;

public final class t implements g {
   public static final float hy = 24.0F;
   private static final float hz = 13.0F;
   private static final float hA = 12.0F;
   private static final float hB = 80.0F;
   private final String EW;
   private final com.yiyiaddon.l.j.m c;
   private Supplier<String> i;

   public t(com.yiyiaddon.l.j.m var1) {
      this(
         (String)com.yiyiaddon.m.b.a<"s2b04j6f6hdczo","NOILN0bDYASdKNUH9kSMfyJ3fhOqp31wvZgM1A==",2439343946036944479,6484298737983010624,2586034288210708243,2573759569951302623>(),
         var1
      );
   }

   public t(String var1, com.yiyiaddon.l.j.m var2) {
      this.EW = var1 == null
         ? (String)com.yiyiaddon.m.b.a<"s2b04j6f6hdczo","NOILN0bDYASdKNUH9kSMfyJ3fhOqp31wvZgM1A==",2439343946036944479,6484298737983010624,2586034288210708243,2573759569951302623>()
         : var1;
      this.c = var2;
   }

   public t a(Supplier<String> var1) {
      this.i = var1;
      return this;
   }

   private float a(float var1) {
      return var1 + (24.0F - this.c.d()) / 2.0F;
   }

   private float f(float var1, float var2) {
      float var10000;
      if (this.EW.isEmpty()) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s22iriwjkr4w0t","UrR8WpSLSQWFW/XHW+WI6As+yLaiDWapbFx6YixbW80=",2202051460723343066,5069076591213703652,7196546433676005610,3556203183927795006>()) {
            case 1578481978:
               var10000 = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s36jl32vsa9h41","rqc/eTpR/XvAHN6Kw/s3ZqaZgLWMcJAU7/lFoIhZTRE=",-1091933817174153350,-2053532839201117904,4321986716693464665,1975555086492209461>()) {
                  case 704561531:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.m(var1);
         switch ((int)com.yiyiaddon.m.b.a<"sxrumr80rhd0b","i8glmVP3rCk+Zxt98jSwrQ5BRC5+U4qePBgh3kYcqmg=",-3713779184952705597,504126568695628717,-4161541733124359916,-5332831347768526151>()) {
            case -1173952168:
               break;
            default:
               throw null;
         }
      }

      float var3 = var10000;
      this.c.a(Math.max(80.0F, var1 + var2 - var3));
      return var3;
   }

   private float m(float var1) {
      return var1 + 14.0F + com.yiyiaddon.l.g.d.a(this.EW, 13.0F, false) + 12.0F;
   }

   @Override
   public float b() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      this.c.a(var1);
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = this.f(var2, var4);
      if (!this.EW.isEmpty()) {
         label55:
         switch ((int)com.yiyiaddon.m.b.a<"s3v8wi57h2z26a","VuzarPFXyP4JtjUDsc/agS8hij9N3bqq7wLDm2RRMFg=",8255097229318867860,-8160956074188811252,6410110096961470447,-8321041020442190839>()) {
            case -1056773579:
               com.yiyiaddon.l.g.d.a(var1, this.EW, var2 + 14.0F, d.c(var3 + 12.0F, 13.0F), 13.0F, com.yiyiaddon.l.i.c.a().uT, var5, false);
               switch ((int)com.yiyiaddon.m.b.a<"s1tcw5ftiphlye","22x2dXV8F5vUNizx/6UFQDtAz4JZUj1GeovaGsMtPtg=",-3450811749249894063,2891277157103541981,-7020919606912247350,327226993279625404>()) {
                  case 1507862732:
                     break label55;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      boolean var10000;
      label65: {
         if (var6 >= var2) {
            switch ((int)com.yiyiaddon.m.b.a<"sokc5j3dnrpl3","OjoIWrlo34MZjS32JRr6LZBbFR7H2JWp3mf5Im++2qM=",3306264055151030221,-3852521203848270335,7239251379917070006,4194703685406257118>()) {
               case -861843276:
                  if (var6 <= var2 + var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2bxahu2y12u48","fsjd4V5+R44lHft6/0l1z7Sg0w8FOikIhuCgnh/AVpU=",-5242963899408291822,9222297438854919444,-8472769595301708100,-6677164622406175441>()) {
                        case -1696541345:
                           if (var7 >= var3) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1ohbtfs5fud74","yg45+KKREaObqqsw/g2UaVCqHl/rzY8SRXiSNWG82/Q=",7257555198389330462,6291297839633693268,939000622406534702,-2391869167548810113>()) {
                                 case 1900490504:
                                    if (var7 <= var3 + 24.0F) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1zlc58m0cxq2d","mLVUn4U/XLWiIiav1vunT85QWLzLGfCOQFJXKVE6mwU=",8780924164493266327,-3726289628722112730,-1197772958498619782,-8342265254157766315>()) {
                                          case -1108440753:
                                             var10000 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"s19k5wuma966jx","uYK0xD47To5MyyCmgn7Nj0CJyFp9PWVHEIsC4Kyqi6Y=",6964061758031869510,7186376461546231751,-1612709477366052340,-8767012320839612172>()) {
                                                case 926402467:
                                                   break label65;
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
                  break;
               default:
                  throw null;
            }
         }

         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s2h0lxkgg3v3ga","S6Gd1Y4ZWuP3jQR7ekSM1rwzr40F4zRn6irIx8f98BA=",-6079142194921616928,4569538745797798527,1793544424280862045,5440511138150421103>()) {
            case -2115344241:
               break;
            default:
               throw null;
         }
      }

      boolean var9 = var10000;
      if (var9) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nhqxd5hk1u9y","Ky5cIpVHsjzCe+1Q6BCeuPAW2PBgKiW0tZtsOyVeHtE=",7444856181198276670,-5077150650768000891,-2714160823192196253,-6890449247137220053>()) {
            case -820735405:
               if (this.i != null) {
                  label37:
                  switch ((int)com.yiyiaddon.m.b.a<"s34wf29lkfk18j","2lslcXZK/UrfMJ502ydfhGYdfYNsFDRGJ2+d4miTpKQ=",-9008318093362239357,-2459417181695683282,1011785713308274836,4441095212323750251>()) {
                     case 586470250:
                        com.yiyiaddon.l.g.j.c(this.i.get(), var6, var7);
                        switch ((int)com.yiyiaddon.m.b.a<"sjemgaymln3cn","nEN6ohPFuVH5JlM5ow4Isb81eN1HmN6HjQWdugibNWw=",-7906530251620826232,-4170633224894726457,-3248200702057099915,5257913445039861940>()) {
                           case 612236210:
                              break label37;
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

      this.c.a(var6, var7, var8, this.a(var3), this.c.c());
      this.c.b(var1, var8, this.a(var3), var5);
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!(var2 < var4)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3i0ue5u5jj91o","1ubTcG3UFEsu5ADIajkOcfHNDJjpiNmXZNtH99MGI1U=",-590123290206950191,4412334696045819258,5682884142641092548,-8200955222197713331>()) {
            case 1291201926:
               if (!(var2 > var4 + 24.0F)) {
                  return this.c.a(var1, var2, this.f(var3, var5), this.a(var4), var6);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wo35qts6r52q","kOxe3RrxJ1FaKZzp5N3W/mOd2yjDoh182Gy+ncvNxDc=",-1042720701044462652,-6697439116417002936,-587376989197025546,3314920315599983665>()) {
                     case -1557285501:
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
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return this.c.a(var1, var2, this.f(var3, var5), this.a(var4));
   }
}

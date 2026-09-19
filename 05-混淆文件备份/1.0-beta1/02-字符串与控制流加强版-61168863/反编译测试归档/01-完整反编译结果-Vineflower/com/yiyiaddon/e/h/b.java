package com.yiyiaddon.e.h;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.h.e.h;
import com.yiyiaddon.g.c.d;
import com.yiyiaddon.i.c;
import com.yiyiaddon.l.f.i;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public final class b extends com.yiyiaddon.d.b.a {
   public static final String kE = "id_identify";
   public static final String kF = "ID识别";
   private static final String kG = (String)com.yiyiaddon.m.b.a<"s395cde0s0c648","QUZh1wMdAMS84xo9Ij4PvywvrmY6WfuFcDP+QCRR",-8959933408258637560,2782690277746317366,8258681650302797920,268662632138646901>();
   private static final int eH = 8;
   private final com.yiyiaddon.e.h.b.a a = new com.yiyiaddon.e.h.b.a();
   private int eI;

   public b() {
      super(
         (String)com.yiyiaddon.m.b.a<"s1i5nr38bmjgdp","ywpcQepsJARqmDi7MKv0mXswfe0bp1GxRIfBk/FXK3Qj7DqOd8F274CvtoVFkloimW4=",-5241525984655368942,-763203105047533758,4642978336352252161,3703816343837864559>(),
         (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
         (String)com.yiyiaddon.m.b.a<"s1q5412v3006tc","HtKdeQIfrN7Jp3XhlvUWgbhM1aMexEs8S/XHjsDMdFvM4NUM4RnCDg==",-4172046304852133733,-5402306846573904821,-3298652659423850867,-7644373265462456958>(),
         (String)com.yiyiaddon.m.b.a<"s1si8tye833er9","tf5F+v2nVo1kvDrDVh7TfAEyHb0YmiyXPcn29O1NeYu+IxB23K9SEBfLhgqzxYaQB9sO0yaldRLjVLw3KMIs/AxvBeiQfr16aKgZNXE6GQwIqw==",-7298484496510319753,8310185837691317573,-7205778644813694792,-3797768083511945229>()
      );
   }

   @Override
   public String a() {
      return (String)com.yiyiaddon.m.b.a<"s701dbjnabrvz","uHmzcsqXtjZ1Z6kkT9lMHY5+Fd21Xvq1e1Bkzf4mx7RLLSd2QqgntFygN1WvUsmM",6797800208707731656,-3378006757508283726,2591632804011454697,-6657499876441238484>();
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s395cde0s0c648","QUZh1wMdAMS84xo9Ij4PvywvrmY6WfuFcDP+QCRR",-8959933408258637560,2782690277746317366,8258681650302797920,268662632138646901>();
   }

   @Override
   public int i() {
      return 10;
   }

   @Override
   public String x() {
      return (String)com.yiyiaddon.m.b.a<"sa6d7rncsx10w","PD3G46i9R0G+RXMxgAGc4EG/pJmlFQbUijdfwKoZsVCKmhsHVDM=",-8411613874932853037,5426301352026829272,-3536982717420788228,9214914400073851055>();
   }

   @Override
   public List<String> f() {
      ArrayList var1 = new ArrayList();
      if (!c.fo()) {
         switch ((int)com.yiyiaddon.m.b.a<"s272toq5yto4b6","lhjbzDQnzzD1A86yPgz6kjrPpsQl2zvliW2Ts2qdYCc=",8227476700762956602,7334963767051547841,2811816200660097847,7875262445857125204>()) {
            case -1533789843:
               var1.add(
                  (String)com.yiyiaddon.m.b.a<"s1nfrbworul1c9","zj1Y3L77yYUoZU9IT9V9HnRgAwy26qZhex3x4hIvM9fip0D+vFg=",-4003750869684508831,-2538846891769655805,-2636308119923816497,117785345794283344>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3bn37jrokuz20","66LrUTbHsbskmKdJ6UJDKTFrVDOYH1kFm0iDz+s9EjA=",5439822415346251523,-150813891472853422,-8984542554130308188,-3258390598536041829>()) {
                  case 1823015460:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   @Override
   protected void m() {
      com.yiyiaddon.k.b.a.a().d();
      this.di();
      this.a();
      this.eI = 8;
   }

   @Override
   public void b(Minecraft var1) {
      if (this.eI <= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x7q2o328jaha","wyz/Jmc5F9/qq6MfITg9E4VoeFQxxV9S2B7L67Hmcbo=",3175101401600425376,1286299682172928318,-6444104110059674075,-3668374634003629900>()) {
            case -850649128:
               return;
            default:
               throw null;
         }
      } else if (--this.eI == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1595q5ua0o5e5","nBn3hMX+TnLjHsZo+Xv6HasoyINDY6UkULdNrgP0y2k=",297806569086859212,-7316772590373021722,-2444307467619360711,3373995388234577407>()) {
            case 103695887:
               this.dj();
               switch ((int)com.yiyiaddon.m.b.a<"s1ln4nb04438kz","gQ51Op5Caku7lDrOZ79yCE+9pe/ggi/50Ap6WcLOyoo=",-5425606024884952585,-5056892735401303322,9222060748577388268,-4419223955696965881>()) {
                  case -1079919304:
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
   protected void n() {
      this.eI = 0;
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return EnumSet.of(com.yiyiaddon.d.a.c.JOIN_SERVER);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1.a() != com.yiyiaddon.d.a.c.JOIN_SERVER) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jz307wdqua3z","dCi4m+bH/OXjThaLdwX/E5z57j5PIUQgFggLs809JhU=",-8115477972687733286,2123169162833328651,-8260194718636855651,-3358059316733373895>()) {
            case 552090788:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.k.b.a.a().C();
         this.di();
      }
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
      com.yiyiaddon.i.e.a.x(this.a.bd());
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.h.e.i(this);
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.h.a.a(this));
   }

   public com.yiyiaddon.e.h.c.a a() {
      if (this.a.b() == com.yiyiaddon.g.c.c.CROSSHAIR_BLOCK) {
         switch ((int)com.yiyiaddon.m.b.a<"sty22l5reqeyh","FfUDAXjS4iWToX2S8DlryGW7qTcXI5/xMltQA3L06wg=",-4125599274189744635,-8184360366755135255,2973789540648392865,-5321205491201585259>()) {
            case -414160988:
               com.yiyiaddon.e.h.c.a var10000 = this.d();
               switch ((int)com.yiyiaddon.m.b.a<"s1x89wel9pajyk","JmYSjiq1x/Gojwn9wCawx2THykkKdfBtMv44hqjMHaU=",-8642643912001508591,1827311041734098426,-4269271616579733818,-5061089878193892712>()) {
                  case 1764082893:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.h.c.a var1 = this.b();
         switch ((int)com.yiyiaddon.m.b.a<"s23p9gq6wr86xi","CGr+fRqZrauxw1s1rlCN9p6BWROW635YIbGdmUNtWg8=",1046227807088417400,-136850551819825749,4320848734115756312,-1338443015037820568>()) {
            case 1714006051:
               return var1;
            default:
               throw null;
         }
      }
   }

   public com.yiyiaddon.e.h.c.a b() {
      if (this.a.b() != com.yiyiaddon.g.c.c.AUTO_SAVE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2r9vt2schw9xr","LG4fJcEs/roKn3ThW1yWK3BqoOjxXdle35wxoil0DbU=",-2342139686888271317,-174813174258869370,-3080844674255909804,8328069684125247323>()) {
            case 1578889409:
               return this.c();
            default:
               throw null;
         }
      } else {
         return this.a(com.yiyiaddon.e.h.d.a.a(true));
      }
   }

   private com.yiyiaddon.e.h.c.a c() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28q15slr5gs1o","+Nhfvsk+zSWNpMZPkCMP2Eztk1IR2Ry/KrvB3aUEYh0=",9042603987433621672,-263314829891693663,-4366540608156147078,-7684038225488067068>()) {
            case -908014047:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                  (String)com.yiyiaddon.m.b.a<"suo2rxxbcr8il","qp//i8PiAZb2Ef0jKd9r8H5vMtBLPunWv05YTyYAlLRRFqGD4pFe1wwFRLyVew==",-626122593460903898,-3700579430393242626,-8614439438980975081,1188789734945772448>()
               );
               return null;
            default:
               throw null;
         }
      } else {
         ItemStack var2;
         label52: {
            var2 = var1.player.getItemInHand(InteractionHand.MAIN_HAND);
            if (var2 != null) {
               label44:
               switch ((int)com.yiyiaddon.m.b.a<"s2va0mmfy31h5","hjSSkc/tVU5MBt6H1iP3bFkF61x0gAsTiPS/RQ4BW3U=",-593480769887682225,4662176629856186371,1649293931251700012,-8977969915680361860>()) {
                  case -471206309:
                     if (!var2.isEmpty()) {
                        break label52;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2vbx8k1w1ylwf","/jaY9VC/+SaHDstdZ6h0I3zw+rj8np+JQjHL/7GHeEo=",3035317682795479324,-6232997649570385897,6440895641950242861,-7307315150826343000>()) {
                        case -1986842752:
                           break label44;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var2 = var1.player.getItemInHand(InteractionHand.OFF_HAND);
            switch ((int)com.yiyiaddon.m.b.a<"s2kevray2ojaan","6xPYJXaAFf1ebjfljtz3g3ae5FJkrX3dVgHNLyJ4KjQ=",-7630766863660353894,686034883581928739,6542327060749145920,-6141471825816815199>()) {
               case 1641002852:
                  break;
               default:
                  throw null;
            }
         }

         if (var2 != null) {
            label37:
            switch ((int)com.yiyiaddon.m.b.a<"s2knsxv7wcs7al","yUGHES5/xe6JEozLxqJpMfCsB+0v4tErHqIrBdzKc+A=",-6689367577780259594,-1688621293272583575,-1034222304672934763,1970761873285893287>()) {
               case -1173870989:
                  if (!var2.isEmpty()) {
                     d var3 = com.yiyiaddon.i.b.c.a(var2);
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1z6i6vnmbcbtt","DaFqByJZiDBRUGTcamVWK3zA3jt61j/A6OnbDUGgVYI=",1980772964196067571,8305187625977964754,998327499162998192,2625888487924992551>()) {
                           case 608597910:
                              com.yiyiaddon.d.c.a(
                                 (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                                 (String)com.yiyiaddon.m.b.a<"s2bn2xzobnlsqn","ICJ+WHKaF+m1XMoTpPSUcOtzfSqm7cKQdCzstxH5w9NNRihs+Szn6hDs8Hw=",-6279469794095171884,3589303178405487117,7198010889662868783,7486846211554402849>()
                              );
                              return null;
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.e.h.c.a var4 = com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.ITEM, var3.m(), List.of(), null, null);
                     var1.execute(() -> h.a(var3, var1.screen));
                     return var4;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3nk5nc4lfy390","myAh16mgd6TNZn3jCdnKWjklq/arVyk0ItyCKAzN55c=",-7303875762573706985,4439414101966846976,429509226614224365,5113891167111849601>()) {
                     case 974453745:
                        break label37;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
            (String)com.yiyiaddon.m.b.a<"se0vztz7k06u1","ZM6Ll4ZyT+yUnlb+3GsG3/RhPEGpNG2FC5YY9AEx8mQOUyIH0gs1gFylz0rDGUP/1179li00lBR470zWKjjESOcydZ90wA==",-6022340355597183251,-2160650969426619057,-8869627646810313043,2015205137176785438>()
         );
         return null;
      }
   }

   public com.yiyiaddon.e.h.c.a d() {
      if (this.a.b() != com.yiyiaddon.g.c.c.AUTO_SAVE) {
         switch ((int)com.yiyiaddon.m.b.a<"s3byfz6v8eh0cf","5EZF1x+cU+HZZFO6SrmzKs5/3NqWNGT5PYeLcQ4aAEU=",-3263456811852740923,-7654240862701398558,-5967775593151232404,7007874938179371466>()) {
            case -47904806:
               return this.f();
            default:
               throw null;
         }
      } else {
         return this.a(com.yiyiaddon.e.h.d.a.b(true));
      }
   }

   public com.yiyiaddon.e.h.c.a e() {
      return this.g();
   }

   private com.yiyiaddon.e.h.c.a f() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hfple5qcl5hi","eh2w6Ja3BXJlilSuguPPXHZbx+/78RnaccT8SSKFjFo=",-9033772790594459072,-3971408826126940696,-5892127822850687830,6106429087819247613>()) {
            case -166539323:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                  (String)com.yiyiaddon.m.b.a<"suo2rxxbcr8il","qp//i8PiAZb2Ef0jKd9r8H5vMtBLPunWv05YTyYAlLRRFqGD4pFe1wwFRLyVew==",-626122593460903898,-3700579430393242626,-8614439438980975081,1188789734945772448>()
               );
               return null;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.c.a var2 = com.yiyiaddon.i.b.a.a();
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1nll76s60uzz5","uiyWEMH4CtpJkPPEdUOCMtECqlgkryj/+vz+QVw9Qao=",117610422797719848,-827700938925298190,4329174730411571590,4245682083491640203>()) {
               case 5716843:
                  com.yiyiaddon.d.c.a(
                     (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                     (String)com.yiyiaddon.m.b.a<"s2s42jya5dfjwd","EaMdn59O7le7fHZCCl7danvUXVQf5p4fb8CMZlzL9NrjYcwvkCt6qYIcXv6SyCEmdqJIJRT9M6LZDADLb/fvmQIVlxw8aXIIx0U=",6558060450130354444,1108390278137322087,6158700236198817732,-447965041506528024>()
                  );
                  return null;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.h.c.a var3 = com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.BLOCK, var2.m(), List.of(), null, null);
            var1.execute(() -> h.a(var2, var1.screen));
            return var3;
         }
      }
   }

   private com.yiyiaddon.e.h.c.a g() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e3opxvy3ijcm","wN6C/kTbgjMvyKkrCHWb5d9h/872WP0obLBZUyVy4s8=",1199571406466328216,-1867480449681956099,-5471966285030017529,7237741992355715756>()) {
            case 469304180:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                  (String)com.yiyiaddon.m.b.a<"suo2rxxbcr8il","qp//i8PiAZb2Ef0jKd9r8H5vMtBLPunWv05YTyYAlLRRFqGD4pFe1wwFRLyVew==",-626122593460903898,-3700579430393242626,-8614439438980975081,1188789734945772448>()
               );
               return null;
            default:
               throw null;
         }
      } else if (var1.crosshairPickEntity == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hj4j3t6z6kdb","JnLMiKgXumTqwfKG2maZA7Q94wN6Op/Kz9gPs8wz7Hs=",959148673370139703,-4895794438613055645,6438622901591512784,-8574934489011082239>()) {
            case -983651723:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                  (String)com.yiyiaddon.m.b.a<"s3gwqqtde9roqg","vDy7ww1TN9+kml5HUlS/xDLoXnepaYeRcPbOE+anf0tqcQcV3O95wxppFhTlD6SRVs5fVVNp3jBEZipj",2635986996555779771,-412985979877328462,4691160238988805031,743493890790793200>()
               );
               return null;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.c.b var2 = com.yiyiaddon.i.b.b.a(var1.crosshairPickEntity);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s65m4jmpq5lag","4uGFBqsN8viFe8O5PkE1qqhIOz/UDfNxG9vgXndIhdY=",-7643837293135163820,-1475900329200856050,-1672453537001676472,-1901910136034137911>()) {
               case -1142622572:
                  com.yiyiaddon.d.c.a(
                     (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                     (String)com.yiyiaddon.m.b.a<"s3eojgljxmelpv","xxyTeF/GR5A4l/hGQXWNxTLANP+T36MtaGzKrTpRtXFGOx3sm+NAIcQJN5NDNxnAnXrMBxHWp4TvwYCP",-4456808100164528331,4600319304241132005,8987650351128110324,-6011254384839711495>()
                  );
                  return null;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.h.c.a var3 = com.yiyiaddon.e.h.c.a.a(com.yiyiaddon.e.h.c.a.a.ENTITY, var2.m(), List.of(), null, null);
            var1.execute(() -> h.a(var2, var1.screen));
            return var3;
         }
      }
   }

   public com.yiyiaddon.e.h.b.a a() {
      return this.a;
   }

   public List<String> aa() {
      return this.a.aa();
   }

   public int aK() {
      return this.a.aK();
   }

   public void r(int var1) {
      this.a.r(var1);
      this.I();
   }

   public boolean bd() {
      return this.a.bd();
   }

   public void i(boolean var1) {
      this.a.i(var1);
      com.yiyiaddon.i.e.a.x(var1);
      this.I();
   }

   private void I() {
      e.d(this);
   }

   private void di() {
      int var1 = com.yiyiaddon.e.h.d.a.aL();
      if (var1 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s37t0jegh5sb72","XEi6fTnsCo8C9oBlMTXS8NsFjowirYxwX4enUSgxM8k=",8402442810985156810,8804628555431544454,4789933232559762910,8720627610126101024>()) {
            case 522458749:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                  var1 + ""
               );
               switch ((int)com.yiyiaddon.m.b.a<"s168gud2rcigr2","Wi0xZQiBNu85DucPeIz3mMVvA+l1a26I7tEeWh22a/I=",-9045858916087096241,-5212956714092874552,6251125482387652489,1781797902969944328>()) {
                  case -1885562748:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.e.h.c.a a(com.yiyiaddon.e.h.c.a var1) {
      this.a(var1);
      return var1;
   }

   private void a(com.yiyiaddon.e.h.c.a var1) {
      com.yiyiaddon.e.h.c.a.a var2 = var1.a();
      if (!var1.bg()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1paxq30hhe7a7","xTg5YvZqif06IaoT87tsxRQt7YAFp398tmfo4+BcJzs=",-6455661582719421623,-1222407575724041221,-1064825248649662154,8137574362572739237>()) {
            case 1013321523:
               String var10000;
               if (var1.ab().isEmpty()) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s22h5wpkf79cl7","Fa1K6be/ruQ9iw7cW3pfiNoZLpurIV+FAafa+YrOedA=",-5290784100010121882,1835944972166565528,-4297483588612021991,6080564983411511324>()) {
                     case 1056994891:
                        var10000 = (String)com.yiyiaddon.m.b.a<"s2u7g1nuomgbc4","8TpMucCqiRVrba+Ajs54iS3V5NRKBlzUCCgOqdgMXE29ieTM",-170910934064726129,7732341035170729083,4037891681439338648,-6281257666950220468>();
                        switch ((int)com.yiyiaddon.m.b.a<"s2nxmlu7iyxym5","2FuYeuhDOVVyLBcOOGmaaQTsSBQLUYbXWdgMB7epmjk=",8425244718366074419,5067687308471213953,5140496951984567,6749489362230176500>()) {
                           case 1412433783:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = var1.ab().get(0).bi();
                  switch ((int)com.yiyiaddon.m.b.a<"s35ggiz91v8q6e","Dx4wjY4MGsaY+ijeFiIYsAwwqqUQHi+nZEjTFd5AeU0=",8215820100068851050,7022141955050150396,-5021089826744807138,2458638607465807497>()) {
                     case 635921943:
                        break;
                     default:
                        throw null;
                  }
               }

               String var3 = var10000;
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                  var3 + ""
               );
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
            var2.m() + var1.D()
         );
         if (var1.be()) {
            label44:
            switch ((int)com.yiyiaddon.m.b.a<"s3k7hphovvonk4","RDdk0YcjpicgjpSrypeHCWrx82Iu7y9+Zub33u+96Po=",-1579173996593923105,-6565166755119489912,-4266862083905652069,-1773293151904820113>()) {
               case 1188864902:
                  com.yiyiaddon.d.c.a(
                     (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                     com.yiyiaddon.d.d.a(
                        (String)com.yiyiaddon.m.b.a<"s1q2gc75d001k3","wHyaEfCRvstS9B3I6/tY9938geyuLkCXZCJac+kYpEzNV9do",-1234801119534480336,-1627973856305639949,-4953979092355708365,-7380559667266634489>(),
                        var1.bg() + ""
                     )
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"sa9bcn2wcm8zy","DS3HPDzgB25iLf0k6XXz9wZahDr4hjdTNOCsl35MtpA=",-6050610034575579878,4650561788585823561,-1913207905122011180,-539102027306605508>()) {
                     case 1479872790:
                        break label44;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.d.c.a(
               (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
               a(var2)
            );
            switch ((int)com.yiyiaddon.m.b.a<"s3igwt1pppr0d1","ZDdBGq/xg7ZWnODn2ZUe80CcgyvRJ+pMZMumWC/uXCM=",4214501960875258519,-8912486592150332484,-5072474774982071710,-3855552100489837545>()) {
               case -183027181:
                  break;
               default:
                  throw null;
            }
         }

         if (var1.bf()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2mwkegmg1z9zi","nh3YcC+rZzSbvPUYJcKWS+id9bxa8u/nP+USegmX+3E=",8988725199984799601,-3192892983470232492,-245309934839980947,4316869547699513078>()) {
               case 2087926314:
                  com.yiyiaddon.d.c.a(
                     (String)com.yiyiaddon.m.b.a<"s1a4a862bxbtln","5vLKPCSlYSsE3+3zUVvEyxccYoqYUOizocDIAefCEPlUQwL1",-2931196859758755125,2956178050922327917,4443038071255402232,-4522619845596301731>(),
                     com.yiyiaddon.d.d.a(
                        (String)com.yiyiaddon.m.b.a<"s2pzemt27op13u","Bcm7GKluvJ8VTv9imBHyWpMhCHMENfyI1ikRvxjz1RpXnNf3",3392993676189475835,8140338378681508471,5383988397253287434,-4855732265800269643>(),
                        var1.bh() + ""
                     )
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"sl2hi2hcyv5an","/113qT+cE3pK68BKoiKUGzLjUgbj3IuMU/UBIwBbf2A=",-6040070897510819038,5352511390488997413,-2782323308765892291,8955120964853786658>()) {
                     case -915558639:
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

   private void dj() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2knemm84i5o8d","0F2h5uXftd8UGYbM+K73PErHCpPjRfodegW+g4dnp2E=",7086368504895447585,-138456459857047698,-1479606122143240282,-5255602211589967222>()) {
            case -1894566388:
               return;
            default:
               throw null;
         }
      } else {
         var1.execute(
            () -> e.b(
               (String)com.yiyiaddon.m.b.a<"s1i5nr38bmjgdp","ywpcQepsJARqmDi7MKv0mXswfe0bp1GxRIfBk/FXK3Qj7DqOd8F274CvtoVFkloimW4=",-5241525984655368942,-763203105047533758,4642978336352252161,3703816343837864559>(),
               false
            )
         );
      }
   }

   private static String a(com.yiyiaddon.e.h.c.a.a var0) {
      switch (var0) {
         case ITEM:
            String var2 = (String)com.yiyiaddon.m.b.a<"s1cphpchlhd6vp","gV34OQxmcBfrZzn+qCsG9IVmwdLU/5gAGmsc1l3E+AmWicgfVDJkewH6uyzDhViuUG+VeJmETz9O+aE0kTA/lt0qn+zicv28",-6447726230077533595,-3423363267130747065,3741462052162759876,3511306828133684691>();
            switch ((int)com.yiyiaddon.m.b.a<"s1fvjih0q5qlre","YN8O3gyXgZRtjwjRbfEsl8uyXicMiKKQSpTf988rRhw=",-4149385040064088527,406515951326600496,3288485056044204588,-780223897957946463>()) {
               case -829171954:
                  return var2;
               default:
                  throw null;
            }
         case BLOCK:
            String var1 = (String)com.yiyiaddon.m.b.a<"s3gueudtdipr1w","ak7sAErFFIPHjY03PXigL/sU8h4G4XljP7hQdrQ9NtkOlaerT0lhEcQkn2eJ4dqXvk04AuFuFHahYZKZmYYpD5iQmoU=",3758255151859986104,7166539140783462825,201905681576209349,-80484107198126592>();
            switch ((int)com.yiyiaddon.m.b.a<"s1jjkc0xk466xw","NBvpfdkMHbHwlGYB8KZb4kdBff0RaxB+/RsjQArPn9g=",-1080298353202823853,-4704062721160068076,-8128351026003111302,-8860698357571783966>()) {
               case 1443343319:
                  return var1;
               default:
                  throw null;
            }
         case ENTITY:
            String var10000 = (String)com.yiyiaddon.m.b.a<"s2tpnlspgryink","B7OCHZyMEVzAwyRhIt8jyxqQFS8YK7CklUBgLKiVK/d/lDenoCYlxUjMvt0wdjwXExDmac+NBz+dbWPp",4562929208325693444,1354414565668601579,2384266528433535273,5042856648074969065>();
            switch ((int)com.yiyiaddon.m.b.a<"s3kovoh6mc6exk","ALxrYMzIA44l3z4qZkDN3Z+Txc0E+BQ4QEyPeGoQsEw=",-1315444225203873623,-4266585544519708800,-6285237195034223902,-1726490172331766758>()) {
               case -1943133800:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }
}

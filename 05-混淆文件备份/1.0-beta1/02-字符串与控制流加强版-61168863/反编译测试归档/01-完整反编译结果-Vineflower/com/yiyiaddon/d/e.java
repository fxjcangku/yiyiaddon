package com.yiyiaddon.d;

import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

public final class e {
   public static final String I = "https://yiyiaddon.asia";
   private static final String J = (String)com.yiyiaddon.m.b.a<"s1uo65uj2j4ls3","j3LHl2ns3p/SFzk/KMPfL5ksIcZHc9YfCfyjH+t0MqTVnt6drPSdsuUVy93m7spQw2efS/ziCb5aPQH+Qu3KrAvELZqHa22isz+OBc+BJg0=",-4774063732600697762,2617948823553803667,3482164037753992003,-2387255093051674234>();
   private static final Duration a = Duration.ofSeconds(5L);
   private static final HttpClient a;

   private e() {
   }

   public static e.a a(String var0, Duration var1) {
      return a(
         HttpRequest.newBuilder(a(var0))
            .timeout(var1)
            .header(
               (String)com.yiyiaddon.m.b.a<"s1t1aouz4009km","LVzh2/H9l7lNrNc7OEFBjhGxafG1g7/0oh7U8uWZmnNXRUoII81tVw==",3432555747373679361,95021728392960007,8506465624795133265,6851200334111073519>(),
               (String)com.yiyiaddon.m.b.a<"s2bllrune95cvy","CwzHLs+aMycz0p5pD4g+NIZ2GVPNOLES/Z6Y5D0CIpQIYgZFwBysBU9iL2SfKiDS4V26FbgZdJ4XbSCS",-9049012793228785660,-6391579882667371956,-1459676451543254751,7217433036824980656>()
            )
            .GET()
      );
   }

   public static e.a b(String var0, Duration var1) {
      return a(
         HttpRequest.newBuilder(URI.create(var0))
            .timeout(var1)
            .header(
               (String)com.yiyiaddon.m.b.a<"s1t1aouz4009km","LVzh2/H9l7lNrNc7OEFBjhGxafG1g7/0oh7U8uWZmnNXRUoII81tVw==",3432555747373679361,95021728392960007,8506465624795133265,6851200334111073519>(),
               (String)com.yiyiaddon.m.b.a<"s2bllrune95cvy","CwzHLs+aMycz0p5pD4g+NIZ2GVPNOLES/Z6Y5D0CIpQIYgZFwBysBU9iL2SfKiDS4V26FbgZdJ4XbSCS",-9049012793228785660,-6391579882667371956,-1459676451543254751,7217433036824980656>()
            )
            .GET()
      );
   }

   public static e.a a(String var0, JsonObject var1, Duration var2) {
      return a(
         HttpRequest.newBuilder(a(var0))
            .timeout(var2)
            .header(
               (String)com.yiyiaddon.m.b.a<"s3l5uam9dkghig","sg/I80EdYUs6efeT67Sk8Pv61xfpevPCWMRLEI1YCXKylAgwVmwiNdNaZJXlDtZGEsTV2w==",6197187164999273428,-5849708133101960748,5043518717700557648,-8557526335779870444>(),
               (String)com.yiyiaddon.m.b.a<"s2bllrune95cvy","CwzHLs+aMycz0p5pD4g+NIZ2GVPNOLES/Z6Y5D0CIpQIYgZFwBysBU9iL2SfKiDS4V26FbgZdJ4XbSCS",-9049012793228785660,-6391579882667371956,-1459676451543254751,7217433036824980656>()
            )
            .POST(BodyPublishers.ofString(var1.toString()))
      );
   }

   public static e.a a(String var0, String var1, Duration var2) {
      return a(
         HttpRequest.newBuilder(a(var0))
            .timeout(var2)
            .header(
               (String)com.yiyiaddon.m.b.a<"s3l5uam9dkghig","sg/I80EdYUs6efeT67Sk8Pv61xfpevPCWMRLEI1YCXKylAgwVmwiNdNaZJXlDtZGEsTV2w==",6197187164999273428,-5849708133101960748,5043518717700557648,-8557526335779870444>(),
               (String)com.yiyiaddon.m.b.a<"s2bllrune95cvy","CwzHLs+aMycz0p5pD4g+NIZ2GVPNOLES/Z6Y5D0CIpQIYgZFwBysBU9iL2SfKiDS4V26FbgZdJ4XbSCS",-9049012793228785660,-6391579882667371956,-1459676451543254751,7217433036824980656>()
            )
            .POST(BodyPublishers.ofString(var1))
      );
   }

   public static long b() {
      long var0 = System.nanoTime();
      e.a var2 = a(
         (String)com.yiyiaddon.m.b.a<"sggftuz93nz2y","mWzqmJeUEq93Ws9PB3LaMjTjH4s7dMLVLpmW3ZekD9cJ9H8uxSYHxlgUYLr4fs+4",-7548660635936317750,7682069760034580035,-518583290605088635,1682427841567132551>(),
         Duration.ofSeconds(3L)
      );
      if (var2.d() == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lyl7hrq1g2ov","uHTIUzJjdRraRPMMiJ+D0a4jFisxtkxGlG1EN5HHzVs=",-577339352355337040,1300647180752804351,-7384361011260496756,-8755096571593580777>()) {
            case -665666712:
               return -1L;
            default:
               throw null;
         }
      } else {
         return (System.nanoTime() - var0) / 1000000L;
      }
   }

   private static URI a(String var0) {
      return URI.create(var0 + "");
   }

   private static e.a a(Builder var0) {
      try {
         HttpResponse var1 = a.send(
            var0.header(
                  (String)com.yiyiaddon.m.b.a<"s13xi6w2oeb90d","yb7DlkFvYxSzXhZwCYs/wELScf9PJZOrP1GnIpdgkHhludZDLjJ0h1+tVKJdEJ4T",7457980442690446351,-8903628794572039034,-4082888766676383562,7463229170113657870>(),
                  (String)com.yiyiaddon.m.b.a<"s1uo65uj2j4ls3","j3LHl2ns3p/SFzk/KMPfL5ksIcZHc9YfCfyjH+t0MqTVnt6drPSdsuUVy93m7spQw2efS/ziCb5aPQH+Qu3KrAvELZqHa22isz+OBc+BJg0=",-4774063732600697762,2617948823553803667,3482164037753992003,-2387255093051674234>()
               )
               .build(),
            BodyHandlers.ofString()
         );
         return new e.a(
            var1.statusCode(),
            var1.body() == null
               ? (String)com.yiyiaddon.m.b.a<"s1kat2bk2hcqon","ikhiKNwtjUGcIKhgDF9y9qRgfD2BW4u5p3H9sA==",5598315069793052714,-2111188660638833902,-2464544938780456755,-3804054423521035991>()
               : (String)var1.body()
         );
      } catch (InterruptedException var2) {
         Thread.currentThread().interrupt();
         return e.a.a;
      } catch (Exception var3) {
         return e.a.a;
      }
   }

   static {
      a = HttpClient.newBuilder().connectTimeout(a).followRedirects(Redirect.NORMAL).build();
   }

   public record a(int g, String K) {
      public static final e.a a = new e.a(
         0,
         (String)com.yiyiaddon.m.b.a<"s258yw7vijryjy","3tAw2n/mVLKw8Pu8+eYjBahkKKTTA402OsAzlQ==",-5308437349271703475,2834375105768421473,7249287185535402068,-6835705759693854696>()
      );

      public boolean b() {
         if (this.g >= 200) {
            switch ((int)com.yiyiaddon.m.b.a<"s2v8do9jmpigic","WCGrU/5BwrJrvdZVw8QhA612rDk0MQ+hr94RLTkZQzs=",3017292470509801444,-7831986131779968961,7201007454560267757,-3698613562158102084>()) {
               case 1162529075:
                  if (this.g < 300) {
                     switch ((int)com.yiyiaddon.m.b.a<"sbj6wr97axklh","1vBClfcujouG1fAt75K0tfZajy8/9Ra1elY2qG80NnY=",1759095371073127130,708281000354085237,-1574637415896485282,3145467517268323362>()) {
                        case 1034283914:
                           switch ((int)com.yiyiaddon.m.b.a<"s19dg3zoawqaau","YO732/Pighb1/A8q1kAPXDtobivALUX+4/OLGbf78lg=",4508117354440290232,-5921645943446792874,-2973161983715019523,-6408079685816135029>()) {
                              case 1750479579:
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

         switch ((int)com.yiyiaddon.m.b.a<"s1fz5abnyzcchr","SW2bFLX/g4IAdXHtmoztjTHydwHKnWnftn8OaoaSbwE=",-3652486561387277759,-1766248456751516819,-963509737405931022,-6265435949674059302>()) {
            case -781499748:
               return false;
            default:
               throw null;
         }
      }

      public JsonObject a() {
         return f.d(this.K);
      }

      public int d() {
         return this.g;
      }

      public String j() {
         return this.K;
      }
   }
}

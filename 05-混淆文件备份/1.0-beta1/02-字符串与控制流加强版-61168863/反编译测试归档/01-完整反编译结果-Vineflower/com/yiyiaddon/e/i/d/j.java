package com.yiyiaddon.e.i.d;

import java.util.Objects;
import java.util.Optional;

public final class j {
   private final com.yiyiaddon.e.i.d.b b;
   private final i b;
   private d a;
   private d b;
   private j.a a = j.a.NOT_SUBMITTED;
   private j.b a = j.b.NOT_WAITING;
   private j.c a = j.c.NOT_VERIFIED;
   private String lM = (String)com.yiyiaddon.m.b.a<"s1p4xi7fd5s349","/35smS/3PL2NgNtbI/jS9smUt1i65tBruE8E+w==",3039386764056115252,6388337624441019447,607834371958647375,3011021280808431957>();

   public j(com.yiyiaddon.e.i.d.b var1, i var2) {
      this.b = Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"s3nkrmjnogfa3t","ETq7nOMcXAs9NoLesoxi0mVTOolh5CGA35wvEW1KaK5gcEvoT5MKNA==",-3597859621159739142,-8618128690728262675,-8402508689108473763,-5065655103586376280>()
      );
      this.b = Objects.requireNonNull(
         var2,
         (String)com.yiyiaddon.m.b.a<"sk5394qb82dec","cz4hF2cShgi4a9ywKc5wyAa4MEqqwna2IHcO87UDN74yfSjPKSwziVdakFhGi3ss",3128450475607606871,-7765830969632308765,1871646100631214902,-3309208928038502728>()
      );
      if (!var1.bs().equals(var2.bu()) || var1.ai() != var2.ba()) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s2ma97w4jjcavy","SfOJNLs9HgqbIkdiqLqif6BJPdNAY8U3v5ymns+Wec1e8lbproaBW8iB0owCvBfYvrqoSabwWlNT2cO1+O51/rbxtbkwyouWYT4=",-1463028361605778620,-64833434754926534,-3020651269980287044,169610122026907626>()
         );
      }
   }

   public void a(d var1) {
      this.c(var1);
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s4hp92higazer","UIdWwyw3RPutlwl2XJBBYYw8gK1HmTgREM/2Dm6g4as=",-5477124519311757907,-3033750479876548131,-5950413333792908018,8385512847042166751>()) {
            case -958224067:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s2uonb71m0glqi","knh1hmE8TROQDEzJhz6rXQYTmfd3GazTASV5bBszpEshXPf2jE12ZkJwZi1jDL2O4nk=",8869665031328588318,-5712800806888098654,4004301430602107043,7338643370287701662>()
               );
            default:
               throw null;
         }
      } else {
         this.a = var1;
      }
   }

   public void dM() {
      this.a(j.a.NOT_SUBMITTED);
      this.a = j.a.SUBMITTED;
   }

   public void dN() {
      this.a(j.a.SUBMITTED);
      this.a = j.a.ACCEPTED;
   }

   public void ab(String var1) {
      if (this.a == j.a.ACCEPTED) {
         switch ((int)com.yiyiaddon.m.b.a<"s306t9nnoz3ti7","7NE+JdFd+vqutAo7/+k+KtgiJNRoYPxUnBRifXP8bBk=",5920072363080305083,-7617198194553781064,-5920703392681763058,-1263849148922141498>()) {
            case -844610590:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s74x43qd8h2y5","JhTcj2ZxyHp7MJgePcyewXs0Ij1PCCLRx7zU58Vi/ULIUJXf9dZno8CF0TCtBgxLwBzuuBu2xuY=",-30432656768661459,2494353345326559488,-7578686547665806166,-4517978184003814892>()
               );
            default:
               throw null;
         }
      } else {
         this.a = j.a.FAILED;
         this.A(var1);
      }
   }

   public void dO() {
      if (this.a != j.a.ACCEPTED) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wbvpygrrii1n","yEq+HIh4LyotgFvHJkNWYK6Lfw7aHvxdznWu5JxlHoU=",7023713841332540186,-7689025013666331055,7785428071589110191,-6648024762044259153>()) {
            case 783025431:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s22httgknec3ng","TTQhOX99F9wmoLOC4CE9BTXTr3T/EdxXK1MsYWKV+wVd1XPktkAFppIFSnUXfA==",785777186732808720,5008150118157891835,-3549096627738710844,-7991383027557770871>()
               );
            default:
               throw null;
         }
      } else if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g7z23s16nikx","RD5QXMumQWHuK6tSSxQmmY0dRBJ8pVzjenGtUU1rwdk=",5398362324972002557,-4533609969910634821,4712627251680698747,4949552601461782219>()) {
            case -437251775:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"sbr8d6jfr1842","7dMTF7PBdlF5LxvIYj6cPGRhMo0ZFnVluKOpjjICr9EMsrtxWIbu+Cc3ilZDwQ==",2736810114861906598,8099852341830893008,-6542591745809809222,6077772878464061110>()
               );
            default:
               throw null;
         }
      } else if (this.a != j.b.NOT_WAITING) {
         switch ((int)com.yiyiaddon.m.b.a<"s39g6ei2b0bhzi","EZ8yv1dI/bHT2s6xo40KVQUNwnsBxutjP6S9vBPCEaQ=",-6241189791683843458,1312029101348455201,-704260779856474107,-4860691102810197398>()) {
            case -864556246:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s2n29gzpne7zkt","fimjawmvQPSjPubf0oJINtAyc1Y8J1/SAZUgIFSAJjg6wnR9RBUPWFn8cZ1okidtodY=",6836325051276333721,-8137452606369963286,-4675971452912840149,9026710281521698567>()
               );
            default:
               throw null;
         }
      } else {
         this.a = j.b.WAITING;
      }
   }

   public void b(d var1) {
      if (this.a != j.b.WAITING) {
         switch ((int)com.yiyiaddon.m.b.a<"sov66bc067tmk","RYj1hg2n5GUFpPaY2IKtlG6LF3j+04FpjujVE9GDwBQ=",8410423239709654952,-7221830540118447264,-8855256290198235391,7638975741020333279>()) {
            case 505691842:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s1qh0p0u6xv6fq","rLbNszddHGJ8xHr+yPaGgLutHq1tpRFQAH/rZEu1EaZXJJ84MEgzOboq0buGfcNu",-133927340630460419,1850856718814699531,6963025158280204056,-6730600179053942648>()
               );
            default:
               throw null;
         }
      } else {
         this.c(var1);
         this.b = var1;
         this.a = j.b.SYNCHRONIZED;
      }
   }

   public void ac(String var1) {
      if (this.a != j.b.WAITING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ltkqn8gqzjue","YbDQ6WxoFRIw7iywMDaHhvRDIjnC76iFfXmik56WQJ0=",-528447660663772233,-6888136660719965996,-7397090922920026711,-9042761257202969715>()) {
            case -977831785:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s1qh0p0u6xv6fq","rLbNszddHGJ8xHr+yPaGgLutHq1tpRFQAH/rZEu1EaZXJJ84MEgzOboq0buGfcNu",-133927340630460419,1850856718814699531,6963025158280204056,-6730600179053942648>()
               );
            default:
               throw null;
         }
      } else {
         this.a = j.b.TIMED_OUT;
         this.A(var1);
      }
   }

   public boolean by() {
      if (this.a == j.b.SYNCHRONIZED) {
         switch ((int)com.yiyiaddon.m.b.a<"s3syomkkxii1d0","KUdWiHOJADrw5EO72ginjvIy7YemT69zzqewjWGbwEc=",5519227636694991428,-3803039098463686179,-2633805653646507040,-3244619618463444169>()) {
            case 855662074:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s10minami3j5hm","5q6PdMO5XtQOAarMDteErr+5+cxB/2V5UCzYCnV0RlU=",-3243056391021260080,1766580738800147250,-6801642093054959092,5645186411138387531>()) {
                     case -989608823:
                        if (this.b != null) {
                           if (this.b.b(this.a)) {
                              switch ((int)com.yiyiaddon.m.b.a<"sq4a2kcr4xc06","OVf35zsq0Uz3WY/jksuCXja6TSX5lUqicHYg/jYlqtQ=",5834436929566664291,-6736367876813275485,1953866955835801570,-8579595137260144758>()) {
                                 case 1529607983:
                                    this.a = j.c.SUCCEEDED;
                                    this.lM = (String)com.yiyiaddon.m.b.a<"s1p4xi7fd5s349","/35smS/3PL2NgNtbI/jS9smUt1i65tBruE8E+w==",3039386764056115252,6388337624441019447,607834371958647375,3011021280808431957>();
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           this.A(
                              (String)com.yiyiaddon.m.b.a<"s2v848plbqjz2a","4wYjp5jTF0Ap4Thcy4fa80GWLI/gHVnC4T8C+A0He8u4K3pFhaMqwwkRJtdmVMCXoYRXRLrvfE6fUxzytoGvuw==",-2423049596601974478,-7657149163228072632,552310952792889904,3954818832702530540>()
                           );
                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"shssn69bxprjv","RmJmmR517i+qq1u0p8HKKq5tNAbSJPuLVYUU1j13wS8=",-6347103259464953910,-657407191947425677,-4518794075493740366,6642411100146824053>()) {
                           case -1620787184:
                              return false;
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

      return false;
   }

   public void A(String var1) {
      this.a = j.c.FAILED;
      this.lM = Objects.requireNonNullElse(
         var1,
         (String)com.yiyiaddon.m.b.a<"s1p4xi7fd5s349","/35smS/3PL2NgNtbI/jS9smUt1i65tBruE8E+w==",3039386764056115252,6388337624441019447,607834371958647375,3011021280808431957>()
      );
   }

   public com.yiyiaddon.e.i.d.b c() {
      return this.b;
   }

   public i a() {
      return this.b;
   }

   public Optional<d> h() {
      return Optional.ofNullable(this.a);
   }

   public Optional<d> i() {
      return Optional.ofNullable(this.b);
   }

   public j.a a() {
      return this.a;
   }

   public j.b a() {
      return this.a;
   }

   public j.c a() {
      return this.a;
   }

   public String bz() {
      return this.lM;
   }

   private void c(d var1) {
      if (!Objects.requireNonNull(
            var1,
            (String)com.yiyiaddon.m.b.a<"s3jlbf3k8bfick","44KO6/wOLS7af069cQ7XiMedFtNHTR9g1xt265EJOldQzRPbUwJ2RQvkOVo=",4962154551518627442,8688175193836590846,-4294511938149723312,-970421839220029437>()
         )
         .a(this.b)) {
         switch ((int)com.yiyiaddon.m.b.a<"sbwqu6dm1rjmg","2sbVPXqAn8rKh7Mi2ccjZcm611pgdLPMDB0zIsURdcU=",2959622327265319406,3207827231002365831,-4141021909397560171,1830265432164678088>()) {
            case 553426048:
               throw new IllegalArgumentException(
                  (String)com.yiyiaddon.m.b.a<"s3nrl31t92gqvd","cp6xNH0RZiXDMhmjxGH1BupXOCu2fZVyYLxMNNCSS74FkltF/kba0jkudvQJx+ilA8PkHRaeYvC0DnFR4QEFrrilKy8NIsDoXr4=",-108939105829426715,5146156949111883446,6195251398060004640,7063914392601175695>()
               );
            default:
               throw null;
         }
      }
   }

   private void a(j.a var1) {
      if (this.a != var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s28u9is867mpk0","dEj8YDAyBLGPp0I2dy8p7UlfdCqzgwOkBIT7RObBGWI=",-4678159338418978211,2460810320263806329,3689113295285601403,-2293310659293754733>()) {
            case 484877995:
               throw new IllegalStateException(
                  (String)com.yiyiaddon.m.b.a<"s2nycvrja1jo2","cMFZzPOXyjNtvF2sQmUcJllUD9OmifkEM5HPy9IaohAKArYA+xsjbQHDsfad5UMrs5DFNtjx",5329344498962098618,-2780070509330314137,8978576665993461440,3732069063132901457>()
               );
            default:
               throw null;
         }
      }
   }

   public enum a {
      NOT_SUBMITTED,
      SUBMITTED,
      ACCEPTED,
      FAILED;
   }

   public enum b {
      NOT_WAITING,
      WAITING,
      SYNCHRONIZED,
      TIMED_OUT;
   }

   public enum c {
      NOT_VERIFIED,
      SUCCEEDED,
      FAILED;
   }
}

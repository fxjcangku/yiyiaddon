package com.yiyiaddon.a;

import java.util.Arrays;

public final class c {
   private final String[] a;
   private final String g;

   public c(String[] var1) {
      this(var1, null);
   }

   public c(String[] var1, String var2) {
      this.a = var1 == null ? new String[0] : var1;
      this.g = var2;
   }

   public String[] a() {
      return Arrays.copyOf(this.a, this.a.length);
   }

   public int a() {
      return this.a.length;
   }

   public String a(int var1) {
      if (var1 >= 0) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s1c1uc5agd9w7b","huE5OXCx/afxMoF6T0KXNMjUL5MZ89hbPwLNW7G+IPA=",6356746465749942735,2994514021480926816,-4558988878713377567,5694916418016747360>()) {
            case 605820002:
               if (var1 < this.a.length) {
                  String var10000 = this.a[var1];
                  switch ((int)com.yiyiaddon.m.b.a<"s308pkf82wykvi","FJzJrpmTUpi8m2SqyV6xcq5u8SK51UL4usf2xNBaBvE=",1262323071533057373,-7704406846531397221,1282403091026209205,883215873399232079>()) {
                     case -37935150:
                        return var10000;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2kjxwzbn0m9lx","5vsCmUFb0Z+97VpXf6YzkcqQF7lHASNziID8/ikYVCE=",-1668737629189922977,4397489940879567205,-7257908858090217048,3961247067056373299>()) {
                  case -1371698457:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"saug4kwshmwo9","kGijtAsD8ic6UAMHStn6NOjUUdE2eWHgrj5qdPkI888=",-2166666969771214796,-1361458844539737316,-3571853743162268417,1535345650534228724>()) {
         case -627803477:
            return null;
         default:
            throw null;
      }
   }

   public String b(int var1) {
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3mt96m30jnz8r","Lqd4APN3gGg7lGEJxOruoPa1UkWfzeFkdBsMjpts43I=",4650902761776964827,-1362061205450510397,7347545932477011700,2568150285865640227>()) {
            case 1718488628:
               if (var1 < this.a.length) {
                  return String.join(
                     (String)com.yiyiaddon.m.b.a<"s2vmtse49cc4bj","9xlwXAYIJK4Z/ThHK2py+AU+Jy5jEyGpVmRlVJii",-4635994078768949279,5008426839859388319,-8293655383872836818,5954615295456067621>(),
                     Arrays.copyOfRange(this.a, var1, this.a.length)
                  );
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jdihnocyq48b","XED0gbYCsoDtqyFh4Orh6S4nRsSaq0Qu0Sw4KTplIlo=",5994492988612089724,-8646372194293590914,5425351314418654172,-5099120336107360330>()) {
                     case 1928325564:
                        return (String)com.yiyiaddon.m.b.a<"s2tijtg8zgipys","iiGpGaKN1hRFQPEAkmC41H/171PZmv8ZLFLckQ==",-6775836930583150946,3823368143680697203,-2350635408677155013,-7770407147918096173>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2tijtg8zgipys","iiGpGaKN1hRFQPEAkmC41H/171PZmv8ZLFLckQ==",-6775836930583150946,3823368143680697203,-2350635408677155013,-7770407147918096173>();
      }
   }

   public Integer a(int var1) {
      String var2 = this.a(var1);
      if (var2 == null) {
         return null;
      }

      try {
         return Integer.parseInt(var2.trim());
      } catch (NumberFormatException var4) {
         return null;
      }
   }

   public boolean a() {
      if (this.a.length == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s9hz9dkskkn8b","nqf84/y0G32bVFJZh3QTxAL6rml5m0LvD/WXnj4oMbo=",-6580753010620686251,-3353737951604163242,-3922998284489519137,1020702710260971800>()) {
            case 134434093:
               switch ((int)com.yiyiaddon.m.b.a<"ss1wiqfdhirps","pB3TfPHqdUG2SSRYqqvqUXHCbl77qDOlCopSU6WNiXM=",-3621244022709089625,-7568166280245133524,-5331021813367527816,-650940532280317525>()) {
                  case 818360640:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s13pnnxh71bcc2","tV5rty2cDK9gEBYg7e0qPjVRYeXRBKSvt7CB6qvkkPQ=",7303839372112310874,-8892696748576562798,-4500131687189389493,8019585866415295571>()) {
            case -1954560103:
               return false;
            default:
               throw null;
         }
      }
   }

   public void a(String var1) {
      com.yiyiaddon.d.c.a(this.g, var1);
   }

   public void b(String var1) {
      com.yiyiaddon.d.c.a(this.g, var1 + "");
   }

   public void c(String var1) {
      com.yiyiaddon.d.c.a(this.g, var1 + "");
   }
}

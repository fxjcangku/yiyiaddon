package com.yiyiaddon.e.i.d;

import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public record b(String lD, int fr, boolean bG, boolean bH, i a, String lE, String lF) {
   private static final Pattern f = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"s38zd2k2notfqp","KjLJPCLCu7a809D0a/q6bLRarlhKUUJ1rmvp+j2YJeOGasTmeS4dvV4FL90cvTynaeAZPsFrwy5J6K/58oGrH8DNuhmup3cmoOH975PbVQs=",137050639249829546,-7218551840751290538,-6892431413334076769,-4389334596210483343>()
   );

   public b(String lD, int fr, boolean bG, boolean bH, i a, String lE, String lF) {
      lD = Objects.requireNonNull(
            lD,
            (String)com.yiyiaddon.m.b.a<"s10khteh67m8m3","lCwUj64WIx3OSElGuim9C5YCpeiDES6gXIQuGzVpFHaEayvhn/HP2T3Kl2q8NMHu",-7010961044602012746,-2676216544320628463,-1970313568599561347,7973990607026259241>()
         )
         .toLowerCase(Locale.ROOT);
      if (!f.matcher(lD).matches()) {
         throw new IllegalArgumentException(lD + "");
      }

      if (fr < 1) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s2oityw2rbv7yc","gsxA4BWFuPLpZ/ZIp/cO5UpEnOJGjVB7dWGDGq9xJVcrH100qJfRhFQUVnia7b2srsvPLA==",7569942084466003785,-8378240621165250380,-8571414958709538397,6827795070245482536>()
         );
      }

      if (a == null || lD.equals(a.bu()) && fr == a.ba()) {
         lE = Objects.requireNonNull(
            lE,
            (String)com.yiyiaddon.m.b.a<"s3tmbjr2ecm5it","GUOI+JK8kljw3hzzTCFtuBsJEQAlTI1HeLwMkYjJbCziOFSvjazShkpOfDf0sndODzo=",-7825172526293113167,330848255928446089,-5232935970703516990,-471251263882600500>()
         );
         lF = Objects.requireNonNull(
               lF,
               (String)com.yiyiaddon.m.b.a<"s1m8nftzxufksy","leoICCQKV8YrZUKsvgWxXJdlhdXmT2DrTFFrzypN6y72Ly2pqH/0adVCqjNcX8hBwWWxXn2I6GY=",2348353795251628308,-2775764454798863600,-6046048816049485788,-2806866298596644570>()
            )
            .toLowerCase(Locale.ROOT);
         if (lE.isBlank()) {
            throw new IllegalArgumentException(
               (String)com.yiyiaddon.m.b.a<"sq8lo5zzki4fg","7EsSB3Lt9g/y4y//AILsZo97KPmc6zu6emwkvPxOwcR7phlIMrVGYSeU7x8ZpjkACFUoYficLr3VilTP",170983323055263341,6493026874712091517,-6575026180896875457,-7801821015733507528>()
            );
         }

         if (!f.matcher(lF).matches()) {
            throw new IllegalArgumentException(lF + "");
         }

         this.lD = lD;
         this.fr = fr;
         this.bG = bG;
         this.bH = bH;
         this.a = a;
         this.lE = lE;
         this.lF = lF;
      } else {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"s3pitqtrq02l1h","OBYa0jSE62IB2x+PxUbYpcLF0iR6Qzkwux9hyv7TvjkIV8YD1BJtHtVIHbPuDCahXxGQJy1fli4lVF7QEYutyWRiZ0qfWQ==",-4120809310531986051,2312174761912422981,-3436651019781883128,6993808602640745103>()
         );
      }
   }

   public b(String var1, int var2, boolean var3, boolean var4, i var5) {
      this(
         var1,
         var2,
         var3,
         var4,
         var5,
         var1,
         (String)com.yiyiaddon.m.b.a<"s1cgonx42c1o96","f1m6czPJ5LNpzyP1bCA8w1CNRESVfDXWveq+kJohafUjFrst4bR432RBwiIhuAPXchJxmA0oT635mgIKYqIgsQy/q8t/j5wZbSu3fg==",-3283862991805529076,7040033406386440518,-4143388199286197827,5638555476056672096>()
      );
   }

   public b(String var1, int var2, boolean var3) {
      this(var1, var2, var3, false, null);
   }

   public static b a(String var0, String var1, String var2, int var3) {
      return new b(var0, var3, true, false, null, var1, var2);
   }

   public int aW() {
      return this.fr;
   }

   public int aX() {
      return this.fr;
   }

   public b a(i var1) {
      return new b(this.lD, this.fr, this.bG, this.bH, var1, this.lE, this.lF);
   }

   public b a() {
      return new b(this.lD, this.fr, this.bG, this.bH, null, this.lE, this.lF);
   }

   public b b() {
      if (this.bH) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lzbbgcliekk5","INS7Tw9kmduSxRhP4EZPfjN8FVRKFMlk9EeQAL8oDT0=",-822127677363590848,-5326522476952094388,6337018273688046278,-7065553204069816444>()) {
            case 688858030:
               switch ((int)com.yiyiaddon.m.b.a<"s15asife8gploy","DO23iXLDkbWNoR4AmBqMq29DnWWNnuOsxGMC66QUSFk=",873067178853954083,-600350459803252188,-8002996050180492429,-6935639245376266077>()) {
                  case 1532361882:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         b var10000 = new b(this.lD, this.fr, this.bG, true, this.a, this.lE, this.lF);
         switch ((int)com.yiyiaddon.m.b.a<"s2uuqyfi4voe3y","EiujZqVJWQi3QDLdGYaCLnhmvBPnWGGieE2emw2Rse8=",-324717747622864310,7341808245311440615,-8965441003087564855,-5956888331146535653>()) {
            case -1867076767:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public String bs() {
      return this.lD;
   }

   public int ai() {
      return this.fr;
   }

   public boolean bq() {
      return this.bG;
   }

   public boolean br() {
      return this.bH;
   }

   public String m() {
      return this.lE;
   }

   public String bt() {
      return this.lF;
   }
}

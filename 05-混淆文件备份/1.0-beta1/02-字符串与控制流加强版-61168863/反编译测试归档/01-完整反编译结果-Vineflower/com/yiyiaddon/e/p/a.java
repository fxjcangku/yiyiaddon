package com.yiyiaddon.e.p;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.a.c;
import com.yiyiaddon.d.a.g;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String yw = "teleport";
   public static final String yx = "传送";
   private static final double aS = 1.62;
   private static final long at = 3000L;
   private final Minecraft ak = Minecraft.getInstance();
   private final com.yiyiaddon.e.p.b.a a = new com.yiyiaddon.e.p.b.a();
   private final com.yiyiaddon.e.p.c.a a = new com.yiyiaddon.e.p.c.a(this.a());
   private long au;
   private static final String yy = (String)b.a<"s20n92b0kl4763","KkDPAJOsK+k0B+z7xmXUTGrn1jr4KIcBLlWFkPIK",-5950776831813360260,-7228354104378671666,-8078194824348264534,2546048853261981799>();

   public a() {
      super(
         (String)b.a<"stjkd23hwhhxj","x0Naq5V9EgpnmIh8x8L9QHgqa/HFKF/Oj8H2CsaAGecrFFwH5SRSdmHbtP0=",7128816460746251726,-1511494463231120368,-3810150850795522002,-4392388472584475250>(),
         (String)b.a<"s3velfzok2c1io","tlTEzT86vTFag+iYOhv859tKSWEBnhPgmLFZbVDOvW4=",-1398677127672075904,-4771145090378629428,-4431909038570439221,4878629918045224191>(),
         (String)b.a<"s1hn4be01sdl0v","80wBJeOP/Y8fSQ0mRy2Gx9F1xAf+u2tSb7A6aY/QsnmursjlHfQmbcak",33146390235425917,-8669229876820118251,650313597461998974,6491029761571168811>(),
         (String)b.a<"swidxns3v5p5q","NtWyrqbavYmVRUcv3+8C5OzFfxNUihMSUkLUBJnL7rnp8xGsBA2cSrUSkSz9PPDq8mMJ9hpgnt/Oo972ziSaEX28+P6k72CrxIdfEvNiVxqFEi8q10UgwDpsiXODjqnmrsmJ6oWlzuoFG4dQHwjUN1/cft0=",3143062098933333589,-8859996455331452340,-3551917119452439046,-6034809206085724646>()
      );
   }

   @Override
   public String w() {
      return (String)b.a<"s20n92b0kl4763","KkDPAJOsK+k0B+z7xmXUTGrn1jr4KIcBLlWFkPIK",-5950776831813360260,-7228354104378671666,-8078194824348264534,2546048853261981799>();
   }

   @Override
   public int i() {
      return 40;
   }

   public com.yiyiaddon.e.p.b.a a() {
      return this.a;
   }

   public com.yiyiaddon.e.p.c.a a() {
      return this.a;
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   @Override
   protected void onInitialize() {
      com.yiyiaddon.l.d.b.a(
         (String)b.a<"stjkd23hwhhxj","x0Naq5V9EgpnmIh8x8L9QHgqa/HFKF/Oj8H2CsaAGecrFFwH5SRSdmHbtP0=",7128816460746251726,-1511494463231120368,-3810150850795522002,-4392388472584475250>(),
         (String)b.a<"s174vukah7c463","FlemuxfiPEDsPf4vJdelBfKWclCF35lg0Rf8xQfe6fNBJNNthN0=",8530683771974301655,7056512465563907284,-6282354461367158597,5307941766489426350>(),
         () -> this.a.b,
         var1 -> this.a(var1, com.yiyiaddon.e.p.e.b.GROUND)
      );
      com.yiyiaddon.l.d.b.a(
         (String)b.a<"stjkd23hwhhxj","x0Naq5V9EgpnmIh8x8L9QHgqa/HFKF/Oj8H2CsaAGecrFFwH5SRSdmHbtP0=",7128816460746251726,-1511494463231120368,-3810150850795522002,-4392388472584475250>(),
         (String)b.a<"sogarr79h2ni0","pdTQynfFZoc6CS1fSunlbPD1+GZ0d7kZ+dNbObgB8AEwZhDR2gI=",-687832339373061649,8906148319187547407,2285123202298784375,-3776526719117583319>(),
         () -> this.a.c,
         var1 -> this.a(var1, com.yiyiaddon.e.p.e.b.WALL)
      );
      com.yiyiaddon.l.d.b.a(
         (String)b.a<"stjkd23hwhhxj","x0Naq5V9EgpnmIh8x8L9QHgqa/HFKF/Oj8H2CsaAGecrFFwH5SRSdmHbtP0=",7128816460746251726,-1511494463231120368,-3810150850795522002,-4392388472584475250>(),
         (String)b.a<"s25r6pn2ap13m4","tDcCkuYqbZsC5c+/szNAX87JkiAEEtGJGpC6nOmh9hgzRGZi6QA=",2349223300177375727,-402223904302995586,2105536853006813785,-3704423552709070483>(),
         () -> this.a.d,
         var1 -> this.a(var1, com.yiyiaddon.e.p.e.b.COORD)
      );
   }

   @Override
   protected void m() {
      l.a(
         (String)b.a<"stjkd23hwhhxj","x0Naq5V9EgpnmIh8x8L9QHgqa/HFKF/Oj8H2CsaAGecrFFwH5SRSdmHbtP0=",7128816460746251726,-1511494463231120368,-3810150850795522002,-4392388472584475250>(),
         this::d
      );
   }

   @Override
   protected void n() {
      l.l(
         (String)b.a<"stjkd23hwhhxj","x0Naq5V9EgpnmIh8x8L9QHgqa/HFKF/Oj8H2CsaAGecrFFwH5SRSdmHbtP0=",7128816460746251726,-1511494463231120368,-3810150850795522002,-4392388472584475250>()
      );
      this.a
         .aO(
            (String)b.a<"sbip9jpspexze","a4PSh+7B0J5knDUrJ8vxrS1QsPiuqhptgelNeXoXVZyZfMxQl/f9zv42q0IDlqFJ",4558208139890100170,-2411007615061484962,221110328406388974,6846650625338814489>()
         );
      this.a.b();
   }

   @Override
   public Set<c> c() {
      return Set.of(c.TICK, c.SERVER_POSITION);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 == null) {
         switch ((int)b.a<"s11tyty7yj2k92","ALDLxlJ5fbryqTJ241giyOGoweYqv+NW5hnGF6RvHVg=",9065370841810342847,-2655823514217253821,2181508047276054453,-9124662936859241886>()) {
            case -889170732:
               return;
            default:
               throw null;
         }
      } else if (var1.a() != c.SERVER_POSITION) {
         switch ((int)b.a<"s1is98e2raid0b","8pYF5sXmqyfv/lg3DeY+FGEefCHpSahyd/Ena6dQHvE=",5956438975670027826,-3297851256608269970,2111343461704965007,-2064663261757160265>()) {
            case 637969469:
               return;
            default:
               throw null;
         }
      } else {
         g var2 = var1.a();
         if (var2 != null) {
            switch ((int)b.a<"smkelxh9rzn52","JsoG7V5vG3XFu9oe/dv9iNEtPlOmbOZRFfl+BUi0e8A=",-419277031258451690,2641304546866195628,4504100585825308541,-3886216445276995974>()) {
               case 831285897:
                  if (this.ak.player != null) {
                     this.a.a(var2.a(), var2.f(), this.ak.player);
                     return;
                  } else {
                     switch ((int)b.a<"s35gctr1emsy22","JHnQiC7ZIVgwo9xBJI8UzHkzU+L0mm4jjh6DxiOHv64=",-6582971555810934548,1008138458578675005,5836929625779723946,6916093157100921942>()) {
                        case -449407424:
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
   }

   @Override
   public void b(Minecraft var1) {
      if (this.ak.player != null) {
         switch ((int)b.a<"s1uttpfb8xxdk8","LaBR4ypvd1ouh0PXvq1M8o08AVsbBoEiy5ZPuBTvhjk=",-2558954661294996635,778462493675366943,-1045428903083567109,6685681169682664926>()) {
            case -1743709265:
               if (this.ak.level != null) {
                  this.a.b(this.ak.player, this.ak.level);
                  return;
               } else {
                  switch ((int)b.a<"s1ct3g0dxjvykj","egcoJZgOZVMqdo4PC1givAWJWcZJYLMVOcC7563hzRI=",-1668776002186148869,5060357284331935445,-7940690510465755456,4458879694502558124>()) {
                     case -1285630020:
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

   private void d(f var1) {
      if (!this.a.eO) {
         switch ((int)b.a<"s1jf6qmbfshf7i","0G6tCyKBbMKDn85ZnyOQ4kRgv2rlIdzE+dalUdKmN1s=",-8510882079094674858,30207591205908671,2483811590522436820,3516226675282213857>()) {
            case 1024193135:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.p.g.a.a(var1, this.a.a());
      }
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.p.i.b(this);
   }

   @Override
   public List<com.yiyiaddon.a.a> g() {
      return List.of(new com.yiyiaddon.e.p.a.a(this));
   }

   private void a(String var1, com.yiyiaddon.e.p.e.b var2) {
      if (!this.g()) {
         switch ((int)b.a<"s2h6tcmm8h6gsv","DiXo4CcziQkVu07fn3xnMncx1/Va48qdOw5htcF94EE=",7774304092784488994,8393798026559217589,-6853726211588839402,2857543871354867866>()) {
            case -1180250137:
               long var3 = System.currentTimeMillis();
               if (var3 - this.au < 3000L) {
                  switch ((int)b.a<"srwbea7hokpge","o+Zihy+dMYg9GWJCJXE5t8RdEQHFvqAmZp1yF00Ie7A=",5224943452413486475,-5835384096354426848,-1756300489375700675,-8632420428134144390>()) {
                     case -1037454436:
                        return;
                     default:
                        throw null;
                  }
               }

               this.au = var3;
               com.yiyiaddon.d.c.a(
                  (String)b.a<"s3velfzok2c1io","tlTEzT86vTFag+iYOhv859tKSWEBnhPgmLFZbVDOvW4=",-1398677127672075904,-4771145090378629428,-4431909038570439221,4878629918045224191>(),
                  j(
                        (String)b.a<"s3velfzok2c1io","tlTEzT86vTFag+iYOhv859tKSWEBnhPgmLFZbVDOvW4=",-1398677127672075904,-4771145090378629428,-4431909038570439221,4878629918045224191>()
                     )
                     + j(var1)
               );
               return;
            default:
               throw null;
         }
      } else {
         switch (var2) {
            case GROUND:
               this.il();
               switch ((int)b.a<"s6u4ixizw4c2p","g5IzMtjzmCZX6ZopervlNj5hheTSGR3F22O6MikqfI0=",7064285816111454078,880250174628037354,-1553040510815497535,-2642040536399427883>()) {
                  case 955243785:
                     return;
                  default:
                     throw null;
               }
            case WALL:
               this.im();
               switch ((int)b.a<"s34alcf0pfx96s","Q3T0bFBFU6ekxveRUn+TEc1ky9BSPN1d1uqJOE5YxVU=",-2639445887669656846,4095884879301125709,4878399625029083973,-4765767987813341390>()) {
                  case 2064907570:
                     return;
                  default:
                     throw null;
               }
            case COORD:
               this.in();
               switch ((int)b.a<"s3vgklvcxosfj3","qQCsm+eVXRc0Fpwm6hMRibI0FjlpjNknN/RdtN9g04Q=",7616619365720205627,-4479383571669711137,3668632381717938212,-4936857610716953356>()) {
                  case 1365879929:
                     break;
                  default:
                     throw null;
               }
         }
      }
   }

   private void il() {
      this.a(this.a(com.yiyiaddon.e.p.e.b.GROUND, null));
   }

   private void im() {
      if (this.ak.player == null) {
         switch ((int)b.a<"s10b2nkkwjktit","K8oVzlLc/SshOkUZ7TiIOEcfZf+J/kFx8TxTWKiJsTc=",-1847581664908577843,-4426162282035139015,7035073533951015033,2602917625330373322>()) {
            case -1277286840:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.p.e.c var1 = this.a(com.yiyiaddon.e.p.e.b.WALL, null);
         var1.f = this.ak.player.getEyePosition(1.0F);
         var1.g = this.ak.player.getViewVector(1.0F);
         var1.bi = this.ak.player.getEyeHeight();
         this.a(var1);
      }
   }

   private void in() {
      this.a(this.a(com.yiyiaddon.e.p.e.b.COORD, new Vec3(this.a.pR + 0.5, this.a.pS, this.a.pT + 0.5)));
   }

   public void c(int var1, int var2, int var3) {
      this.a(this.a(com.yiyiaddon.e.p.e.b.COORD, new Vec3(var1 + 0.5, var2, var3 + 0.5)));
   }

   private com.yiyiaddon.e.p.e.c a(com.yiyiaddon.e.p.e.b var1, Vec3 var2) {
      com.yiyiaddon.e.p.e.c var3 = new com.yiyiaddon.e.p.e.c();
      var3.a = var1;
      var3.j = var2;
      var3.pP = this.a.pP;
      var3.ba = this.a.ba;
      var3.bb = this.a.bb;
      var3.pQ = this.a.pQ;
      var3.eN = this.a.eN;
      var3.pU = this.a.pU;
      var3.bc = this.a.bc;
      var3.ay = this.a.pV;
      return var3;
   }

   private void a(com.yiyiaddon.e.p.e.c var1) {
      if (this.ak.player != null) {
         switch ((int)b.a<"s1cznzp8godh7c","5Enq1HhmIObumnLWnLRUl0I3FskaU8FYrDLL7munGyQ=",8003842897637349887,-6068518556517871089,-7188730823244215904,1591235332220287170>()) {
            case 815958009:
               if (this.ak.level != null) {
                  this.a.a(this.ak.player, this.ak.level, var1);
                  return;
               } else {
                  switch ((int)b.a<"s21f2dpw63ccb5","x29JGwe1VkoLCu6cgjrBS8BawiF6AeR72onMpi0Z3uc=",-6626426441996739132,6517696209713294856,-3788872494270975652,3793452314970001100>()) {
                     case 1741997638:
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

   private com.yiyiaddon.e.p.c.a.a a() {
      return new com.yiyiaddon.e.p.c.a.a() {
         @Override
         public void az(String var1) {
            com.yiyiaddon.d.c.a(
               (String)b.a<"s1xxx3ui23ufbi","ETPSXnxVKt8Ai16LDojm7L4+IzQRudg3AHedc5vU27c=",6516511811034881121,3951797596986130997,7428891998738314908,239838911103012317>(),
               var1
            );
         }

         @Override
         public String u(String var1) {
            return com.yiyiaddon.e.p.a.i(var1);
         }

         @Override
         public String bn(String var1) {
            return com.yiyiaddon.e.p.a.j(var1);
         }

         @Override
         public String bo(String var1) {
            return com.yiyiaddon.e.p.a.N(var1);
         }

         @Override
         public String bp(String var1) {
            return com.yiyiaddon.e.p.a.bm(var1);
         }
      };
   }

   private static String i(String var0) {
      return var0 + "";
   }

   private static String j(String var0) {
      return var0 + "";
   }

   private static String N(String var0) {
      return var0 + "";
   }

   private static String bm(String var0) {
      return var0 + "";
   }
}

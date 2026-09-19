package com.yiyiaddon.e.g.d;

import net.minecraft.world.item.ItemStack;

public final class k {
   private final int el;
   private final ItemStack e;
   private final o b;
   private ItemStack f;
   private k.a a;
   private int em;
   private int en;
   private int eo;
   private com.yiyiaddon.e.g.e.b a;
   private k.b a;
   private String jC;

   public k(int var1, ItemStack var2, o var3) {
      this.el = var1;
      this.e = var2;
      this.b = var3;
      this.f = var2;
      this.a = k.a.IDLE;
      this.em = 0;
      this.en = 0;
      this.eo = 0;
      this.a = k.b.PENDING;
   }

   public boolean n(ItemStack var1) {
      f var2 = g.a(var1, this.b, com.yiyiaddon.e.g.d.a.STRICT, 1.0);
      return var2.aJ();
   }

   public void b(p var1) {
      this.a = k.a.ERROR;
      this.a = k.b.ERROR;
      this.jC = var1.toString();
   }

   public void cx() {
      this.a = k.a.DONE;
      this.a = k.b.DONE;
      this.jC = null;
   }

   public int ab() {
      return this.el;
   }

   public ItemStack e() {
      return this.e;
   }

   public o c() {
      return this.b;
   }

   public ItemStack f() {
      return this.f;
   }

   public void b(ItemStack var1) {
      this.f = var1;
   }

   public k.a a() {
      return this.a;
   }

   public void a(k.a var1) {
      this.a = var1;
   }

   public int ac() {
      return this.em;
   }

   public void m(int var1) {
      this.em = var1;
   }

   public int ad() {
      return this.en;
   }

   public void n(int var1) {
      this.en = var1;
   }

   public int ae() {
      return this.eo;
   }

   public void o(int var1) {
      this.eo = var1;
   }

   public com.yiyiaddon.e.g.e.b a() {
      return this.a;
   }

   public void a(com.yiyiaddon.e.g.e.b var1) {
      this.a = var1;
   }

   public k.b a() {
      return this.a;
   }

   public String aI() {
      return this.jC;
   }

   public boolean aL() {
      if (this.a == k.b.ERROR) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sksleq5qiibo","U5sbxwUf6IYXPzVeVJs+ehoAvmcjqMM5ABhonEYSHn0=",2102035288006462125,4002929418667885428,-9012026616588259413,-5060535894157062072>()) {
            case -546418203:
               switch ((int)com.yiyiaddon.m.b.a<"s191cjw5ch0wh7","ur0i2OBauz6ftpxQ/x562uafw+mgdJMhpePOT9dphOc=",4252282529273774161,-2071520194717000020,-5460174108519322154,-444919483704515276>()) {
                  case 9944179:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3mveokav0o6bi","zvWYPCtVCmL2NWfhmNBJmJDvsBAw55xVOQc39rYiWT0=",8848859168848999394,-7144363975532853988,6659002832835352376,-3491527768968724289>()) {
            case -484992944:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean aM() {
      if (this.a == k.b.DONE) {
         switch ((int)com.yiyiaddon.m.b.a<"s2gtrqn8tldlnh","BtOgUo4rsl7a0eFprAeDSU0b6w5Df79zoAJsVIoN4L4=",8857706192908099585,-8002127628822530965,-5992548017600302482,-6877862947379108124>()) {
            case 223117629:
               switch ((int)com.yiyiaddon.m.b.a<"s2qz8jddui3wrk","/5ww/258jO8GCRPCuXnhxhbuA6X6Ych3DqBgq3iwoMU=",-8657663782232164926,-5791402646429292915,-7912303253365836120,4237097895899624789>()) {
                  case -1461318907:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s34gf9wo4vf2b4","Ee3/zCgckzD3ZetGivmg6U3cAd4mtU+N3h5nNLJNlck=",7520641825225638455,-5492692473258384946,-2776673597572308689,1916452161321583663>()) {
            case -1397841531:
               return false;
            default:
               throw null;
         }
      }
   }

   public enum a {
      IDLE(
         (String)com.yiyiaddon.m.b.a<"s3ev5b2guxp0fl","xT7rtI9bAkKUMiiXflGhejUny+lTRS2x+CPnb+OcS+E=",-1858636627564811654,-7968430145660296557,-6169647864899887421,-1147195705422833622>()
      ),
      ENCHANTING(
         (String)com.yiyiaddon.m.b.a<"s33clrqog4uoa2","Ot7XWfqIED6fOUjAZG+JV3fccuFHPKRU4VBDodj74ajdYQ==",1693846676659821273,872686107315925833,-4607554706928464948,8767197784986484923>()
      ),
      EVALUATING(
         (String)com.yiyiaddon.m.b.a<"s1uz5qwcmgpgq1","9rk69TFviJ37KAMaLVxOhQ6Zra70gxd6nJEjc63jhoJy4Q==",-6355164816546885619,5338704459368162391,-4236224967385792521,-6746118834991674152>()
      ),
      GRINDING(
         (String)com.yiyiaddon.m.b.a<"s1sgk81mpddwvz","cIEAWrpxPAoJr28rL34txNbifYWfH2mvIqLkBP20SH4MCwiEoJM=",3908186542644887319,-8795352711718592254,-7091656915252424953,8504377784488467111>()
      ),
      ANVIL_PLANNING(
         (String)com.yiyiaddon.m.b.a<"s2jbicxdwx6pl8","JeXI8iSf7jxYw5aEm7NJiuLUD3OPRsZx8du66FFJB8VxL21f",-728339592252447912,6367330783515783935,-7705769070033881317,8011908708171564029>()
      ),
      ANVIL_MERGING(
         (String)com.yiyiaddon.m.b.a<"so2vqpdww76hu","s3SNkYe+LpiGE8TPZqlmtjdlSBEYfGCcrs/JCwJ7ciqEedfd",5148904794856942357,-6633862007440043086,2429358546668857180,1319132522934699532>()
      ),
      VALIDATING(
         (String)com.yiyiaddon.m.b.a<"svm3utu6g8dkp","Iet4bho6y/rVHPLvXbykKL3LlJ3oAZrBC/67ZXyD7CFdCFJW",-2187928686477536675,-169604074588756390,5314769377445320979,-175289150095971890>()
      ),
      STORING(
         (String)com.yiyiaddon.m.b.a<"snymyear8i7b","EbSGjCEVUc3k7fSxaXga9wDmhQERV/RmtvqF2nVfkR69uNcd/AA=",2479764073649125399,5426725236880775491,-4890491224016801626,-5889994861620022170>()
      ),
      ERROR(
         (String)com.yiyiaddon.m.b.a<"sifhzmhsa0za2","/e0YVqp8lB2Doo+G98AMbVpaJn/H1PjwNsColaG26o8=",-1074651530472437079,-2428048018272984596,2973392457642196518,964641915240652825>()
      ),
      DONE(
         (String)com.yiyiaddon.m.b.a<"s3topv1tbr5690","e+TUDl8rraPtejzFR5NjgoWWAwO4gUlYm01TNGTEORI=",-7995187277064772474,-6052395138132462634,1157364646607916751,-4147844929991437410>()
      );

      private final String jD;

      a(String var3) {
         this.jD = var3;
      }

      @Override
      public String toString() {
         return this.jD;
      }
   }

   public enum b {
      PENDING(
         (String)com.yiyiaddon.m.b.a<"s1vqapi4in3rfe","hDfDK+a3RG6c5UJBvEhYI8P622g2uFepMGKZZUZ/ps2wTQ==",7354836637290755508,-8106964350340090198,-6343634128587714624,6031330691845624538>()
      ),
      PROCESSING(
         (String)com.yiyiaddon.m.b.a<"s1qjxoeuoa10vc","q0KZW+HDOw4EDXJuwzdTd6w+oS+meOOnttZmvYf1qhevvw==",7864243068476764306,4680248662140433819,3474266246319092689,-1646863036279846429>()
      ),
      ERROR(
         (String)com.yiyiaddon.m.b.a<"s2esk9dzbgvh2r","EFlgfTpCJOKH90dedNQTJ8rGJLjwAQowsTvyIaQT2FA=",-4704929758714104575,3187393433526070764,446738664485888282,-180676784149691964>()
      ),
      DONE(
         (String)com.yiyiaddon.m.b.a<"s2f4gflrptq3c4","e/XuA7lQBClZBmCb32uxGYabdYHL8TmvKHtJVjaxUFQ=",-5891588323819497236,3856353910174705496,4545050580343199387,5807046602510803160>()
      );

      private final String jE;

      b(String var3) {
         this.jE = var3;
      }

      @Override
      public String toString() {
         return this.jE;
      }
   }
}

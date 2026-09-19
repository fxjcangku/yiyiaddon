package com.yiyiaddon.l.h;

import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.b.q;
import com.yiyiaddon.l.b.s;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.g.i;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.function.Supplier;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;

public abstract class f extends i {
   protected static final float lT = 18.0F;
   protected static final float lU = 70.0F;
   protected static final float lV = 6.0F;
   private static final float lW = 18.0F;
   private static final float lX = 16.0F;
   private static final float lY = 58.0F;
   private static final float lZ = 27.0F;
   private static final float ma = 19.0F;
   private static final float mb = 44.0F;
   private static final float mc = 11.0F;
   private static final float md = 120.0F;
   private static final float me = 10.0F;
   protected static final float mf = 40.0F;
   private static final float mg = 8.0F;
   private static final float mh = 8.0F;
   private static final float mi = 4.0F;
   private static final float mj = 26.0F;
   private static final float mk = 12.0F;
   private static final float ml = 8.0F;
   private static final float mm = 13.0F;
   private static final float mn = 16.0F;
   private static final float mo = 1080.0F;
   private static final float mp = 760.0F;
   private static final float mq = 24.0F;
   private static final float mr = 520.0F;
   private static final float ms = 380.0F;
   private final String Gw;
   private Supplier<String> A;
   private final com.yiyiaddon.l.b.i h = new com.yiyiaddon.l.b.i(6.0F).a(true);
   private final q d = new q();
   private final s c = new s();
   private final com.yiyiaddon.l.b.a c = new com.yiyiaddon.l.b.a();
   private final com.yiyiaddon.l.g.g e = new com.yiyiaddon.l.g.g();
   private boolean gt;
   private boolean gv;
   private boolean gw;
   private boolean gz;
   private long bi;

   protected f(String var1, Screen var2) {
      super(Component.literal(var1), var2);
      this.Gw = var1;
   }

   protected final void c(Supplier<String> var1) {
      this.A = var1;
   }

   protected final com.yiyiaddon.l.b.i d() {
      return this.h;
   }

   protected final void bw(String var1) {
      this.h.a(new w(var1).a(26.0F).b(12.0F).a(true));
   }

   protected final void w(String var1, String var2) {
      this.h.a(w.a(var1, var2));
   }

   protected final void a(String var1, Runnable var2) {
      this.h.a(new com.yiyiaddon.l.b.b(new com.yiyiaddon.l.j.a(var1, var2)));
   }

   protected final void a(com.yiyiaddon.l.j.a... var1) {
      this.h.a(new com.yiyiaddon.l.b.b(var1));
   }

   protected final void kN() {
      this.h
         .a(
            new w(
                  (String)com.yiyiaddon.m.b.a<"s30g8fxoy8jr1f","382SfDFJnFFF2e+MgI5BKE7vOB+EfKF0CZh6tDUY",3968811003564057631,-4749257750159582738,-8922462007981264935,2847969539576725214>()
               )
               .a(8.0F)
         );
   }

   protected final void kO() {
      this.h.a(new com.yiyiaddon.l.b.g() {
         @Override
         public float b() {
            return 13.0F;
         }

         @Override
         public void a(float var1) {
         }

         @Override
         public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
            j.a(var1, var2 + 6.0F, var3 + 6.5F, var4 - 12.0F, com.yiyiaddon.l.i.c.a().uW, var5);
         }

         @Override
         public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
            return false;
         }

         @Override
         public boolean a(float var1, float var2, float var3, float var4, float var5) {
            return false;
         }
      });
   }

   protected final void x(String var1, String var2) {
      com.yiyiaddon.d.c.a(var1, var2);
   }

   protected static void bx(String var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3061r16f3mllu","E303wLeNwtn+1gkxAf0XkpgFBkPHnrPWbdgIB61TihY=",-2590650988987226032,4092321641638418023,3890528447285170564,4131388486003966678>()) {
            case 900222155:
               if (var1.keyboardHandler != null) {
                  KeyboardHandler var10000 = var1.keyboardHandler;
                  String var10001;
                  if (var0 == null) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"s143vwfu7tga7","J5tV3b+69sO1e3NGJIcqhfOJDYo34ug+L7AS32Ppy6k=",4695013036118140084,-6109331913611410741,-3576013693570981170,-7200808893030317613>()) {
                        case -853700574:
                           var10001 = (String)com.yiyiaddon.m.b.a<"snjymavki9o41","PdmF6AkUfKm7xs7V1w4C4UJcHY3cJR736LIvFg==",2731640257947219675,7551072222859932660,-8708982809651214557,-6298226322972592607>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1mz86k5u7212m","drnr0Ej0/Ud/pSIUqd7d8OZbTkHfI8IPe3+rAb97hiA=",-1650725954621880971,4671728076788005363,-4640926982581010455,-6884972418899724950>()) {
                              case -1597168520:
                                 break label22;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = var0;
                     switch ((int)com.yiyiaddon.m.b.a<"s1vvg1cbm3gk8u","y3jpisMoWG+cZbVauTilW25yfnU5T7OxFiV3cK7bNT8=",-4896787964995169638,-1617890924247456239,576840686067651079,-6910981107659847637>()) {
                        case 1324510303:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var10000.setClipboard(var10001);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3mzp8hy3wj9jy","iIp2BenoDOlobE3E5nwCguOufWKtjtOwQXRadaSYVzM=",3012216862901942378,736844340219502102,7930024838611025771,1088980064981601926>()) {
                     case 590362495:
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

   @Override
   protected void init() {
      super.init();
      this.kP();
      this.d.a(this.minecraft, 0.0F);
   }

   protected void kP() {
      if (!(this instanceof com.yiyiaddon.l.c.b)) {
         switch ((int)com.yiyiaddon.m.b.a<"s190qt0u0j8euy","1+25mVKbsJ/3Z0NinrE9ouU6TWVLl14w8zFgK/bsIJ4=",-8047529040374287478,6489533985662252604,391423397974155744,8357681451574243656>()) {
            case -1626078732:
               this.d.d(740.0F, 500.0F);
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         float var2 = 1080.0F;
         float var3 = 760.0F;
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2fcyqljyumg92","wgYwOq6SEqY6iKqsBvUsoDr4WRf/aaDFT5GNHLEOoa8=",6969061876153424824,-953647402464100399,6210835073621443269,-8692744113253247523>()) {
               case -1096177001:
                  if (var1.getWindow() != null) {
                     label21:
                     switch ((int)com.yiyiaddon.m.b.a<"s1d39zxql1sykg","rYSeNLGqZ0OZGNAge4GB8x5ubXe2FsnXYh2pqKRm0j8=",-7195178574739077610,1354177029799378414,-3629263934168935627,-2870406344054488924>()) {
                        case -662424657:
                           var2 = Math.max(520.0F, var1.getWindow().getWidth() * 0.5F - 24.0F);
                           var3 = Math.max(380.0F, var1.getWindow().getHeight() * 0.5F - 24.0F);
                           switch ((int)com.yiyiaddon.m.b.a<"s2ms03cauuxhh6","y53/Lm/Y1JeHnavEJqcCxQu/L1vJ+QY1cDVB+puc9I0=",-2125015907027558946,-8097811782549018130,4957404806973830648,1452305303765265115>()) {
                              case 823486994:
                                 break label21;
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

         this.d.d(Math.min(1080.0F, var2), Math.min(760.0F, var3));
      }
   }

   private float al() {
      return this.d.w() - 70.0F - 18.0F;
   }

   @Override
   protected boolean gi() {
      if (this.d.z() >= 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3d0mybdjqp8gr","HvX14V51jIBbhud96fsOn0/ZlSCIUMZNffSDMjIQtuQ=",5430220934228588244,1509956011254164730,-891822865081009705,8299968297841244720>()) {
            case 1357758225:
               switch ((int)com.yiyiaddon.m.b.a<"s3l929bmz90gia","Zlli6as0SjtqhOmYWJgjv21QGX8SejaTb8QWUUutcq0=",1716507702033043279,-4196405156220317580,-372103582313428855,8785667482622318795>()) {
                  case -195035066:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s334ri6rbt49gs","mNqWaaKbRqNe6wtSEgqOxJS2+WwPd+9/uc/UBJ3tpfY=",-2794719017624933835,-7505115896045675415,7335450348108731814,3860918999066301841>()) {
            case 430561609:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   protected void a(int var1, int var2, int var3, int var4, float var5) {
      if (this.minecraft != null) {
         Canvas var6 = this.e.a(com.yiyiaddon.l.g.g.ef());
         if (var6 != null) {
            try {
               this.a(var6, var1, var2, var3, var4);
            } finally {
               this.e.km();
            }
         }
      }
   }

   private void f(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6) {
      if (this.A == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ddri98afe34z","j+XCB4ix2Ud5QTppSxbfiMsdiir2i3VxHeTViRbJATI=",6643726910860427987,-9097356285685778735,2329600816907807637,-8592525330465316082>()) {
            case 1925551865:
               return;
            default:
               throw null;
         }
      } else {
         String var7 = this.A.get();
         if (var7 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s30u1e7xhufl6s","cfIwijZlLKowXignMfbOQdT3yh5gio6upHEzoHZuuVg=",-7619709100779380680,-6179870087507176865,-2445473449276079579,-3240718402064976106>()) {
               case -1767255440:
                  if (!var7.isEmpty()) {
                     com.yiyiaddon.l.g.a.b(var1, com.yiyiaddon.l.b.d.a(var7, var4 - 120.0F, 11.0F), var2 + 58.0F, var3 + 44.0F, 11.0F, j.a(var6.uU, var5));
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s20wad59lpd18l","tRlqIUKtWq7GOrTpxebZp00achnv4app/mK2WnIe3ys=",4100206458655238809,6901576833030030126,-7331443205056075457,5206369498431518977>()) {
                        case 1747791955:
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

   private void a(Canvas var1, int var2, int var3, int var4, int var5) {
      com.yiyiaddon.l.g.j.kv();
      long var6 = System.currentTimeMillis();
      float var8 = this.bi == 0L ? 0.016F : Math.min((float)(var6 - this.bi) / 1000.0F, 0.033F);
      this.bi = var6;
      this.kP();
      if (this.d.a(this.minecraft, var8)) {
         this.ku();
      } else {
         com.yiyiaddon.l.i.c var9 = com.yiyiaddon.l.i.c.a();
         float var10 = this.d.z();
         float var11 = this.d.A();
         float var12 = this.d.x();
         float var13 = this.d.y();
         float var14 = this.d.v();
         float var15 = this.d.w();
         float var16 = this.d.a(var4, var2);
         float var17 = this.d.b(var5, var3);
         float var18 = var12 + 18.0F;
         float var19 = var13 + 70.0F;
         float var20 = var14 - 36.0F;
         float var21 = this.al();
         boolean var22 = var10 > 0.01F;
         this.c.a(var16, var17, var12 + 18.0F, var13 + 16.0F, var8, var22);
         this.c.e(this.h.b() + 8.0F, var21);
         this.c.a(var8);
         this.h.a(var8);
         if (com.yiyiaddon.c.a.d) {
            float var23 = (1.0F - var10) * 16.0F;
            com.yiyiaddon.l.g.f.a()
               .a(
                  var1,
                  this.e.b(),
                  this.minecraft,
                  com.yiyiaddon.l.g.g.ef(),
                  this.d.b(var12, var2),
                  this.d.c(var13 + var23, var3),
                  this.d.k(var14),
                  this.d.k(var15),
                  this.d.k(var11),
                  com.yiyiaddon.c.a.c(),
                  com.yiyiaddon.c.a.a
               );
         }

         j.a(var1, 0.0F, 0.0F, var2, var3, 0.0F, var9.uY, var10 * (var9.gB ? 0.16F : 0.1F));
         var1.save();
         this.d.a(var1, var2, var3);
         var1.translate(0.0F, (1.0F - var10) * 16.0F);

         try {
            j.d(var1, var12, var13, var14, var15, var11, var9.uY, var10, 1.15F);
            j.a(var1, var12, var13, var14, var15, var11, var9.uN, com.yiyiaddon.c.a.d ? 0.62F : 0.94F, var10);
            var1.save();
            var1.clipRRect(RRect.makeXYWH(var12, var13, var14, var15, var11), true);

            try {
               j.a(var1, var12, var13, var14, var15, var9, var10, 0.46F);
               j.c(var1, var12, var13, var14, var15, var11, var9.uX, var10, 0.26F);
               this.c.a(var1, var12 + 18.0F, var13 + 16.0F, var10, var9, var22);
               com.yiyiaddon.l.g.a.c(var1, this.Gw, var12 + 58.0F, var13 + 27.0F, 19.0F, j.a(var9.uT, var10));
               this.f(var1, var12, var13, var20, var10, var9);
               float var39 = this.c.r();
               var1.save();
               var1.clipRect(Rect.makeXYWH(var18, var19, var20, var21));

               try {
                  this.h.a(var1, var18 + 10.0F, var19 - var39, var20 - 40.0F, var10, var19, var19 + var21, var16, var17);
               } finally {
                  var1.restore();
               }

               this.c.b(var1, var18 + var20 - 8.0F, var19 + 4.0F, var21 - 8.0F, var10, var9);
            } finally {
               var1.restore();
            }

            com.yiyiaddon.l.g.j.b(var1, this.d.x() * 2.0F + this.d.v(), this.d.y() * 2.0F + this.d.w(), var10);
         } finally {
            var1.restore();
         }
      }
   }

   @Override
   public boolean keyPressed(KeyEvent var1) {
      if (com.yiyiaddon.l.j.f.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s30158ny7ncu3l","wygLCPdnF3BF9m//Lmgvji0CSz2fRx72imqF/ZpvVxo=",8721046931248949585,6421857254244489844,-1715513686389072501,-8847556799405128943>()) {
            case -516080110:
               return true;
            default:
               throw null;
         }
      } else if (com.yiyiaddon.l.d.d.w(var1.key())) {
         switch ((int)com.yiyiaddon.m.b.a<"s25t5f6f4fxqai","VxmFHTpBsgSDw9IHo/tyv+Gjm7EhRn5rHyjiUpUbxA0=",-3620779014287749887,3312955286531720172,-2391416240053655013,-7515284988805827164>()) {
            case -303664785:
               return true;
            default:
               throw null;
         }
      } else if (m.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s5yprjzfuokp3","xyjAma9U693zStl9NUW0Bn70Ed2+OIZFzub2mjcffhY=",3036349048163001078,3488916135252028260,-8531767184447397757,-1989012458859623226>()) {
            case -228874810:
               return true;
            default:
               throw null;
         }
      } else {
         return super.keyPressed(var1);
      }
   }

   @Override
   public boolean charTyped(CharacterEvent var1) {
      if (m.charTyped(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2556v71alsm6e","WQNx4YcUzn+SRatWcp9yN2gSNkFylWVc2darhq6TxTs=",4540887160015619866,-1831032701859919494,-6607246993907598461,1225340819546974051>()) {
            case 1457436503:
               return true;
            default:
               throw null;
         }
      } else {
         return super.charTyped(var1);
      }
   }

   @Override
   public boolean preeditUpdated(PreeditEvent var1) {
      m.a(var1);
      return true;
   }

   @Override
   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      if (this.gt) {
         switch ((int)com.yiyiaddon.m.b.a<"s139y03twvj6qn","QodJOUu8Y7fqj7Qzg516fN4zcM+06bqZ9aQ8Enf7VF0=",5729734887509180581,-4654755400752378795,8879551620019946003,-6392261259008742781>()) {
            case 1940117564:
               return false;
            default:
               throw null;
         }
      } else if (com.yiyiaddon.l.j.f.c(var1.button())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1w7k1iqgg3sp3","plui7Wm3SEEXBGCMb7SXudpp9lQ3/sUe7n2avaa3aIw=",7917372123789279317,618747501163913695,187260336480447377,-501943516680138617>()) {
            case -279065100:
               this.gv = false;
               this.gw = false;
               return true;
            default:
               throw null;
         }
      } else if (com.yiyiaddon.l.d.d.x(var1.button())) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ejtixeuy8y0t","ivB3zpY4txZk6IMzFTQX9lvKpw1LF1zVy5g439NkNrg=",-42850567403632949,5389328578407969867,-952290745076857202,7239903264152258967>()) {
            case 1927645757:
               this.gv = false;
               this.gw = false;
               return true;
            default:
               throw null;
         }
      } else if (var1.button() > 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tee4l4h1juzl","b0KfnURAwQFe7Gbk6FmI55ol7EfFLtxw66Q90OQOrvs=",-1425834197371728665,4323318753638771159,-5649243522554152879,-4452491993182618358>()) {
            case -269634718:
               return true;
            default:
               throw null;
         }
      } else {
         float var3 = this.d.a(var1.x(), this.width);
         float var4 = this.d.b(var1.y(), this.height);
         float var5 = this.d.x();
         float var6 = this.d.y();
         float var7 = var5 + 18.0F;
         float var8 = var6 + 70.0F;
         float var9 = this.d.v() - 36.0F;
         float var10 = this.al();
         if (var1.button() == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s38g4q3b7u304y","STVPGbATyLPZkK8jZ1bkah45dIAKRJJTiPoJNM9DNqY=",-1153034994560437784,-8248294549254614319,-6690054208977003919,9004522242742947678>()) {
               case 925641914:
                  if (this.c.b(var3, var4, var5 + 18.0F, var6 + 16.0F)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s169igd353dnvc","TqF9df2qe5XQAgHTp/DVVXjYUS7q7+trpyW8VPyR10U=",6055907251817667432,-4598992395856136232,8406877818313247681,2446992598063813710>()) {
                        case 875447282:
                           this.c.jJ();
                           this.kR();
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (!(var3 < var7)) {
            switch ((int)com.yiyiaddon.m.b.a<"s35k5tp8q5coll","7Y4kDpLJit90bunhXMZ8jXz3u4ZG2bYKbw+i4P+Ncdw=",3119751218014102833,-4501889145035025751,6025508740655859176,5402174679640439661>()) {
               case -1813847061:
                  if (!(var3 > var7 + var9)) {
                     switch ((int)com.yiyiaddon.m.b.a<"ss5l00wvz4oju","VsMaxkJITAewwbiBWsMbKwODvyNRk2iuynyPq3vPzW8=",8347183663517573464,4322861163295515591,-4144739562098454247,-894473365287816064>()) {
                        case -169290157:
                           if (!(var4 < var8)) {
                              label93:
                              switch ((int)com.yiyiaddon.m.b.a<"s17dau1v11ylbj","VC+pPzOLR2XeY2vA6cGKM3eU5p8tPodxxduEQSFNcuk=",-3874886797659847622,392123750848398074,-1087062623462341454,4192892741033580552>()) {
                                 case -2073231125:
                                    if (!(var4 > var8 + var10)) {
                                       float var11 = var8 + 4.0F;
                                       float var12 = var10 - 8.0F;
                                       this.c.e(this.h.b() + 8.0F, var10);
                                       if (var1.button() == 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1xeye70isy2x9","+APTU+e+IX0ld35yJs0031liNIIBcz4InR/PKuvoA90=",488278878428501188,-995341001668780245,-5720412656629368308,5744429780768766125>()) {
                                             case -612971611:
                                                if (this.c.fR()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3fqk3btlig5se","/FMFvjmEuQSltjK/8h0SpoVDEHUFMtLOxA63GID5caE=",8593360917956874334,3733992491626347987,-6316002134513902200,7657332200648278436>()) {
                                                      case 84554588:
                                                         if (this.c.d(var3, var4, var7 + var9 - 8.0F, var11, var12)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"swwcuenxqziph","oPH95UvQI7tGL5yvjUUfO3QfYxNDFoRZ3qYqfXrqT4g=",-1150176815337455071,2566443169560882664,-6213129117105633403,-4209346338134220616>()) {
                                                               case -1579555277:
                                                                  this.gw = true;
                                                                  this.c.a(var4, var11, var12);
                                                                  return true;
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

                                       float var13 = this.c.r();
                                       boolean var14 = this.h.a(var3, var4, var7 + 10.0F, var8 - var13, var9 - 40.0F, var8 + var10, var1.button());
                                       if (!var14) {
                                          label80:
                                          switch ((int)com.yiyiaddon.m.b.a<"syw9hvahncqk0","Llp76KiMm1IGGLRtYxHTonatGlhgiqSB56XwLP66CLg=",-4859761657457922219,1959986605404021853,-3383660132896437425,-658481310925026806>()) {
                                             case -736307365:
                                                m.lb();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2jfwr4lvh7wo","RoRjUr/ai9z+jrzzu3Lo5tPg9YgKvOxFeOttEVnxCwo=",-2866036527449936879,-8253401464948369935,5647313896909262607,7434186243844499083>()) {
                                                   case 1920661261:
                                                      break label80;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (var14) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3iv49r4ha3h3o","/SvQQc8V4xEez1lJWwAAQ0VTqGBB+zkKxryQkD+tcgo=",7104345082317947856,1962686904106685839,287437010027839593,-8145382129199289603>()) {
                                             case 1078077973:
                                                if (var1.button() == 0) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s291ejjtne6ghx","YNiIv/RyffKjOv7hpA02QJDZtW7LrmW5G2JBUmyPe2Q=",4118493282916264580,-7407606324881233374,-1508207032464318772,2122706998308383938>()) {
                                                      case -906757267:
                                                         this.gv = true;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3ksybcohy52f7","OPu8iMtKbpvdW8GPbPIz17H7mxc2ucd7tl6QnzYgFeg=",6780830683335967676,-2840394699165422784,-3447754500743581175,-6285025950970120151>()) {
                                                            case -835654777:
                                                               return var14;
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

                                       return var14;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1exncms2ytffk","Xhqcv2VvKV/YFzGWErbTZ/b/W6YXqBXr9qK4oMVf620=",8763657579948379268,-7011945365451391624,4029861461704600807,-6154370915696894074>()) {
                                       case 2096789556:
                                          break label93;
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

         m.lb();
         return false;
      }
   }

   @Override
   public boolean mouseDragged(MouseButtonEvent var1, double var2, double var4) {
      float var6 = this.d.a(var1.x(), this.width);
      float var7 = this.d.b(var1.y(), this.height);
      float var8 = this.d.x();
      float var9 = this.d.y();
      float var10 = var8 + 18.0F;
      float var11 = var9 + 70.0F;
      float var12 = this.d.v() - 36.0F;
      float var13 = this.al();
      if (this.gw) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xiwhd430opm","FG8ACI3WA0HnPQGIVQvmCQH9CQVdrgSxQvSzpYUSsnI=",7702013806209225663,-5471107949390868686,7486236419244764468,-8841379704982165696>()) {
            case -1142021607:
               this.c.e(this.h.b() + 8.0F, var13);
               float var14 = var11 + 4.0F;
               this.c.b(var7, var14, var13 - 8.0F);
               return true;
            default:
               throw null;
         }
      } else if (this.gv) {
         switch ((int)com.yiyiaddon.m.b.a<"s383rajg11fcu6","j99+qEIIi12DR/ywge8cHR8YR016Q+LK+bx0YBYqfTw=",2952344409313037924,4034947758104913300,3277334381066522875,-2001687999737151971>()) {
            case 676071140:
               this.h.a(var6, var7, var10 + 10.0F, var11 - this.c.r(), var12 - 40.0F, var11 + var13);
               return true;
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(MouseButtonEvent var1) {
      this.gv = false;
      this.gw = false;
      this.h.F();
      this.c.jK();
      return false;
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5, double var7) {
      float var9 = this.d.a(var1, this.width);
      float var10 = this.d.b(var3, this.height);
      float var11 = this.d.x() + 18.0F;
      float var12 = this.d.y() + 70.0F;
      float var13 = this.d.v() - 36.0F;
      float var14 = this.al();
      if (var9 >= var11) {
         switch ((int)com.yiyiaddon.m.b.a<"s25hku8q3wufso","9fV4PZ5YkwLYdKIug9bT0azTTeGzqgdvDhsAmKkVTXs=",-8496217150152233287,1075514552843023228,7982576560380128572,-7647930550391062745>()) {
            case -598547192:
               if (var9 <= var11 + var13) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fq57gtig5nac","p+7y3LcZP6Rj7Ztx5DhC0MyuxmEGLwT3Tv7BZvd68DU=",4321886023323219231,-9188727432065227092,4290854096480363917,-5471847459441856503>()) {
                     case 39836084:
                        if (var10 >= var12) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1vwbw9waep7xp","nr/JJ4Oeq9IdjawCD76w7KTjML//TTbi1VT4oP9gRBk=",4179293906500972700,-8873390535434905403,-8905734270721191047,-178631831121344085>()) {
                              case 216319106:
                                 if (var10 <= var12 + var14) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s36ws7nae7odce","AbcZChmGeM+bWdEkEfOrpEfAzXxc1cyW3bDxj15mivU=",-347507453469161324,-3272208822286115448,-82995058633113766,-7365266578869223025>()) {
                                       case 1201416056:
                                          this.c.e(this.h.b() + 8.0F, var14);
                                          this.c.a(var7, com.yiyiaddon.c.a.b);
                                          return true;
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

      return false;
   }

   protected final void kQ() {
      this.gz = true;
   }

   protected final void kR() {
      if (this.gt) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g530lx3y1mgd","mroyfqQpJo2nilWgS7xayrv4wjXFH2lXwltO5Pg2W3s=",-3249701315417934338,3495981870169593215,-7745474205391791905,6356587461995988274>()) {
            case 1352017147:
               return;
            default:
               throw null;
         }
      } else {
         this.gt = true;
         m.lb();
         this.c.i();
         this.d.jN();
      }
   }

   @Override
   protected void ku() {
      if (this.gz) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lrwyo66l8lta","HkFLLBpsBFmi6zJ6xoGOlxSR1yIpxD4ZiX9TA0Zykdc=",-5026325184709169570,3685252216837443444,8660281002122086450,-5789949382087549777>()) {
            case 460551176:
               if (this.minecraft != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s8p92xhc89bq9","s4qHjfBlMb+oLf9NsR0SPWY+dzV30jGGIOqd2MuXawM=",-6629740582204220011,3880750479632616263,-2914705864182462653,2338272069417078767>()) {
                     case 2107584227:
                        this.minecraft.setScreen(null);
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      super.ku();
   }

   @Override
   public void onClose() {
      this.kR();
   }

   @Override
   public void removed() {
      m.lb();
      com.yiyiaddon.l.g.b.f();
      this.e.ko();
      super.removed();
   }
}

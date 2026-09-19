package com.yiyiaddon.l.g.a;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.gizmos.GizmoProperties;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class f {
   private static final float kD = 64.0F;
   private static final int uv = 10;
   private static final int uw = -1;
   private static final int ux = 0;
   private static final float kE = 64.0F;
   private static final Paint s = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeCap(PaintStrokeCap.ROUND);
   private static final Paint t = new Paint().setAntiAlias(true).setMode(PaintMode.FILL);
   private static final Paint u = new Paint().setAntiAlias(true).setMode(PaintMode.FILL);
   private final Canvas b;
   private final m a;
   private final h a;
   private final List<f.a> dw;
   private final e c = e.a();
   private final i[] a = new i[8];
   private boolean gk;
   private int uy;
   private float kF = 1.0F;
   private boolean gl;

   private f(Canvas var1, m var2, h var3, List<f.a> var4) {
      this.b = var1;
      this.a = var2;
      this.a = var3;
      this.dw = var4;
   }

   public static f a(h var0, List<f.a> var1) {
      return new f(null, null, var0, var1);
   }

   public static f a(Canvas var0, m var1, h var2) {
      return new f(var0, var1, var2, null);
   }

   private int s(int var1) {
      float var2 = this.c.Z() * this.kF;
      if (var2 >= 0.999F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1w7jpmk67v7nr","ggs15lnubymVRm3ueSYosm169DJw/DbYTWCR+vJ/xco=",-1873988085930324423,-8036722827113408700,-279726652126883197,-1623980469795499481>()) {
            case 1538388537:
               switch ((int)com.yiyiaddon.m.b.a<"s1cxfohp6wjip3","4OVOVKQ3+jUawiaGAvASHj2fWkTbbQ1LAteBITek5yA=",772727371568356992,5402217666281341412,8930203127584194281,-507329221248861452>()) {
                  case -1669076732:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = com.yiyiaddon.l.i.c.b(var1, Math.max(0.0F, var2));
         switch ((int)com.yiyiaddon.m.b.a<"s1hc3pkblqr51a","zRcGp1UYHsB9TquV8aqzk4+vVYPOw5IKXVKvWpfnHGs=",-7637835503674670082,-7261909815213428094,8199187346334020346,-6652015305059462022>()) {
            case -199247631:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private float t(float var1) {
      return this.c.s(var1);
   }

   private static float u(float var0) {
      float var1 = var0 * Minecraft.getInstance().getWindow().getGuiScale();
      if (var1 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1odi7fzf7cd15","29jVGxqsKRK078Ce9RiRnP1Dy+k1FFKn+V7ZSDTwqME=",-3415654597788133020,-3889813393163381963,-9129705337525392417,8479233349680913530>()) {
            case -563879878:
               switch ((int)com.yiyiaddon.m.b.a<"s3dal79ghrijnz","m3muIcux6ZYCrYQc0/kD3p26aUN+cR6r+gcFiUQNpiA=",-9219009413168012423,-106860520679801943,-1241420769248227585,3799478623145538949>()) {
                  case 1076329059:
                     return 0.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2lj470shrgr5b","opY5qXG82kGncUeWcU+CO34S02nwqFGRh80sHEzg7yc=",6221267691294222307,-3115203939308761134,4699201118836909473,-3176161335175626947>()) {
            case 565918852:
               return var1;
            default:
               throw null;
         }
      }
   }

   private j b(j var1) {
      if (this.gl) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mdxxtfqb6wfs","4swb6DyfkwMbZ/HvVp9u11EX3vd//joNlZjYtT8C064=",-6593706971962125037,7801687682849015585,290698632894905699,-7237259218503928597>()) {
            case 1494760400:
               switch ((int)com.yiyiaddon.m.b.a<"s1tll69362v8zi","SjuXJwAr2QgS1p4wvjU8KEAsE/iolzXb6bBKT82rSPI=",-3476046086911136880,2431257920345359608,4490005004049791119,-1438207847833300132>()) {
                  case 468040664:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         j var10000 = this.c.a(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1bv16r79umsqy","W+pnbqequpB84pnBzFrFiBm8iqMUvAuF9HbYEbJqTIE=",-5770249945115802472,-450632343277488574,657353708069742171,-7743150760405009612>()) {
            case 1440902364:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private boolean gs() {
      return this.c.d(this.gk);
   }

   private float a(double var1, double var3, double var5) {
      int var7 = this.c.el();
      boolean var8 = this.c.gk();
      if (var7 <= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m1imkdcs8r0d","B6DzXhzosLNwkC2wk1V/ylw2RJu634kAhSjMW2NMq3Y=",7750941423941975327,2378937589529792510,-163695812508753541,8737569846564364148>()) {
            case 1950164639:
               if (!var8) {
                  switch ((int)com.yiyiaddon.m.b.a<"ssl3q7joigwky","P57vvjSm4SsBPqjkQC8m+d8Pwz8PXE/QdVqcknypz7Y=",-3097670165889550936,-8301106999656307683,-6454941311533385004,3363418487230968076>()) {
                     case -1164094153:
                        return 1.0F;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      Vec3 var9 = this.a.a();
      double var10 = var1 - var9.x;
      double var12 = var3 - var9.y;
      double var14 = var5 - var9.z;
      double var16 = Math.sqrt(var10 * var10 + var12 * var12 + var14 * var14);
      if (var7 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1xe3f19vzmcrg","ReUB7MImSJVbBpFBl+ZCt6b55NcYxtyp5a5xPqZFUwI=",-1993409838443609189,2606726464662014229,643534361604120365,-59921853939549952>()) {
            case 1278400965:
               if (var16 > var7) {
                  switch ((int)com.yiyiaddon.m.b.a<"s13130mrgyprbg","N8g67lwMwGx0voCnnGH4pWEX4j3BlWHky/U25Oadl7Y=",7746696565133164237,1927398749347185767,8859119508030677139,-2663671587909767441>()) {
                     case -686335864:
                        return 0.0F;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (!var8) {
         switch ((int)com.yiyiaddon.m.b.a<"s5u1ue968wohs","lXUmWwAmg64XdMjvs2AbkFTvWysoebbPPCDXDfGFm14=",-7690485823055274723,8855498855893167269,-4608943882142031249,-3495143489579948995>()) {
            case -915614527:
               return 1.0F;
            default:
               throw null;
         }
      } else {
         float var10000;
         if (var7 > 0) {
            label43:
            switch ((int)com.yiyiaddon.m.b.a<"s11xbr2qzz9wei","vZO/dvp6eC3GHB5/3BQ6FGg70/NNt6s3kDapMpM4cB4=",-2327383851724793239,950951047079747991,2356073788128584080,8191514143623959334>()) {
               case -344651411:
                  var10000 = var7;
                  switch ((int)com.yiyiaddon.m.b.a<"s1m9wwzjwy529f","mG9sghMAZteB8yVXUqF+H7lxkmgDczm+/GHHQYMfEqc=",-6517372264545711947,-4397589779264265519,685963248881664582,-2371178776709258218>()) {
                     case 1532942239:
                        break label43;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 64.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s1jj8e23qkr53w","iYxDKLulBM/W3b976DeXgWD2iGTGENCFo7sFJIzuxAA=",-9027447745554038728,-5326161584695013716,-5472282714638974277,5732744035474884452>()) {
               case 53442401:
                  break;
               default:
                  throw null;
            }
         }

         float var18 = var10000;
         float var19 = Math.min(this.c.em(), Math.max(0.0F, var18 - 1.0F));
         if (var16 <= var19) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ywe99783dvau","oW1F/uLoGT5/NhG5y80jBwUpR+WqSOTtOUu8nFNBrYE=",-1321157019498584194,-574290468121537638,4585319697381782112,-4036636126965104524>()) {
               case 2066203103:
                  return 1.0F;
               default:
                  throw null;
            }
         } else {
            return Math.max(0.0F, 1.0F - (float)((var16 - var19) / Math.max(0.001, var18 - var19)));
         }
      }
   }

   private float a(Vec3 var1) {
      return this.a(var1.x, var1.y, (double)var1.z);
   }

   private boolean gt() {
      int var1 = this.c.en();
      if (var1 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lu7nm8xiqzgd","6HoViZPi4rfOlTP9YWZsutUD2lVfa3sUlUs7JC/5pMg=",5389921612017568276,2113517052686791927,1539398108560232233,-4083860155162245124>()) {
            case 1514115067:
               if (this.uy >= var1) {
                  switch ((int)com.yiyiaddon.m.b.a<"slwozq63r2g5z","z3BLXK8WHx5d5bQDwO5Cv866quvtvX4l3m4mNAaJZbU=",-1464665599366609072,913957382105059947,-3985827897526599109,5904121816760858967>()) {
                     case -955038557:
                        return false;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.uy++;
      return true;
   }

   private void a(GizmoProperties var1) {
      if (!this.gs()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ftj8c1v7c0nh","Bljr/N3b3TTq1BGywf4IvwmZwJt+lwTtYGcadmo6FK0=",-8412167323573154867,-1199056684581831301,-2939461190350683492,4878545669412303093>()) {
            case 1424362320:
               var1.setAlwaysOnTop();
               switch ((int)com.yiyiaddon.m.b.a<"s1hgjdy2flq70k","uqtHhLxuWN91mkW7zz0h7bdCpa7INhcgHtA+UI9XOWg=",-270259462576175782,5175604724264505028,-1877991587367355513,-2991937287283046080>()) {
                  case -1281169810:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public f a(boolean var1) {
      this.gk = var1;
      return this;
   }

   public boolean gu() {
      return this.gs();
   }

   public float ab() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11mnrwyu99us8","qWLKcVp8Gm3mAc3Vs1/a/5v2gLf2Y4dJuC7EV1gqWh4=",-9203646907521939432,-3782761209513050884,-1999640468210794855,370581765718988194>()) {
            case -1405736656:
               float var10000 = this.a.ab();
               switch ((int)com.yiyiaddon.m.b.a<"s1r92h6b09uiti","VZrRNMsHuD0QDNiQ2o7+OoMsLOUVIzwxOpJ7IElIjqE=",687376559330976726,9131905512440265771,-7198379805756729288,-4968403092538449739>()) {
                  case -1922326845:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var1 = Minecraft.getInstance().getWindow().getGuiScaledWidth();
         switch ((int)com.yiyiaddon.m.b.a<"s1pl3b2ifbftgr","hMCzCK2lLYAqB180NilEJbw9qjD+/V2my3ANHjQkBx8=",-4916266574582468112,6084020531608810172,2180817443204157661,2899831572240533913>()) {
            case -1252348432:
               return var1;
            default:
               throw null;
         }
      }
   }

   public float ac() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sl58p7g5r71op","D8o9XkfXSOfoO9RcjjQBkJzQpNOoDoGFf/vYnNJUGSU=",4102039879378150127,3570098705911814555,-8640989135469440495,2386601667234802573>()) {
            case -2091494213:
               float var10000 = this.a.ac();
               switch ((int)com.yiyiaddon.m.b.a<"sdln9ul8442wm","357LVZYwF63L6lUe+cUx6swHB6PJWfAsZsr8vXytJPQ=",-4846970333957720195,28027766669291900,8558003603156547553,-994257856486475386>()) {
                  case 462280273:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var1 = Minecraft.getInstance().getWindow().getGuiScaledHeight();
         switch ((int)com.yiyiaddon.m.b.a<"s3668h2eyrkg4e","yFnxPm3lKb1842vaM2LGCdxTgkzvThQ1UVvAh6lm710=",2086699409208120376,7231732798871479086,7436153947462307816,-8523742536238408545>()) {
            case -2112975324:
               return var1;
            default:
               throw null;
         }
      }
   }

   public i a(double var1, double var3, double var5) {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ryy8apzip08o","IzNm+sWlbkd7fVXHUR+reEY5TfHTupXPZTFVOUFwIDU=",2735634548250091181,-4577248764168848067,4363414118328010314,-4002552074360050351>()) {
            case 1093137169:
               i var10000 = i.a();
               switch ((int)com.yiyiaddon.m.b.a<"sxmy4o85m1oh1","LsLR/THor88r64w+6PdNIB275NoINf5nNyPVfwTFZP4=",8252799444465063515,-3206899000758689554,3478746391837409989,8175711816019032082>()) {
                  case -319093526:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         i var7 = this.a.a(var1, var3, var5);
         switch ((int)com.yiyiaddon.m.b.a<"s3je4futgukg2a","Q5GphqSx6F6rNpU2M7v7Op/llXXr8RZ7gsC5D+oU8Hw=",3198670113778203475,-9027656612842653164,-8684719310084029444,4561913704430499238>()) {
            case 263366018:
               return var7;
            default:
               throw null;
         }
      }
   }

   public i a(Vec3 var1) {
      return this.a(var1.x, var1.y, (double)var1.z);
   }

   public boolean c(Vec3 var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1h9o1pysa5b1e","rBjeFzMwE1IJ2q3n2sFYi7XF4F1Hw+dpV08uLOAtZJI=",7887501669068189335,8349127134381044958,5399277189543840787,1574124629089201326>()) {
            case -1367603566:
               return false;
            default:
               throw null;
         }
      } else if (this.a(var1) <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s14bje98m7mlhr","otto5sRcyrxtHp2F7U/OuCAnw+NZtJb8Ww2+Dpuxh7Y=",-2118760336739220562,5746087569654802802,-6785815323553021338,-3849133488035211747>()) {
            case 474105498:
               return false;
            default:
               throw null;
         }
      } else if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28acguto3mbp7","X7QU7Z9yzgMl781n1f0YDL9avpQE7mgWjV6SCAwVTo4=",-6486892061291796278,-833699926458255516,-278987689956713621,2529244967057810891>()) {
            case 1896224400:
               return true;
            default:
               throw null;
         }
      } else {
         i var2 = this.a.a(var1.x, var1.y, var1.z);
         if (!this.a(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3okbv1th7hys3","qAH2yapi2Q7XT1zvmql5AY2ixl6sDE3fJKbH4jABDrg=",-6648207361621314124,6562691962620239074,5844976151071844404,1640981948863444184>()) {
               case -482385622:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (this.gs()) {
               label42:
               switch ((int)com.yiyiaddon.m.b.a<"sxcihhy79qrne","k1nODT4Q+NKH032lqYn7dFxEm5/EkfdE78R/4o4nAj0=",-796650831947503236,-7630116465709594439,-982185978099920340,-3406703554529717137>()) {
                  case -846356723:
                     if (k.a(this.a.a(), var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3gx9jbebzz07","cl82x0s2/NTJIO6b7plLKvqHvoij/fI6KExvkNUrCb8=",352213522479049494,3775374243609971678,6525037185179249626,6683660397065711441>()) {
                           case -966667937:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1nz8knvemg93q","K4kJtTgY/6qNqGcTXFXdraAbG6MWPFrg1kGvC8HZrII=",-5760776509344821190,-7470984955047524175,2320090293848717547,8236140771623087308>()) {
                        case -1960365329:
                           break label42;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"sudxnky1ehizf","uE0fuDspqTyd/M6nkwHt7hGUCKTO06F3QXzFXuF7QoY=",-5496255178392281723,-5281508921976235655,-6513559520315948743,8485231208217027844>()) {
               case 830515412:
                  return true;
               default:
                  throw null;
            }
         }
      }
   }

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, int var13, float var14) {
      float var15 = u(this.t(var14));
      if (var15 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fv7e2556xos4","prAGYAYzrT10J6ZNOnHhJVM/pyqNMgI/Y9RBwH16DFs=",2358670820401030808,8972987624141225187,-356056942179104389,1543135207365801133>()) {
            case -1303829573:
               return;
            default:
               throw null;
         }
      } else {
         this.kF = this.a((var1 + var7) * 0.5, (var3 + var9) * 0.5, (double)((var5 + var11) * 0.5));
         if (!(this.kF <= 0.0F)) {
            switch ((int)com.yiyiaddon.m.b.a<"s1hjayhavc3jmw","SAeswzEg7Qdcfc/zuZi/h06bmPjLcfdpiineLLSSnhg=",9186607899152850096,-7475140982779775856,1803633796759869395,7721237258003227895>()) {
               case -2137648520:
                  if (this.gt()) {
                     this.a(Gizmos.line(new Vec3(var1, var3, var5), new Vec3(var7, var9, var11), this.s(var13), var15));
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s14oiii9y23og6","dIpykvnCwlizGSZehmQJ4I91TSVz7FgyaQWer1gYOZY=",7348435803463756055,7998683964177905114,-1912069738158861252,-5647498453143427692>()) {
                        case -1086571871:
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

   public void a(Vec3 var1, Vec3 var2, int var3, float var4) {
      this.a(var1.x, var1.y, var1.z, var2.x, var2.y, var2.z, var3, var4);
   }

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, float var15) {
      float var16 = u(this.t(var15));
      if (var16 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bdzatiwmh58s","aPOYQueXPUEjXvQ6q/78qc+1VNhSQknIZKcyjnW0Lbk=",5119628550411877909,7967980435057607144,6034590081007237,2114964746123232886>()) {
            case -1421267624:
               return;
            default:
               throw null;
         }
      } else {
         this.kF = this.a((var1 + var7) * 0.5, (var3 + var9) * 0.5, (double)((var5 + var11) * 0.5));
         if (!(this.kF <= 0.0F)) {
            switch ((int)com.yiyiaddon.m.b.a<"s21ty8l4knv9a1","5bJUTDP7nNywscDai6daPH/Th7U5YI6TwOrmk+6QQvs=",1153890789637989945,2572634262398010898,-3892109617695471302,2057535901522947935>()) {
               case 1172947332:
                  if (this.gt()) {
                     int var17 = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s25kjinadcbza5","kiD0kZ8jKoqbZ+E3ca/u7QTg3tO//jqmEJo7IWyf4B8=",2483914799088680624,-149252071327519394,-4793220750113075993,3719622889434933311>()) {
                        case -606316832:
                           while (var17 < 10) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1fnc0irgntd8d","cgKQLHX3ipFjXJUbml50AnbN1RvBtwtP3tkS8J7U1VY=",-2510478375667404694,5283768246286846104,-7012547598437230711,5100448519878923734>()) {
                                 case -994524871:
                                    float var18 = var17 / 10.0F;
                                    float var19 = (var17 + 1) / 10.0F;
                                    this.a(
                                       Gizmos.line(
                                          new Vec3(a(var1, var7, var18), a(var3, var9, var18), a(var5, var11, var18)),
                                          new Vec3(a(var1, var7, var19), a(var3, var9, var19), a(var5, var11, var19)),
                                          this.s(b(var13, var14, (var18 + var19) * 0.5F)),
                                          var16
                                       )
                                    );
                                    var17++;
                                    switch ((int)com.yiyiaddon.m.b.a<"s37hwhlj9g778k","BPYBD4251lIjlMdZI7E7a88RQNWgQ7dK2UdaAxarKPo=",7969854012840322300,3776816822665699788,-2132488511502068468,-4697739587804577221>()) {
                                       case -1822634668:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s36h4m43a35iz6","pLFMdnzO/FLCgdX5AZjbFqNhFA8izhavAX7lL+wMzn4=",7617382931531579808,-2442709688727412891,6492853584952047802,6339857854044822655>()) {
                        case -917349697:
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

   public void a(Vec3 var1, Vec3 var2, int var3, int var4, float var5) {
      this.a(var1.x, var1.y, var1.z, var2.x, var2.y, var2.z, var3, var4, var5);
   }

   public void a(AABB var1, int var2, int var3, j var4, float var5) {
      this.a(var1, var2, var3, var4, var5, 0);
   }

   public void a(AABB var1, int var2, int var3, j var4, float var5, int var6) {
      var4 = this.b(var4);
      float var7 = u(this.t(var5));
      Vec3 var8 = var1.getCenter();
      this.kF = this.a(var8);
      if (!(this.kF <= 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2u15yad1m1k5x","24jWDjNrol8C2nlhPzdku9jMjHAUqW89qrSqUtbr+KA=",6219379437000734879,-796524215382960443,-7684028525838443657,-4386283342840609709>()) {
            case -2822735:
               if (this.gt()) {
                  boolean var10000;
                  label82: {
                     if (var4.cD()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3ovecgtm7t1gg","cO2Wu4CqBzm1wx26h/VZDA91l/RGViIBk0GnjGDbgYw=",6133491361734375764,-961977045297381470,-5275878270972710054,-4956253008295598166>()) {
                           case 642793496:
                              if (var7 > 0.0F) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sktllnfumvzkx","JA9zAQMhHOd1OB9py8+sX5bGa6+KXHyo9EIQmBzsT1w=",-5772223244500084099,8523490425939329584,-8642619910111681034,2221803144157929307>()) {
                                    case 1143091085:
                                       var10000 = true;
                                       switch ((int)com.yiyiaddon.m.b.a<"sbr43338mj0pu","xvY5FPokx9tMQ++uRFOYg5GI57qzP2VjAwRBbrDf76g=",-8612944149451020217,4218695399822850376,-1016888868705616978,-1914790031577087444>()) {
                                          case -2011211166:
                                             break label82;
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

                     var10000 = false;
                     switch ((int)com.yiyiaddon.m.b.a<"sz5bjhhkiz9i6","bTLSqrtiG6PD8c1+xGdcoVNEx1qcRCAdIKF4xei284k=",-2843147652372799732,-237020089551505455,-4505191367749400540,272452489755959954>()) {
                        case -916312167:
                           break;
                        default:
                           throw null;
                     }
                  }

                  boolean var9 = var10000;
                  boolean var10 = var4.cE();
                  if (!var9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s9fzh2ok5jmui","RlZhr2nyR1I7XV5vRxiyzYAnY9rAW5CjonJxMKmJIzM=",-222929958609028035,3431357019269121494,4751385764903506671,1086194488009877768>()) {
                        case -1257713972:
                           if (!var10) {
                              switch ((int)com.yiyiaddon.m.b.a<"s18do411lio635","Jj1/ZWlmmYgOY78chyUM4CvEUkQ7eh5oXZv5FOq1AF8=",-184310994854966583,-1822596175492944446,5681093063332581129,3609369254945096511>()) {
                                 case -1975299048:
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

                  GizmoStyle var11;
                  label75: {
                     if (var9) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2spgyne46qnew","NflrUCwAY2mVNUfxwWzMphk0ZaIgmOqRha3pAJfkTDA=",-9039529640753486618,-4806647443851559865,3700644118197640044,-2163343090574026630>()) {
                           case 1028811172:
                              if (var10) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1o9e3kwebsr45","cPJsRjJCylmdSTzojfD/8eqrabp6ENkClzYY8qV+5Rg=",1344578420276194960,-8578134200018215330,4964635659128756056,2718672390240594896>()) {
                                    case 1250929424:
                                       var11 = GizmoStyle.strokeAndFill(this.s(var3), var7, this.s(var2));
                                       switch ((int)com.yiyiaddon.m.b.a<"sdr2ugy81j0e8","zUx82pijBCt3naDuj2oDnX0xBX1dA8rb2ujhT3meM7k=",4843832204508925061,2332468141863123603,4976131984372697509,-611903141867424691>()) {
                                          case -492522245:
                                             break label75;
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

                     if (var9) {
                        label48:
                        switch ((int)com.yiyiaddon.m.b.a<"s1vaipp5ts161l","G1rtEzZ1a001F9lzDqACYAScmuCK7DoyByMTGxAmWzY=",-8882245002317953314,-189194051649765396,-984431271158248596,5719909555809628107>()) {
                           case 2137910343:
                              var11 = GizmoStyle.stroke(this.s(var3), var7);
                              switch ((int)com.yiyiaddon.m.b.a<"s1w4gqbd5n5157","XrnzXJa/XZVwc91hxriPUGXNu3VHUtHiBgkVjfNHJsA=",-4479486742175982706,-8110223642171160155,-5237349568964968334,-1288643517850339020>()) {
                                 case -542749055:
                                    break label48;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var11 = GizmoStyle.fill(this.s(var2));
                        switch ((int)com.yiyiaddon.m.b.a<"swrr84hfwxud3","9w/wcYIpCJWrQb3pyYmbORFMwKi2nJRibOdIocZGx9E=",-5955221807172146057,-6137554974616298321,-9057331958966826496,5524165481500064739>()) {
                           case -2124011341:
                              break;
                           default:
                              throw null;
                        }
                     }
                  }

                  this.a(Gizmos.cuboid(var1, var11));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"shbs7wkzweqlb","oguuSa2DM5EB77CHb6c91v4GXeBHUOO5lGnpmEPHiLI=",3199011180359763499,1860338135420170449,7183584411184308925,8669919450302289925>()) {
                     case 1666285025:
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

   public void b(AABB var1, int var2, int var3, j var4, float var5) {
      var4 = this.b(var4);
      float var6 = this.t(var5);
      Vec3 var7 = var1.getCenter();
      this.kF = this.a(var7);
      if (!(this.kF <= 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s13a1nxaksqu57","dCbAAqj50n2vIAGKdPVu9KP/RAlqNwvB96119q1iQOI=",8843373490465384745,-1028201184304167367,8976894401029079127,6256609645736549934>()) {
            case 1680061496:
               if (this.gt()) {
                  if (this.dw == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3or8ljqdn0ycl","uEivYK+7tjx4aWAKCn5JcMkj26m6gxOuysygpD5wrFA=",1832171529331940064,-6945353079017815178,5945027313284437545,5452234010265496042>()) {
                        case 1261497296:
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.dw.add(new f.b(var1, var2, var3, var4, var6, this.kF));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"skbelug7fiw8l","OIa3rQEY+894K9r9TMgI0eOizjEJ7XrPR4muFbJoop8=",5765899252773146236,6986175620883960652,1799249637112528820,4063910272402156831>()) {
                     case -6599942:
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

   public void a(int var1, int var2, int var3, int var4, int var5, j var6, float var7) {
      this.a(var1, var2, var3, var4, var5, var6, var7, 0);
   }

   public void a(int var1, int var2, int var3, int var4, int var5, j var6, float var7, int var8) {
      this.a(new AABB(var1, var2, var3, var1 + 1, var2 + 1, var3 + 1), var4, var5, var6, var7, var8);
   }

   public void a(AABB var1, int var2, int var3) {
      this.gl = true;

      try {
         this.a(var1, var2, 0, j.Sides, 0.0F, var3);
      } finally {
         this.gl = false;
      }
   }

   public void a(int var1, int var2, int var3, int var4, int var5) {
      this.a(new AABB(var1, var2, var3, var1 + 1, var2 + 1, var3 + 1), var4, var5);
   }

   public void a(AABB var1, com.yiyiaddon.l.g.a.d var2, com.yiyiaddon.l.g.a.d var3, j var4, float var5) {
      this.a(var1, var2, var3, var4, var5, 0);
   }

   public void a(AABB var1, com.yiyiaddon.l.g.a.d var2, com.yiyiaddon.l.g.a.d var3, j var4, float var5, int var6) {
      this.a(var1, a(var2, 0), a(var3, -1), var4, var5, var6);
   }

   public void b(AABB var1, com.yiyiaddon.l.g.a.d var2, com.yiyiaddon.l.g.a.d var3, j var4, float var5) {
      this.b(var1, a(var2, 0), a(var3, -1), var4, var5);
   }

   public void a(int var1, int var2, int var3, com.yiyiaddon.l.g.a.d var4, com.yiyiaddon.l.g.a.d var5, j var6, float var7) {
      this.a(var1, var2, var3, var4, var5, var6, var7, 0);
   }

   public void a(int var1, int var2, int var3, com.yiyiaddon.l.g.a.d var4, com.yiyiaddon.l.g.a.d var5, j var6, float var7, int var8) {
      this.a(new AABB(var1, var2, var3, var1 + 1, var2 + 1, var3 + 1), var4, var5, var6, var7, var8);
   }

   public void a(AABB var1, com.yiyiaddon.l.g.a.d var2, int var3) {
      this.a(var1, a(var2, 0), var3);
   }

   public void a(int var1, int var2, int var3, com.yiyiaddon.l.g.a.d var4, int var5) {
      this.a(var1, var2, var3, a(var4, 0), var5);
   }

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, com.yiyiaddon.l.g.a.d var13, float var14) {
      this.a(var1, var3, var5, var7, var9, var11, a(var13, -1), var14);
   }

   public void a(Vec3 var1, Vec3 var2, com.yiyiaddon.l.g.a.d var3, float var4) {
      this.a(var1.x, var1.y, var1.z, var2.x, var2.y, var2.z, var3, var4);
   }

   public void a(Vec3 var1, com.yiyiaddon.l.g.a.d var2, float var3) {
      this.a(var1.x, var1.y, var1.z, a(var2, -1), var3);
   }

   public void a(float var1, float var2, double var3, double var5, double var7, com.yiyiaddon.l.g.a.d var9, float var10) {
      this.a(var1, var2, var3, var5, var7, a(var9, -1), var10);
   }

   public boolean a(String var1, double var2, double var4, double var6, float var8, com.yiyiaddon.l.g.a.d var9) {
      return this.a(var1, var2, var4, var6, var8, var9, 1.0F, false);
   }

   public boolean a(String var1, double var2, double var4, double var6, float var8, com.yiyiaddon.l.g.a.d var9, float var10, boolean var11) {
      if (var9 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ndmulp961drt","t9le5q389y444tTIUr2w75EKxIPgA4/LT4TbNqnZ3OE=",3550359225439287420,-5730043510455882457,2802033929605676580,6343518087607361434>()) {
            case -1254481356:
               return false;
            default:
               throw null;
         }
      } else {
         return this.a(var1, var2, var4, var6, var8, var9.ek(), var10, var11);
      }
   }

   private static int a(com.yiyiaddon.l.g.a.d var0, int var1) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2z9e7ajfbdam6","mXNugMVY1OrwjlUj6bOsOQXe78CEorTCkemqvytTsFA=",-5721748199947602762,6593647298862141964,-3008512076728800626,7767408948822573565>()) {
            case 134889252:
               switch ((int)com.yiyiaddon.m.b.a<"s39efjhy7my9yc","5kqrhPdoNTt0lDKtRhbhuPGtofmkha0ei52wi5qeUSw=",8906116480719990326,-8064682782683824566,-3162863923665625874,-9013650397253567638>()) {
                  case -850351718:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = var0.ej();
         switch ((int)com.yiyiaddon.m.b.a<"si044l94ite83","N7o1TmWlzXheX3ExXJ7gvfdMmhJaKnJnGJxIEz8wVBo=",1232457630189469081,-7472556247905009551,-1048437221396403710,5902524706465583212>()) {
            case -235749653:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public void a(
      double var1,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      double var19,
      double var21,
      double var23,
      int var25
   ) {
      this.kF = this.a((var1 + var7 + var13 + var19) * 0.25, (var3 + var9 + var15 + var21) * 0.25, (double)((var5 + var11 + var17 + var23) * 0.25));
      if (!(this.kF <= 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"ssfxdtptu0blg","tqfX1hG8IXGtyb51BUPfL2iol+wClkMTRhMaWHVwi4c=",3744918808736723401,-4874232955689870827,6888156829516829789,2950471857259793528>()) {
            case 368378398:
               if (this.gt()) {
                  this.a(
                     Gizmos.rect(
                        new Vec3(var1, var3, var5),
                        new Vec3(var7, var9, var11),
                        new Vec3(var13, var15, var17),
                        new Vec3(var19, var21, var23),
                        GizmoStyle.fill(this.s(var25))
                     )
                  );
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2cccwk5thyhyz","QQ5KXbxLF0tcmQpbqaaK8pQJTMqgGZthJ4SJF7G5KqU=",-681556089276614075,-4913567738839878445,-4807835860292362100,-3335952840332475305>()) {
                     case 423294641:
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

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14, j var15, float var16) {
      this.a(var1, var3, var5, var1, var9, var5, var7, var9, var11, var7, var3, var11, var13, var14, var15, var16);
   }

   public void a(double var1, double var3, double var5, double var7, double var9, int var11, int var12, j var13, float var14) {
      this.a(var1, var3, var5, var1, var3, var9, var7, var3, var9, var7, var3, var5, var11, var12, var13, var14);
   }

   public void a(
      double var1,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      double var17,
      double var19,
      double var21,
      double var23,
      int var25,
      int var26,
      j var27,
      float var28
   ) {
      var27 = this.b(var27);
      float var29 = u(this.t(var28));
      this.kF = this.a((var1 + var7 + var13 + var19) * 0.25, (var3 + var9 + var15 + var21) * 0.25, (double)((var5 + var11 + var17 + var23) * 0.25));
      if (!(this.kF <= 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"secdidclb3627","YJQ1nvGhY5Zv5qaU+CU/pq4IFtJD7MgV+3lgj7SUBnk=",-5530282539402482686,-7118350987120315683,7220649589919039647,6263068017592228450>()) {
            case 1803519788:
               if (this.gt()) {
                  boolean var10000;
                  label82: {
                     if (var27.cD()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3pvoim39vf4kd","pvcBYN86QnS7dYkhKMw7rndFvAAl/3EKhqGNRdHxkQE=",-6227281323914253536,-5917882572828953638,-8895576553819803968,-5788119904833361967>()) {
                           case -1978796176:
                              if (var29 > 0.0F) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ycuqkls65oyg","aoh9PF5ePcnpEvDMLwWGHundpiakvWsaDNlv4guR54M=",-2156124178279155443,946751038353368238,6890520736197867290,720728708657883811>()) {
                                    case -1170792080:
                                       var10000 = true;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1rnfrpyvxe9tz","IRq9JSts4CC3EKlCgOQoCA79OT3sWsp2hSYGkZxn8qI=",3618469855751610274,8087058306627624147,5114963736274557897,3176221295337278172>()) {
                                          case -1987013699:
                                             break label82;
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

                     var10000 = false;
                     switch ((int)com.yiyiaddon.m.b.a<"s164yj9vpxlrrm","YgBwJT/ni+8dlVq4t9ry1nbEA09tyyxxn5/wuCNJ/qc=",-180918369818300030,225280061255477504,-6242884618422789424,3458621760238409624>()) {
                        case 685151318:
                           break;
                        default:
                           throw null;
                     }
                  }

                  boolean var30 = var10000;
                  boolean var31 = var27.cE();
                  if (!var30) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1kq79vqd5u5x5","Vy2bSOQVXP88Bew1JpT8Vl/Jur52vaYveVeSjjVdIDA=",-280843526168575525,-3647861071031633582,5019810467866679930,9137632523232412476>()) {
                        case 1242157109:
                           if (!var31) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2gumql8skz7jb","PG9n7ph8MYkgne9hKkcdcTcOz8p+rUtdbDHWWP+ox7o=",-2385008233098208257,2664562726682697603,-502122592938995042,-1851969889312064701>()) {
                                 case -1002207565:
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

                  GizmoStyle var32;
                  label75: {
                     if (var30) {
                        switch ((int)com.yiyiaddon.m.b.a<"sck79emil7757","Gaxbln6szboO33dxX79oxopT5xvWBD87yxg6ERpNiwE=",-269183307805501550,-1054362397075197571,-3867979850332656330,7901105187733173706>()) {
                           case -1733281019:
                              if (var31) {
                                 switch ((int)com.yiyiaddon.m.b.a<"szj5d5p5tnywe","1xYa982vYaXgWTmoi8Ss6ueQC+ZR3bibArA2bu0VoV0=",-3500793344440259010,-2266412704958008118,4348402131242700339,3701916784736763633>()) {
                                    case 105449480:
                                       var32 = GizmoStyle.strokeAndFill(this.s(var26), var29, this.s(var25));
                                       switch ((int)com.yiyiaddon.m.b.a<"s2t2lw8yejcs4h","rOQWDVoL5frk4Hh2tUjX0Cdpd4/2AvXX9EPqbVh7XMo=",-6701796676102498325,8018202483440624559,7836893156052418454,8344334889892730692>()) {
                                          case -1020298873:
                                             break label75;
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

                     if (var30) {
                        label48:
                        switch ((int)com.yiyiaddon.m.b.a<"s1tyb1bytbrx6j","u5uzmhF6a/eh1LGLkq+HoPptD1G6v63t3qzSc+dBGJo=",5537285645606476573,-2067129155686873463,3390059660393777598,-7296137177953280240>()) {
                           case -2066931348:
                              var32 = GizmoStyle.stroke(this.s(var26), var29);
                              switch ((int)com.yiyiaddon.m.b.a<"s3f42x8h0mahpa","tK+oOvkCOxL48BgeZDcHun9U6ZRGdGvdnugPPucstWU=",-6344082921273680945,6736095243360023609,-8318253649529192651,7015038252858199413>()) {
                                 case -1343698854:
                                    break label48;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var32 = GizmoStyle.fill(this.s(var25));
                        switch ((int)com.yiyiaddon.m.b.a<"s887153n4ug9z","j3wgSlCKhGC/pluPizxdRjy/T9h8AdgLjAjZ8r3Ic6w=",3412766612218169602,-3005514162552523632,916582767224943527,6994630190710616948>()) {
                           case -1608469668:
                              break;
                           default:
                              throw null;
                        }
                     }
                  }

                  this.a(
                     Gizmos.rect(new Vec3(var1, var3, var5), new Vec3(var7, var9, var11), new Vec3(var13, var15, var17), new Vec3(var19, var21, var23), var32)
                  );
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1lfarcxk43iyq","rfDFOWCwY/UIo6IG/BIvmUSXRWf5s9WyPrmQkGSi4po=",-4811496315040006339,2132028434831826619,-6147132940254785884,-481449621758684174>()) {
                     case -237178101:
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

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, int var13) {
      this.gl = true;

      try {
         this.a(var1, var3, var5, var7, var9, var11, var13, 0, j.Sides, 0.0F);
      } finally {
         this.gl = false;
      }
   }

   public void a(double var1, double var3, double var5, double var7, double var9, int var11) {
      this.gl = true;

      try {
         this.a(var1, var3, var5, var7, var9, var11, 0, j.Sides, 0.0F);
      } finally {
         this.gl = false;
      }
   }

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, int var13, int var14) {
      this.kF = this.a((var1 + var7) * 0.5, (var3 + var9) * 0.5, (double)((var5 + var11) * 0.5));
      if (!(this.kF <= 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ocr9ba28udmj","9Hl62wVqjsUcV6eRYFxxvBBt4ih5xSEWtarSizIFrSI=",-1201298137503086809,-7938735482364553674,3356431412653677808,2684334127233010163>()) {
            case -1541332332:
               if (this.gt()) {
                  int var15 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s1cigo1623l5hn","bp4PCqBopG8IRoqWTPWjcy849Ls3oo4yyZRK/JwZezA=",2763006844999736705,8560798734938876330,1264237208817240302,-346790719015648681>()) {
                     case 1171052930:
                        while (var15 < 10) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1kh5re83uhide","2Mb4Emob22/Uuv5ij+JfnOCua6L5r/T/qIyOnFsOu9k=",3494574671196923387,-6212693895043484783,-4342896645614153710,3088435245705898456>()) {
                              case 1730290544:
                                 float var16 = var15 / 10.0F;
                                 float var17 = (var15 + 1) / 10.0F;
                                 double var18 = a(var9, var3, var16);
                                 double var20 = a(var9, var3, var17);
                                 int var22 = this.s(b(var13, var14, (var16 + var17) * 0.5F));
                                 this.a(
                                    Gizmos.rect(
                                       new Vec3(var1, var18, var5),
                                       new Vec3(var7, var18, var11),
                                       new Vec3(var7, var20, var11),
                                       new Vec3(var1, var20, var5),
                                       GizmoStyle.fill(var22)
                                    )
                                 );
                                 var15++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ydpew9v0yule","SD2KcRI9T2jv+bx2scMl6KKmFz172fZNwBb8azy4C8I=",935989838575403294,6210910835437607811,3545871742091109860,5177742879757120404>()) {
                                    case 1676141969:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1tip8tiyuxvbx","NtDxSuEs/bIrwtDuRECfEnB/CNtT1zZtboDJ5xZZ9Xo=",-4010097017108676467,8870307433579616333,-2713479216836532939,1960045943343532109>()) {
                     case 654946720:
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

   public void a(double var1, double var3, double var5, int var7, float var8) {
      this.a(Float.NaN, Float.NaN, var1, var3, var5, var7, var8);
   }

   public void a(Vec3 var1, int var2, float var3) {
      this.a(var1.x, var1.y, var1.z, var2, var3);
   }

   public void a(float var1, float var2, double var3, double var5, double var7, int var9, float var10) {
      float var11 = this.t(var10);
      if (var11 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j02535yfqdhr","4iG77UjPQ4VKe5LAsFx1NOhyDcltNPHgtEcy/EYpaMc=",5076723563855239058,109150729876299442,7371940268451810690,-2200814800974501768>()) {
            case 1673802172:
               return;
            default:
               throw null;
         }
      } else {
         this.kF = this.a(var3, var5, (double)var7);
         if (!(this.kF <= 0.0F)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3asd214yeyw46","y2g8zHA7Xwe6hTk+yH6CYg0sNTzLvvw+772oz3C9dLE=",-6407602028807544844,-2672035148990103248,-8569764860428061647,-126393066574656080>()) {
               case -1909820076:
                  if (this.gt()) {
                     if (this.dw == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2g3nvmmo17rjc","ymhB6KFow3Jimd/RWtTcXu8PL0J5nCUYe7RFFjF9GQ4=",8301937744256877432,7759896863661456673,6819120492857946824,7021745328736727937>()) {
                           case -176321637:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.dw.add(new f.d(var1, var2, var3, var5, var7, var9, var11, this.kF));
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sm7759lcpiu1x","sM/YYc+3oTXfjtuwwRYk2EP04EHLu4lSDO1LJhM4NXE=",-2523862766118238750,3922979425150546716,2709406624936806460,-6766101679949284521>()) {
                        case 1008274672:
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

   public boolean a(String var1, double var2, double var4, double var6, float var8, int var9) {
      return this.a(var1, var2, var4, var6, var8, var9, 1.0F, false);
   }

   public boolean a(String var1, double var2, double var4, double var6, float var8, int var9, float var10, boolean var11) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s105q19zti9fqw","syhxOlXGd/lHcUbniV4x9cCTKa8xuE4veaclDrsJ90w=",2050533134523473111,-7025884937528256077,5718753456683731368,-7286326286020994485>()) {
            case 819205037:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s35wqxgrtjzzo","Go2Q7fpl3jeWjjL1eJT2Y0BOT1MGmHJsWy8bryYbvH0=",7077616315569631503,-3153792048852198157,3072283117846015624,-1823078508103669384>()) {
                     case -656525449:
                        if (!(var8 <= 0.0F)) {
                           this.kF = this.a(var2, var4, (double)var6);
                           if (!(this.kF <= 0.0F)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s34a595bd0ymtj","ScczV+IAsmz+X4aFK6tGQTFtVqBqnvKE75n5+HyXZAI=",-5039442172256835155,-9095747185527907654,6777794215040978771,-2141019606976099513>()) {
                                 case -1290938034:
                                    if (this.gt()) {
                                       if (this.dw == null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s20ui3qo5z6jra","Q8j5zUY7AlWoj5RRU3LEqRjahUd1ZAU+vqDhPBF5KMg=",9092809560504135084,1371419584448049,-1234507476274379521,9081797351594350276>()) {
                                             case -1972171928:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.dw.add(new f.c(var1, var2, var4, var6, var8, var9, var10, var11, this.kF));
                                       return true;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s15lny0qxnmrlr","KKP76RMoIOINC9nYyZiA6dRDtTIeIk+nx2d7iRpBsFg=",6350742240050079309,-1599146844613213897,-3128356125727204152,2235190192841631025>()) {
                                       case 2052830010:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s32jypifyvha7c","weQnfmXLtknVFAdenvHUpBfUvvIVfzCP7wm6HVpAbLo=",7394373803681247566,4925093297433283124,141847184681460855,1937026952922304152>()) {
                           case 1845083487:
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

   public void t(List<f.a> var1) {
      if (this.b != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3eb2e0zpqny2k","DzvjmTOOvgPZmaiBXnp1T8kTgppFiYa/6TPHxueIvLU=",335541722096135454,6399193510549419627,-821016830181432484,4083505996987672848>()) {
            case -1961503218:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s10trt2lzkijnk","IewdouxugGg/VVEXixY+juNVpw5iv+YQeK0TnsS1v/c=",-6108925238855668811,-1716618165746847662,-2372996426902659458,1523527018561208832>()) {
                     case 502236572:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sh6j2qzuhoi6y","OVYuLbCRUm4vlBQk6WjKQEcRkoijXapgXaBQmkzI9uw=",-9068597234039106134,2594882181899611537,-5164214751558519982,1635175511583475743>()) {
                              case 142198683:
                                 if (!var1.isEmpty()) {
                                    Iterator var2 = var1.iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2wxe8mtf4bft1","6U7/PTi27+CY6bfxl5b47LN2DTJ/NF8824gJ5jiGYVo=",6473846601990787945,-1985369651586036641,-987043407766566615,-5219477887006755516>()) {
                                       case -1961904178:
                                          while (var2.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2je47gzk56buh","AulXkCxdUUouExLNFhRMybbXG7h372EOj7CpCVNmfoA=",6151975012276082022,370265879605035510,2599441404652684195,-118222999315359851>()) {
                                                case 244102898:
                                                   f.a var3 = (f.a)var2.next();
                                                   if (var3 instanceof f.c) {
                                                      label54:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s4bdchsgxa3s8","R8KgNf7KW5LBTZode2sjR5d7New9/JOFPoW/d1nWZZc=",-3998661094188671487,-3838926806391676933,6369256658186888097,2333609775457702230>()) {
                                                         case -868701374:
                                                            f.c var4 = (f.c)var3;
                                                            this.a(var4);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s33wz5hs05nmqj","mrHcDFyFETV9TUr3xZHxQENba3+iGnC8qDZ/7bkgXXQ=",6156050394211491316,-673960515323479545,-8197250972166693466,-2511421114348577790>()) {
                                                               case -336106180:
                                                                  break label54;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else if (var3 instanceof f.b) {
                                                      label50:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3t67ybphzhpyq","NAh7HzwmFj9TR3pqSGoxS5Q8RGZ5visRIlj+hni50t8=",-3464536160999969413,-8367764425840217003,-6019620316340558314,-8319050708383845164>()) {
                                                         case -2124845373:
                                                            f.b var5 = (f.b)var3;
                                                            this.a(var5);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1c8itak4u8jea","yZKnvZWBeSDU/k8+XysQRJ3FctteaVvcE+bWxt8j+tc=",-6529239353263987890,-448833066505488310,-2910490064737311117,7929998588094506489>()) {
                                                               case -1976224179:
                                                                  break label50;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else if (var3 instanceof f.d) {
                                                      label46:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1ho9zx3tt533n","OvZM9qlvMhoGyyEjFGwuTGTzkXg49Jk1X7Qz1BJ6wUc=",-5058685493145304226,8508727051155491170,-6487349827226705151,2579622303484693051>()) {
                                                         case -337940452:
                                                            f.d var6 = (f.d)var3;
                                                            this.a(var6);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s25pikd9hisgo9","sOg4LMeP/mHoEcitq1P5y+aOd3mdf3TLoQLHVieENNc=",-328413000298003143,-4935240284822892081,-8342666390117439843,-5874372184357099357>()) {
                                                               case 1938667817:
                                                                  break label46;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s1fmsj79s2xug1","7Aghytdo69LmWRcs5YGjeSg742oiiaWpTQBw/qQelSE=",-2651361665795103078,-2946446651430481819,-4226684563707579295,-712817178984885034>()) {
                                                      case -508461591:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2d418hz0bgcfl","Qhbo99+QtjYMIwYrCBBvDUSGvMA/u/uotUj/hSQHsgY=",-3665665960593854898,125788452241689461,2504356041783439588,-4462892323248933809>()) {
                                    case 1671055615:
                                       return;
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
   }

   private void a(f.c var1) {
      this.kF = var1.ae();
      if (this.gs()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ie4p4m1kwx5h","9aJfLSUjAe7wkpxabD7+XIQ+1m39vSMOsPw8LrNbf3g=",83226222776632366,-2930691408932994814,7181261967683607036,3044995693889750151>()) {
            case -171434686:
               if (k.a(this.a.a(), new Vec3(var1.o(), var1.p(), var1.q()))) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3072gfigp86p4","ppTCjw7I6rkkQ/KN6oLDmediXy6Y8DnRY80XDPlWDYA=",-8697971183740340269,-7581980394104766197,2776581019179495519,-4795057406596538775>()) {
                     case 992088320:
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

      float var2 = var1.af() * this.c.aa();
      i var3 = this.a.a(var1.o(), var1.p(), var1.q());
      if (!this.a(var3)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1j1f75p2rh99n","+RvKCcCqrQPDf9cb5EZbc6fQmPSM/wLeCNpfT73qxbM=",1889096043499192824,2299682550744668572,4373079235732236544,9090789180790353051>()) {
            case 1001330084:
               return;
            default:
               throw null;
         }
      } else {
         float var4 = var1.ag() * this.c.Z() * this.kF;
         String var5 = var1.A();
         int var6 = var1.er();
         float var7 = com.yiyiaddon.l.g.d.a(var5, var2, false);
         float var8 = var3.T() - var7 * 0.5F;
         float var9 = com.yiyiaddon.l.b.d.c(var3.U(), var2);
         int var10 = com.yiyiaddon.l.i.c.a().uY;
         if (var1.gv()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3swxutjs5hp4s","BVajsSyvzjMkf38HFsImdEw/uc99jA0yAUGVroYFcE0=",-4205976286622567533,-3742461105805120097,-5269564868546202958,8949638165983003362>()) {
               case -63101675:
                  if (this.c.gl()) {
                     label29:
                     switch ((int)com.yiyiaddon.m.b.a<"s1azec6m7pwq9m","tFHkQwcyhn+cQY5pUXLL9r5tCwqS+T8JMdzdMccwoy8=",2102661946261299096,-6104449769040144877,875048543843387557,-5476857694008955787>()) {
                        case -653853461:
                           float var11 = Math.max(2.0F, var2 * 0.22F);
                           float var12 = Math.max(1.5F, var2 * 0.14F);
                           u.setColor(com.yiyiaddon.l.b.j.a(var10, var4 * 0.65F));
                           this.b
                              .drawRRect(
                                 RRect.makeLTRB(var8 - var11, var3.U() - var2 * 0.5F - var12, var8 + var7 + var11, var3.U() + var2 * 0.5F + var12, var2 * 0.45F),
                                 u
                              );
                           com.yiyiaddon.l.g.d.a(this.b, var5, var8, var9 + 1.0F, var2, var10, var4);
                           switch ((int)com.yiyiaddon.m.b.a<"s1sed2lhtr4jkl","r5Q66DRUeck1SW4ELgg+4u9Tm1odVqyqAS2LJ34705E=",3981588966761929988,-1013174097729152556,-4409507719415650650,2452671728210071357>()) {
                              case -141621923:
                                 break label29;
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

         com.yiyiaddon.l.g.d.a(this.b, var5, var8, var9, var2, var6 & 16777215, var4);
      }
   }

   private void a(f.b var1) {
      this.kF = var1.ae();
      AABB var2 = var1.a();
      if (this.gs()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2tc00ub4hkujw","tlrGnMRcNTalj1LxS71JTULG0NupeCdGpdE702Ib1rA=",8129221899306572095,375470763039825247,-6301303853532876240,-5171194090376356463>()) {
            case -147710424:
               if (k.a(this.a.a(), var2.getCenter())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3en0oir3j9a2c","9jCUK7uSjtglb/8qfrA2nC4x8TyTjTTkjdXCpHqPUC0=",8994814111258232622,-1089714939225529406,6990668258262029089,-2133952985945309110>()) {
                     case -571647502:
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

      this.a(var2);
      float var3 = Float.MAX_VALUE;
      float var4 = Float.MAX_VALUE;
      float var5 = -Float.MAX_VALUE;
      float var6 = -Float.MAX_VALUE;
      int var7 = 0;
      i[] var8 = this.a;
      int var9 = var8.length;
      int var10 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1c6kxj5z35bor","XcEmVDtYb9ZGxMFeOQCIc+pZ8dVponFIrokzYjhO00c=",1979370639010137828,-1750777422102689279,-2879451899326376144,-3885097611485417273>()) {
         case -720727107:
            while (var10 < var9) {
               switch ((int)com.yiyiaddon.m.b.a<"sl0xzqr4h3tuz","6prLOXxJruIMGk2CwbhKJQrzxhxMLwSn0YRfJLBtUJs=",1373155750632766397,9136269353954608296,-536915491169511940,6047780683351396119>()) {
                  case -935190650:
                     i var11 = var8[var10];
                     if (var11 != null) {
                        label86:
                        switch ((int)com.yiyiaddon.m.b.a<"s12xvqeysvjr3","dZZ0iK/1fDe0X6JAqbRv3PiLwUtp9TRngjH8K7vLit8=",-73180344790783429,3967564336106783704,-9097726751594300629,77523139616248905>()) {
                           case -1547157688:
                              if (!var11.gf()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sybeuchn27brb","TvwrDIMGvDFD6Hz9eiaaBNqqm2YRjzo4+2PGuftBIJo=",-439082656449897714,-8549935255110972700,3163653480865649046,-3253707296285358500>()) {
                                    case -1464350923:
                                       switch ((int)com.yiyiaddon.m.b.a<"s37fbn8hoyvcjv","2ETqnO2+jSpbErh2nq0DGeQ1JZ7C2ukgoRWOv/EBWYI=",8928102146855896121,-1191908044647507680,-8381981558265226331,-4513687920310898446>()) {
                                          case 2004860759:
                                             break label86;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var7++;
                                 if (var11.T() < var3) {
                                    label100:
                                    switch ((int)com.yiyiaddon.m.b.a<"s31pcw4vexj0mk","A6Qs5OcWt4178uSeWCg7CRP45qQK1G9v8EwEIJnk2hg=",-8567282151047326842,883663898641817308,-7414272857819116894,-4167508750948581687>()) {
                                       case -495875882:
                                          var3 = var11.T();
                                          switch ((int)com.yiyiaddon.m.b.a<"s2m64e41z28wvj","aqSMpQVW/R/dA1pj2nIaM8Ib9//9jyP2tWiu5mSzMo4=",-2087516661045200999,-6190534168100943708,7338024154269496716,4150200833089385156>()) {
                                             case 1818373379:
                                                break label100;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var11.U() < var4) {
                                    label96:
                                    switch ((int)com.yiyiaddon.m.b.a<"s13v4hrj3k3n9e","WRZ34Y1qWv99hJcegA6VEAoLhvdiPqiIGL7jFpcUTWI=",-2220986733162494648,2433886551341198010,-1112129610217647099,941942520697525092>()) {
                                       case 1474926789:
                                          var4 = var11.U();
                                          switch ((int)com.yiyiaddon.m.b.a<"s11om6h3gzpt7j","pKVbewF31WtXijgXmevzM35YpUqHw2gC2k/duBRQInE=",8559548340622651906,4935099184882852789,-6417005592910229422,6490424665308997751>()) {
                                             case -1960748449:
                                                break label96;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var11.T() > var5) {
                                    label92:
                                    switch ((int)com.yiyiaddon.m.b.a<"s29v0relp4ag68","0O4w24vVB65HPvbwK7BBRiPdBh+QZdPBuz5F17oYWRI=",-3620966361641080237,-8011809363913235541,7077186250614177926,-7876570692556306256>()) {
                                       case 1277953266:
                                          var5 = var11.T();
                                          switch ((int)com.yiyiaddon.m.b.a<"sfxsl8mdanxg1","b9M+TacLcLkf9hP4/yjzO1QAyMdSmgRgaocdPlGlSNM=",-2781122106715611864,-4390624147740255594,-4922783081014701589,-4385274255412689964>()) {
                                             case 1189981674:
                                                break label92;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var11.U() > var6) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s16e7n2eql9zx3","hsygZ9MGY/g+0lbDY2WY0mGyEdponlT096v1lCacF4g=",-7225090381544541003,-5413264256389949114,-4855174506495633051,4366189977926047303>()) {
                                       case -1873402997:
                                          var6 = var11.U();
                                          switch ((int)com.yiyiaddon.m.b.a<"sz4e0lx8gdgfp","vOXYqKsbc0DEvk9i3j9Oq3deY8NNg0n1dogfUM9uQUQ=",-5862709970509201411,-4786521062956779343,-817372307806291879,-4526286670656359538>()) {
                                             case -992726856:
                                                break label86;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              }
                           default:
                              throw null;
                        }
                     }

                     var10++;
                     switch ((int)com.yiyiaddon.m.b.a<"s22yf4gllu5wp4","odOxRCaNwkaACjO9jWJJC+eBwIXgoAm46KgNaTU4usE=",2498554974165537941,6632166151567777613,-8788171026833637273,-8984539208973981223>()) {
                        case -1781965004:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var7 == 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s3lva78jqqzan7","Q/6OADgBPUfKWQ6atZUECG8nJrxkyLyo+Cud+Ij5Wuk=",6533333922168913736,-3571788476698485562,108696563386438008,-6769290225854539117>()) {
                  case 970805410:
                     return;
                  default:
                     throw null;
               }
            } else {
               j var12 = var1.a();
               float var13 = var1.ad();
               Rect var14 = Rect.makeLTRB(var3, var4, var5, var6);
               if (var12.cE()) {
                  label76:
                  switch ((int)com.yiyiaddon.m.b.a<"sheexqxve4r8c","CXHK17eOoWB+w0cfn7Vr8XsV/tt4ho14l0qBckdECDE=",-5520284909687266225,6567513106977175738,3868990894811928775,8350534203371349363>()) {
                     case -1806190541:
                        t.setColor(this.s(var1.ep()));
                        this.b.drawRect(var14, t);
                        switch ((int)com.yiyiaddon.m.b.a<"s2a2f58768zlcz","8HDwvcDURqSPlqWFu02r9HMw3fgtlk2qEr9TJZZXJs4=",876431086223396104,-4119153165004133499,5917095162675510034,-8311869206472184771>()) {
                           case 1418805315:
                              break label76;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var12.cD()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3vj719z55j7x9","SSHcd0vgWycYu1WbJ2S6u9PYgy6oDeKFLIF9Qu1ZLnw=",-6020368897704171855,2883854183570160477,278680831172875830,2331669553290350636>()) {
                     case -1214081230:
                        if (var13 > 0.0F) {
                           switch ((int)com.yiyiaddon.m.b.a<"s27yx35czx7xd1","Tjr/3CvsR9ZuzVRbncmUAx65BGykqo2B+2O0+KQGiw8=",-1097403335186663108,-5057695329719432785,5418747998129936412,-5743477093608201637>()) {
                              case -758283671:
                                 s.setColor(this.s(var1.eq()));
                                 s.setStrokeWidth(var13);
                                 this.b.drawRect(var14, s);
                                 switch ((int)com.yiyiaddon.m.b.a<"svddecddiffsu","WlKVQgvEx8K1HMvu2kwZGPldWRtBfRxUqB+i/2kaVkY=",9017137307053332357,-4592929779891053384,-7510564495880285568,-6772046699221429157>()) {
                                    case 1205086515:
                                       return;
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

               return;
            }
         default:
            throw null;
      }
   }

   private void a(f.d var1) {
      this.kF = var1.ae();
      if (this.gs()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1r8sbej7mh3kx","cOGP7othxSvFqzpBUp1iv8IYX7+hbCfof57EOnVS3To=",-2289826185048062506,1663456161798189660,-2714921822875079496,7878861601402299352>()) {
            case -1393589277:
               if (k.a(this.a.a(), new Vec3(var1.o(), var1.p(), var1.q()))) {
                  switch ((int)com.yiyiaddon.m.b.a<"shwoq0zod1gg9","+MmbYIF3sPlBU4czmHzgoMvsDBpq3m1RVyCSeXdbQDw=",4474306207083877213,-3573473848877683414,6104739924992978863,5430565020897553008>()) {
                     case -438535731:
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

      i var2 = this.a.a(var1.o(), var1.p(), var1.q());
      if (!this.a(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"snjxfcmdql1m4","0C6t6bGDgRbGsiAZfgQAVDyGsAemZoi3pC/myt0V2Fw=",2066895166902982114,-2989191112217417007,6806907092870965896,8070578860466699894>()) {
            case 580586030:
               return;
            default:
               throw null;
         }
      } else {
         float var10000;
         if (Float.isNaN(var1.ah())) {
            label42:
            switch ((int)com.yiyiaddon.m.b.a<"s21xt0exigzjyj","vYlDmvt1FDir6GWdgrfMensM+LNfq6T+2SchoCjjHgA=",7670544363240564434,484211588936427032,-5404205101914082077,-8703858794860176171>()) {
               case 821622131:
                  var10000 = this.ab() * 0.5F;
                  switch ((int)com.yiyiaddon.m.b.a<"s2twyqsxg8lv8f","I/EbW0/0M8CeIAu1wilGS3IFQ4rNkupDWRtu2R3CmYg=",2147663367419374680,9148912380580537223,3292665299163953663,-1304288247872528879>()) {
                     case -1019482106:
                        break label42;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var1.ah();
            switch ((int)com.yiyiaddon.m.b.a<"sn67c0h96xxma","HjaIQ+2JAgiH6E8kNSUH2UX8MQSwyx94QCMRk2ACANk=",143271061783931250,-5374610362495141347,9142798259461474453,1994121833335258183>()) {
               case 423281097:
                  break;
               default:
                  throw null;
            }
         }

         float var3 = var10000;
         if (Float.isNaN(var1.ai())) {
            label35:
            switch ((int)com.yiyiaddon.m.b.a<"s2nkw8kjc2lfb7","YrMCIW2QUYa67SwbwxhbhB8EYTeKkREbGYMaWfzQ+L0=",-3808157111266505646,6811847373702484014,-5464344308988547104,-488370539072663855>()) {
               case 1233236035:
                  var10000 = this.ac();
                  switch ((int)com.yiyiaddon.m.b.a<"so8cmio8ohcxb","iAUk7PHGjCopWmsi+Z/aj0roAGtjFafn5xPUumShBpA=",5972885315393604936,-6893440611820390567,-5568159266033714330,-8679448973607222549>()) {
                     case 1160064288:
                        break label35;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var1.ai();
            switch ((int)com.yiyiaddon.m.b.a<"s1iuk7t7pf4su6","MuFMfFx5yYNRW2pKesHDn1pXcbOC67G9rWUtr1W+r6Q=",-4747892963935311946,-5477731019562838870,-7449945318899905062,4371822570053324294>()) {
               case -813577966:
                  break;
               default:
                  throw null;
            }
         }

         float var4 = var10000;
         s.setColor(this.s(var1.er()));
         s.setStrokeWidth(var1.ad());
         this.b.drawLine(var3, var4, var2.T(), var2.U(), s);
      }
   }

   private void a(AABB var1) {
      this.a[0] = this.b(var1.minX, var1.minY, var1.minZ);
      this.a[1] = this.b(var1.maxX, var1.minY, var1.minZ);
      this.a[2] = this.b(var1.maxX, var1.minY, var1.maxZ);
      this.a[3] = this.b(var1.minX, var1.minY, var1.maxZ);
      this.a[4] = this.b(var1.minX, var1.maxY, var1.minZ);
      this.a[5] = this.b(var1.maxX, var1.maxY, var1.minZ);
      this.a[6] = this.b(var1.maxX, var1.maxY, var1.maxZ);
      this.a[7] = this.b(var1.minX, var1.maxY, var1.maxZ);
   }

   private i b(double var1, double var3, double var5) {
      i var7 = this.a.a(var1, var3, var5);
      if (var7.gf()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lnw57yeyeov3","MDrH6R6QHexPl1NNnc9xzkglHYF7clxLkkP5dK6Vd8w=",-3340727758305281944,-5323970714135928306,-2157805877326329033,5856311933609158312>()) {
            case 757960482:
               switch ((int)com.yiyiaddon.m.b.a<"s2yxxvg8hmriqk","gxqlwtuKwnRdt7GFRofSEUaO5SAf+GjLRGk8ghywA/Y=",4702525897335060193,-212833109729286069,2591941371087477750,115168805781727385>()) {
                  case 1432624906:
                     return var7;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"ski0plyegts0c","/f5uqghQL0RIh/U7kJxJMU8pXRMp2jVTTGfW5nS0LvQ=",-8920281915110286588,-108721202915665972,-3721372468600149091,-2487025915701169601>()) {
            case -488410764:
               return null;
            default:
               throw null;
         }
      }
   }

   private static double a(double var0, double var2, float var4) {
      return var0 + (var2 - var0) * var4;
   }

   private static int b(int var0, int var1, float var2) {
      int var3 = c(var0 >>> 24, var1 >>> 24, var2);
      int var4 = c(var0 >> 16 & 0xFF, var1 >> 16 & 0xFF, var2);
      int var5 = c(var0 >> 8 & 0xFF, var1 >> 8 & 0xFF, var2);
      int var6 = c(var0 & 0xFF, var1 & 0xFF, var2);
      return var3 << 24 | var4 << 16 | var5 << 8 | var6;
   }

   private static int c(int var0, int var1, float var2) {
      int var3 = (int)(var0 + (var1 - var0) * var2);
      if (var3 < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s32o3anppg0nd7","4tXjK9uBEdnl+bk5eejcXnVCX0Y5Qe9PthRG4X8B90M=",1079721912645277818,-1938967118220634243,6391789176797607420,5208286971662721184>()) {
            case -579604606:
               return 0;
            default:
               throw null;
         }
      } else {
         return Math.min(var3, 255);
      }
   }

   private boolean a(i var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s30ay36xm3pjmp","deWleMCzLAqY5XEoAZhfBfVYO3LyRtzCMQOaynWU7cs=",-129029730455036084,-4485837669550750466,7143424330487364174,-7807096359373134961>()) {
            case -1343907470:
               if (var1.b(this.ab(), this.ac(), 64.0F)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s5yo0embb9xc0","eMOR5gQGzqg13QbWlq/Fx08mmu5laE31fDTrlgYnblE=",-6387169793010464325,387025535009116711,-6393295452709381266,432451132801897010>()) {
                     case -694738167:
                        switch ((int)com.yiyiaddon.m.b.a<"s2b2aunv41xpz1","uodWNMbQLG6BhHE9k1ZvQ1JzLiFLmYRJZ0YLwFWSz+k=",-6064521540753422089,7541390374086725168,-3577210491189535716,-4319097091789012321>()) {
                           case -1619852520:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1q1fknk340ve9","NyW8MimQZQabeNefbmpWUF/IgJN7VZ9Dr8EFEj85IfI=",-5261293489006310400,3063088210074939384,-2505508445602858175,-7329569360915608109>()) {
         case -1315546983:
            return false;
         default:
            throw null;
      }
   }

   public sealed interface a permits f.c, f.b, f.d {
   }

   public record b(AABB a, int uz, int uA, j i, float kG, float kH) implements f.a {
      public int ep() {
         return this.uz;
      }

      public int eq() {
         return this.uA;
      }

      public j a() {
         return this.i;
      }

      public float ad() {
         return this.kG;
      }

      public float ae() {
         return this.kH;
      }
   }

   public record c(String Gl, double bJ, double bK, double bL, float kI, int uB, float kJ, boolean gm, float kK) implements f.a {
      public String A() {
         return this.Gl;
      }

      public double o() {
         return this.bJ;
      }

      public double p() {
         return this.bK;
      }

      public double q() {
         return this.bL;
      }

      public float af() {
         return this.kI;
      }

      public int er() {
         return this.uB;
      }

      public float ag() {
         return this.kJ;
      }

      public boolean gv() {
         return this.gm;
      }

      public float ae() {
         return this.kK;
      }
   }

   public record d(float kL, float kM, double bM, double bN, double bO, int uC, float kN, float kO) implements f.a {
      public float ah() {
         return this.kL;
      }

      public float ai() {
         return this.kM;
      }

      public double o() {
         return this.bM;
      }

      public double p() {
         return this.bN;
      }

      public double q() {
         return this.bO;
      }

      public int er() {
         return this.uC;
      }

      public float ad() {
         return this.kN;
      }

      public float ae() {
         return this.kO;
      }
   }
}

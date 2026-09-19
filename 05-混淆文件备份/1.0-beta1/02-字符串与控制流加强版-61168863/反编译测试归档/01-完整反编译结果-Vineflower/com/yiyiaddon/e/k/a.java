package com.yiyiaddon.e.k;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.d.c.f;
import com.yiyiaddon.d.c.i;
import com.yiyiaddon.e.k.b.d;
import com.yiyiaddon.e.o.b.c;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class a extends com.yiyiaddon.d.b.a implements c.a {
   public static final String nW = "packetbreak";
   public static final String nX = "发包秒破";
   private static final int kw = 100;
   private static final double aj = 0.7;
   private static final double ak = 1.0;
   private static final int kx = 10;
   private static final int ky = 2;
   private static final int kz = 20;
   private static final int kA = 1;
   private static final int kB = 100;
   private static final int kC = 64;
   private static final int kD = 8;
   private static final int kE = 10;
   private static final int kF = 20;
   private static final int kG = 36;
   private static final String nY = (String)b.a<"s1d30ndo3d155z","5glr/2Few3TKrq1fWev0p1fH2c3ItM0ZV9Bu5fnGTegJe6x0qv/rmL9bvqzKlZv5hwDVwBGXYQX7Rrq8Jjkm4NZX",6275166295947564000,1223816215485290050,3495289215368114257,4693162829960124381>();
   private final Minecraft R = Minecraft.getInstance();
   private final com.yiyiaddon.e.k.a.a a = new com.yiyiaddon.e.k.a.a();
   private final com.yiyiaddon.e.k.c.a a = new com.yiyiaddon.e.k.c.a(this);
   private final List<d> bl = new ArrayList<>();
   private d a;
   private final Map<BlockPos, Integer> O = new HashMap<>();
   private int kH;
   private int kI;
   private int kJ = -1;
   private com.yiyiaddon.e.k.a.a a;
   private volatile BlockPos x;
   private Runnable e;
   private static final String nZ = (String)b.a<"s12yw73fvfbtu2","3J4nvhCxSal6HOYfnViSs8TEOda5ySsOjo8v0aX3",-225007529332784946,-5557227095581599978,-8834393939012746452,-8597972227224108718>();

   public a() {
      super(
         (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
         (String)b.a<"sr48otb250ac6","eNQX4bqOKYul6jQZM1aMEtfeqpfW+wfCe0/iuXj/2R56KXcw",1735465521227501560,-3617001657321155231,-4737580959637668186,-6754647352704626143>(),
         (String)b.a<"s2ni8izajiw24c","Sdw9hDoGXM3BjODVQnRLwA0nMHMYVkk+UoH50JWPwB/ACW5pyCjNVQ==",8419199010898119053,281370040169267355,801093596205548279,-2722189043567162812>(),
         (String)b.a<"sja7k82z99wgl","75EmokUPDt/xmxObhFl96EWJ6B8I8vLH2/fhmahXhwHgyICTc9sRK+Khd92Eh4S1G1gasNSXjs6Dy1pHZl+CyOFI+Nv80NqAIGpbFdDrKMIYrDfiz4Cemcs08bRJ5npaW0W5mEihA7XtuWze0oS76JliWo2pk4wVUc77phLTBw1Mx8osLenrkG2bWCsu+6XGlqtw0e5qDU0RpA==",-6274703616024997713,3473822034995678204,-7918461605136887733,3969514876697165800>()
      );
   }

   @Override
   public String w() {
      return (String)b.a<"s12yw73fvfbtu2","3J4nvhCxSal6HOYfnViSs8TEOda5ySsOjo8v0aX3",-225007529332784946,-5557227095581599978,-8834393939012746452,-8597972227224108718>();
   }

   @Override
   public int i() {
      return 40;
   }

   public com.yiyiaddon.e.k.a.a a() {
      return this.a;
   }

   public List<d> aw() {
      return this.bl;
   }

   public d a() {
      return this.a;
   }

   public int bH() {
      return this.kH;
   }

   public int bI() {
      return this.O.size();
   }

   public int bJ() {
      return this.a.bm.size();
   }

   public int H() {
      return this.bl.size();
   }

   public double a(d var1) {
      if (var1 == null) {
         switch ((int)b.a<"s3hdv0l0dymk4k","rcWfGrBz3ShgoETuVadflPMJmU7BJ/niH0mAaK4tRzM=",6595717156880992193,-4319142238686743550,-2219479790233897190,7471031430253691991>()) {
            case -322468989:
               return 0.0;
            default:
               throw null;
         }
      } else if (var1.a == d.a.AWAITING_CONFIRM) {
         switch ((int)b.a<"s3kfffjsycg2cz","b04eiElxvoa+5UqqypF5D1w2makFuWE9k+YKJKP+R5o=",-6344092711152410352,-4658893178028090976,-1023015315230994313,-5139628670466078306>()) {
            case -1337445742:
               return 1.0;
            default:
               throw null;
         }
      } else {
         if (var1.a != d.a.MINING) {
            label39:
            switch ((int)b.a<"s1akn9cn6m8sn1","epHzvKP57H8b/m9F+YMwvykttjecANXxQ6n2QgSZGWQ=",-101718552468242323,8281558731615989477,817657280717905750,-7670534239920240254>()) {
               case 40441388:
                  if (var1.a != d.a.STOP_PENDING) {
                     return 0.0;
                  }

                  switch ((int)b.a<"s6afzkqknx2dc","lm2McuXASHRRJru9wyFy/b0WjzjI+ZSr36VudWfwUeQ=",8236126723065462336,-6584346190036773206,-5351218458442935237,-534269364476017>()) {
                     case -1557075735:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var1.gP != Integer.MIN_VALUE) {
            double var2 = this.j();
            if (this.R.player == null) {
               switch ((int)b.a<"sy5lv3wm2ki78","4uJCW88NKNb0yazH5ta2L/wvW7IEH7GhTkPN6T+5+vs=",-4145610518818703939,1830184448569229673,8274394319149921899,-8957307001873889316>()) {
                  case 844373641:
                     return 0.0;
                  default:
                     throw null;
               }
            } else {
               return Math.min(1.0, var1.bE * (this.R.player.tickCount - var1.gP + 1) / var2);
            }
         } else {
            switch ((int)b.a<"s2jtvwfghq06p2","QD9Kc/oeQmj1o1uVi+kfrodBKcdXFdzJfTlxuPDCdWI=",6175289906111740384,-740828731503519189,-8184243089289456241,836213769164376102>()) {
               case 2127202512:
                  return 0.0;
               default:
                  throw null;
            }
         }
      }
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      com.yiyiaddon.d.b.e.d(this);
   }

   @Override
   protected void m() {
      if (this.R.hasSingleplayerServer()) {
         switch ((int)b.a<"swnwg3j14xhbp","N6MdKFehrdi7Wg5zVQIGk2CD/IfvHTmsgSr12pCeLL4=",-4805640488762240587,-8109181579179911687,2411399712048281292,3566738236197389628>()) {
            case 463154506:
               com.yiyiaddon.d.b.e.b(
                  (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
                  false
               );
               this.ad(
                  (String)b.a<"s1y1rdfrp00lco","5dj/rn6ECz0n6gWOvpnzCDLp3KPejPRV0hueBUSfSXe+NCX4k23qZsorciZmFBy7daS3SQ==",4911839300058517466,-8421608532081921207,-1531497456082355009,4782569371130332372>()
               );
               return;
            default:
               throw null;
         }
      } else {
         List var1 = this.ax();
         if (!var1.isEmpty()) {
            switch ((int)b.a<"s2wo54nx8rr5ir","9+sOAjerZqy3CYcKIPjBlQCS1HE1XsQk5bnQ3V7BOa0=",-4847813468681847129,1466856195814492754,3793095232475222419,-290785499903183519>()) {
               case -300617998:
                  com.yiyiaddon.d.b.e.b(
                     (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
                     false
                  );
                  this.o(
                     (String)b.a<"s2wgorbv5v2xsi","B5y2zrbUVhK9Edn8DgCGU1OTW9CP57HMC1jqwLMgWALs1Yb6rgA5CQGO4xTJojMMnvUBnqqpAryWjrbBUT8ivgo27mM7VME2wJM=",8657838363682129536,5278638784415961458,8717202387448680075,2581284589702166684>()
                  );
                  Iterator var2 = var1.iterator();
                  switch ((int)b.a<"s2xyw1e6fajbjx","Map2Y/ac7rBUJjv7OFOh6PLmt1AwrcjArc/jYFgeKYc=",8126539924750310613,7854741847968404235,-4557794272147907152,-3699974897685493655>()) {
                     case -1441169593:
                        while (var2.hasNext()) {
                           switch ((int)b.a<"ssba45blskmhv","z0FCPbsUIz8WVLHAWgLJIm6Vq8gLTFZB94NHRzB4BFs=",7684845901642177587,7718258146576673270,-9151038846186656702,9125411705372611151>()) {
                              case -1276854038:
                                 String var3 = (String)var2.next();
                                 this.u(var3 + "");
                                 switch ((int)b.a<"s15fho2cs8szm0","TcwxHZv4UFS0r534b66cnoGF1kMgmGgVKAKFmAbO3sU=",3039234243559937669,-5691649139232910182,2889357786285971227,3635875890477717114>()) {
                                    case 1443206928:
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
               default:
                  throw null;
            }
         } else {
            if (this.R.gameMode != null) {
               switch ((int)b.a<"s3r1dxbphqsahw","WPupIB57y7Bl/JPUrYsI0RG0jxX+Z43BYbZnYELMdH4=",-1897672401060505683,-5726753416320074637,-3313792878146868252,195006036556461820>()) {
                  case 1946981438:
                     if (this.R.gameMode.isDestroying()) {
                        label42:
                        switch ((int)b.a<"s1s8oay6653277","1M1doaCUfGF1m8wzgCmHqNrkz07udMZQkHws4dgLYLg=",-4726884558907392985,-5454652489781204370,-5614268008391388103,-8132795882678459900>()) {
                           case 1483621776:
                              this.R.gameMode.stopDestroyBlock();
                              switch ((int)b.a<"s1qhkaerpel0im","4DURUOFEKvPO6jN1XFwoLBSvh2hA7c9seQU6AjECmiE=",-1157284689729398307,-2310050317604285509,-3797475136160596552,1521589378895624431>()) {
                                 case 941626310:
                                    break label42;
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

            this.bl.clear();
            this.a = null;
            this.O.clear();
            this.fS();
            this.kH = 0;
            this.kI = 0;
            this.x = null;
            this.e = null;
            f.a(
               (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
               100,
               this::b
            );
            c.a(this);
            com.yiyiaddon.l.g.a.e.a().A(true);
            l.a(
               (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
               this.a::render
            );
            this.ao();
         }
      }
   }

   @Override
   protected void n() {
      this.bl.clear();
      if (this.a != null) {
         switch ((int)b.a<"s1crvjbh2sney1","rGXiwpKZQoMAb6i60RMnrDxfgb0Ftg6D6yzIgxwktv0=",6096201487914310377,-7070781449821379116,-7544517905395044567,768872598463816273>()) {
            case 247983445:
               if (this.a.gP != Integer.MIN_VALUE) {
                  switch ((int)b.a<"s1jlkx4v4l0t09","58LpfM3titT7erIwYzeWk8JLoGWClixoJansDvj5bew=",-749848845523488669,-588561492101620911,6563396320705666592,-5412692755926146688>()) {
                     case -349806564:
                        if (this.R.player != null) {
                           label19:
                           switch ((int)b.a<"s2iycw8537pax4","Mmz0BFiriOUuaCtGpgEUvLoQXXgIGmTtTDfSlECqLeQ=",6109400781596107109,-3670988889425629378,-2474968954071284987,7559749861629927368>()) {
                              case -1463085150:
                                 com.yiyiaddon.d.c.b.b(this.a.z, this.a.c);
                                 switch ((int)b.a<"s3rhiqcl9x8ba","AICUn0e9RyWVVVeFdJHh6g+eSaNVLDR6slw0ZVcDBg8=",-3306427597197082823,-663651363264292535,5721437349841837312,2099470786076925469>()) {
                                    case 213116968:
                                       break label19;
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

      this.fR();
      this.a = null;
      this.O.clear();
      this.fS();
      this.x = null;
      this.e = null;
      f.l(
         (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>()
      );
      c.b(this);
      com.yiyiaddon.l.g.a.e.a().A(false);
      l.l(
         (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>()
      );
   }

   @Override
   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of(com.yiyiaddon.d.a.c.TICK, com.yiyiaddon.d.a.c.SERVER_BLOCK, com.yiyiaddon.d.a.c.JOIN_SERVER, com.yiyiaddon.d.a.c.DISCONNECT);
   }

   @Override
   public void d(com.yiyiaddon.d.a.a var1) {
      if (var1 != null) {
         switch ((int)b.a<"s3dpfzzrdbyfd4","mVJSusCWDvRNFcpsTaVdYwcCKhk/bIzlCl6biJZo4DQ=",5896417590276809323,7704482559490838215,-3022456776101702080,4923545577753245196>()) {
            case -359591709:
               if (this.g()) {
                  switch (var1.a()) {
                     case SERVER_BLOCK:
                        this.a(var1.a());
                        switch ((int)b.a<"s17y9kv2cj0ldm","To2NAVcEuYq56SuwRM4fbabLJOi90PpuI+wA8ovTkRk=",7751899054684130531,4066474496810298381,5182389356681388282,-3740937660069052866>()) {
                           case 1085577643:
                              return;
                           default:
                              throw null;
                        }
                     case JOIN_SERVER:
                        this.aF();
                        switch ((int)b.a<"sj4vmsl4tz4wd","9lsqgmegBvKxcg+dsBxMrzQ6EHxNcWykbnZ/yl4F7Z4=",-5874636241536415259,3920927861815568845,6391933327346902845,-6658807693266653214>()) {
                           case -83795296:
                              return;
                           default:
                              throw null;
                        }
                     case DISCONNECT:
                        this.fP();
                        switch ((int)b.a<"s2cpiy65ozpxrp","yVdeCrKz/KnyJtp5u3ZwRKyZ4O7bX2ci1Pc9IFngUwM=",6731262202245369958,7371326939297440925,676107102588418852,-3786173576029566729>()) {
                           case 249750209:
                              break;
                           default:
                              throw null;
                        }
                  }

                  return;
               } else {
                  switch ((int)b.a<"siq81qltdoifp","I2Di/P0cheyahTXgXbyOeIpOVtDiFrL6iNXPTzCb11Y=",-4970355986347898478,-6080680473456818520,-3108471130923668266,-1813398662524451163>()) {
                     case -1111877500:
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

   private void ao() {
      StringBuilder var1 = new StringBuilder();
      var1.append(
         (String)b.a<"sea0sykyk19bh","9xNjP4bZSW//dJ/cWy80II+ndGMklGDCh92Et6NgYQxFKBP8vWcEQc8AuPe5pLSji41DsFoo6+XHEhz1yx0=",-8768393768047138716,293129891562804236,1957557001275108233,-4134229562591460883>()
      );
      var1.append(
            (String)b.a<"s1q0wthh7fiuqr","PqauS6/ky4TZ6GA55q/PV47d5WH2K40MFUs3jmTR2+cG7J3ErBk5iSLWEEcljnfm3g5WcQ==",-5811237185102873156,8595480928950272613,1615766928479112323,-6935057998967020811>()
         )
         .append(j(this.a.a.pc))
         .append(
            (String)b.a<"s25jefqws4biot","W/7w/4/Z37Y50ZN89heOP5bTYppJkAohgH3DLgu80cM=",-8000375429161018630,1205784317321382818,-6000015361227990875,42584089894504347>()
         );
      var1.append(
            (String)b.a<"si19v5dv6usup","EOKsxr7rO/4ViDq0HnS0DoFlNJfU/flCOYAJrOa06WFf9MaedKO+kKusezcIvEeHBQ0qtA==",-7425944176119952120,899649639827210515,8728680825666912204,2582163017722735140>()
         )
         .append(j(this.a.a.pf))
         .append(
            (String)b.a<"s25jefqws4biot","W/7w/4/Z37Y50ZN89heOP5bTYppJkAohgH3DLgu80cM=",-8000375429161018630,1205784317321382818,-6000015361227990875,42584089894504347>()
         );
      if (this.a.a == com.yiyiaddon.e.k.b.e.RANGE) {
         label27:
         switch ((int)b.a<"s109ld8njzn6su","2YF/Bha6j5y/71Aa1UlYrwWFX1iDHpgknoReVaQIk9Y=",-8799360074697591124,7507228234876567470,-5355790684977489115,-3725014493301143402>()) {
            case 1972153763:
               var1.append(
                     (String)b.a<"sahslzisjmguv","sIPMUfPM7F1EgwOX9GjA4WbWeuKL4ey/pQJAH+oD+VsS8rIgzJNJOZvI6fjk6o+Yxz27Jg==",7347380326806315239,7546317090284625244,-5405730734335803692,4716198333802149737>()
                  )
                  .append(N(this.a.df + ""))
                  .append(
                     (String)b.a<"s25jefqws4biot","W/7w/4/Z37Y50ZN89heOP5bTYppJkAohgH3DLgu80cM=",-8000375429161018630,1205784317321382818,-6000015361227990875,42584089894504347>()
                  );
               switch ((int)b.a<"s3eccefl6ei958","8NoGLlA33XSlzy2syos6wfQu4E1n76Ygse1XcJhbI/I=",-4331809182848246216,-4334721883795177143,6316514923490571197,3195688670815609244>()) {
                  case -432953260:
                     break label27;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.append(
            (String)b.a<"sfzsdyfwlbnkn","yf9+AU0hh6z73lw6v3MB9hhdpVVcvxbEIKQP+5B8ARxRP1jiYZI83yL5qt4CUJhbILLOQw==",7525173157831641501,-3116386557842646600,-7571094208939520090,-5801585015127267008>()
         )
         .append(N(this.a.kW + ""))
         .append(
            (String)b.a<"s25jefqws4biot","W/7w/4/Z37Y50ZN89heOP5bTYppJkAohgH3DLgu80cM=",-8000375429161018630,1205784317321382818,-6000015361227990875,42584089894504347>()
         );
      StringBuilder var10000 = var1.append(
         (String)b.a<"sovexrclblnlr","FyaWZ4TdHkV/iIWo42E1t81KyRx+EmhrDahRwOTGn1YGHwu5Orwzp+GmK3vsHBeXjn3LJg==",5707965417775756003,-3367462424038509258,5538725835853198545,5723479527231727001>()
      );
      String var10001;
      if (this.a.aS) {
         label20:
         switch ((int)b.a<"s3bnuwma37vzbz","y1wS/nBhLlTGmuyEe+ZXKMN8KRDWVxmA/fGsurC60cI=",-7516265108379440005,-2976784864203425822,-4679236768847415085,-3769643107095874190>()) {
            case 727881052:
               var10001 = i(
                  (String)b.a<"spr1kn97pbm8d","C6qyiu4P95pZlfwnQe3THZSqFp9gNX5Drse0RWwd",-8669012358503115499,8046226116483706677,-6105387791329335354,-5344625208932071422>()
               );
               switch ((int)b.a<"s2crmh3psy5mxm","0g1UGfvfNg3VQuyzYh1EAYr0H9NkdgbOwVbqssl7NoU=",2766692413878705731,9169866857747671406,3676482474046837354,-3050745897990350469>()) {
                  case 35734862:
                     break label20;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = N(
            (String)b.a<"s3dpwvkb7tcxly","T6kOvMaBzqo61MbPNCAYWcLVUqq/zMNZ6vkiVh4B",8809477029962316268,-1131618342872940978,8476444646144182367,-4236429117273897291>()
         );
         switch ((int)b.a<"s3p7erdlfr5tam","5usH0DoMz4wj/mxO8eawSqDIpHVcurHK+dQOg8VbxMU=",8388960134426776966,-7808767551652576859,1477265526620045358,-6345005866163774405>()) {
            case -883612898:
               break;
            default:
               throw null;
         }
      }

      var10000.append(var10001)
         .append(
            (String)b.a<"s25jefqws4biot","W/7w/4/Z37Y50ZN89heOP5bTYppJkAohgH3DLgu80cM=",-8000375429161018630,1205784317321382818,-6000015361227990875,42584089894504347>()
         );
      this.u(var1.toString());
   }

   private List<String> ax() {
      ArrayList var1 = new ArrayList();
      com.yiyiaddon.d.b.a var2 = com.yiyiaddon.d.b.e.b(
         (String)b.a<"s1yyekvsxvdlbo","8LZDSGJcKt9P6iruu4O0oOn0UnIJSL5szPAdVYeWVanZKtYnI13hLw==",-1217248549158540809,514390718587423266,8563517648780045834,-959550270190096535>()
      );
      if (var2 instanceof com.yiyiaddon.e.j.a) {
         switch ((int)b.a<"s2f99uflau4c4w","mAo85cW+pUuBMEf+b1wwZB0b3x76qB2PT6PjFfa/qtk=",3491892996162632421,1712758962785517457,6863823395106070254,-8382025179978416266>()) {
            case -636549958:
               com.yiyiaddon.e.j.a var3 = (com.yiyiaddon.e.j.a)var2;
               if (var3.g()) {
                  switch ((int)b.a<"s1wvdlbclc7pr0","bLl/To/jbKaP7WeCY0yKT9vGMalKjp9RWioAozeb+KA=",2497600950254542323,-1691777032467076819,4411356851059854020,-487616251700493085>()) {
                     case 1777262999:
                        if (var3.bQ()) {
                           switch ((int)b.a<"s2jqhj0js21y6z","ZzOltM3iKplfI68HhcEdxi0TIZ9EtvAAJKkRIjdnYug=",8382699652734869113,8242669423052039425,5461434130064043109,-7440858266329370612>()) {
                              case 339274085:
                                 var1.add(
                                    (String)b.a<"s2ub1smfsx2sh3","c7IGqqttamJVT9p+JfnK8NJE6QrzhouA6rQMvp5ATf+vrf0O8BVGGSaXCslDlNVZi/YsO1+IvJ3/qA==",-5083077384503783294,-3403748483265828883,-109638553797149010,6295870071283716770>()
                                 );
                                 switch ((int)b.a<"spayjf5rnv93d","teWjnnM8Bqy75uv/asl2IbfHKxpZN4AYNTdRuuWxuF0=",-130183064528639560,-7684619644311220939,7759345706334331304,7357986921493683913>()) {
                                    case -397481901:
                                       return var1;
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

      return var1;
   }

   @Override
   public void F(String var1) {
      if (!this.g()) {
         switch ((int)b.a<"s2rdtytkkmfbxe","ZzKtO9PJoqU+lTrWCvf/tReJeeDOHzahZrT4Bw7syTc=",5288289408629933483,8771150951590503984,-37330547199860026,5506930306372997690>()) {
            case 208164515:
               return;
            default:
               throw null;
         }
      } else {
         if (c.eF()) {
            switch ((int)b.a<"s69q8uw6ea8wx","xS3ENcRTilUL6+XbgxcrCJVAQcE8wOQdcPoVHKBeVNQ=",1235581277564424170,-6856650624253307801,3145002191147736960,5130974727589689069>()) {
               case 1188611099:
                  if (this.a.a == com.yiyiaddon.e.k.b.a.INSTANT) {
                     switch ((int)b.a<"s395qi709kg807","6PskwZ+Rjdn42HqLhb7KwPvctg9iudt5DcZFCEUKKH0=",-9201661903970603627,169220664212765371,-6999986881854300681,9211027619923607773>()) {
                        case 1484889615:
                           this.u(var1 + "");
                           switch ((int)b.a<"s36grxbjbb3pqy","zGDVXZXbJBFcPko3ssWzG3LyKAvcNpDkuiQJbAaNHRc=",-6970304629258878765,57665219993551206,-3866611115256526482,680143008952557790>()) {
                              case -1820219395:
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
      }
   }

   private com.yiyiaddon.d.c.d b(i var1) {
      if (var1 != null) {
         switch ((int)b.a<"s1vczwejsv4bw6","xmZ6R+IX5ozKS0okeCtNO5H25bRPtsr/nFJqfdq8RJY=",4067732369823563829,8161810606066506506,8414252085260706033,-8514551020391717585>()) {
            case 1414390532:
               if (var1.a() == i.a.PLAYER_ACTION) {
                  if (this.a.a != com.yiyiaddon.e.k.b.e.AIM) {
                     switch ((int)b.a<"sf5l7jji7fo4k","wWZ7uNRn8hFXsdgY7nXKmgix8vAiIDmgD9Srl/ZEgVQ=",1150049168095160838,7959113977539097029,6504828422604675923,203585795442582603>()) {
                        case 694365601:
                           return com.yiyiaddon.d.c.d.a();
                        default:
                           throw null;
                     }
                  } else if (!(String)b.a<"s1d30ndo3d155z","5glr/2Few3TKrq1fWev0p1fH2c3ItM0ZV9Bu5fnGTegJe6x0qv/rmL9bvqzKlZv5hwDVwBGXYQX7Rrq8Jjkm4NZX",6275166295947564000,1223816215485290050,3495289215368114257,4693162829960124381>()
                     .equals(var1.B())) {
                     switch ((int)b.a<"s3pvvrx8lvbg6n","T9e841zN4TAAVAobEDR5N7CbabB2Igl4/Wz0yC7SDxo=",6079584286804121999,6265044870874420845,-8371484167132850410,-6041911738238546170>()) {
                        case -2009534568:
                           return com.yiyiaddon.d.c.d.a();
                        default:
                           throw null;
                     }
                  } else {
                     BlockPos var2 = var1.a();
                     if (var2 == null) {
                        switch ((int)b.a<"s3ocsyk383wlpt","Cbaugda8igJe+AgRyipzNGXI1dIUhUlLs8DO0duI3Os=",4630702367063291422,-134860276547483772,-7323461698725260640,-1677197627339173875>()) {
                           case -1889223180:
                              return com.yiyiaddon.d.c.d.a();
                           default:
                              throw null;
                        }
                     }

                     this.x = var2.immutable();
                     return com.yiyiaddon.d.c.d.b();
                  }
               } else {
                  switch ((int)b.a<"s2rf5qb7rj7ul4","PF6nOSBIWvg0PfV19m0gZVTYaycEbDO0jXo6PBWB0Hs=",6885769518242713091,-2442295457507078652,1578965004821376037,4349526725607509637>()) {
                     case 1072662972:
                        return com.yiyiaddon.d.c.d.a();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.d.c.d.a();
      }
   }

   private void fO() {
      BlockPos var1 = this.x;
      if (var1 == null) {
         switch ((int)b.a<"s3msg24jbp9iu2","/3nrjFtcl/78T8m774ExMBTzSofe/SmI34TDI81NaRg=",-4046233537505345873,-8079680724299080118,5094589836248755019,8182036532938964688>()) {
            case -921104118:
               return;
            default:
               throw null;
         }
      } else {
         this.x = null;
         if (this.R.player != null) {
            switch ((int)b.a<"s1n1ykcuxfg9k4","DpLRuT2lFalRzLVPXOGQKxxfh+s204XZvK8QVppEeOE=",-175354014879641982,-4360905600942578958,-6809836060006129571,8744605718892595896>()) {
               case -76372493:
                  if (this.R.level != null) {
                     if (!this.u(var1)) {
                        switch ((int)b.a<"s22xraxp3rz4q8","5KGwKO5KwA4b7emA0H72gwq7/zIUrkIbDkJputb5AwY=",-7954507723563067052,-6982859815457019322,-2861514033719037056,6245922916751916681>()) {
                           case 415083911:
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.a(var1, this.a(var1));
                     return;
                  } else {
                     switch ((int)b.a<"swpnr49qfmy1m","cXysIp6hQJFwu1P+2xOY6n2C6LFmFIED3gamAjumxig=",8093285399044378167,-3873174701571306917,-3755324168851325002,-6512037155888253411>()) {
                        case 797247948:
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

   public boolean c(BlockPos var1, Direction var2) {
      if (!this.g()) {
         switch ((int)b.a<"s1pxo5uqtfu2gy","2o1y4eCWM3OT8rXAgsiONQW6yXoA3hbpinfKMJnfaDM=",-8055495101421623402,6724166602090267223,-4925351232796266996,-7308467662511635016>()) {
            case 1719853698:
               return false;
            default:
               throw null;
         }
      } else if (this.a.a != com.yiyiaddon.e.k.b.e.AIM) {
         switch ((int)b.a<"s2t5tr3w57dhtt","Gr0HauqxVW00mOLIsGP5/V7R8SWMVtXDCDY/WSRnlT0=",803273220739915563,2466135043206928907,7171399722646855638,-7067570594131577511>()) {
            case -549368756:
               return false;
            default:
               throw null;
         }
      } else if (this.R.player != null) {
         switch ((int)b.a<"s26q0gzapee5vx","zG+T8lMYE0Wv2NLTPJvimCrxIQrAzTDAA45p6Aw/JnY=",-3583782024462401023,-6179253224246725798,6707600939411775197,-5554597317508698636>()) {
            case -800391547:
               if (this.R.level != null) {
                  if (var1 != null) {
                     switch ((int)b.a<"sedv75ulpq1nf","ck6lBCZtyg2HAsbyZ/k2P87IaM5zRV3ZbQPHNOwZ2FI=",-820264461831415204,8532091584670710626,-6925297615099241127,5650367356976899524>()) {
                        case 1301974099:
                           if (var2 != null) {
                              if (!this.u(var1)) {
                                 switch ((int)b.a<"sakcmv4c31tht","37t42CDXxVdvC1TP8v7IJ22c0T0pAzLmq0LgH6jdptU=",-6241231961755899580,-2726648111393511741,-781479481088651059,2425187305389263290>()) {
                                    case 1956669092:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              this.a(var1, var2);
                              return true;
                           }

                           switch ((int)b.a<"s24jtojjljg7xc","PzAMwB0lpqij7tBKg9cJgN+NJnbdJlGgRQrAhRn239c=",4569946577225027377,-3545394264728396537,-2516034970560540369,3334973870330963559>()) {
                              case 178897135:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return false;
               } else {
                  switch ((int)b.a<"s3p0jz4cem22on","QtU+VGAyeQZJM8xzYq1XnfeyITRylAjSpNrwK+Iakik=",-3966669872887175866,645421444053241476,-2560400228269076392,-5303010831503250687>()) {
                     case -1079722017:
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

   private void a(com.yiyiaddon.d.a.f var1) {
      if (var1 == null) {
         switch ((int)b.a<"s212ybkzhecqi1","2fAS2d0HWGGTMoUyZEAwMjMXgVDv6V6eQ9XZKj6WMag=",-1639846360659174331,-7690747196170117915,-3094963873434456799,5067920255928676138>()) {
            case -2112922047:
               return;
            default:
               throw null;
         }
      } else if (var1.a() == com.yiyiaddon.d.a.f.a.ACK) {
         switch ((int)b.a<"s1ayh7ym3gwgp9","ViZRogFphRPesuF5vzvJ1MsGEX7UotCm0GC74HfD5yo=",-2711511638173726588,3395294231755246051,-5961574074989451096,703841397410140424>()) {
            case -40697897:
               this.kJ = Math.max(this.kJ, var1.h());
               return;
            default:
               throw null;
         }
      } else if (var1.a() == com.yiyiaddon.d.a.f.a.UPDATE) {
         switch ((int)b.a<"s15lyy39xafkce","N5Zj1U9s2L6YFn9Zu3+UQEvIifdv7A84q9wLHNHxvkI=",763489288194831381,3941592289930805015,4049984236877073487,-2120848904194016341>()) {
            case 1684105933:
               this.a = new com.yiyiaddon.e.k.a.a(var1.a(), var1.a(), var1.e());
               switch ((int)b.a<"s3w03p0mki6q86","fbu0jw4aylYK4xR/MoYkYWXOFEKENm5hh09XFB50n8M=",6560977518893122034,1543754611627835271,-1694536578909595482,-3644214845512852612>()) {
                  case 1802638052:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void fP() {
      this.fR();
      this.bl.clear();
      this.a = null;
      this.O.clear();
      this.x = null;
      this.e = null;
      this.fS();
   }

   private void aF() {
      this.bl.clear();
      this.a = null;
      this.O.clear();
      this.x = null;
      this.e = null;
      this.fS();
   }

   @Override
   public void b(Minecraft var1) {
      if (var1 != null) {
         switch ((int)b.a<"s3i5me37ux9yzv","5dvlljvw59bK6r2AyxRtK2s9Kr/cQdKQYyMkWBmhZj0=",-255059360293027952,477443611377433517,5050238803345056970,-6210021740241225370>()) {
            case -2142893377:
               if (var1.player != null) {
                  switch ((int)b.a<"s6d1jtilwjsve","HEaG+BIt+4N0q2SAMQLmyGFILXmkvZGT7ir7roYKPNE=",5774299993491391718,4874106659884637790,2664688514411375466,120757692604479713>()) {
                     case 1920773918:
                        if (var1.level != null) {
                           this.fO();
                           this.fU();
                           if (var1.player != null) {
                              switch ((int)b.a<"s88g7k1w3unrq","tGQYyURbFpi7MwBklnoIrv0TQD6AIRHTfz5RtBuNTJY=",-1991774119310644231,-8644933840058324970,6972875380574147675,-4998529693300675618>()) {
                                 case 230256386:
                                    if (var1.level != null) {
                                       int var2 = var1.player.tickCount;
                                       this.O
                                          .entrySet()
                                          .removeIf(
                                             var1x -> {
                                                if (var1x.getValue() <= var2) {
                                                   switch ((int)b.a<"s2e4wyl2ops0wg","1MHQivMLJNrS4hFhsUMaWkpUaa5URPe01s6nXlssjM8=",-8923554113105505122,2106636245432147500,3087302177508354830,-7104913610798200827>()) {
                                                      case -530001559:
                                                         switch ((int)b.a<"s1fqrak617o0xt","EnkEJ/M6F+w9kRDwLHeG5uB3+MJTTQUYEZuwECrktRg=",8640073695856838150,-5198964057409636219,4293120222667914118,1335654107240351366>()) {
                                                            case -1012083243:
                                                               return true;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   switch ((int)b.a<"s1pncophltrai4","cCwo6tQTsACQorwpUcMTMHdbs8tN+mebPlLDQluJQNc=",-2390526217218177689,109085519245398856,8075428700246560742,-7568799524972220282>()) {
                                                      case -90359239:
                                                         return false;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                             }
                                          );
                                       if (++this.kI >= 20) {
                                          switch ((int)b.a<"s3t4yrz9d03rf9","Sj28jMm1CljNphaOBGOhDvm6Vb9e+QEvk9g1LEFlRVc=",-4663157531537884718,7498639212910534902,-3877417858307809818,9154175158730301592>()) {
                                             case 600918195:
                                                this.kI = 0;
                                                if (!this.ax().isEmpty()) {
                                                   switch ((int)b.a<"s2td7etcxqtzlo","H5PZKWU7mUHsMbzu6xyU1GYpK69DtASeoAHPSPfvsOs=",-5570508350097796633,8803550638943736038,-3806053540038350386,3299796423478894562>()) {
                                                      case -1751119273:
                                                         com.yiyiaddon.d.b.e.b(
                                                            (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
                                                            false
                                                         );
                                                         this.o(
                                                            (String)b.a<"s77pkd919b2u1","cFhSohPub8cBCWbq37Xegc78tu9pStK/Au37d8H0pGPBEUsHd6Ee6A6EMnGIHTxYW3HBKh7Lp3cjCl8Ox7rlpQD+SPrjXQ==",6061403378313031935,-7417312198930270313,8719693447672883983,1552979427906724092>()
                                                         );
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

                                       if (this.cC()) {
                                          switch ((int)b.a<"s2m3p6gcau3a8d","P+wReaf+dPdefm+elVv3SgPfyZMTPyyJE1sI+6pNbwA=",1024251485878894682,7746713717489748040,192800619205373835,-1212676853636407639>()) {
                                             case 741010353:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.a != null) {
                                          switch ((int)b.a<"s3teq8q1mu5lra","DPJtzV06k20dRKvKwuqYpmm8UhjYH7HGnqW+m88T+KE=",-4968488072395704253,-1333744234534072368,3544855168418003141,8690099625796106072>()) {
                                             case -1912543374:
                                                if (this.a(this.a)) {
                                                   switch ((int)b.a<"s15tt5kpdmn2bh","cbCMm4s4Tqo06053Yp1+xJMOqY5aU6T7LPeizO6CWpg=",-8693919336861882087,-8054566827107432870,-3649609015566594900,-6956067031236431015>()) {
                                                      case -950275995:
                                                         this.a(true, null, false);
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

                                       if (this.a.aJ) {
                                          label194:
                                          switch ((int)b.a<"s2qh1w5ckwbxr7","4km8rBqMdJgRBVlcQ7ymTAiZmXUtRF2DjsgEdaxJghY=",2528651894545007558,9222486953911320974,8775419981030485148,-7073957934145983215>()) {
                                             case 26238382:
                                                if (c.eI()) {
                                                   return;
                                                }

                                                switch ((int)b.a<"s2l1ded39fhjzo","J7zib6Sdht8cJ1uj3LJOVU1g8Ea5P19plAVx6l+UIuM=",8679201715447605111,7722498299665881712,2900680663951457258,-2137229338274857958>()) {
                                                   case -2024368507:
                                                      if (c.eH()) {
                                                         switch ((int)b.a<"swtcafc521z12","UfcxUjh5GDgI/BRlZay+uAoQgrtrFOQ9N3jY7RJGMKY=",-6221952229647957273,-3493445875311559077,-2715670826078802667,-3484214513676212002>()) {
                                                            case -89207880:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                      break label194;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.a.a == com.yiyiaddon.e.k.b.e.RANGE) {
                                          label190:
                                          switch ((int)b.a<"s1sdlz3pmx9ofd","AIfMs22HRRiy85ChFODSqR2RRnODEKNDJpVg3lO77rE=",-8687051083584301287,-7569345394608336056,-7491645327111680153,-7742232720822992924>()) {
                                             case -1614937970:
                                                this.fT();
                                                switch ((int)b.a<"s2hc3kkksylsl1","jGVMEKSpl2j68V2lmPkCKBj9RePAtGJXE+aB/hrPwY8=",-2917370807362539488,4402292233520680557,-403622261431397147,-9127633026834907060>()) {
                                                   case -548317251:
                                                      break label190;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.bl.removeIf(this::a);
                                       if (this.a == null) {
                                          label187:
                                          switch ((int)b.a<"s4b58dv6o2jwp","5WvokNZyKCt3cSbD6zciycR2gXl88DsoDBZp+QGFaCI=",8316301454414491299,2002079658862902583,-1269506887173152209,6368397701656992675>()) {
                                             case 514565519:
                                                if (this.bl.isEmpty()) {
                                                   switch ((int)b.a<"s3etj6eankooeh","LrBc+D35ugb2sCfTNBB5pUFqQgyFLCBjMt5mBWhyGa0=",1076849173665339349,1315762667692392834,496203007589751605,-5129391709051593220>()) {
                                                      case -1836761453:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                this.a = this.bl.remove(0);
                                                this.a.a = d.a.SCHEDULED;
                                                this.a.aX = this.a.kW;
                                                switch ((int)b.a<"sjol2neue627n","Q9aTfaVW2a4hTyXsaB2lln7p5MLutCoknE0wfPW/csk=",-2389077629107780654,5063492365495188186,-235563075173313675,-262387721567755324>()) {
                                                   case -1891476372:
                                                      break label187;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.b(this.a)) {
                                          switch ((int)b.a<"s1zgdc8keotk5f","4cgVqEC8jkSCetcxgD96kJmmnD39qIS8UL21IE/B4Ck=",-8468078731565050784,8802994350448488958,-3469935551621278544,8734911316340543807>()) {
                                             case 1564050588:
                                                String var10001 = (String)b.a<"skk80bc2mbec8","bhYFnBRN6IoEel0JStBGxXvmEhG601vftzG7gpUsaW64VxivDVtFzNho5syzuZPR",3958597279367821354,-4175414205472803609,6487245391992445798,3753637218816022673>();
                                                boolean var10002;
                                                if (this.a.a == com.yiyiaddon.e.k.b.e.RANGE) {
                                                   label136:
                                                   switch ((int)b.a<"s360ztwl1gap6g","wX8/3ez0pE38rQZMBmz6iYqBzCRUcVhdjjUt6pP4uR4=",6913001957140199276,2516032688269048065,-6923964251932847718,-9159699222936163300>()) {
                                                      case -1047619716:
                                                         var10002 = true;
                                                         switch ((int)b.a<"s1i2mslwd8tupu","ZopTWsONwo+qCjcX11kf2Mk7kFFk+xPOpOabUbHhg5Q=",-5377181419575166468,-4986901781019251020,8872090121327860048,-7358142493371836553>()) {
                                                            case -1986886383:
                                                               break label136;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var10002 = false;
                                                   switch ((int)b.a<"s25ycn8ujuxkoi","YzF1XQ/C0IFyB5bCd8yUrEcWW2d/AmGPJAnCvKrZiC8=",8990490213529096171,6450466835193116949,-6930055222488618373,2286890844512825908>()) {
                                                      case -758055938:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                this.k(var10001, var10002);
                                                return;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch (this.a.a) {
                                          case SCHEDULED:
                                             if (this.a.aX-- > 0) {
                                                switch ((int)b.a<"s2lvleofrhrnyk","3xpbClR2gdRMkC2ER6RgJby/dFrZ/IJC+zgtX3QDwzQ=",-1309341456812053598,7191130872889449287,-6558590326819140710,4156014336589827741>()) {
                                                   case -653019456:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.fQ();
                                             switch ((int)b.a<"s3pyw67mat77k2","saxLLsektDcx0er24Yj1gXIQ9fkMzQv7joQjpx4rhh8=",-6641722991337046265,2399193835631969372,5480479132144446813,6058659699333549619>()) {
                                                case 1397116480:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          case START_PENDING:
                                             if (var2 - this.a.kY >= 10) {
                                                switch ((int)b.a<"s11j03zr83to4l","9x2K8ZcvICz3KVZvEgTOSmFU92yHExlQzi7vmPxVOHw=",-6543235387810839108,3830107771039735104,6045410852709218351,4797364665210177607>()) {
                                                   case -730784422:
                                                      this.a(this.a);
                                                      switch ((int)b.a<"s1ezu0ll9xpamp","TI09z7ruVGhugmiQ74C2pwfxJNFFd3oVOBNY6C8lDsU=",7225956367064228592,-3596428041605212820,-4272184318699548339,5867318638446700058>()) {
                                                         case 1418482260:
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          case MINING:
                                             if (this.c(this.a)) {
                                                switch ((int)b.a<"s2p00dgxf72mvi","tPgZ2DH3c1tCWxS5rLjijv+Kpsfy+bMgnMG1RRqdx68=",-4282732990704810515,-3411587658990067962,-1322883816951306782,-1283198637978632072>()) {
                                                   case 1681482663:
                                                      this.aq(
                                                         (String)b.a<"sh6qx8ifbgrud","wXAc1bEs6EOMU9/Zd2KeObHRt3IA64pWPQqujHQg5FHyIvqsdUIL+Tx+Zi11nuSnmP61/w==",4940721132840615003,-385896170896041799,5372778720872761992,8657735881493426599>()
                                                      );
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.a.bE = this.a(this.a);
                                             if (this.a.bE <= 0.0F) {
                                                switch ((int)b.a<"s30n58tjdw76zm","Nem1vZP4fdRYuvD9JXUHtlbT61aDqJo2Mb2pd4KGzx8=",-8122408295703045284,8351000304132364318,-7607240909649613866,-1481569491445392201>()) {
                                                   case -1817142232:
                                                      this.k(
                                                         (String)b.a<"s2n09nakjcpsbi","d/IvR8gEzKZU60fSvSeCLLn8FHdswkUyCDcU/WckzVgV3Qw4eHdIWfTica2q/KpawrE=",-2621031746214462927,-2554930538624863742,7457383904483184008,-1126742081787167619>(),
                                                         false
                                                      );
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             int var3 = var2 - this.a.gP;
                                             if (var3 >= this.a(this.a.bE, this.j())) {
                                                label164:
                                                switch ((int)b.a<"s16whjp1om1vta","XWX+HRHaI64Z9Js9lOY8oFCj7sv3iHFYYIUYwJANRgA=",2103847350602248464,-2860232007871247423,-5036265734745291782,8075190031328108518>()) {
                                                   case 216824089:
                                                      this.b(this.a);
                                                      switch ((int)b.a<"s14mmx0sl36a5y","TL9XAuFGGiW9fkD8xdb6DtiojdYWqYUrdsGemFJTKuU=",-7441965691163605924,-2019905755720646363,3137785720490694610,2002701051799414528>()) {
                                                         case 864530456:
                                                            break label164;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)b.a<"sf43m0sud5gzo","wJifNfkq64o/NelmzxSUy8equeH+Ndq17VWgF0+ZZPw=",6442354616660669170,-6454842984992726702,-155998658881480541,3704840624739797732>()) {
                                                case 2126070541:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          case STOP_PENDING:
                                             if (this.c(this.a)) {
                                                switch ((int)b.a<"sc0uhxgc68zh3","LqWvwhBqeqiRCFmOIq57MtOhNiRjlrUICfwOHFl8hMc=",8546767132134083734,1050652445270481819,3438815430130105803,-1567204614721846439>()) {
                                                   case 2098664538:
                                                      this.aq(
                                                         (String)b.a<"s17nbitwl6uvnt","fXXZVLxkrWqxNAI/k5xQcaRLD2OFcoo/k04Xc8RtLponWsJzczs7mO5XvHryneZvCGWbZ3tI",2392843803294358192,-3031610757486584913,3543668768007173011,5396245934107800920>()
                                                      );
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (var2 - this.a.kY >= 10) {
                                                switch ((int)b.a<"s32ok6y56bynx2","EtjOtDpoGob2DD4Kgm26Yf5LWG1wtH1l89dUiEwgRo4=",1499364686459334243,-362429941076525424,-8188531075135645760,36885343281394806>()) {
                                                   case -1020157925:
                                                      this.a(this.a, false);
                                                      switch ((int)b.a<"sgdjet851bdyi","//XDNxSVBo3uu0EEnsV1dynDWcYsPU6MDI4tjDTx9Dk=",142861724042219207,-6842257276926610781,-668337590647299185,6425781256656929298>()) {
                                                         case -2020002526:
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          case AWAITING_CONFIRM:
                                             if (!this.a.dg) {
                                                switch ((int)b.a<"sbg1fpdatz8qr","3ooB/dNI/rVkZX5jZhhanfrw/JSiM3QnnSlltjzdKJ0=",3859745552533732403,-1625936241033453933,-947764391182272167,5577027669289439023>()) {
                                                   case 2146136045:
                                                      if (var2 - this.a.la >= 10) {
                                                         switch ((int)b.a<"s2vjoj9rmnrxnh","UNaRDdglylYxuBl9WUhgrllA7K59w96UE+5HccunEcg=",-1054909943871563392,-8772864504431617700,4841893142637733411,-3439980759050546110>()) {
                                                            case 1130868448:
                                                               if (this.a.le < 2) {
                                                                  switch ((int)b.a<"s3hd9589jkabe6","3KcQtN+u6vgjDuLB9HvmzBzc9BRl5TU+ih1Ph6wkI4s=",4024933596658165629,3600032938664468289,606100482258982404,-5041057800803604766>()) {
                                                                     case -459192875:
                                                                        this.a(this.a, true);
                                                                        switch ((int)b.a<"s1m9vzp0hn14h0","s7gIOuvLn2FoZ1YxV7dM2Y350dgl4S63NcgfXlI2KSg=",4794250021372720399,12322827641831725,1553365076931527477,9217645210093711302>()) {
                                                                           case -1742151314:
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

                                             if (var2 >= this.a.lb) {
                                                switch ((int)b.a<"s1dtm4rrdmbe0p","yBELkXWi8Na9wI3fnEqURu6ixIYM0bxC3d14yI0Kfk8=",7497969845993567519,4006503984245051214,-3031221193467742557,6621425808704304505>()) {
                                                   case 581859088:
                                                      this.aq(
                                                         (String)b.a<"s2zwhpo9jpty20","PRfs1JXuwblznF0hQCeBnTSSvgjE/S+ebgucU7tpz+cQYY4A78GQRBc8O3rcMBz5",-8229226854197620512,-5191538219938837178,-3487954071137077904,3177693142704157330>()
                                                      );
                                                      switch ((int)b.a<"s3o4s8jllhfakx","ClRGyg/z7mYxCS83L4fzZ+3RlL+xgbNcS64C74rNLH0=",7000659841074132973,2319412204574415451,578506250459961754,3806091071028540268>()) {
                                                         case 896688602:
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                       }

                                       return;
                                    }

                                    switch ((int)b.a<"s1bzsq6a9v6wwc","k3pHhGdE31ZNcxmWIOhEEjGdISqjLVG6JMPhhI2lf44=",-8649891320811322647,2470623811512346092,-7731914369506521156,-8449825816122390852>()) {
                                       case -985868828:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)b.a<"s34jogboprjlkq","JiNAQOqARPUCWrK3NgtAMWtCWXiBC4A/fpajsPk0qx4=",3748168050798151134,-1038073970579851527,8430972329137483494,1100359402517799615>()) {
                           case 53294827:
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
   }

   private void a(BlockPos var1, Direction var2) {
      if (this.R.player != null) {
         switch ((int)b.a<"s2hxquv4mga634","0z1ubyIxuzeBOB2yE3tJpOivETb9U6tOpYEVaUKk2s4=",-6799530659641047661,-3434246444263556797,4492500781974305142,-5412702004716281490>()) {
            case -1526360948:
               if (this.R.level != null) {
                  if (this.a != null) {
                     switch ((int)b.a<"sm06rji0uyxae","feEfbboHF0bDpz0Tvjnn/sArMvVX03cURTvOwtftNNw=",-1596078450170322448,-8128741803859490550,1618593506449006203,-5292130843317714035>()) {
                        case -2088198799:
                           if (this.a.z.equals(var1)) {
                              switch ((int)b.a<"syjini5qvhj4n","RNEwtSbGOdVNeQ+3OA69fhmIF/VpOvH3oKdfkClfMLM=",-292748228451446831,-1170157069615682210,-813985983613067572,-7066410727691961382>()) {
                                 case 265841423:
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

                  Integer var3 = this.O.get(var1);
                  if (var3 != null) {
                     switch ((int)b.a<"s3kp7rctangcn1","lFjSfv2F/4XyGygYMHixpQF18BttU/9lxW6+pnxTFhU=",-8840478478227180613,-8120303263038713216,1330900314414561308,784083011742487854>()) {
                        case 725865346:
                           if (var3 > this.R.player.tickCount) {
                              switch ((int)b.a<"s1uzty0kejw32l","aJZwfAIFJlRKaLYOrcO+Y3AjQeNzwUWum5/ofDf+gfc=",5799732760000204673,-4722843217821697058,70935292623418020,-799286111190104885>()) {
                                 case 1987385973:
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

                  Iterator var4 = this.bl.iterator();
                  switch ((int)b.a<"s29c88fxdtyncy","SGLXyYOOLeqASvx4vS7WhR3VQ9Lb8/OVfbrqhhGKz4Q=",6980271386935530415,-6200844910398432764,-8852486787101705865,-8009985855833970502>()) {
                     case 1666482025:
                        while (var4.hasNext()) {
                           switch ((int)b.a<"s3r151si230b6f","ZR+I7BH7HM97A4kj9VkqrODvvllC9RceC9KvU/aZA+I=",3260563963188559384,8849301336660469120,-2063905806268037756,5981076559573127854>()) {
                              case 1317989245:
                                 d var5 = (d)var4.next();
                                 if (var5.z.equals(var1)) {
                                    switch ((int)b.a<"s266rlpgoead8q","aeraZhQ3gxQY3d33oZsfyxqaR1S+ut5ChhW260IRyRo=",-6582881865071482590,-9194146384535205880,-2400481713335382930,-8984382473046600323>()) {
                                       case -1741873297:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"s3c7erpu10atwf","1UrlnLl0ewgOZYqbTV4m0PgtOu6OtwmKECsWwsxER0c=",5387330399031042273,6796967186336409989,5127702224586493542,5986574726907848649>()) {
                                    case -476810392:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        BlockState var6 = this.R.level.getBlockState(var1);
                        this.bl.add(new d(var1, var6, var2));
                        return;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s26axax3jazwu3","SP5Al2KQGVrRgJZL6gKTtuUjUbVkiy9vBuiqFIE/2Ho=",-7653183076646391641,-6381606282158089871,7398438808400844176,-2083587725624409195>()) {
                     case 1001782798:
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

   private void fQ() {
      d var1 = this.a;
      if (var1 != null) {
         switch ((int)b.a<"s181wix2sulmya","J8gD6cyzzW9WH9jRvnCqQdfQEK6NU3W5KK6kBZ5xQjI=",7170492105274847054,3273395335933859420,-8088661647741994583,8526243338438799215>()) {
            case 2139422033:
               if (this.R.player != null) {
                  switch ((int)b.a<"s3az3us02kuy5e","Ci+6IkhtiSzL3AZmh3lzHQfCnnrk/7cvLbSUhgX3nmQ=",-3295917182090598689,-4437416585623627560,3744077578063611652,-6300178763384453441>()) {
                     case 769970316:
                        if (this.R.level != null) {
                           if (this.a.aS) {
                              switch ((int)b.a<"s3t81hfgalrbu3","AtKY4VsZTuP+QDNr1ba+4YLCRzsM06gBm/c7OTSy+Ik=",2696936263388116153,4750747989402514772,12078347883056349,4158601316883766466>()) {
                                 case -1168638118:
                                    int var2 = this.c(var1.c);
                                    int var3 = this.R.player.getInventory().getSelectedSlot();
                                    if (var2 >= 0) {
                                       switch ((int)b.a<"soteymm63792a","q9DAHjPO6723tfkxxJxFBDFGbR1xopBib6fudpkI+wQ=",-1354052182675356053,-7528548816595613038,-1237152746618478512,4051365302408337892>()) {
                                          case 441835375:
                                             if (var3 != var2) {
                                                label57:
                                                switch ((int)b.a<"soyq0p3jskzwm","GV7fU952EVPuaPjCAF/ouKSTfJhjUJl3vcPnz0BgEWc=",-4782022263420143282,-4337546119608030731,376059191759081856,5589618862053134294>()) {
                                                   case 843560840:
                                                      var1.do = var3;
                                                      if (!com.yiyiaddon.i.a.b.s(var2)) {
                                                         switch ((int)b.a<"sp3bp87s8o0b1","yn/Eyu9tZnWFaZ3G5Dz3YEwUYjQExD3nKjarPAneTAE=",-907806619263202611,-3915607648047200498,-8504798797434966389,3896357039318785279>()) {
                                                            case -2003616495:
                                                               this.k(
                                                                  (String)b.a<"s192ec5ccp19aw","ZVtJO/WOUteNEv2BOQ01U0aqTgsb0J1JDzpkFRGi+x6k13ZouWFoquLlMx3fiRZI",-3536872013360750812,-7521354446658348960,-5374082192187972116,8534482048415939744>(),
                                                                  false
                                                               );
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      var1.de = true;
                                                      switch ((int)b.a<"spb6sbcz36107","mMZocTNJEmVFiDXX4AuHGkENm6uyDh6LO+3YLaV+8XU=",1206473518596933677,-2443712994656497392,-6862660392661989207,-5999926732560370783>()) {
                                                         case 222655943:
                                                            break label57;
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

                           var1.lf = this.R.player.getInventory().getSelectedSlot();
                           var1.bE = this.a(var1);
                           if (var1.bE <= 0.0F) {
                              switch ((int)b.a<"s21h9scn16r1no","AAu0wu3/07wvU4DPa3JL8k9UnDO4I1aHb4vcKCQ/tpc=",1166294188497930914,-2090806325374508398,-2730105519447742276,7032349151149734532>()) {
                                 case 1998102284:
                                    this.k(
                                       (String)b.a<"spwx6sp94wzoy","7gOhQMQhmfpjEqOgnzBxvNEHS13WL+WkE9gFyV4jGop+NWF0lIMU+hqqX2uaQKys6TI=",8746154391683011750,719538274857768296,6393234725903462431,552087675220204100>(),
                                       false
                                    );
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           var1.a = d.a.START_PENDING;
                           var1.kY = this.R.player.tickCount;
                           com.yiyiaddon.d.c.a.a(
                              (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>(),
                              var1.z
                           );
                           if (this.a.cY) {
                              switch ((int)b.a<"s295ur7lq0kc24","Y3EJsqw/iv+sf7EOecYcZysWE+mEc7bE4z4TBSX2cx8=",-404112354251862761,-2906859946949767615,3513955428396962687,-6242038416559041093>()) {
                                 case 1744504563:
                                    this.a(var1.z, () -> this.a(var1));
                                    switch ((int)b.a<"s255wrlthl7b10","pp8lqA7QWwpYZrSlKs5WQr+sVAxPBXzunr6QWDu7qZw=",-7279962058939946282,-5078549888577858955,-5568380614056816655,-8761278278770448429>()) {
                                       case -1771651557:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.a(var1);
                              switch ((int)b.a<"s154lx71mvw4nh","tRf5XQwqHPzag+070K/1178IWoGXaem48QpYt1JwIlc=",3445400223047857374,1621474542425949891,205192456597015389,-3313045203622354639>()) {
                                 case -887490119:
                                    return;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)b.a<"s3knut3gl212j3","/k/TaS5ihn1GCbQmLU8hTIeAWN8rrpN92NBnhkfnGLQ=",-4493663899727445478,-8233942295106860548,-3052721367512912037,8994780399995940680>()) {
                           case 1837925553:
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
   }

   private void a(d var1) {
      if (this.a == var1) {
         switch ((int)b.a<"s2jkvd5qyc1yqp","YeoSUvtp4eZuuZF3Eaq7Ie3fUSBr00qwjweProYwM4o=",849945174225081095,106555392532884047,-1600471853212590068,-1833047506176578233>()) {
            case 577622514:
               if (var1.a == d.a.START_PENDING) {
                  if (this.R.player != null) {
                     switch ((int)b.a<"s1q5mz03jvo70f","2TQ7gEPadfRW+wAgtnDcW6wUhRy47UZ2RcVRwg9fDJc=",-4159036409665834592,-3659638577516309272,-2541499504101991739,-3713855159722787796>()) {
                        case 12191014:
                           if (this.R.level != null) {
                              if (this.a(var1)) {
                                 switch ((int)b.a<"s179ob2ji0d0kg","6868mpYU/+yAB8DVqBgZE7SfUVz5tK4XzpPHuF3mYuE=",3182141709738946404,7941173236767474801,-6951363304995240652,-7569119148767081944>()) {
                                    case 194475632:
                                       this.a(true, null, false);
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.c(var1)) {
                                 switch ((int)b.a<"s2b3ndthrkvx9v","W0I+IoMyb19plazUq/CzhTIJ9/pjWHmxQAlxUfoGzcc=",9014231397861958727,8990565778272766225,-5080502524422809689,5985547894740993471>()) {
                                    case -1466275733:
                                       this.aq(
                                          (String)b.a<"s18957mbaud16j","w3/0Y1uROA+HIXbkYrYwyL6vPBA5VGeZlfanQ9+5Kf6u3CGfF4fTUZPkgNmlkv7HsDvnjj/p",2749825457836035465,5839380163732421847,-3851420515802975331,-6604064721398030501>()
                                       );
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              var1.bE = this.a(var1);
                              var1.lc = this.kJ + 1;
                              if (!com.yiyiaddon.d.c.b.a(var1.z, var1.c, this.R.level.getBlockState(var1.z))) {
                                 switch ((int)b.a<"s1g3te317vf9ni","ONMpCuQlFSV5rIVFt11yb24n7eGhpyGB+/5ejMHydBs=",5890390489622936698,5318389262536431450,-8558705339087135083,6948119268037696072>()) {
                                    case 1786798391:
                                       this.k(
                                          (String)b.a<"swd0rvddhi7ws","Plvz/cBsP88kLKWh3uLGhHpdHM7HHEV7YjcTROan5nrhr/YJ53D3MJMM",-5113377301192048624,3303874842671271493,1137029027024658091,6734924846800587334>(),
                                          false
                                       );
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              var1.a = d.a.MINING;
                              var1.gP = this.R.player.tickCount;
                              if (this.a.cZ) {
                                 label61:
                                 switch ((int)b.a<"s1mgycycohu4ls","jK2OEYup2sTkzPfXc+r++X2sHybI967uXd7GwrP+Nz0=",-7461215256771111637,-2861449344786185122,2272311348643909245,-7544956383816748>()) {
                                    case 1359058084:
                                       this.R.player.swing(InteractionHand.MAIN_HAND);
                                       switch ((int)b.a<"s18a9tg36aah71","GtZW8fle1+VS32xT/z7SKn+jni5QPYCILMzruPZgnjY=",4221154131905798092,5909224683211052923,2370836286499570406,6791310891227875239>()) {
                                          case 671176043:
                                             break label61;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var1.bE >= 1.0F) {
                                 switch ((int)b.a<"s34j347bhpb977","389NZC6+luZDYjtfMew/yAzoNHoDEqZfzgwMCBlCAW4=",8477553639502644627,-7386874541297885745,-185450922464108899,1563328351440249208>()) {
                                    case 167476199:
                                       var1.a = d.a.AWAITING_CONFIRM;
                                       var1.ld = var1.lc;
                                       var1.kZ = var1.gP;
                                       var1.la = var1.gP;
                                       var1.lb = var1.gP + 20;
                                       switch ((int)b.a<"s1ws1cbhwbbigy","lkIORVdBYvtxC92KVShnBMLu49X+GkwVXSs/mfi6Ses=",-3616902427995725783,-6256552675913413095,1136504374591722809,5224856934607951824>()) {
                                          case -2117839160:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (this.a(var1.bE, this.j()) == 0) {
                                    switch ((int)b.a<"slf4iljjmbku8","BJaZyUz13kt92/aK8oWQIZEqX9+hW19p/lTMCq5mOvo=",3178308396007010160,2853375245638874337,-7021056086121281855,9218503025551171931>()) {
                                       case 1968070974:
                                          var1.a = d.a.STOP_PENDING;
                                          var1.kY = this.R.player.tickCount;
                                          this.a(var1, false);
                                          switch ((int)b.a<"s2y6msdjdwzn8s","U8f3mtViTrNUVa9tf72tVOlr5dBEezN7mRDDgPPnEcQ=",431919186259961675,-4727940764125880782,286739877315262519,4760549875782049010>()) {
                                             case 1574105054:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return;
                              }
                           }

                           switch ((int)b.a<"s1p7sjw3cbktms","75OjXzAtXwkJVf9+yUga5fPU2nhyQ4+VLwp+0uQ6zqM=",-2094464122736073063,-8334264248846917950,-8680808241178524696,8744826100409956461>()) {
                              case 2100843173:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)b.a<"s12mlz0qdbrjxr","8uloDI3jTiqs/wf/N2/HYgzjtnzzFOdt6HLxWaA8AyA=",1941352616791865107,952214796954508394,-7903536247729404244,-360071194691694867>()) {
                     case 2089733685:
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

   private void b(d var1) {
      if (this.a == var1) {
         switch ((int)b.a<"s1pewvgql3dwdd","JQprp5hY4Cn5LOfX2HKhxZnZPD/Znld07LMe2Mrzzxc=",8790069220101610585,5591327396407849268,-754980787654945087,6327192592009266623>()) {
            case 2071214083:
               if (var1.a == d.a.MINING) {
                  var1.a = d.a.STOP_PENDING;
                  var1.kY = this.R.player.tickCount;
                  if (this.a.cY) {
                     switch ((int)b.a<"s3loqgolg7i1ub","ZXlTz2/pSeXD2z3GtDmfq2kTUTGy8+DHq9jNi11/jKQ=",2452923367956193601,6631832492697993941,-6482397261721790267,8729418578214268886>()) {
                        case 1788793505:
                           this.a(var1.z, () -> this.a(var1, false));
                           switch ((int)b.a<"s8fw6y7jxpt2p","/vFQ9qgD58xfhFqUvzDOYfASdJ+mLmhcMHD2xFv6JL4=",-7592542419179794031,-5994729221028459541,-5052413314356332857,4353743527711252178>()) {
                              case 1657437015:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.a(var1, false);
                     switch ((int)b.a<"s3re4xxvlk23x1","RER3bvMaGJeyTkVzxgT2925/RwgrSOgpGlgUlb0ZhbM=",6882961032690614793,8287095964766918108,-6828959023172137066,4274370966531033046>()) {
                        case 480449580:
                           return;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)b.a<"s2kdekc126c0tq","n5wIc84YTQyuadMN1rSIc2VX/mKuRXb0V7orbXHm+mk=",-8392368546804511364,-8954921568802966798,3764943644251174690,1281004391561574295>()) {
                     case -554014666:
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

   private void a(d var1, boolean var2) {
      if (this.a == var1) {
         switch ((int)b.a<"s2m9ikap181b9w","HakBSmhBVX1+UN1i4m0zUHKXofWY18dZRrYDFQYTQGM=",-4338357178274316061,887361520900457663,531765431194009508,-5387713236552933511>()) {
            case 406690236:
               if (this.R.player != null) {
                  switch ((int)b.a<"s3cgr089qet0lj","0QzKqIAcLQ+WikrF8TZqk7I4/hr6VE9CNUYHMmIw0Jw=",3344964198771838673,2966169101560167044,-720929941940654922,505370812110341043>()) {
                     case 337405934:
                        if (this.R.level != null) {
                           if (var2) {
                              label84:
                              switch ((int)b.a<"s2eycjqtflte7d","YagXIK8pfc95DZmctMdYEyhcovrxFz5ys0EHsYN77v0=",-750828470301886164,5247325569508866935,8778740959924734764,-1023619567395572266>()) {
                                 case 1398175490:
                                    if (var1.a != d.a.AWAITING_CONFIRM) {
                                       return;
                                    }

                                    switch ((int)b.a<"s14fow38velh7b","ZnHnQZJwjCyXe8BqRxqZr0LaWGUii8icIooEOdTuKhA=",285305580110183481,1469411930397232315,330211810504534392,-95751026477228195>()) {
                                       case -2036707358:
                                          if (var1.dg) {
                                             switch ((int)b.a<"s184mnbmhit0hn","T3y/nios13OMGdUeXvo4CXhRWo6sOoC2xftJRzENeu4=",-433338113640999764,1096732110196932840,1167002827475813432,3266177194783461591>()) {
                                                case 1405895412:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break label84;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else if (var1.a != d.a.STOP_PENDING) {
                              switch ((int)b.a<"s2eggzhyzk350g","xlUpc+6M5QlwJ/vU7lSyGf7JGqDA/927pniUo/KLzq4=",-7265864856411730564,-867734843666101233,-172854380560201703,8439037541958511813>()) {
                                 case 1107443426:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a(var1)) {
                              switch ((int)b.a<"s2rm424c4c1mc7","5ot64ghPyOIkzQWQiw5UlWSnp7ii2XlWBWl9h+gVZFg=",-89183307789830669,-8403184924235642176,3107780343645840477,1888016434362952903>()) {
                                 case -1495115458:
                                    this.a(true, null, false);
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.c(var1)) {
                              switch ((int)b.a<"s29s4pbfwoaesy","duQdYYDFKFvHN1TS2Tb+9/ffB1mwrywmtWd94u7AYDI=",-3895614263259767992,2049859829211992190,4835287111647818829,-715562697840735338>()) {
                                 case 1550878290:
                                    this.aq(
                                       (String)b.a<"s3sbr04jpt2ax5","OrAAr02fQhM5+0pNg7Q3VP3CRhnx4ot+x7VRnHuGzU+Hde7bX779vMQ68XygXqNC76j7ycm8/FADTl4u",-7115259371262232274,2650096337942610917,-6871535976539023302,5297766117962287937>()
                                    );
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           var1.ld = this.kJ + 1;
                           com.yiyiaddon.d.c.b.a(var1.z, var1.c);
                           var1.dg = false;
                           var1.la = this.R.player.tickCount;
                           if (var2) {
                              label72:
                              switch ((int)b.a<"sp5guo9vtlgto","58g90mo6TM9Z5Ju6zZdahWM6ZQ5zlRVFtV1sYEAiJYc=",8345894260074701866,6340902313761143094,132023114931704044,-3707570721822403034>()) {
                                 case 1525590483:
                                    var1.le++;
                                    switch ((int)b.a<"sgn2t6t5e7lmn","mLiOFT3adKqMoXnTHNvdUqCml/B5mmWm6P0OJKQ/vsM=",-7447231925022049569,-846366401337569917,6544849474686240149,-7979831197240945297>()) {
                                       case -1526759013:
                                          break label72;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var1.kZ = this.R.player.tickCount;
                              var1.le = 0;
                              int var3 = this.a(var1.bE, 1.0F);
                              int var4 = var1.gP + var3 + 20;
                              int var5 = var1.kZ + 30 + 20;
                              var1.lb = Math.max(var4, var5);
                              switch ((int)b.a<"s32dyjbh69ksdm","2e2vW8kwcObSKfX4N3ghesHnoeV54MOeCNwXL4eJUjk=",-8471583322441751797,-3408434916852702132,-6087523507912545911,5765468416655593501>()) {
                                 case 373932524:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var1.a = d.a.AWAITING_CONFIRM;
                           if (this.a.da) {
                              label67:
                              switch ((int)b.a<"s1myerno1o16kh","YFR1e5ohBmnFxOjI9ZcBSaMnz6N8WbWBmc2/EDULFxM=",5977471042794600921,6298078124353240304,8844710886918462701,-384117364145696020>()) {
                                 case 975130058:
                                    com.yiyiaddon.d.c.b.b(var1.z, var1.c);
                                    switch ((int)b.a<"s2pp3ob5ice5sl","nd7sXkHiD8sjZWK4F7VkXKjwVb+Qe6yTPQxG/jzfZuI=",3862487934642542194,6005644387485469049,6178804648165553502,4997451774524626988>()) {
                                       case 1873291260:
                                          break label67;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (this.a.bV) {
                              switch ((int)b.a<"smsk08mg7bl37","xLnVszwp/QIzdeUo7IfsjTnRKkqosRdjwzbFmfz/vdw=",5165982481812428102,3605770679458781720,-237711024990309040,6254262695376725105>()) {
                                 case 1569139002:
                                    com.yiyiaddon.d.c.b.b(var1.z.above(), var1.c);
                                    switch ((int)b.a<"sp7uscqwghfay","W/dxekcFrDNh6WtRoSBSpq3kvsnaWik/yrXDlW0j4uY=",2195839633828226722,1934790658242750319,-1556917909280712115,-8986705622748472373>()) {
                                       case -2093523681:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)b.a<"shczf5jbeyfz5","2ny9IcTuDamzliMTPONhHoIN9Y3a82iEI0PTL2zVuMQ=",2079931492794709163,-6550071823373082602,-6605561531127869148,8757999784699995861>()) {
                           case -1761829439:
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
   }

   private boolean cC() {
      if (this.a == null) {
         switch ((int)b.a<"s23xqhcov7ywob","n7FRBaK8IXDCBXRtpXy9gaYGhdAgyNae6r15DjVRmds=",-4184130409417143091,-8268532352796383459,-6045069069988308426,1919594873875889123>()) {
            case -861874474:
               return false;
            default:
               throw null;
         }
      } else {
         if (this.l(this.a.lc)) {
            label61:
            switch ((int)b.a<"s2n51ustmsp9hx","U45MhRsrmmZWvWmPMwVJlnYZMCr1wTybwyLQNTBY1ss=",5229264296268467223,-225103474346751355,-5324323479056205978,-5636210192731308512>()) {
               case 1518140306:
                  this.a.df = true;
                  switch ((int)b.a<"s1hnpxi5rn281k","bgBEbm/ihvn4HJasKx8QNuqiIIigpVjf6kSy4BfulUA=",-6581472779970347086,8396171555819377696,-6788466707708581740,3001664967058051109>()) {
                     case -965655708:
                        break label61;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.l(this.a.ld)) {
            label56:
            switch ((int)b.a<"s1ewpzw28yl297","NfDh/IMYN2/Zj8sfn4idqJbjesPg6k4fTNccsLwdR+U=",8900180646893381912,-7850756113899136670,4380683923913656448,6855882178467830408>()) {
               case -2136450761:
                  this.a.dg = true;
                  switch ((int)b.a<"s116q0bec1dnxq","8VYRxRpShXt/KVXJHKizx1AAxsGUY8IDHKrZSGPMoRM=",7797769428686388779,4520979796321268490,7989912117744503001,-7903290705911970067>()) {
                     case 382085650:
                        break label56;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         com.yiyiaddon.e.k.a.a var1 = this.a;
         this.a = null;
         if (var1 == null) {
            switch ((int)b.a<"s4ydmkh4ghpua","BwAsXy1K+3i2KN0zPqHid1Qd5/p9ruhPqhor09sEQbo=",-4599987243485553641,6840553426937734978,-6031672444336517546,130774181988037531>()) {
               case -2030370408:
                  return false;
               default:
                  throw null;
            }
         } else if (!var1.a().equals(this.a.z)) {
            switch ((int)b.a<"s1209x2ieqkt3v","pl6ho19GtgrK8eI+E+1q1sT3yfLtRYpwtocoQVO/pb8=",-8364623607859950686,3616775218739386306,-17537791769953414,-5485530635706300306>()) {
               case -1120718918:
                  return false;
               default:
                  throw null;
            }
         } else if (var1.a().getBlock() != this.a.h) {
            switch ((int)b.a<"s3q7vlrji6yhu8","wt23hyN8ko0cnOPX1etROhb4BtoZNYszADryEddGwQ4=",7653386213224937127,2470394989660996448,-34793274890429902,-3564393532785306301>()) {
               case -2103942368:
                  this.a(true, null, false);
                  return true;
               default:
                  throw null;
            }
         } else {
            if (var1.e()) {
               switch ((int)b.a<"s1t8dlyrvn0gpb","hu3YFaJYHLlVHck+Vpj/KPT3U8mep/MLHxi0QMF/Few=",6063500009610117530,-1128502110560172572,-6303422403651270986,896866539828066149>()) {
                  case -208855682:
                     if (this.a.lc >= 0) {
                        switch ((int)b.a<"s2b1v0df5e1dbh","EjobRccrVII6PFQLQYnqdgRPg/tIms6pMbl/3FMvxZg=",3033206281445392137,-5413471213884527189,9062271648545326127,-4199038175621297321>()) {
                           case 786792966:
                              this.k(
                                 (String)b.a<"sftrlpo3xtfyv","zUNAfJA80Ks82+AE/i1rrYH+SED9+Im6xWF/30FNkutX5eFXzXR5RWHSdmGJRy66jqRWQoH/agk=",1090524110549835165,-5830151483949494914,2672150822774122476,-6978586130584184852>(),
                                 false
                              );
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

            return false;
         }
      }
   }

   private boolean l(int var1) {
      if (var1 >= 0) {
         switch ((int)b.a<"s225c59etspkz9","VZpHoh9oBa01u9kVKz/WOX5Pi5DBCmsG7wOMYTiApwY=",2447355132441971687,-2886550607426675584,200767589875055445,5718962272603793876>()) {
            case 695077758:
               if (this.kJ >= var1) {
                  switch ((int)b.a<"s2oibtszz5xsnu","hNBDxPgZqRSpYo/2/7OVD8h1JP0JVwHewEFUTdzB7Sw=",-4099530538858382758,-8893131285544368785,-4335815424091469443,-8194577357965016219>()) {
                     case 1098600163:
                        switch ((int)b.a<"s2eni947minp3t","DqMG3Ik37uhJV/hRMMylLI2I4Xcb2RxL+MIWd6j2yWE=",-5463349998077108828,3209589351973359925,4016703431619110314,456972865843975380>()) {
                           case 1252776735:
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

      switch ((int)b.a<"s3sq6teb5s6c1a","ABDx5UA16ggNSm3jRd7B4cvnQg7y6Dy4uVSEcTm0qfw=",-6239584116433888893,8932614109162157448,8650555993253511266,4152091645610231670>()) {
         case -1575394196:
            return false;
         default:
            throw null;
      }
   }

   private void aq(String var1) {
      if (this.a == null) {
         switch ((int)b.a<"s1rg23jnehozy0","94DkqhQ++55fNMWgWJ9OWdK5vlEpVqGeTzAXkG74ox8=",-103054307777282973,1605556945571922990,-2938617321970336521,-6837475046330395789>()) {
            case 1463822655:
               return;
            default:
               throw null;
         }
      } else if (this.a.gQ >= 1) {
         switch ((int)b.a<"s3esrnrttus6r1","jU0qsYLlOycgk882u6XBiIV2Howh46hbU+wb6GWfEyM=",6848674178925484392,8631847628572752932,6482745969464196932,-1008545750791693466>()) {
            case 546727769:
               this.k(var1, false);
               return;
            default:
               throw null;
         }
      } else {
         d var2 = this.a;
         if (var2.gP != Integer.MIN_VALUE) {
            switch ((int)b.a<"s2swu5uo2bpqq3","IWUH66JrA6y8OYS6sOzTFgJEvjg1AFBvEsCwg7OTOKI=",2377950260385316840,1031478022827047448,-8280212817959759134,-7860334161141131415>()) {
               case -450664746:
                  if (this.R.player != null) {
                     label26:
                     switch ((int)b.a<"s12fl7n7ds1lbu","8BJ+kxvLqcYtENRvc8Fgmxr6uEL5Imyt+bo8VnV7c6o=",-4495446784746092844,2535090373427888307,3952558678000524346,5319862362880193716>()) {
                        case 1352227044:
                           com.yiyiaddon.d.c.b.b(var2.z, var2.c);
                           switch ((int)b.a<"s2q37hfdey5i29","iMVSdiR5IECcJWGrb27GjPSyfuXvW42ECuvF5OlDhRQ=",917731218814289447,1995355929722414624,-1615001229098057814,-2435740525344858799>()) {
                              case -391180679:
                                 break label26;
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

         this.fR();
         this.fS();
         var2.gQ++;
         var2.B(this.a.kW);
         this.a = var2;
      }
   }

   private void k(String var1, boolean var2) {
      if (this.a == null) {
         switch ((int)b.a<"s2223gtr6x2kdp","bIkeeUauBpC3Spm4Mno9vJzG0UAxwRlbW5lGmpEWoTI=",-335338792758766698,-1556623448931885617,3818681242774130848,-6947888689470571713>()) {
            case -1821105789:
               return;
            default:
               throw null;
         }
      } else {
         if (this.a.gP != Integer.MIN_VALUE) {
            switch ((int)b.a<"s2a0x5x19672d0","cupzqHqrO/qiGHloipMM9fUK9mf/UK+CpkIuBhBOT9c=",4661463590468586015,176586860791017183,5959419835835460523,2013792454745510768>()) {
               case 1443125155:
                  if (this.R.player != null) {
                     label31:
                     switch ((int)b.a<"s340osrsl1pxo9","7kRwVfIS/Gmbs2oWwcAnEY1g2XISLRiAxVfkZnzptT0=",-4846033870188281281,-3821218086710214794,430487969604052382,-6146772547650252692>()) {
                        case 844380310:
                           com.yiyiaddon.d.c.b.b(this.a.z, this.a.c);
                           switch ((int)b.a<"seiqozs42y1ic","Q602gs10P830Dwmf4hmyaxfsXsDzavdREcwSonWJq24=",-4381167436948080422,496133832497191721,-1220641775990094559,-995504358239278568>()) {
                              case 1329267894:
                                 break label31;
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

         if (this.R.player != null) {
            label26:
            switch ((int)b.a<"s1p3chst03dwzo","P+61g+I3WNFKwi0W+V6zAMj2zmXq9vM7w5LqmPTRLds=",-9020712615793243461,2546100304707143061,8961908175342298132,3739989755428635411>()) {
               case -1212585464:
                  this.O.put(this.a.z, this.R.player.tickCount + 100);
                  switch ((int)b.a<"szbnz8sz9z6wm","sXF4yhHMBaVS/w0Ot3ACJGiDVyWhu1ECv2s4oJArqZA=",-1733081999311319075,-8098265186061578446,-1600587535703206553,-6545743086723199319>()) {
                     case 1705694778:
                        break label26;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(false, var1, var2);
      }
   }

   private void a(boolean var1, String var2, boolean var3) {
      if (this.a == null) {
         switch ((int)b.a<"s32j3ytm5vvj8n","AMvKQlye5f+X6dTcNvJGYihw0nUMYk1IEXDXLFkRJrc=",-7678760625224559632,6479964254379641354,-2228453209546134033,-6914206981518044957>()) {
            case 211590020:
               return;
            default:
               throw null;
         }
      } else {
         this.fR();
         this.fS();
         d var4 = this.a;
         this.a = null;
         if (var1) {
            switch ((int)b.a<"sz9k09d05j37g","1BwdqsnWFpzrzWLCk/Ntq0fTYv//IwRxQ8GiwuiWGFI=",-2048717356807246249,-1828721958629326995,1253717617325579779,4064057090238152827>()) {
               case 1335327299:
                  this.kH++;
                  if (this.kH % 8 == 0) {
                     switch ((int)b.a<"s5lynwewomdck","rugUVI7QDl75V3Z4w7lQ97G2hVBIhdUtzdLxh8Qm23E=",-7314927754248909617,4350609444735681432,3979463838388398931,-8090761925186216668>()) {
                        case 1525776241:
                           this.u(N(this.kH + "") + "");
                           switch ((int)b.a<"s1on61c0kj4qr7","HzlD7Ue0FSENiyHu/KiCPSy7q8DAe3RuZHjunhqjvsI=",-5295520916433860813,7196577728299772292,-5755293243921770048,4185067874492369567>()) {
                              case 1969608538:
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
         } else if (!var3) {
            switch ((int)b.a<"sdf2wpcimwked","pD44xElqqvsBHZGcIiIJ2OdQCwyX9/lG85MwkpYD6bA=",6128298571478724380,869716691059430089,317406686145128805,-6851069685575130224>()) {
               case -515119027:
                  String var10001 = i(var4.h.getName().getString());
                  String var10002;
                  if (var2 != null) {
                     label37:
                     switch ((int)b.a<"s1z2e54iji4vkk","raz5fRZehycbf8isjIjjc3+8+xnrMYTYzmyCMnxDd0Q=",612970327807634339,-1098774156435928596,5208220036466337818,141270078704044089>()) {
                        case -165087488:
                           var10002 = var2 + "";
                           switch ((int)b.a<"s2jqzb1xzd8jvf","J2LRTRdPKLjo4fjW6SZrsgJBZ5HRRTMhRVea4Pt/xnM=",-7271089492723608352,-6719762268907729506,-1933060414847897731,-7525683652258419205>()) {
                              case 1702229632:
                                 break label37;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10002 = (String)b.a<"s2ip2spvib32u4","AzF2yu+q3LDrIUZwjM5yCooyfwXyTIy87Ingkg==",-578265561679851992,-5474651615947742114,8732751706823411073,8655792157058959521>();
                     switch ((int)b.a<"sp3n3oes7fhtw","/JGwnLgtlrk8/ZZH4IxAmZtFlL3UtXe5MhTbDibmfvM=",2813522059653888074,-3139955794652112589,6242048812984075885,5915030212899411214>()) {
                        case -2137446955:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.u(var10001 + var10002);
                  switch ((int)b.a<"s3mjuat0y7415w","+SbiMFDVfB8zKpHCybfiZzTayVczM+66lwOpMK+0vkQ=",3692893911759834559,6718069604848066619,-3257384161822145151,1055613744081280008>()) {
                     case 220994354:
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

   private void fR() {
      if (this.a != null) {
         switch ((int)b.a<"s33qymxd5dion1","UbJAaW2rtpQLVPOdVpCZCfHWCtg0Xdn9L4y46rgFl2M=",-6313433806485479039,-3224763751248052027,-6345468978210706350,6040471109166161094>()) {
            case -2110954395:
               if (this.a.de) {
                  switch ((int)b.a<"s1rjtvuei0cggl","fGeOHw1TFLYq+Qbb3KFMIfDoUAHAs7jsc/Q3elpma2k=",-2521140059688071372,6676389441192797478,5392718562840563605,4400324071317908139>()) {
                     case -1546151311:
                        if (this.R.player != null) {
                           switch ((int)b.a<"s2lj9azihwic3u","K/fIq/i3f/0tpoD9ij+Tyn1f3ggTmvpT9qZRuZHF2e0=",-6213373270814074634,3140871820081024272,-5904335001837013869,-4603452229024787588>()) {
                              case 769889577:
                                 if (this.R.player.getInventory().getSelectedSlot() == this.a.lf) {
                                    label26:
                                    switch ((int)b.a<"sxv9heknm8rx2","SixovOxALN9SGJMPacIIi5NDG7URark5ACuRUgceqx0=",-5979392078190248116,-3124685562456988875,4840670726472037141,1569672687580003904>()) {
                                       case -320539523:
                                          com.yiyiaddon.i.a.b.s(this.a.do);
                                          switch ((int)b.a<"s1nnxtdzcx95qa","w0wZE0WamfdeX+MhL28cM2yaoVUTNpX+vL0XTdbg5sA=",1380743278025481878,-5569302051605723709,7791260950075554195,6992011572041001672>()) {
                                             case 1287173670:
                                                break label26;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a.de = false;
                                 switch ((int)b.a<"s3il4forv5w31z","4EAXAdKcRcd95MowPuJEfLTDSgEJVuzzS1LQOI0Mu5I=",-901326483622433395,7341242770556181406,-8265454537118491306,1773387340010903343>()) {
                                    case 1690485009:
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

   private void fS() {
      this.a = null;
      this.kJ = -1;
      com.yiyiaddon.d.c.a.k(
         (String)b.a<"s3ql70lh0130xb","Rk3BZCstWc11rfwsT4E2iHS9YA2AXIR72DwS+LuK3oD6jgKrh61G2WwgaTtQkkaxMHE=",-8462836268447651398,2232736298721428710,-4533750369836058878,-1084267554621651424>()
      );
   }

   private boolean a(d var1) {
      if (this.R.level != null) {
         label22:
         switch ((int)b.a<"s2v96bbdxlzly3","zVS4rKhc+nujiZ5JK1G5EqXFQhWq2wZ0M0OPVuw3bAA=",-5317138910792241624,238788023909465068,5566086325310375728,8188092582312814940>()) {
            case 1760774572:
               if (this.R.level.getBlockState(var1.z).getBlock() == var1.h) {
                  switch ((int)b.a<"s1oc0f9ba8y7yg","zp7xbuqy8Ms6H+P/x9HRmrIO0IHw4+sO2vBX/K6DzvI=",1360454327013762364,-4712384838130929105,-4096499279030780859,5144741376074680164>()) {
                     case 380276852:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)b.a<"s1wwhfbvfq3r0v","QCUN2cdCOY9eU/iWa5QS86DWqnkMKdo0pA/1jh7kxv4=",-7852560947008481906,3033880207747479394,2856785947490089355,-1063539302237001194>()) {
                  case -1108156971:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)b.a<"sk6vc7x31dihq","Ts8M6yp56uotgu/VDR3ovnBsAn6ogH/KXSAAXSaoyNY=",-3319776456315494745,-2195576800739231055,6169128869559318892,-7468552931504080191>()) {
         case -1630982498:
            return true;
         default:
            throw null;
      }
   }

   private boolean b(d var1) {
      if (this.R.player != null) {
         label22:
         switch ((int)b.a<"s1qstau8z1u5kz","2FvR3P5JvIudWnDCjh/CMWgVjDLGgy12APDSbpoTcfU=",-2992725702389359419,-5559779169783111697,-5673083868015407750,6103392970628158563>()) {
            case 462439425:
               if (this.R.player.isWithinBlockInteractionRange(var1.z, 1.0)) {
                  switch ((int)b.a<"sg0qd14njdzal","7aezEr2T8gsLwAQ0K2sjZvXnocP1+BhGw9LQoxeeQiQ=",6874826305491421679,-2195477679207179760,1658545143860519322,5296066801293848768>()) {
                     case 2051558902:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)b.a<"s2siu1rt9stvax","K1KWfwDBf1nv8h+2IrRPUIZST4estqK/G2VsL8JNk74=",7477428707691762824,507877839545986806,1050344313546612397,5869776258455273038>()) {
                  case 1964011085:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)b.a<"s39yg6a6uvbjdx","ZvTYmBwCRmbHX0Tz8/LP3pgdf601Bka2y/YjdWnOd+o=",3706607110635197971,8192952400952651464,-4751332546018451370,-2781689322547733767>()) {
         case -618773584:
            return true;
         default:
            throw null;
      }
   }

   private boolean c(d var1) {
      if (this.R.player != null) {
         label22:
         switch ((int)b.a<"s27lnsz8pgyq55","pt6eWEpu9VPZg3+QVqfspsqhovyh3BoNuuVtYHY9zVo=",-7772754468231278709,1243928971884192435,-4211094744032664980,-8825530289184863542>()) {
            case 211848396:
               if (this.R.player.getInventory().getSelectedSlot() == var1.lf) {
                  switch ((int)b.a<"szpol1498nbik","l7LGe+5Fsbw5gJ+vpiO9sO2k6xU+BVDw/pq7cFRqD2w=",2463471522002620932,-675469732044116562,-5295447655697214027,1264975475346450346>()) {
                     case -372837984:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)b.a<"szqcojddz2064","9v3zD1d9nvVgwZ+u/qxVRBx0LElk9bDez7CBf4Yvhh0=",-5422874265761303175,-433793355348935455,-8492206564232348206,2179800073045040424>()) {
                  case 809270233:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)b.a<"s2m43857lzc1va","wQy/ELYvJGMum8MvPXYBSpOv6HY82kpaPLLNWYAJyWo=",-6553528011086596699,-4695920833447129262,-7613795655771364257,-6159295533255975492>()) {
         case -625677977:
            return true;
         default:
            throw null;
      }
   }

   private float a(d var1) {
      if (this.R.player != null) {
         switch ((int)b.a<"s165n6cv5lu1cj","6a8BV27cbNy4faub3XFUZjZUyfV9SiuF64rjdF7mlG4=",3214148731170529709,-1989203667566090755,-2872795995256646756,-7616265971032713535>()) {
            case -984879249:
               if (this.R.level != null) {
                  BlockState var2 = this.R.level.getBlockState(var1.z);
                  return var2.getDestroyProgress(this.R.player, this.R.level, var1.z);
               } else {
                  switch ((int)b.a<"s106hk4v7sk7vu","8YiT9f9o74a36jqucb2ed554Y5XFJVhQ9GI6gsbWQQI=",7145099064924111732,146096337799771530,-1713236342741699710,-4756673162165970103>()) {
                     case 923198040:
                        return 0.0F;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0.0F;
      }
   }

   private int a(float var1, float var2) {
      if (var1 <= 0.0F) {
         switch ((int)b.a<"s3ou7s80rl2kri","405LbSuzgBuga6H+dAkVE9jaNlfakg4fgqspLOYRY7w=",-7395189575556944236,616073088420378456,2693261828735723904,-1240457644438472769>()) {
            case 512859599:
               return Integer.MAX_VALUE;
            default:
               throw null;
         }
      } else {
         double var3 = Math.ceil((double)var2 / var1);
         if (var3 >= 2.147483647E9) {
            switch ((int)b.a<"szv4ssx5l7szl","E5LDfi2c8tDgZF99WJz4KXwaYgfX8+eNAS5rqhsk+Cs=",-1753014002346143974,5589685263349221920,-1731368562900619994,-7123005998265713704>()) {
               case 1870559637:
                  return 2147483646;
               default:
                  throw null;
            }
         } else {
            int var5 = Math.max(0, (int)var3 - 1);
            switch ((int)b.a<"suiuvrham0k43","2VID3S+oP37zAk627xkipQtQ9g/SqX6jOvX6K1LHvWk=",-5292127402400787912,-2809725819828149590,-5060726605828174186,3847653066629033862>()) {
               case 1830761163:
                  label64:
                  while (var1 * (var5 + 1) < var2) {
                     switch ((int)b.a<"s29sd1ntw9bwao","/SdaF0kgUiN4IUAqyIY2Naw2SJQTU1BqVyjS0yQ17vc=",-7125563776659069366,6296857149496379237,-8918150806455612584,565317759929360229>()) {
                        case 959560692:
                           if (var5 >= 2147483646) {
                              break label64;
                           }

                           switch ((int)b.a<"sxjigtfdw90i1","nwzWQ6AB9RSxg+7a9yAdVCB4ktZfLAEgAhFVrpCcXxo=",473842643324931760,2858326662403125867,-2900501922054899302,5556108088236398723>()) {
                              case -1373722117:
                                 var5++;
                                 switch ((int)b.a<"s2n4fm6lr76ih7","LNV+GJea31eLp551Wc0rDZwOrrQNMQJuozkSbNuH6Rc=",-5639014423440238633,6775976935856952627,-8315287793899638629,-5178262840853177245>()) {
                                    case 80603837:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  while (var5 > 0) {
                     switch ((int)b.a<"sab4cnrpgalk8","w4iG+WjOKZ5w8u51UKll1vvW/m2aTWGUSyZlDsQKWro=",-6835563520163412009,343724126879985928,-8010781299957106006,-6696708910398928953>()) {
                        case 1146456035:
                           if (!(var1 * var5 >= var2)) {
                              return var5;
                           }

                           switch ((int)b.a<"s1y2orfrg3im54","VItA6mQDYJSkZoA4KxhSZP2sUln63c+WtDkYwJ7sDPM=",7635487337351390236,4695669852610561675,4720308809871351740,-1916254968066871138>()) {
                              case -631199796:
                                 var5--;
                                 switch ((int)b.a<"s3mci2k4dharz5","A9xMi8knJaT55X7XN6EbhnxjkA2F7zj9FGV12txJUg8=",-2301259337990024892,4686505633414646467,-2447202333511011020,2090146355686941637>()) {
                                    case 917247544:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return var5;
               default:
                  throw null;
            }
         }
      }
   }

   private float j() {
      boolean var10000;
      label43: {
         if (this.a.a != com.yiyiaddon.e.k.b.a.VANILLA) {
            label36:
            switch ((int)b.a<"s39ltn7mqigdjr","Fg3TCAWRgi6WE30H2d9qfv00OHuzyPC27mqG2oVyrx0=",-5424915792919373205,-6538234940705543768,6445338080639876116,-2946028012768084475>()) {
               case -1188361738:
                  if (!c.eF()) {
                     var10000 = false;
                     switch ((int)b.a<"s1uhsyj0bajq89","yQF855C274KLS6sN6zHfgx5jmTPkx/NMQhmPjKWMbT0=",-8831452112676481907,-5555606893188439859,-559424399613388828,-9191910917204705547>()) {
                        case 1304867893:
                           break label43;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)b.a<"sld9gteaxucqt","h0+LZjETHu3eXps7EPuFX34iOWxZHuoRd4xLZAvQDeo=",-62554885417601978,-4538577190431709194,6081050472076271246,-490900985955174418>()) {
                     case -1332035514:
                        break label36;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = true;
         switch ((int)b.a<"s23b8j61c3537h","WE/XpBT0PovBubSY0Ki1dpQWLW4n18V1hQegapeOOjw=",-946494957972555808,-5201422325671187646,-2283703724216595736,7788618405951342020>()) {
            case -1915998562:
               break;
            default:
               throw null;
         }
      }

      boolean var1 = var10000;
      if (var1) {
         switch ((int)b.a<"s7qj3br1bpjj6","/S7CbPAd5ZCwwg7zv3dnO2dbiCEOki92lQcRgiA7DDI=",6405381853061904556,-3011450631600008379,-8664798452571441229,-6018739231699403498>()) {
            case -1216692638:
               switch ((int)b.a<"s3bgzpagdhxdk8","73yYGohk636CT+B/QG/gXGM8Ez1vq+lBOXZZnSVbGFQ=",6433166846617660196,2231578957653901075,-6872251154304748661,-4018131210704781701>()) {
                  case -927706313:
                     return 1.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s2rpti55uwwetn","ukRJHr6wKPkX66lDPgq/xJYyyp3sSsRJ/z5eFrhw/QY=",2983374790711258,-2578902363416663779,-3706516101159987128,6506707791531305104>()) {
            case 369853305:
               return 0.7F;
            default:
               throw null;
         }
      }
   }

   public int b(float var1) {
      return this.a(var1, this.j());
   }

   private void fT() {
      if (this.bl.size() >= 64) {
         switch ((int)b.a<"sqy8gujdjrtvy","mULuo07onUfZY+BFp23Hx+CW+PhNIc+81ibn5URJoWM=",-1121346401331765816,-3919273579818219318,4530687898506038654,-6383221807727909817>()) {
            case 1619660064:
               return;
            default:
               throw null;
         }
      } else if (this.R.player == null) {
         switch ((int)b.a<"s1tx005hfrtp5p","4ybj1z218e67I4+ooj8ZX4v0KJFpTaJM5QYC8MwlxzI=",-4866555668105737240,4002503849742535957,6304250362439675158,-4362080484925801153>()) {
            case 1558931462:
               return;
            default:
               throw null;
         }
      } else {
         Set var1 = this.s();
         int var2 = this.a.df;
         BlockPos var3 = this.R.player.blockPosition();
         int var4 = -var2;
         switch ((int)b.a<"s2ama0n6n1izyt","PRY89khDrcDYSlsublLK907c89CLCkqr5KyX2zo+XhM=",4134256471586223588,1840807240488146874,3089164739745577150,5795829531227143741>()) {
            case -240248002:
               label170:
               while (var4 <= var2) {
                  switch ((int)b.a<"s36irojomet2xx","PxD4NhLkrb2K8R1dXW9hDCM19vPheEXc5ZIAljri/fE=",8499884882661036689,-592079096295877397,-3215415358358324727,-1948051367978388346>()) {
                     case 434130745:
                        if (this.bl.size() >= 64) {
                           return;
                        }

                        switch ((int)b.a<"s2wsag457c87vw","UoMQ8QiaTtELUcAur8LmP1ccCpZsnQRgcekWrUYBtIc=",6757674542987260136,-4775889269040588970,3494640086535623167,-3389724112958418685>()) {
                           case 492816253:
                              int var5 = -var2;
                              switch ((int)b.a<"s2sgs46kqtjlbz","tguVB2UvtHN+cCif63630sdIDlNyi/4fcJJ23Cs+wSw=",-8255264018799646224,4984572921638876306,-1033381655127834250,-2082163357733749939>()) {
                                 case 1207597509:
                                    label168:
                                    while (true) {
                                       if (var5 <= var2) {
                                          switch ((int)b.a<"s1amy8o0tim0av","958fzbh023ViI1vskXfXtDxSJnDg8nnpgV4mCjMDPl4=",-2163032754517864469,724096169080841547,881078271359843274,2672339459943499453>()) {
                                             case -198669835:
                                                if (this.bl.size() < 64) {
                                                   switch ((int)b.a<"s3o1r9l2luo2j8","u6kGr4S/FBNcjWSxu49u80t7b969xR/xusGSS+1PCLU=",-1162316608164240417,-2486249513638416253,3889634100196241059,-8670200568731026059>()) {
                                                      case 1569115769:
                                                         int var6 = -var2;
                                                         switch ((int)b.a<"s3o0447goyy3hi","GU8L5GAMWouRf6B7+WUMfub9pUZNEHwaZT6mJ+95ZGY=",4833291286539923621,-8459467392588815424,-5027779596592228650,-2255174895203388724>()) {
                                                            case 1587145978:
                                                               while (true) {
                                                                  if (var6 <= var2) {
                                                                     switch ((int)b.a<"s1zpxu6kqhpnbq","kisBGEE1fFjVypuo+O7ho9PAts1kbLEqL7if0xFy09g=",-8493853401238074798,5264785279949016039,5083973701081510136,-5596936718885670369>()) {
                                                                        case 2110115436:
                                                                           if (this.bl.size() < 64) {
                                                                              switch ((int)b.a<"s3udpztadkpt78","AT98qeGXPHp0kmB/65L4iOG7Mz18SfMInIUKM+dwOtA=",2361794217949169834,-6136715460664649040,8856539836862534628,-8699099752978070646>()) {
                                                                                 case -1426489110:
                                                                                    BlockPos var7 = var3.offset(var4, var5, var6);
                                                                                    if (var7.equals(var3)) {
                                                                                       label99:
                                                                                       switch ((int)b.a<"sgruusz8h5t5g","J2uT1tQ+U8T19qkjc9ZfVBGCAAaZmdOV62T/MPu952A=",8561090832479260970,1114162072113912351,1369681386000675180,-6990101135539618828>()) {
                                                                                          case -629907181:
                                                                                             switch ((int)b.a<"s40tkb1s11sv3","uoUpssa3gkZIaHz+l/FqdUvnkla/wSMUvMz+8rtvWBU=",-2953997778431559311,-4592874588236023453,-7031200509408127406,-1362118043155492078>()) {
                                                                                                case 1083261623:
                                                                                                   break label99;
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    } else {
                                                                                       label154: {
                                                                                          if (this.a != null) {
                                                                                             switch ((int)b.a<"s25jej7z5wj66m","Ua6ZhuQg2/kUfmE/OTDLNPr3BiHdYX2Q50Sd86GyFmI=",-7747801643371230903,-8291623116333801064,-1564001243904835717,-8468237522871782766>()) {
                                                                                                case 620931053:
                                                                                                   if (this.a.z.equals(var7)) {
                                                                                                      switch ((int)b.a<"s1qh7kbdew7r82","XBAX1GHX3ZX4fT+R3NzRzDfBTmrakAGW4Qwlpm6EXLg=",-2212708190532925077,-208966059265385811,-1119943737636861750,-7637958905959194831>()) {
                                                                                                         case -2136444283:
                                                                                                            switch ((int)b.a<"smtopv7wjiuaf","7AKnKY6+J/VVu12OEI3ReFe4RdrAtvC94Dbx+wFafCc=",6797961569874574262,4365784876590659709,756036600709035621,-6928817103488561326>()) {
                                                                                                               case -866675:
                                                                                                                  break label154;
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

                                                                                          if (this.t(var7)) {
                                                                                             label111:
                                                                                             switch ((int)b.a<"s3ck7kgeszwzvc","XTYVLYW6aRElK1yWdcSEyKbx3ZIPeOIT365a54XWvY8=",2707479655546860299,-6692418870555584280,-6535786269019847943,4011755935425035153>()) {
                                                                                                case 829219922:
                                                                                                   switch ((int)b.a<"s1ado58yl8j8ee","vX6XbPVZ72RT2H8CzVglWvw8ibA3ZUcdGoIF+Ya5gl0=",5736404989385874504,201383301883772682,5836488724639371799,1202663933354148518>()) {
                                                                                                      case 582273704:
                                                                                                         break label111;
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          } else if (!this.R.player.isWithinBlockInteractionRange(var7, 1.0)) {
                                                                                             label118:
                                                                                             switch ((int)b.a<"s39kcox7mkqv3b","MlDm9296Va/pRxcXbCi6qKc9mBT+Txt2yXrJLZB0GqA=",453376159084198166,2543801716301946540,2170246126998728510,-1412394739453393949>()) {
                                                                                                case -1834412485:
                                                                                                   switch ((int)b.a<"s3v9vwt18o7xc0","yxVaHwXClCbXh23UxJA9Lh1sRcGIE5bqntZ/CbFiPYU=",-1561485127079274656,-6720207801470839818,-3427947413130059826,2923577837175809287>()) {
                                                                                                      case 695266877:
                                                                                                         break label118;
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                default:
                                                                                                   throw null;
                                                                                             }
                                                                                          } else {
                                                                                             BlockState var8 = this.R.level.getBlockState(var7);
                                                                                             if (!this.b(var7, var8)) {
                                                                                                label102:
                                                                                                switch ((int)b.a<"s1m1edi0qvqwv4","Pr0d/ecF9JmovNJAmxVYJO4+ReqhrO2ydiWnAmPGpaE=",8325633891658173868,6576571935879509123,1946251039819460268,4928269010753546409>()) {
                                                                                                   case 969340117:
                                                                                                      switch ((int)b.a<"s1httk037swz4z","YF8UR8RUF07UA8qS+FSc9NA2BjQV6Z+fl2zxN+E4YrU=",4038409303116376317,1090246666297672352,-939981956269971263,3145512215649287542>()) {
                                                                                                         case 2098996093:
                                                                                                            break label102;
                                                                                                         default:
                                                                                                            throw null;
                                                                                                      }
                                                                                                   default:
                                                                                                      throw null;
                                                                                                }
                                                                                             } else {
                                                                                                label146: {
                                                                                                   if (!var1.isEmpty()) {
                                                                                                      switch ((int)b.a<"sa6aa5r0ermru","67m05ZdVRrDt/LhamdHzlMKpqHJvFYyDs6JydWNSu8Q=",568359189636257295,2980509484556764087,-3659252839896219649,-2493956546191625877>()) {
                                                                                                         case -879858260:
                                                                                                            if (!var1.contains(var8.getBlock())) {
                                                                                                               switch ((int)b.a<"s2e4u4b5evkd9e","h2nxiEJDJCJ47aVDF7FJU+hfjDBreMW0fGeLkG0D+eE=",199352038435151440,8444678286121006680,4446235444658381718,-5671377896822755567>()) {
                                                                                                                  case 1738264370:
                                                                                                                     switch ((int)b.a<"s2qt8wzg9ltiga","N5PAV2CgvGEThtS9O/77+OZVAHOJN46fFdAfrEjgOps=",7504941162104152911,318792420429135553,-4434196842001485327,-7028093350655809862>()) {
                                                                                                                        case -2024019656:
                                                                                                                           break label146;
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

                                                                                                   this.a(var7, this.a(var7));
                                                                                                   switch ((int)b.a<"sbpi1qrn2pf4a","vUhgzPJhlPFNcCo3987NBZ4/30LVTna1ptymZjMINJI=",-4813880992551853461,3670341481400132363,-1983922766766801959,2035107626540986911>()) {
                                                                                                      case -469364587:
                                                                                                         break;
                                                                                                      default:
                                                                                                         throw null;
                                                                                                   }
                                                                                                }
                                                                                             }
                                                                                          }
                                                                                       }
                                                                                    }

                                                                                    var6++;
                                                                                    switch ((int)b.a<"s127kfefhz8cj7","o+xDv901urWinQzggfblDvz+Cew/rRIYzeyA/7HQlZE=",-1386577917885471741,1879783285218921098,-688242378849359011,2897480419975368717>()) {
                                                                                       case 1955539042:
                                                                                          continue;
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

                                                                  var5++;
                                                                  switch ((int)b.a<"s3edl43jupppy8","7eb+RrirXbBk056cKNTqJD/ouGcZpT21Pc5yTttRvDU=",1174095481553759588,-4714457719767924781,-4442296588919206051,8882600485075328464>()) {
                                                                     case 835794641:
                                                                        continue label168;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }
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

                                       var4++;
                                       switch ((int)b.a<"s1ahgka047asp8","hOwJk2bfp88JxWwpAvBcV/K0HYzr6CY5T2bVsEj5RKw=",-675562256540780560,-4291783435012515224,-5413586890679752066,1073918456714383537>()) {
                                          case -1233369721:
                                             continue label170;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
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
   }

   private Set<Block> s() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.a.bm.iterator();
      switch ((int)b.a<"s2vm9jjfyygyp2","ZS/Luo0pzFejj23mIh0Px27L8ThwIVuX+CA8p+oZMvg=",-2296764229636208544,-6972519057324113543,9166111998500196786,-8817844266646623861>()) {
         case -1982794827:
            while (var2.hasNext()) {
               switch ((int)b.a<"s1t6x63vuftg4v","AiQX1AdtAdvmSndJ06aw1ojaEHCA/3ih6mCZk6FwHmk=",-3690830381206982198,-3529022289940638674,8306134655882271160,-7756411509714289507>()) {
                  case -624972050:
                     String var3 = (String)var2.next();
                     Block var4 = a(var3);
                     if (var4 != null) {
                        label23:
                        switch ((int)b.a<"s17adz6pbha88l","J7zNmnlQBftfhIVv84hcT40ezmykdQXJA8J7wWTSGeg=",1360256437069094655,-6231123856902582615,6026425427715319382,-5493495232812142589>()) {
                           case -1657612291:
                              var1.add(var4);
                              switch ((int)b.a<"sbnfifv1fv6qd","VlO3wVAOZ/R9P+7fnCAgjKSsi15bHn5RIIjMprydPDA=",-6020931856066864187,-7488090958883338168,-2205827093617568873,4309037237189470835>()) {
                                 case -1419676333:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s26xu4ks811swf","xtkTJbQ2pk+gvOyPmcBIGb+KjM4tmu+IehU5k47Qvt8=",4683266864171539249,3581348333521115189,1461390084782801641,34313141531503300>()) {
                        case 1462189792:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   private static Block a(String var0) {
      if (var0 != null) {
         switch ((int)b.a<"s28fdeh6csowwb","J5KfanXxEg9zjriYTiq/4O8q0S7eTUuzp9v0FVGptc8=",7844795810673256886,5955108407174050365,4600278303318697376,-6548380604836581411>()) {
            case 1552558270:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)b.a<"s3km4ly7nvbppc","htfk4aUsl4u4Qg+aJfVYniXj2acfLZNraPEpn/A502Q=",4849139756683178750,-8448663445887981784,-5177036530742824910,-63793313724611461>()) {
                        case -1224541287:
                           switch ((int)b.a<"s3a3bb0pjojsan","gQM/f9f68xNMluDhV2nBD02dW+Rj/6xTE3itznpaFKc=",-1723759559424625017,-7177083987005832909,408141497412804070,7060490508650149524>()) {
                              case -1639415372:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     Block var10000 = BuiltInRegistries.BLOCK.getValue(var1);
                     switch ((int)b.a<"sgg3qc44vtbm3","wiyhkTKrltwgpKgdTH4Ag8BLNYLZO7PXjfKgXyhFE2E=",9010804871814953237,6345769201922003526,-3185949080258405572,-8757453314691377659>()) {
                        case 1746293803:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)b.a<"s2yf6bktri8wod","4OJGw/Ezn654mb8pIQKjsu5E+vUlIg7j6hkh6QW3bBo=",3371312173928223427,-8737645391756712943,-8273879921135237395,6669079475697273206>()) {
                     case 102419013:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private boolean t(BlockPos var1) {
      Iterator var2 = this.bl.iterator();
      switch ((int)b.a<"s13z8pcm70qtol","qXIrFtHPvhZGsFNzQpyxaYgwrGbfuFZ+VEf3fcTcGdY=",-5699988373320344473,-7450953047349283885,3972803139145722521,-1801983395915762558>()) {
         case 1194402531:
            while (var2.hasNext()) {
               switch ((int)b.a<"sym0lp517escc","sD10+/sxAIF65BS3kTkRSbq9Nzdew2LX3pC6KBHFE1k=",-4512146238220181188,2236011928820793821,-458453088692378161,-4436858880109121794>()) {
                  case -714233042:
                     d var3 = (d)var2.next();
                     if (var3.z.equals(var1)) {
                        switch ((int)b.a<"sf66zoks8vqec","yVWeOe3ohrbZQKngQhCpq4uGpLJsh/n2kHWw0OYiBjk=",8955244345447691811,4757749349078902978,3892437355506647255,8750187776212955732>()) {
                           case -1047584361:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s3fuklnmcbg835","50UPpvh7aYmkbpchHb6nnY7pL6j1ZjmZncMVkNYZwKU=",-6611106825734321695,3808917495740389684,-5981798855328716276,3868355747540038792>()) {
                        case 646486193:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return false;
         default:
            throw null;
      }
   }

   private boolean u(BlockPos var1) {
      if (this.R.level != null) {
         switch ((int)b.a<"s3ghmt52aco4yq","uqFAoaIW/wF5svv+Fekpwd7PX38yxAoHzyjxkfHmDZ0=",-1021788404919141430,7817999389865090348,-7900486471144917092,3013267733880544573>()) {
            case 1343253077:
               if (var1 != null) {
                  return this.b(var1, this.R.level.getBlockState(var1));
               } else {
                  switch ((int)b.a<"s17mjutij5j15x","ybrze/GkoqqQo8P7s59H3Ia5aGLjdcLWewLeRqh3VMk=",-6113570563894473960,624655876450908393,-3097975368910906354,5851826470811780643>()) {
                     case -35639841:
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

   private boolean b(BlockPos var1, BlockState var2) {
      if (this.R.level != null) {
         switch ((int)b.a<"s3k976brs7b8yr","nMXQyis780+g6zDd7BpvYwLodud2hri5bZYgWd3ONJc=",4133996050013935931,7281882920458158927,-2321470092181913511,6872097439132668774>()) {
            case 253825953:
               if (var1 != null) {
                  switch ((int)b.a<"s2eiznpqqa2kpa","T6HOr/ea7FDAaNWDVbJFjC7Sa6pL5A+l+2I/qhm3MhY=",-1227145678452790481,8982728726497729834,5098193316243827663,2117411204916963973>()) {
                     case 1633796811:
                        if (var2 != null) {
                           if (!var2.isAir()) {
                              switch ((int)b.a<"s3kxkf60jvozuw","zUYAmf0Vfem2IKJTrxa8vHMc9u/K9l35GMWyeFqIKoc=",-2527899718076249781,-9078242215645082306,-4494825038573534278,1700731902921190328>()) {
                                 case 174902706:
                                    if (var2.getFluidState().isEmpty()) {
                                       if (var2.getBlock().defaultDestroyTime() >= 0.0F) {
                                          switch ((int)b.a<"s3164xv4hhol1m","/vqk6XhmDqlJzBe5qId1OPE00bMnf5WuvPVQmLaJENM=",-852845996406965547,3616598021905127598,-4441448157405573145,2252356940464237675>()) {
                                             case 86334590:
                                                switch ((int)b.a<"s14us9ocvo1bg5","CvNRUTQa6fQBkSXj/PEMp2pNTYeDiyOF4L3lYHjBMjo=",-8144838878117993396,-2039417228881349740,3187232280100331088,-9124048001708481366>()) {
                                                   case 1800596588:
                                                      return true;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          switch ((int)b.a<"s1t1b3hf8i63qt","1YA6VpAAd0UF0mzaq7F95dtoJR4ZcyOFKiCVh6dZHdw=",-6224600307354363254,-856777689488241720,5725337112705619751,-8259697998470038894>()) {
                                             case -91772135:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }
                                    }

                                    switch ((int)b.a<"s1sg6vx9qklxpk","JvmXuxCt3oxi3DpAbk5jvd/0la75BjDbfg7gv0/eBXs=",6615281204749205709,-8049357381277061477,-2049393330059682393,5893048668711828581>()) {
                                       case -1419565086:
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

                        switch ((int)b.a<"s13noovw7sxczg","X3zj7FA3wnizBdGYm49RBMh0chtWBza0+t/GMdWTvtY=",-1765471249009313091,-6738707046166627834,3372190298299229266,-7443005895627740122>()) {
                           case -1515954265:
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

   private Direction a(BlockPos var1) {
      if (this.R.player != null) {
         switch ((int)b.a<"smprf3l4z6sza","o4VQ7mpB5AxDBYcZ2VdDUxHQueTJwsPQa2EXrVWYrdw=",-2879712582098388103,-729993328043120832,-835590651398946316,3863855510408768647>()) {
            case 1207765398:
               if (var1 != null) {
                  return Direction.getNearest(var1.subtract(this.R.player.blockPosition()), Direction.UP);
               } else {
                  switch ((int)b.a<"s14r6eoca6kpoc","H34lAGz5F7K+GI98LTQ4K/2AqLsgcguM4gSCweaasIk=",-8753896645912499910,-4085008679127479088,-1044225589496009177,-7303837457289422700>()) {
                     case 108732041:
                        return Direction.UP;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return Direction.UP;
      }
   }

   private int c(BlockState var1) {
      if (this.R.player != null) {
         switch ((int)b.a<"s3g4d3dnewh0si","RgvouNJAktDHOwK7NiYzlAQ6Q5BnK2f0xCfwiaBFkHA=",4065819609076883872,-7854559551618510768,6623966439897786387,2117320049657058594>()) {
            case 1882686110:
               if (var1 != null) {
                  int var2 = this.R.player.getInventory().getSelectedSlot();
                  float var3 = this.R.player.getInventory().getItem(var2).getDestroySpeed(var1);
                  int var4 = -1;
                  int var5 = 0;
                  switch ((int)b.a<"s13estv8amwkcq","eJU3vVxzncXsWO7AvkJmn2gG2O/FHhXvURTpjrtjRN4=",-874124011667668235,4400379839837815831,-5324747542163350852,-5628072154189893288>()) {
                     case -368051523:
                        while (var5 < 36) {
                           switch ((int)b.a<"s3aj74obl28yxr","VC2C430qglA+2y/eGei2BqP6ryHs5aUDGTqKQWOQzg4=",-7661744534824982972,8824028685800827060,-542776008491248156,-7035174395443318196>()) {
                              case 91597433:
                                 float var6 = this.R.player.getInventory().getItem(var5).getDestroySpeed(var1);
                                 if (var6 > var3) {
                                    label30:
                                    switch ((int)b.a<"s2wwyvzdrk6bof","ahwxulGHqlMSxcl6HQbvGX4DjsWf31gErQ/Ij9Qmjqs=",2679649766476484320,-3908543427225994402,6418388151021261277,-8062213073611274286>()) {
                                       case 1769346965:
                                          var3 = var6;
                                          var4 = var5;
                                          switch ((int)b.a<"sttysvnztnt9q","C+bVCB2PPDO3sVaIlsdiXiuNwQry3MEjIk/QY/JYcUg=",6875150302259479250,-5345091039279347322,7871976983993207655,-2695759280986278414>()) {
                                             case -1658042818:
                                                break label30;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var5++;
                                 switch ((int)b.a<"s2tbhalmjfquwh","yitwHc5vHoKlSmvoxUJW5jJZ7c1FBBquhc+SHeSSoIc=",5810759053240289417,-5466299030417690566,6925247481374833182,2002990589401003350>()) {
                                    case -752791732:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var4;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"sypkzeqcwq1wa","SbzikiVkpu5ud53doT/kc7oHL42nQc+CE42dj42VOac=",3794041977399088266,5301103887729400729,2278246092455497412,1419860862978808509>()) {
                     case 1445786077:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   private void a(BlockPos var1, Runnable var2) {
      if (this.R.player != null) {
         label17:
         switch ((int)b.a<"s8qassghrpl9v","98qvdyw6RL337LR0wEhsX8Wm/0H2MT+YizD1/dAOza8=",4261606735534136894,1359650156269585290,-9188034627154855879,9210380760473430661>()) {
            case 1739354634:
               if (var1 != null) {
                  float var3 = this.a(var1);
                  float var4 = this.b(var1);
                  com.yiyiaddon.d.c.b.a(var3, var4, this.R.player.onGround(), this.R.player.horizontalCollision);
                  this.R.player.setYRot(var3);
                  this.R.player.setXRot(var4);
                  this.e = var2;
                  return;
               }

               switch ((int)b.a<"sq2111qdutmxj","5Tbflv++bhPKf1AngCums/WM/uD1Fo6A4Evhi+YE2gU=",-743338777939829766,-8460577204411211245,5716484878617671944,-247418268016167907>()) {
                  case 1503930390:
                     break label17;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var2.run();
   }

   private void fU() {
      Runnable var1 = this.e;
      if (var1 == null) {
         switch ((int)b.a<"s22x984866beml","rs4mTN7jl+dB4QxHJ88FWk/OybzMKahE6FwfNtu8Vkk=",2894912841359479844,8920666648882660123,8026237851678721862,2377704084741597864>()) {
            case -849751510:
               return;
            default:
               throw null;
         }
      } else {
         this.e = null;
         var1.run();
      }
   }

   private float a(BlockPos var1) {
      double var2 = var1.getX() + 0.5 - this.R.player.getX();
      double var4 = var1.getZ() + 0.5 - this.R.player.getZ();
      return (float)(Math.toDegrees(Math.atan2(var4, var2)) - 90.0);
   }

   private float b(BlockPos var1) {
      double var2 = var1.getX() + 0.5 - this.R.player.getX();
      double var4 = var1.getY() + 0.5 - (this.R.player.getY() + this.R.player.getEyeHeight());
      double var6 = var1.getZ() + 0.5 - this.R.player.getZ();
      double var8 = Math.sqrt(var2 * var2 + var6 * var6);
      return (float)(-Math.toDegrees(Math.atan2(var4, var8)));
   }

   private void u(String var1) {
      com.yiyiaddon.d.c.a(
         (String)b.a<"sr48otb250ac6","eNQX4bqOKYul6jQZM1aMEtfeqpfW+wfCe0/iuXj/2R56KXcw",1735465521227501560,-3617001657321155231,-4737580959637668186,-6754647352704626143>(),
         var1 + ""
      );
   }

   private void ad(String var1) {
      this.u(var1 + "");
   }

   private void o(String var1) {
      this.u(var1 + "");
   }

   private static String i(String var0) {
      return var0 + "";
   }

   private static String N(String var0) {
      return var0 + "";
   }

   private static String j(String var0) {
      return var0 + "";
   }

   @Override
   public com.yiyiaddon.l.f.i a() {
      return new com.yiyiaddon.e.k.d.b(this);
   }

   private record a(BlockPos y, BlockState b, boolean cX) {
      public BlockPos a() {
         return this.y;
      }

      public BlockState a() {
         return this.b;
      }

      public boolean e() {
         return this.cX;
      }
   }
}

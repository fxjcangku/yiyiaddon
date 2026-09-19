package com.yiyiaddon.f.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.n;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.j.m;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;

public final class g extends com.yiyiaddon.l.h.f {
   private static final String Cm = (String)com.yiyiaddon.m.b.a<"s2zwyms2yvt0p2","mPJqQF428Sm1w19MHtchnKTp58N8Ivlnh2XH7hcw",2721379121017146203,4691269671133183879,8319103270034995325,5147623237478173380>();
   private static final float eH = 24.0F;
   private static final int sb = 64;
   private final String Cn;
   private final List<String> cC = new ArrayList<>();
   private final Consumer<List<String>> k;
   private String Co = (String)com.yiyiaddon.m.b.a<"s1ycadpo69assc","y65XJK1JGBlBMznOIT6wtQAjg8MDpQfQzBwgNg==",-6595237726388098250,5172460677451910413,6498506672556079239,-1186692693320219205>();
   private final m b = new m(() -> this.Co, this::bg, 64);

   public g(Screen var1, String var2, Supplier<List<String>> var3, Consumer<List<String>> var4) {
      super(var2, var1);
      this.Cn = var2;
      this.k = var4;
      List var5 = (List)var3.get();
      if (var5 != null) {
         for (String var7 : var5) {
            if (var7 != null && !var7.isBlank()) {
               this.cC.add(var7.strip());
            }
         }
      }

      this.u();
   }

   private void u() {
      i var1 = this.d();
      var1.b();
      var1.a(
         new com.yiyiaddon.l.b.h(
            (String)com.yiyiaddon.m.b.a<"s3124tr1ru4khy","PuDkdhoz6ek3ObxVEU+7FtxjcRLbLgjeWusTFJN5Ygg=",-8001273340837229028,9179789617023518437,-5727430743304996600,2382456164148481481>(),
            () -> (String)com.yiyiaddon.m.b.a<"s1oxwqm2ewip2h","RvPmWVaZ3Y7wDmmT988lQGu1Y5EUf5yAhWOGVGQOpprTUdHWDG5oWiX5/01/eKs2EmU=",10690924551522311,5157894143936236374,-8761603074084997627,-38774522791663265>(),
            this.b
         )
      );
      var1.a(
         new com.yiyiaddon.l.b.b(
            new com.yiyiaddon.l.j.a(
               (String)com.yiyiaddon.m.b.a<"senzooitrludk","X5LJLI9B7UjGVL68UzpHKUl8nz6xmArWX1MvUmjbjzqMRImaVjK/PLrG",6973550786965125407,-4438313315782804418,-3593444860433735464,4402041229951822291>(),
               this::cG
            )
         )
      );
      var1.a(new w(() -> this.cC.size() + "").a(24.0F).b(12.0F).a(true));
      if (this.cC.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zxqdk2up7e0b","lzsKu7mbCGbalS3GFpcJmYGHMM0Fs0bPjkweQ90hcC4=",-2626801311722299180,6784628130867133337,3284920272238901691,4985189690425424190>()) {
            case 1236101384:
               var1.a(
                  new w(
                     (String)com.yiyiaddon.m.b.a<"s2cjzcolm2pv8x","reaAWvZLr3lGI7dE5eSMuA3wIquS3S5q5YU/mcAuAIdvOj/2I+jZTtqaNNb5Eg==",4049298621671922333,-8399594388812443235,-6724996056118070671,-1308889487805705160>()
                  )
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3dcz1gsam3xqz","Y5UT9M0W4R9TyO0ZjeAje2DwqMLZIhk9cHtmhvARleo=",-8053820569910686509,2001797426721069221,-2497813979355576449,4152131904589616798>()) {
                  case 1027085285:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Iterator var2 = this.cC.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"shod3ag3483ag","2rMpw0WRayhwk8TsZyfJyO3GRrRq0C/gbBYGBUSD7O0=",-957823807886647668,4308868244150691749,1353180008675490877,8631321845902251535>()) {
            case -844235017:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s23s53e4zc163p","bCssnBxUBB1Ew2gMshXfgmi2YriDQ0gyeJ7zoqJ3nw8=",-1713606825386215968,-4320734049847329807,-6402274419892932462,-462230487383641454>()) {
                     case 2058636792:
                        String var3 = (String)var2.next();
                        var1.a(this.a(var3));
                        switch ((int)com.yiyiaddon.m.b.a<"s2tkkt1vskgllu","zD//l2QYukjARp9R2XIXjBcYYNCTVnuFwT4CwvErmZg=",6978000579355171803,5965878565921524592,4928059040559835331,-7478982452489013257>()) {
                           case -718101956:
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
   }

   private n a(String var1) {
      com.yiyiaddon.l.j.b var2 = new com.yiyiaddon.l.j.b(
         (String)com.yiyiaddon.m.b.a<"s2zwyms2yvt0p2","mPJqQF428Sm1w19MHtchnKTp58N8Ivlnh2XH7hcw",2721379121017146203,4691269671133183879,8319103270034995325,5147623237478173380>(),
         () -> {
            this.cC.remove(var1);
            this.jg();
            this.u();
         }
      );
      var2.a();
      return new n(var1).a(var2);
   }

   private void cG() {
      String var10000;
      if (this.Co == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s2xlbuu730p58t","MfwaBFSpaghizMPWPuBWC9v4pkcHcfFaXq7YJerta9c=",-3590686664962188094,-501737595938705231,6562597283887513803,3692373332028822120>()) {
            case -1458816687:
               var10000 = (String)com.yiyiaddon.m.b.a<"s1ycadpo69assc","y65XJK1JGBlBMznOIT6wtQAjg8MDpQfQzBwgNg==",-6595237726388098250,5172460677451910413,6498506672556079239,-1186692693320219205>();
               switch ((int)com.yiyiaddon.m.b.a<"s20xreqbta5akh","lNOLMZol+3pOoPgO9C59XiMpnIBCThlBfSEehXxmCb4=",5104819244114794589,5881443396093282058,1587043768228398495,5721273231189460374>()) {
                  case 936264308:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.Co.strip();
         switch ((int)com.yiyiaddon.m.b.a<"s2vztcyarq8obr","WbxlHgPyony3tm8Olfefn8qu5SDnIWx6/4U/RBb1iM4=",1100837470675630471,-459595712490358916,3516768970937020334,-8162833150944556087>()) {
            case 1000043928:
               break;
            default:
               throw null;
         }
      }

      String var1 = var10000;
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sx6plns4mgozu","E6BKFcC8rOK31vxpHZo1YAjIaZf2WBKxsHVMb+8C1cU=",-6496863460259742510,1827325274311958645,9046149335285461796,5563190540817861750>()) {
            case 333675838:
               com.yiyiaddon.d.c.a(
                  this.Cn,
                  (String)com.yiyiaddon.m.b.a<"s389skb5fv64d1","BB0Smt+jAwOXxVKqK7r5Bxa8VahGIRgvx83iJYG3AGNoiGC8bSdhIZCDDmQ=",-6703373852687172298,-2849376168552991330,-5433627717724656070,-1822669207930473079>()
               );
               return;
            default:
               throw null;
         }
      } else if (this.cC.stream().anyMatch(var1x -> var1x.equalsIgnoreCase(var1))) {
         switch ((int)com.yiyiaddon.m.b.a<"s16k20gnk79gbk","k2Q0oIWkD3+XqXd2hRVl1pxiKoKwAo/44XmoF10U0gs=",5901557593936539044,1372206098262039058,8340214047673962120,4192022411889031801>()) {
            case -1741416861:
               com.yiyiaddon.d.c.a(this.Cn, var1 + "");
               return;
            default:
               throw null;
         }
      } else {
         this.cC.add(var1);
         this.Co = (String)com.yiyiaddon.m.b.a<"s1ycadpo69assc","y65XJK1JGBlBMznOIT6wtQAjg8MDpQfQzBwgNg==",-6595237726388098250,5172460677451910413,6498506672556079239,-1186692693320219205>();
         this.jg();
         this.u();
      }
   }

   private void bg(String var1) {
      String var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3e6f64x815id2","rPoKm2AHNsiSZ11rnLd2NVmTlvuVtfC73caLWCDMJW0=",4030220845731225972,4258412054211823327,-7304360483582659695,-2202085862496231794>()) {
            case -104469819:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1ycadpo69assc","y65XJK1JGBlBMznOIT6wtQAjg8MDpQfQzBwgNg==",-6595237726388098250,5172460677451910413,6498506672556079239,-1186692693320219205>();
               switch ((int)com.yiyiaddon.m.b.a<"s2sbr07awtzqmb","2FHlZz/95BLDMncsAqMPbmVF/sh/r5WQNTiRYydTuSU=",-5736381280754420595,-2311846987859414898,6892099293948462898,-8357327711227463453>()) {
                  case -1281387376:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s29q9u66kal3gh","f20m40ZK2p//aUJIHajBneQHc9Nyr4mxL6lcBaPwsZM=",3164519203426423886,-15257101890087133,5408311340059623299,7625301207556190609>()) {
            case -329522639:
               break;
            default:
               throw null;
         }
      }

      this.Co = var10001;
   }

   private void jg() {
      this.k.accept(new ArrayList<>(this.cC));
   }
}

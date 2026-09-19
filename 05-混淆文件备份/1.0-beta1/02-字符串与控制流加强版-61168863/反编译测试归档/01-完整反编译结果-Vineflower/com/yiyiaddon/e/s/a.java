package com.yiyiaddon.e.s;

import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String Bt = "esp_test";
   private static final String Bu = (String)b.a<"stlnhepmwpwpm","s3D3rm0Z9jpw5Q82ZqhfSf+xIMDDxsWXswxEEEhz",-2757661877458795516,-473408263497243139,-1724376072664547766,-3339590701212210818>();
   public static final String Bv = "esp-test-player-box";
   public static final String Bw = "esp-test-player-box2d";
   public static final String Bx = "esp-test-crosshair";
   public static final String By = "esp-test-entities";
   public static final String Bz = "esp-test-rainbow";
   public static final String BA = "esp-test-text";
   public static final String BB = "esp-test-gradient";
   private static final int rB = com.yiyiaddon.l.g.a.b.q(0);
   private static final int rC = com.yiyiaddon.l.g.a.b.c(0, 48);
   private static final int rD = com.yiyiaddon.l.g.a.b.q(1);
   private static final int rE = com.yiyiaddon.l.g.a.b.q(2);
   private static final int rF = com.yiyiaddon.l.g.a.b.c(2, 48);
   private static final int rG = com.yiyiaddon.l.g.a.b.q(3);
   private static final int rH = com.yiyiaddon.l.g.a.b.q(4);
   private static final int rI = com.yiyiaddon.l.g.a.b.q(5);
   private static final int rJ = com.yiyiaddon.l.g.a.b.q(6);
   private static final double bq = 48.0;
   private static final double br = 8.0;
   private static final float es = 12.0F;
   private static final float et = 6.0F;
   private static final float eu = 28.0F;
   private final Set<String> az = new LinkedHashSet<>();
   private final d V = d.a(3, 255).a(true).a(0.5);

   public a() {
      super(
         (String)b.a<"sylg9hvswlmmz","7JpqT4wiVg2tGHuKtZlEbUZDjRwlJJwR6FlzPCNWJyUY/O5PZaXRTs8ggkg=",-5645614681578279168,-5065877508742634823,-7118488371385461040,9155641431677666880>(),
         (String)b.a<"s1uu447ga8lj3","ovX08unNc4TEu60xbXwKe1A1pufNnLXSqGDckm04udTKBdQAaso=",-4595601957525749608,-8895282701080512810,-4086679647550124614,7033373436605027083>(),
         (String)b.a<"sev3vj4v8j054","n4TwLeNdBkXxAGQcnYun4X/fU4YoIAP9CKkIJ7Z2sY/BjbOGn6M=",7637772856193768071,-5782115740062866800,-4865647599162002027,2773862321439522380>(),
         (String)b.a<"s3qrl4j2y929y1","GfwZJXAODViBzrxypRkkufgmVrXXW8orp8DoBg1KomDRue4/cngnHqS5uCtn0X3U8OWIkA8VGguSUw1iHfGpuLyB8EkakCO/KFh3NZsujSCdL0k2llE=",5215641058487016316,1409114502047723662,-4030965466388387935,-2520816131212139464>()
      );
   }

   @Override
   public String w() {
      return (String)b.a<"stlnhepmwpwpm","s3D3rm0Z9jpw5Q82ZqhfSf+xIMDDxsWXswxEEEhz",-2757661877458795516,-473408263497243139,-1724376072664547766,-3339590701212210818>();
   }

   @Override
   public int i() {
      return 900;
   }

   @Override
   protected void n() {
      this.jb();
   }

   @Override
   public i a() {
      return new com.yiyiaddon.e.s.a.a(this);
   }

   public boolean aD(String var1) {
      return this.az.contains(var1);
   }

   public int dj() {
      return this.az.size();
   }

   public void bb(String var1) {
      if (this.az.remove(var1)) {
         switch ((int)b.a<"s7qssoicdo94i","+hBlkXz6ZP0zid4YqffIvydf8OKDbEQC0KrofJgF8AU=",4129767818214126669,-89788048028644068,-3642487392995887608,-5160188667497708658>()) {
            case 1346176717:
               l.l(var1);
               return;
            default:
               throw null;
         }
      } else {
         l.a(var1, this.a(var1));
         this.az.add(var1);
      }
   }

   public void jb() {
      Iterator var1 = this.az.iterator();
      switch ((int)b.a<"s3baffj4yck5cz","yXsp2ilcP35KRclCC0XMgBNPSIiztqkGPnT+l3Zkcm4=",-5466135509634774311,287658078063521392,25553379103425091,4335597704930084171>()) {
         case -456667794:
            while (var1.hasNext()) {
               switch ((int)b.a<"s2n3u04m3i7ox6","WdUqg2Wd2dJuofhOgoA7B/6p1kW05Slpz1amKK0A8xI=",-2416945462359231821,3408075615757176192,7076771453573457923,-8320467103073075433>()) {
                  case -1934723800:
                     String var2 = (String)var1.next();
                     l.l(var2);
                     switch ((int)b.a<"s2up49kueq2q90","KbYLSRSReTDz94CKjxka4s6Ai3+q2C3AdByUm7Z9NNU=",-5218909915273995431,-7875445812214436547,-5529588671753101566,-6569731320005001817>()) {
                        case -2039853810:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.az.clear();
            return;
         default:
            throw null;
      }
   }

   private l.a a(String var1) {
      String var2 = var1;
      byte var3 = -1;
      switch (var2.hashCode()) {
         case -2129956177:
            if (var2.equals(
               (String)b.a<"sw1a64lp6r5sl","1HQB0hjR7/lUj60RROawueBAAwD+M/bakY80fIR7vyrdzJD/TShVfjmqweE9dtsgBH8jLCkxl23qrjJf7u6GAJNJ",-1575164298253734617,1465917006700378008,1563472390191739992,7821034305304999460>()
            )) {
               label82:
               switch ((int)b.a<"s1jk76kr6lc81b","1HjfArvmz1qxabACT3lD88t021ID4SK5+DV4bD7X7Mc=",6106855642863040094,3705438604171421406,-5649533067503000333,4658456456429951473>()) {
                  case -938584135:
                     var3 = 0;
                     switch ((int)b.a<"se0y0vpwm9wry","2nFBvpXGaJCQoYw3KGpvlroI3vrFkrDLT1DubLYKd10=",-3500166496050401230,-3821830473287564322,-5671241708453401790,-3426974541282991477>()) {
                        case 984147940:
                           break label82;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -1115443738:
            if (var2.equals(
               (String)b.a<"s195mk4rr52i2t","MdVFGJcF8eJMi0K1KeKH7ehTBrZHxuQzFFOoBWvVJxcEMeYf0sAMFdUI364NHZyHS1afO0zm6ow/1w6m",483603015131345413,-5705362359790598754,8490558339927178721,-4731795827653561858>()
            )) {
               label88:
               switch ((int)b.a<"s267e8wzj44j02","0LQKoicVhb4BiEWdV1e1hnhGeywzzfmtaPpfwrQEdNQ=",-6667785044061501948,930892993721215813,3471476476183243202,4233731132004237504>()) {
                  case -960176220:
                     var3 = 4;
                     switch ((int)b.a<"s2jlbk7ty5l4tw","hiLU8w44laoIKhhX+ahE/pVmogq8PzonGfovixyfz1c=",354775391147453985,1278694657306898459,2134839458316055187,-3157142308893946040>()) {
                        case -647200173:
                           break label88;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -400883630:
            if (var2.equals(
               (String)b.a<"s1961tluc0bu1m","2sN2lQrXUS9tZFcN+vyHxykSqNhJPD4gxR/H8XG2KKLkzc9ujW1PWGqp53/zKFPCP+4aN5Ond5i8GTxhhOzx/w==",8314432262322276635,8805244795723536353,4328219921097043381,6804718546668590534>()
            )) {
               label103:
               switch ((int)b.a<"s15h7oau0c692p","EebnqrA24/1ZPIz3i2uhedJn6mWm7YETeuT7jJoTnmY=",6303242453738154477,859856309408485702,-4342838408226550420,-3672016813508070426>()) {
                  case 64737914:
                     var3 = 2;
                     switch ((int)b.a<"s1hhzb03a5716m","SU6EEzxL+cy88/dQRuyjSLvijdDgC0khv63YTimXvNg=",-1451907484249672365,526610236205668495,-613001757243057513,4066745474950111338>()) {
                        case -968162964:
                           break label103;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case -245470976:
            if (var2.equals(
               (String)b.a<"sgsazg3zs0ckt","ocAMc6KNdu+oANRGWZgcKoIU9waGfwVvOlNsZp+8CHS3qi88PXjya0UCn4a0GT9mNA5lEN5kuZmqSxqPrDU=",-5009274997119934945,-3546877087813217777,-9048726084012302734,-5770892700137113940>()
            )) {
               label79:
               switch ((int)b.a<"sn56kxws93mw","oF5Mc1LwHWBkJ0Bzrwc2iHPQ502E5kG/Mo/LhB/B79c=",6381659370424780072,1528679929027589617,8279764567790987139,8963219256369141593>()) {
                  case 764784229:
                     var3 = 6;
                     switch ((int)b.a<"sjriy2j4eyef1","O5iLio+aUhDpNnmDaxUku8Dz6mu+KhR710RSaEGbkOs=",8401128375965161341,-1722688355120291068,-5926547811483431545,4765646379427975488>()) {
                        case -904590531:
                           break label79;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1516117757:
            if (var2.equals(
               (String)b.a<"ssnoripnxx3pr","Owq/lnFGH0qNkrZBX7ypD/jO63iP1s5/sgtcopvq1++R6dY3/upRn2JpjC0tiVw0oFbpbObl",-1975644020118535982,-385157610116502203,7755943869938458233,-2482289916901245680>()
            )) {
               label96:
               switch ((int)b.a<"svpb6thffaye0","usOrAbZTGJDeUJgu/AEkyIRuBpXhwr3m7pzKieGxR8M=",-559577071437154710,6156453233295844099,5097094450248046352,9038255638912986323>()) {
                  case -1466601490:
                     var3 = 5;
                     switch ((int)b.a<"sftszb25z58lu","X8FXnRWFy4/ZvGFxIUQrzXbHbN6ig++zIA+yH8PgxfA=",4779576233727567964,6248867638398223135,8842546763348158884,-5767847960927322926>()) {
                        case -404860434:
                           break label96;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1811515745:
            if (var2.equals(
               (String)b.a<"s2v05xvnm6b2q3","5TA1bi54TwbQfDXtCp9KRVCz8YdPmjIgkttlrgCdn7Z0CqjaeG8iyj6zWdgW8FuRrAiGeaDAM8eykyeYEsHhDXw5oacX2A==",5104079607685650000,-4613520699187006482,-1523019888677699238,5450784428704022407>()
            )) {
               label93:
               switch ((int)b.a<"s17gibhsaz3k18","4NptU4nly0dfB4a1Xsq/pXHVVNw3lAndVy7S54zTiTE=",426433909150972856,-6953317803933480953,132754256211367855,1585717535474989304>()) {
                  case 2109414779:
                     var3 = 1;
                     switch ((int)b.a<"s1d77x71j1f80","C225TJM7yDpA1O5DSmhdWXzm2FAM87l3bN37ZYaSQIA=",4549931706867766762,4008696867673215710,6465597918343506427,-6460278328205725089>()) {
                        case -1791033762:
                           break label93;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
            break;
         case 1857730961:
            if (var2.equals(
               (String)b.a<"s2lt0ba486x7h7","BQu6VsyH63lFg9qIyRd7atO8HPacQajaVOPgnaWRjoE65mR68tOzvO6Nr9kipnBC9GNRO4uIcjrX/RX8a1k=",1847378854764940300,4767302000408471784,-7329898722035102868,-3686186327265319957>()
            )) {
               label85:
               switch ((int)b.a<"s3udtul0wztv1","u3A1AGXbhElPBcCvh+gw1AKsNsnBYcfJULt7RwL+YXo=",-4084907147551218034,-23755746184650029,-4013600819159993151,-7544026543430906119>()) {
                  case 94970765:
                     var3 = 3;
                     switch ((int)b.a<"s10a3v5wtgj9i1","6mujSAbl5vUyz+8LlqjDebI2Yooy7T16gSU3uOH6h/c=",6305095760951619455,8604865855902915887,3296682182116401807,-3253510896105741945>()) {
                        case 1792317537:
                           break label85;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }
      }

      switch (var3) {
         case 0:
            l.a var10 = a::e;
            switch ((int)b.a<"su20qdv2x94ty","HyrYxefgIUgEKjuQeZbVH6AYQ4F4zY/MxeHyoZMQBKo=",7146892343547231286,8590652978565279140,-9173660585073547504,9066036465499962233>()) {
               case 1842265428:
                  return var10;
               default:
                  throw null;
            }
         case 1:
            l.a var9 = a::f;
            switch ((int)b.a<"s1y3nevjrv3vbk","oZ7p0AWKMJhX2BBaq7PSM8PbQ+I3iktIMJ58YDs0Ows=",-5853621187215591955,9098093943368911697,-872206174357665306,6827827531082830572>()) {
               case 534785642:
                  return var9;
               default:
                  throw null;
            }
         case 2:
            l.a var8 = a::g;
            switch ((int)b.a<"sko2qby2vv8p7","G0X+NiRsL/3YzEWFlc5V7CjHFNdQ8O8i8mTmZTLMd9o=",-533830831545421765,-1154950533086445022,2132248720013975922,-1318020699671189286>()) {
               case 1017784132:
                  return var8;
               default:
                  throw null;
            }
         case 3:
            l.a var7 = a::h;
            switch ((int)b.a<"s2syl7ap9gsv5e","seh7pWjjiE0Yuy9RN+IhbbKjPeFpUGpMgabva5uizno=",-5105169961272738704,58299473310111656,5451006108633324534,6895408094300598239>()) {
               case -908178948:
                  return var7;
               default:
                  throw null;
            }
         case 4:
            l.a var6 = this::i;
            switch ((int)b.a<"s2h7rdhubnoi5n","fKHbAbRnNN66NDIbbd6IrowL7mQMRVY4xkH7Vg/Juz0=",5289477004314076370,-2226218762229233209,-2970039412228445708,1979453987857134587>()) {
               case -1652119088:
                  return var6;
               default:
                  throw null;
            }
         case 5:
            l.a var5 = a::j;
            switch ((int)b.a<"s2n7088p5xjxj4","Euw0x/yVtKgjsbvfTgA5Y+UfOl5jfWIuwYxVjgXzruw=",5442590120219627303,-5036522694194094674,-3454977624402909235,6426573009729893621>()) {
               case -536312709:
                  return var5;
               default:
                  throw null;
            }
         case 6:
            l.a var4 = a::k;
            switch ((int)b.a<"s3g5em69wt9l8n","8nNbKHnea37aRpaApFVOmLXm7yL+ID9bM18JLxV1wX4=",3496275454648828244,6335070435436963247,1795140178556967119,-812627168739037311>()) {
               case -546002827:
                  return var4;
               default:
                  throw null;
            }
         default:
            l.a var10000 = var0 -> {};
            switch ((int)b.a<"s2i74chfr4yrd0","dga4KEJeoYRNX0wGOtJF0RANzFAnWzt0Jpymf3y15fA=",2681637661457619741,-316442519646728254,-2051125450433874389,-1000876920970422966>()) {
               case -1121627799:
                  return var10000;
               default:
                  throw null;
            }
      }
   }

   private static boolean eS() {
      boolean var10000;
      label29: {
         Minecraft var0 = Minecraft.getInstance();
         if (var0.options != null) {
            label22:
            switch ((int)b.a<"scorj11jlzs7a","wrRVqheETciUiznhRyAic4uWzNyJHDSiHQiBjEzo7B4=",6820589862090692337,4971040956973971097,-2983419972119056059,-471353898199264362>()) {
               case -119613525:
                  if (!var0.options.getCameraType().isFirstPerson()) {
                     var10000 = false;
                     switch ((int)b.a<"s2wizd6q4xe7dj","Tcfvj66qqtXfGeBimfIkKzgTzWjg1q3/ABSK5QewUl8=",7600514175160300066,-5084727399263708892,4577341384721167582,1926804641541420670>()) {
                        case -1623505132:
                           break label29;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)b.a<"s2q6rbjgxpv27q","U/nciPxuDTRDhq2Ons+NcJChLFQj9eidjg9CJv8UOS4=",8794948881704602063,8544335807674719186,3149208575795496035,-2354637065983349253>()) {
                     case -1232084464:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = true;
         switch ((int)b.a<"se5cvfpw4ku4s","kTWf2245TQVqtsTckOrInYg/bsywvJGHk11hC3/d85Q=",6676496499482535673,-5731519379882024083,5691231719029919610,8476573633195478468>()) {
            case -766288109:
               break;
            default:
               throw null;
         }
      }

      boolean var1 = var10000;
      return e.a().e(var1);
   }

   private static void e(f var0) {
      if (eS()) {
         switch ((int)b.a<"s22k3lc10pqge4","bZmv4qhV/yo4QaEjoa49EI6fx1zx8U/M0WOneC3KWRM=",6285288357158322538,4588206203558594134,-1883999900659086169,-2504317476780241696>()) {
            case 1122304390:
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1.player == null) {
            switch ((int)b.a<"sfypthn8r3zxb","+btEaPaMkewt4txnrhzyGiHJnXKz0luF5Ugxooa3fWU=",-789947349988718487,3724409713035643774,-9072681929079152565,-5743386410972103728>()) {
               case -1534565824:
                  return;
               default:
                  throw null;
            }
         } else {
            var0.a(var1.player.getBoundingBox(), rC, rB, j.Both, 2.0F);
         }
      }
   }

   private static void f(f var0) {
      if (eS()) {
         switch ((int)b.a<"s1rj2jxyw7725v","jFOGA8cPn+pByIPLZ56SJYQOW6igRoSOIWSLe9RFhOs=",-1480216971115447659,-4985476811919410232,7090432471323712684,7854078350217623983>()) {
            case -1714876885:
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1.player == null) {
            switch ((int)b.a<"s2jgns6fu7tvfw","RLRLoX2vcKnlCsW1fBaxKF911EjHAnJk0Kd+Ph2vhqE=",3869473530510262520,7941026004762212531,-1409687036751290169,3840005492228901811>()) {
               case -1548395679:
                  return;
               default:
                  throw null;
            }
         } else {
            var0.b(var1.player.getBoundingBox(), 0, rD, j.Lines, 2.0F);
         }
      }
   }

   private static void g(f var0) {
      Minecraft var1 = Minecraft.getInstance();
      HitResult var3 = var1.hitResult;
      if (var3 instanceof BlockHitResult) {
         switch ((int)b.a<"s39lf8ot0rfj3i","DOCCwIgDX6TsykhchGu1ffSCyExIQS/El/9puuPezqw=",8355665921929666935,7922173719498668054,-2396986389756607251,1444024371861593495>()) {
            case -1219408543:
               BlockHitResult var2 = (BlockHitResult)var3;
               switch ((int)b.a<"s2vigsq4gdpibw","vOIwiHC2Oc95X32MNo8TOvzEM9WqCGrC0RwYspPscZw=",-1598303513569185406,1928837668530381471,3002922686499400254,6647938499396073476>()) {
                  case -1839388370:
                     BlockPos var4 = var2.getBlockPos();
                     var0.a(var4.getX(), var4.getY(), var4.getZ(), rF, rE, j.Both, 2.0F);
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static void h(f var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)b.a<"sjncig93zcmo2","mkc7+As1FwEJ9G1epIHPa/+A5guE8iyH+as659vV9gA=",-342275920414736980,1079955717021904934,2298486844821177414,-228392644224421961>()) {
            case 763466962:
               if (var1.player != null) {
                  Vec3 var2 = var1.player.getEyePosition();
                  Iterator var3 = var1.level.entitiesForRendering().iterator();
                  switch ((int)b.a<"s27on5ib2tgri9","mlTeAxfBZEvxbnGCKGGPmS78ZPpR+6Hc6vmVT/Gp1ko=",-4183255238069450304,-3681555177956190640,5265020142448814950,6011617995914241630>()) {
                     case 1595624409:
                        while (var3.hasNext()) {
                           switch ((int)b.a<"s2ve39e2v1xmcd","mK55VEvVS3McUswd//JS68KieLqfaSZodktKN/f9MaM=",2169235515138273180,5121993857773851758,-6907183910079151652,6734109652446737032>()) {
                              case 2033185107:
                                 Entity var4 = (Entity)var3.next();
                                 if (var4 == var1.player) {
                                    switch ((int)b.a<"s2mshl9w4jqis2","ycj0obm+XYygoDvmgidFllVfS3zEkvnH0BOVypWpzHc=",-6287806832177769816,-6514793550730658061,-2815028697184307151,4312753205325160677>()) {
                                       case 144893150:
                                          switch ((int)b.a<"s1llhy6ppx9n0d","zxKp1oJZ8qiKEgPnUXOXhyXqNH5gS2DsT2RIsMJ52s0=",-7109602820435849084,-3517075215645059674,6760082484776452564,-6485970924591334179>()) {
                                             case -731562060:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    AABB var5 = var4.getBoundingBox();
                                    Vec3 var6 = var5.getCenter();
                                    if (var2.distanceToSqr(var6) > 2304.0) {
                                       switch ((int)b.a<"s2g997vcjox6vq","hSwbG+goG1jy1+17ZGf0f4BJfIbydQw8lNOGXO1ikUE=",-355327701077519492,-5359661367362035992,8170521378796691792,9161023114731297886>()) {
                                          case 721515767:
                                             switch ((int)b.a<"s1sno5c7979m7x","mgGCm4s40kT6ouZFA2B7+60n8gHa/xZtz2NvczHdlRw=",5537147492703904764,2730348820846848850,-5050533732377201492,-6659688350738216002>()) {
                                                case -1072504113:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var0.a(var5, 0, rH, j.Lines, 1.5F);
                                       var0.a(var6, rG, 1.2F);
                                       switch ((int)b.a<"s337kiel0f6yon","AFnb8Q9u42jqiqafibHByGLypOY63Yk1QgHOtmp3xaU=",-3395166371414996044,8637076770885530406,-1939309043637953882,-8065776393879917564>()) {
                                          case -725191063:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
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
                  switch ((int)b.a<"sl1z7ihlax0x6","hHh0KsFx1gvRx7sRr7dt/Wi898lEx7ODrmhIzLrFjww=",-3394667958738508369,-2440867531226044725,8220382448725120734,-9087149356074471048>()) {
                     case 1736405042:
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

   private void i(f var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)b.a<"s18zg5w532cw66","WEOKrTG+bRK9YbuLGkXOL0wTpCWrS3AZgzIOlVmfrZk=",3239623580670373434,526721789447703909,3213346726416470243,-5512768945992873841>()) {
            case -1090378913:
               if (var2.player != null) {
                  Vec3 var3 = var2.player.getEyePosition();
                  int var4 = 0;
                  Iterator var5 = var2.level.entitiesForRendering().iterator();
                  switch ((int)b.a<"sbkhiltysn0pp","fj2YYyLntvYWsP5x3FYBpzuMt3GcKi8Ytohfeyg/WJs=",-2255125328813915739,1772203049037690501,-3926458370394009702,4455283743340958208>()) {
                     case 376269719:
                        while (var5.hasNext()) {
                           switch ((int)b.a<"s3qy76hiayhxuq","LJV8kK6eYOyr0JEDALFCmhooqPVsofTnP+YMlCtZq2o=",1056387232549656077,4113883161780764335,-4308810739401975352,-3060004712839149700>()) {
                              case -2034278437:
                                 Entity var6 = (Entity)var5.next();
                                 if (var6 == var2.player) {
                                    switch ((int)b.a<"s1mhne49rl2c68","bUHwPB3pzWaoc4Yr6f7WVGbMow2+P5k3UCE0GdnRtJM=",-5261367876478328819,303886383837541124,4120339464377983452,-2205630685781103367>()) {
                                       case -1942654596:
                                          switch ((int)b.a<"ste31i84f8y9b","Wduy6hWBsOnjeR9z0JwU25QVyqMYFLjcHb3My67LNnE=",2323807194388752364,-4086969221011690112,-8210165206549292166,6545571148241853911>()) {
                                             case -2112997278:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    Vec3 var7 = var6.getBoundingBox().getCenter();
                                    if (var3.distanceToSqr(var7) > 2304.0) {
                                       switch ((int)b.a<"s2fhjpjuth89sh","kZAjglrE9u/xI35IDS0kcdzFob4XyEFhm7FT63ZqpxU=",535528027179175412,6793339815635899498,-6069383070519240106,-5164770408353698655>()) {
                                          case -1003395853:
                                             switch ((int)b.a<"s1sjj4rlo54c33","+xpuspHSI1uAyoRPcc1GXuaLz376DdqF5QSas8WieE4=",7516879179905976457,3729927019270490129,828582015845454937,-414205422112426182>()) {
                                                case -1131517825:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       this.V.b(var4++ * 0.12 % 1.0);
                                       var1.a(var7, this.V, 1.5F);
                                       switch ((int)b.a<"s3okp5l4505xbf","PH/9JkkOAs22+Lx+IPAXhRUE1k4XZoS0la69kS0rmgs=",4213206944053097454,-3440610480639524866,2449482309296010408,-8967938403513665718>()) {
                                          case 356410025:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
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
                  switch ((int)b.a<"s3jhigz9t3wxr7","7kLvZNcOgCft/WOmZ6+mJP2yk7NH4yX6ji7e38cKWH4=",-8955410187426650140,2935501067631266487,-8972194805868004029,8157802632350231022>()) {
                     case -881801600:
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

   private static void j(f var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)b.a<"s288po61ycgz62","/+u32GVD6JIeS5+vx1Iq9B1rVCfl2Wy58/UTREXKb0A=",7086600152605727840,-5881472714466783114,4442761227838967842,9028121132429730533>()) {
            case -1818038194:
               if (var1.player != null) {
                  Vec3 var2 = var1.player.getEyePosition();
                  Iterator var3 = var1.level.entitiesForRendering().iterator();
                  switch ((int)b.a<"sz65hclxkwtyy","UO+A/BzlRiHl8ZI4pWgOywY/+OUjuvSUK6b6IDK7d7s=",-649863076876301022,-8970320834480435961,5101739610569861638,-5872453906959326080>()) {
                     case -753400844:
                        while (var3.hasNext()) {
                           switch ((int)b.a<"s1rzo1ks6tc4im","uzcJ5zaNDK0BrHQEeC9Pn9OZShfOvgkjZxflKwnZ97U=",7153030904941124623,5559089019436808727,4537059445384192383,-7410846904101902277>()) {
                              case -908321219:
                                 Entity var4 = (Entity)var3.next();
                                 if (var4 == var1.player) {
                                    switch ((int)b.a<"sqqdo3gopm16j","o8yj6rkDApncJoqEvbJySTKUZKACz0XRnMLQZLSwLwc=",5051110817073305812,-3159897731223585187,-4953833938524461432,5287160527584552031>()) {
                                       case -1641767609:
                                          switch ((int)b.a<"s3cbo9b76635ke","OtkVthOpu7dJ55n+jBnatzl7pD6zDd7fgO2p03jsQyY=",4531537406998059723,-4803950918739878715,-8629328403543260144,-3302544895455816715>()) {
                                             case 1858013569:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    AABB var5 = var4.getBoundingBox();
                                    Vec3 var6 = var5.getCenter();
                                    double var7 = var2.distanceTo(var6);
                                    if (var7 > 48.0) {
                                       switch ((int)b.a<"s2ra0ws9hqyw4c","pBoutG+BndFjR3jCrVon0jj30p1+rVeFSWepu+wB2vw=",-174981371408235800,4971215381016210025,8286855980588383524,8622639879165501584>()) {
                                          case -1311349613:
                                             switch ((int)b.a<"s1our5j4ozjl03","CyuXYGZyec4s+bY39H64tR2cCUYt1MLfMxajmDHu1LY=",3723170604933408729,7402758893879005856,-6764778416490516784,-7390956035642917878>()) {
                                                case 224028330:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       float var9 = (float)(12.0 * (8.0 / Math.max(var7, 1.0)));
                                       var9 = Math.max(6.0F, Math.min(var9, 28.0F));
                                       var0.a(var4.getName().getString(), var6.x, var5.maxY + 0.35, var6.z, var9, rJ, 1.0F, true);
                                       switch ((int)b.a<"sg7wntrm88y9s","tpNkp++VohNXCYy+4lOfjEpmTgZmqlbKDWDP9o8CH/U=",1443180405089707842,2404973226513877817,5945229844826545789,4692781705556590745>()) {
                                          case -351670432:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
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
                  switch ((int)b.a<"s29yynxqybgeeg","sBODcCuTgaVkvELbbMw0VvZ/aEEcRh4jrPlwfs1SJ0w=",-7615469000241670403,8183686333719983542,-6273684715579683489,-5418801482854875924>()) {
                     case -1316494950:
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

   private static void k(f var0) {
      if (eS()) {
         switch ((int)b.a<"saj26bgab6mb2","10hrnMXeLBqTTf7n/VcszvKNwEi8djIey2oIY51D1J0=",-5552126774217434061,1216383754476524288,3763975737319011414,6880466585034160818>()) {
            case 581008994:
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1.player == null) {
            switch ((int)b.a<"s1eam9se4cf5zp","QC2L8DoS3PCPSQDJtRRm+SQ6N2jGsFSjXQC6qyED/V4=",-434499510156517778,3003513648007350529,-7243583705282326993,2878028687810647041>()) {
               case 1888083545:
                  return;
               default:
                  throw null;
            }
         } else {
            AABB var2 = var1.player.getBoundingBox().inflate(0.4);
            var0.a(var2.minX, var2.minY, var2.minZ, var2.maxX, var2.maxY, var2.maxZ, rI, rG, 2.0F);
            Vec3 var3 = var1.player.getLookAngle();
            Vec3 var4 = new Vec3(var3.x, 0.0, var3.z);
            double var5 = var4.length();
            if (var5 < 1.0E-4) {
               switch ((int)b.a<"s3brsa2ct5i77m","0oQlEexkRpTfXYkhwxrjr6bQPVrdBBS4qwNO+ioBlH4=",3874430162353650264,-640557031106107183,-4018632492448365181,4604351632046696698>()) {
                  case -1556769167:
                     return;
                  default:
                     throw null;
               }
            } else {
               Vec3 var7 = var1.player.position().add(var4.scale(1.5 / var5));
               var0.a(var7.x - 0.5, var7.y, var7.z - 0.5, var7.x + 0.5, var7.y + 2.0, var7.z + 0.5, rI, rG);
            }
         }
      }
   }
}

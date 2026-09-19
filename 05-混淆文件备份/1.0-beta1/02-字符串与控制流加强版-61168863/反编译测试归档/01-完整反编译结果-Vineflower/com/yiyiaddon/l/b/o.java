package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class o implements g {
   public static final float gz = 6.0F;
   public static final float gA = 64.0F;
   private static final float gB = 11.0F;
   private static final float gC = 6.0F;
   private final List<g> dd = new ArrayList<>();
   private Supplier<String> n = () -> (String)com.yiyiaddon.m.b.a<"s2nxydz2fyox2p","rZAWCQK+fLSEFPrpUAWEUb8/zikw4Ao36ernag==",-9108839209386943458,1966972879753420955,1823404272631630669,-5385599937493728873>();

   public o() {
   }

   public o(Supplier<String> var1) {
      this.b(var1);
   }

   public void s(List<? extends g> var1) {
      this.dd.clear();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2yezq4nfqmohy","3URKs1PSt7gCSWqGhyclb3kBMdKL278WXdez/I4y6Do=",-7510434198871788458,-4470965585836715520,7949750544432779084,6353808910798553755>()) {
            case 1426823509:
               this.dd.addAll(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2i27geg01iltf","7X86TdWjEDPYbXdGMgSP0CV6Qt0QKe+S+JNTzESOqq0=",2813518494184567747,-1618050952974330206,-3746403674868828166,-6872196523052286542>()) {
                  case -631395445:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void b() {
      this.dd.clear();
   }

   public int a() {
      return this.dd.size();
   }

   public void b(Supplier<String> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ffnnhe1qi0v7","SiBPcNQswZaJdd3xcFsMIecBqtvz5bHmBfd8nS7js8E=",2940645011879761405,5960703431791501496,-3600267752543978453,7401433582611878607>()) {
            case 571691229:
               this.n = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s3rhh9pq9ffz92","4oFYelRrHh5jtVkLT0txKeGxRJt72yDr+/lpvcib25w=",6556115052213888568,-5633360548890104599,-4269340129453697128,2197203171551410742>()) {
                  case -1574986963:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public float b() {
      if (this.dd.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s5ohk516lt10d","5fpHdIHLZj4nc2k9K0dsbfJyIY6AtYafHKeVfPrkrnw=",-2482268595836064349,-5516865274841104295,-2414886677127291547,3677333742139357934>()) {
            case -289960084:
               return 64.0F;
            default:
               throw null;
         }
      } else {
         float var1 = 0.0F;
         Iterator var2 = this.dd.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3noj2lvmo3x3i","jzVCVYiGf45t11suo4lVMsWEn4MYbcVDa4cCe6BHgw0=",8232459919339625941,7068744934367145537,2696027794274793435,-2560014895002701054>()) {
            case 677427638:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xqtx7247rjqh","RUzlHhdCEd533ov95cJe59diWezdvB5+mOWb90l7014=",-6852109858075789513,-6125829021561714670,-8338106086914187330,-5244341801799693247>()) {
                     case -1462258332:
                        g var3 = (g)var2.next();
                        var1 += var3.b();
                        switch ((int)com.yiyiaddon.m.b.a<"seb8erorzpc54","HclyLn1svIm3oDSRaQJOL7eQedarwrMMQqtcTK8SU3k=",6102412069650021012,1692013046524020998,843573856206198672,-2742068465791378624>()) {
                           case 2049829901:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1 + 6.0F * (this.dd.size() - 1);
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(float var1) {
      Iterator var2 = this.dd.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1kvsoyr15ltam","lGcz4KEa1osVjtfAcU+WilLJLw/RmLhNvZykBAHLDB8=",-3356887048507198909,2380075283902899644,4754779587552621469,6686591354252835863>()) {
         case 1592392884:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sxs82mrfll6dq","9s9UOjPDPkDn1+VPyzkpjeQfYj7sLOcJAzRlcK0F7F0=",-5163686595728574877,-5902894551089828100,-5605590709423785517,-5564972726009437989>()) {
                  case 1575690919:
                     g var3 = (g)var2.next();
                     var3.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s1hc7uuj0s6kwj","SStO1h7PYNCG38q4LazExZife99H+YyijtOhqRNT52A=",4452732358035106403,6900106569363926736,-6979729020903774820,-7637907696016146586>()) {
                        case -979610266:
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

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (this.dd.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s10uqjvyiubn6f","noyjNoM9nBXqOhTJWw2MjT0VOZYshv0qlf2wM5P+Mm0=",-6052422189142524778,1316773769684338840,-492468586196078304,-3340133661900071915>()) {
            case 1255245413:
               String var11 = this.n.get();
               if (var11 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nogcpqqhtbkg","YHu3qOieCah4PzAxndgfwDaJ08uN7eg5AW/bOZ18gmI=",-7693932419103683720,2773910187251522231,-3432401472056966625,84810521781885126>()) {
                     case -1713840413:
                        if (!var11.isEmpty()) {
                           com.yiyiaddon.l.g.d.a(var1, var11, var2 + 6.0F, var3 + 32.0F + 3.96F, 11.0F, com.yiyiaddon.l.i.c.a().va, var5);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1m84qo59l3503","8oKsQOfnpwq4FYpw577adr1EE2u0+1SoVvkeYD4JZ9E=",4412287611364708654,544245816440693574,317184151471467052,2273820853927190544>()) {
                           case -1657196611:
                              return;
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
         float var8 = var3;
         Iterator var9 = this.dd.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2rcqt6glrg7eo","uwJVDuzfButMf4gWDaqUyub2I4uYqgDrudPzu6pBQY0=",-4504826610807745271,5693079082053826164,-8049238918389786916,4200953242520545331>()) {
            case 1878404982:
               while (var9.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2e95f5ajw7og","MpC63cESHzpNQP0Q06qK5h9WnQbPlIYs9PXaYjLDVpw=",-4539928207083224205,8993715128175745134,6771019159483284556,-2052344437620401706>()) {
                     case -800174115:
                        g var10 = (g)var9.next();
                        var10.a(var1, var2, var8, var4, var5, var6, var7);
                        var8 += var10.b() + 6.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s1x0uj0pdwxfo2","hjNRSb6Z7F/nYG6eyKrMAfUogS1Mv60iau6SCYOLxTY=",6387416747513506498,6693858904761776255,-6925739103513433697,126282525464550953>()) {
                           case -1099177515:
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

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = var4;
      Iterator var8 = this.dd.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1x8vb4x2pshez","1xAXF4iFJyCtqE/O/cHuyfMgFl4z66pJVQl55oEZ4IE=",9199400051185272585,3532741671218044484,-2639755790977358887,-1582612550167552789>()) {
         case -622837051:
            while (var8.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3sflvno9eky3e","udgxiYG9D22/2+rtUxDpu9KotS9DcTX3AR8k+CnZ0Vs=",-8741010195108623389,-9124648651648015777,7091889736821976694,3858418649647177997>()) {
                  case -2014388832:
                     g var9 = (g)var8.next();
                     float var10 = var9.b();
                     if (var2 >= var7) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2tnhg3cnwv9pk","3sfTZfAToRYgVZ7ovJu8uFQZUcda8ZOElsD6uALmX80=",-5832704857237419532,-5321427911656311962,3529899087341631057,-4061890858833273186>()) {
                           case -885639003:
                              if (var2 <= var7 + var10) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2jbfay2lm7b0d","XBu+jRpnU0p3MpKKRhbWvGstgVSGNrM60YHPfSqgggQ=",-8395641276259792945,6581762185998847709,-2303528711460294559,-1881770392604553708>()) {
                                    case 1130667766:
                                       if (var9.a(var1, var2, var3, var7, var5, var6)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sucp17roa85br","t9a3nU8QH+NIE5t0be3VAic0rXEa51l0kO9kwZrXUUs=",-3902296101357852173,2042674200899153357,-4137243356664206391,-1744917487971976117>()) {
                                             case 1390487014:
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

                     var7 += var10 + 6.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"sbhs7x4ooboky","10u8mPalwodxnoUjXc3d0bLAsOQt7QO4hd+ktJIuaq4=",7489335166768136505,6305622013733402076,4131418556609998725,-7595794200476171228>()) {
                        case 493433529:
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

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      float var6 = var4;
      Iterator var7 = this.dd.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3rhu2z1c1qcou","b7oEdwDEynb2ZAIqfVpNfOUDmcZPZk/mmEoRB53ScE0=",-7975003014701319209,694669328444955111,3822667959710786934,-8069720396333204041>()) {
         case -243387462:
            while (var7.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s4198n57t2ei2","cP2yHeYhFYifrG6Vl567rlFN+p7ZJUGV1rKKeEpZsVc=",1029390343955055449,4851774119078053955,-5735229810407066743,7781850761815352129>()) {
                  case 619347888:
                     g var8 = (g)var7.next();
                     float var9 = var8.b();
                     if (var2 >= var6 - 6.0F) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3aud235b18i81","qgFETXa8gVMRjEBXJLAhOXfDD2dh9ChB+T4A9E09gbM=",8988684896327049873,-5523214371595064523,-1555580777622824413,1879572116160712126>()) {
                           case -1916372371:
                              if (var2 <= var6 + var9 + 6.0F) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3t5qo3dy95m4z","VAQcQesZYZioSq6uvT+VpiOBvjcCMD2vZI5NjH4pff8=",-2606052593206429035,-321896306748447655,-3068363741849678367,-8481688115070602143>()) {
                                    case -993174821:
                                       if (var8.a(var1, var2, var3, var6, var5)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s308zw20jw8088","VpjQxSWfK4jUKqTriFFsGiz9I+ecG3FPryEcNx8bMNw=",-3316508824080659705,-3857470253589691369,-9173498680841332236,4008805886485416873>()) {
                                             case 750733892:
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

                     var6 += var9 + 6.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s3jw3skzfiofjl","+SOMOL+zkPXPATsaJ0zOOO/vKHvYRg+Padk21yJluCo=",7695683220927560432,-8999700285670118850,880877021221315661,-4967973569542320813>()) {
                        case -2100465323:
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
}

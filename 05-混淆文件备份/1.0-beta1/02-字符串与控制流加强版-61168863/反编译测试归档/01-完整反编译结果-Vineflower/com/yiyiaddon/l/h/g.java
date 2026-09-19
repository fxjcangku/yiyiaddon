package com.yiyiaddon.l.h;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.b.n;
import com.yiyiaddon.l.b.t;
import com.yiyiaddon.l.b.u;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public final class g extends f {
   private static final float mt = 200.0F;
   private static final float mu = 4.0F;
   private static final float mv = 22.0F;
   private static final float mw = 11.0F;
   private static final float mx = 10.0F;
   private static final float my = 12.0F;
   private static final float mz = 12.0F;
   private static final char b = '\u0001';
   private static final float mA = 20.0F;
   private static final float mB = 64.0F;
   private static final float mC = 6.0F;
   private static final float mD = 0.36F;
   private static final String Gx = (String)com.yiyiaddon.m.b.a<"s1sksrxyzoohjo","sIPwtKSc3n5ssAjvrJqR7L1NdT9Y2AgAjQAO+fL6Tk+fykP378mt5Mib",2397882545194621361,2939949358549189553,-7744550545298867673,3061264925851876493>();
   private static final String Gy = (String)com.yiyiaddon.m.b.a<"soqdy95wz98pp","aEs3Y8dxyZdNQegBAinlCibOqd8hZ5/kzLb45GTNY7SWYkbpR4KGWQjVaamj+7tU",3997469007805007391,-7934335454248968551,-8877828602787872357,-379576137199869755>();
   private static final float mE = 6.0F;
   private static final String Gz = (String)com.yiyiaddon.m.b.a<"s3lee5xqqhhgvc","8J7YvgdLArFh5rO2UxZpKklSdxAztAYLMZmVlr/x",8623718476203687990,2080586181635793691,8803786868239514119,8147340963697422196>();
   private static final float mF = 13.0F;
   private static final float mG = 90.0F;
   private static final float mH = 0.36F;
   private static final float mI = 8.0F;
   private static final String GA = (String)com.yiyiaddon.m.b.a<"s3p34q9pf4fa3z","A6wHIGkB2KqX0wXC/ax0q+urnFogQ/5BBYkdmyskS74=",-2530827603038101804,6274650306514620490,422749096093159217,5840953431088509715>();
   private static final String GB = (String)com.yiyiaddon.m.b.a<"s3upfjrqqn2ysq","jlGqez33vsodAP1ZQ4+/xJUXemXmVTrj8aGxF61P/UE=",123579332720797241,-792357753657026173,-4432491075485788216,2369230463595855836>();
   private static final String GC = (String)com.yiyiaddon.m.b.a<"s3630s7uwao361","6h9Kt3rt/q7PlDe7jvKexx3md++0oKogABVowotjuY/u06n+/8hSTqjG+7c=",-1271731267369162954,2191591707383268622,-3240396686205925022,7316082923666761736>();
   private static final int uJ = 200;
   private static final int uK = 200;
   private static final String GD = (String)com.yiyiaddon.m.b.a<"spju2teous2cf","pz1C8Gdgs0uN3V5/2U+koBPmYXDUDPEoiCsnKVCt",6053264773543431477,7882060091377151639,9104738854735085149,387332692121726463>();
   private static final String GE = (String)com.yiyiaddon.m.b.a<"s28gnpzqr9xty6","rVja+sDiMEVj7UijvhNlK3+T2R1ywd1EV+6Ic0gz",6417107697504221056,264182419970318153,9174992466141015548,-4876917958663124792>();
   private static final String GF = (String)com.yiyiaddon.m.b.a<"s1nwersata1y3a","9NTHtRQJRJ1YVFVZl7kaYyFYNWnsTsQvBBOjMoZ0xAvqRMrZFNM=",4755594868275577544,-8534827399715495226,7186361100572708203,1492650565977457387>();
   private static final String GG = (String)com.yiyiaddon.m.b.a<"s1uuk214xkexyo","we4Z6Xyjh8AB5JqUNb6fj9yyiCgkwrw5XyKaOlAHSJkn4QwkoGq/uFaoctAqvdYml0SwOgZO",-3417194782804691094,5340705907341527029,-1400586383483774477,8190351233500103750>();
   private final String GH;
   private final List<g.b> dA;
   private final Supplier<List<String>> B;
   private final Consumer<String> p;
   private final Consumer<String> q;
   private final Consumer<String> r;
   private final u a = new u(200.0F, 4.0F);
   private t a;
   private w a;
   private final Set<String> bb = new HashSet<>();
   private final Set<String> bc = new HashSet<>();
   private String vg = (String)com.yiyiaddon.m.b.a<"s1rqsu1a0kop1m","gzZBeNjfjzphO8Jv6UUigvkSDTXzqYVhVuVH5g==",8915044547096428329,-3326938568727127932,-4345746120026003887,5755067903937495743>();

   public g(String var1, Screen var2, List<g.b> var3, Supplier<List<String>> var4, Consumer<String> var5, Consumer<String> var6) {
      this(var1, var2, var3, var4, var5, var6, null);
   }

   private g(String var1, Screen var2, List<g.b> var3, Supplier<List<String>> var4, Consumer<String> var5, Consumer<String> var6, Consumer<String> var7) {
      super(var1, var2);
      this.GH = var1;
      this.dA = var3 == null ? List.of() : List.copyOf(var3);
      this.B = var4;
      this.p = var5;
      this.q = var6;
      this.r = var7;
      this.G();
   }

   public static g a(String var0, Screen var1, List<g.b> var2, Consumer<String> var3) {
      return new g(var0, var1, var2, () -> List.of(), var0x -> {}, var0x -> {}, var3);
   }

   private void G() {
      this.a = new t(new m(() -> this.vg, this::aD, 64));
      w var10001;
      if (this.r == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s8a1kp3lgo03r","LsMQbhD53grIboA4Bq7yt9RBQ5A8dMwpqrc9RAe/N8Y=",4930333957709554588,-2934982189758078390,-474333994005531732,-265090706371077023>()) {
            case -1439935872:
               var10001 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s26s95vbklnkqv","JrZgpGUPj1abg4TjObTta8lbUIm/2EeH/tjuKY7On4Y=",-6841137749832236636,-907502717661548128,7912578967751209487,3073018200461130448>()) {
                  case 1875939344:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = new w(
               (String)com.yiyiaddon.m.b.a<"s1uuk214xkexyo","we4Z6Xyjh8AB5JqUNb6fj9yyiCgkwrw5XyKaOlAHSJkn4QwkoGq/uFaoctAqvdYml0SwOgZO",-3417194782804691094,5340705907341527029,-1400586383483774477,8190351233500103750>()
            )
            .a(20.0F);
         switch ((int)com.yiyiaddon.m.b.a<"s18aur0xq4oi5w","CRYZTn83MXVkUwvSDaqKKZaEm9DM1KpM4U2ch3x9O94=",1429966327148224774,3681984177192420861,-2322828100056680784,-3403913615399943241>()) {
            case 1382109634:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
      this.u();
   }

   private void aD(String var1) {
      String var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s17plxd4mzh4pv","sV8r5TXuYxeCSHA+286pOs2qGIOFUyvh1R4qJBb9hl4=",1792556753808904897,6358161524439719559,8143844011226450379,734569094792482957>()) {
            case 1412254722:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1rqsu1a0kop1m","gzZBeNjfjzphO8Jv6UUigvkSDTXzqYVhVuVH5g==",8915044547096428329,-3326938568727127932,-4345746120026003887,5755067903937495743>();
               switch ((int)com.yiyiaddon.m.b.a<"suvpjhvwk79fj","iqOuiZaaQB9aP7dHDF649qPNmNycrRS6fdfGpN3t4TE=",-213841337202328870,-6852697406212024678,7807343065985215544,-8450734341853971057>()) {
                  case 896485472:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1.trim().toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"sv8lctswzrhm2","r46frB4/5iPxI7V+lsLk27EExQpukx4B7IPwSreXUh8=",-7901545758272204104,-8928099071031241531,1577903836628785640,-8915061448157752772>()) {
            case 385860015:
               break;
            default:
               throw null;
         }
      }

      this.vg = var10001;
      this.u();
   }

   private void u() {
      i var1 = this.d();
      var1.b();
      var1.a(this.a);
      if (this.a != null) {
         var1.a(this.a);
      }

      this.kS();
      List var2 = this.B.get();
      HashSet var3 = new HashSet(var2 == null ? List.of() : var2);
      if (this.r != null) {
         this.a(var1, g.a.PICK, var3);
      } else {
         this.a.f();
         var1.a(this.a);
         this.a(this.a.a(), g.a.CANDIDATE, var3);
         this.a(this.a.b(), g.a.SELECTED, var3);
      }
   }

   private void kS() {
      com.yiyiaddon.l.g.c var1 = com.yiyiaddon.l.g.c.a();
      int var2 = Math.min(this.dA.size(), 200);
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3srliie7et5bi","LFCBphX8/TMDwqaQR3nuFANboeM1Hum31Yvn1qZbJME=",812332331982030938,-4052879651272905547,924001816447257867,7229634826191771284>()) {
         case -1131711746:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s2v019jzrx2qff","W1oXBu9Jc3B4KBuM7ubz4nBNqZhdlxHHpo+cDaMT52s=",-6530423607874527759,2500667498528934629,4686329788306748429,7818196381832906541>()) {
                  case 766560350:
                     g.b var4 = this.dA.get(var3);
                     ItemStack var5 = var4.a();
                     if (var5 != null) {
                        label32:
                        switch ((int)com.yiyiaddon.m.b.a<"s31cn8lgbs3aav","sugXe7swLVsnTTxlLFjO1tcKsYI11FRZNC/nnBT+Yjk=",-5964395705027360307,-2174747515976559394,-8661180632817487660,7973655494620242874>()) {
                           case 1777189851:
                              var1.e(var5);
                              switch ((int)com.yiyiaddon.m.b.a<"s2d8ydwxp1x6fy","9y6bplQAuAVJ3Djgh9dd9FjpGTrAWBQn3ak5F6Owpgw=",7502538792498895625,-5043619864609862926,-6348133561816648865,-1682719208080113079>()) {
                                 case -1478069557:
                                    break label32;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        EntityType var6 = var4.a();
                        if (var6 != null) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s26hbx9z5lrfxb","JYZTsuspglVYqIQicep7S13idJPs1MAG/oNLLgKqWNM=",-7347576949123342973,-5195050106719580380,-2603525936440187913,-5507386187643129630>()) {
                              case -937879564:
                                 var1.a(var6);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3720f9zgdmj4v","9LyxHMbQTBPzrZoLnWb7bmBA+H1MRu+P95WZukYitpk=",4509750317102482368,6669326978049013055,-968220393961188133,-3132853759115002337>()) {
                                    case -32844046:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }

                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3bga8x0vmrce7","fGnApj/U215Th/MBG8GAijsvCA+a/OfyEv7KlCEQvPE=",5256592190533488452,906907707009904218,1270506275896407112,5800259131749545362>()) {
                        case -2083295670:
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

   private void a(i var1, g.a var2, Set<String> var3) {
      if (var2 == g.a.SELECTED) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o56dxn3q0nrg","d6H1zz48bl5/3AKuKe4zD2/aEmQfRpteufHjSkFXn8E=",7208891621312298568,-2102419021544114198,4012851981837378460,8502594421426661461>()) {
            case 1510498904:
               if (var3.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2x73zxqnle7dg","6hlMf7+gU3WZdjczZVLmANbk9FTRcVFwbH77+00ejho=",-3208663867238027774,7046951445307524705,8333213235011705485,-7754404026148696289>()) {
                     case 47258603:
                        var1.a(b(0));
                        var1.a(
                           new w(
                                 (String)com.yiyiaddon.m.b.a<"s1nwersata1y3a","9NTHtRQJRJ1YVFVZl7kaYyFYNWnsTsQvBBOjMoZ0xAvqRMrZFNM=",4755594868275577544,-8534827399715495226,7186361100572708203,1492650565977457387>()
                              )
                              .a(20.0F)
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

      if (var2 == g.a.SELECTED) {
         label311:
         switch ((int)com.yiyiaddon.m.b.a<"sxyqr61df98cd","gdf9ckmVSsdgg7uZI9Y+hlFG87Cry/TvhbTYzNLYsz4=",-25115833796750452,-8645192377966784056,-257101327566640426,3997851934545150335>()) {
            case 1621899826:
               var1.a(b(var3.size()));
               switch ((int)com.yiyiaddon.m.b.a<"s12lsr0knnqcag","y0DuwW6kmw0yCOQVMBp9w4D6c6588K+tn5cM0IktZsk=",-4702052280700291138,6183739131652490876,-1126158607523879872,-6424128851334126714>()) {
                  case 1673845893:
                     break label311;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      LinkedHashMap var4 = new LinkedHashMap();
      Iterator var5 = this.dA.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1sx0gl22ssxz1","sBzXN6NLXrYzIwriULiePolLBCKqmYxR93EcjzxBiQw=",-398596594042262194,8964622114892340023,-4090680536560253362,8344275653082625013>()) {
         case 1474310179:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3fdza4s2tvsrz","+wBbemIHmmBBkb5KTubwt0izcW3B4JRBbHUvamQ4dOM=",7939286152292665762,6927719747848041060,-2245709643504705380,8272804210281326171>()) {
                  case -623729493:
                     g.b var6 = (g.b)var5.next();
                     var4.computeIfAbsent(a(var6), var0 -> new LinkedHashMap<>()).computeIfAbsent(b(var6), var0 -> new ArrayList());
                     switch ((int)com.yiyiaddon.m.b.a<"s2dekuuk15mz2o","CZ0UP19hNEaCejHwJuv99F+95vHBmUzg2WO2QD9oCsQ=",-2181516702574630491,-7645539044948657341,-832540967147347986,7283145778328807962>()) {
                        case 1530518807:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var5 = this.dA.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s2bo3mlcl76az8","FlZrOQa6SGhWtxQvMlFGaOM0tEvSPOl0vCX4Oy/OBKs=",8834074637755957947,-1603012991523751407,3200258783087607826,-760965334330689675>()) {
               case 281186919:
                  while (var5.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s296243zrsp7i1","4iIC0zX7HsgXW0k/UNpSuMObmBZqCcSe6hySTk1PIms=",3936449223459881009,-3692027568407483332,7432535752645867067,1019470115916858224>()) {
                        case 383317017:
                           g.b var21 = (g.b)var5.next();
                           if (!this.a(var21)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s19hsnw1a85f27","zRxBti7SI5NmYDw9cQwhUnWbn7rygQKcO3mb06/VcWs=",2050919481123583229,767191381508596223,3740954359928586374,5978402946258035216>()) {
                                 case -979946992:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1o9nwrzg1ef2e","bkLUsuSvQBX+CLDcGsVhjTvCuV1yly0IfIHtqr/YDWQ=",4097580506063794834,8846989810737891796,8936439614852682142,5899350625038145608>()) {
                                       case 807026604:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              boolean var7 = var3.contains(var21.L());
                              if (var2 == g.a.CANDIDATE) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s30sf5mytvoqwv","yAk2+91pYfaMAtgqhiwSB/o1DSuuS8SYJmHLPjbgUzI=",-3933359835170081285,-5359135299634368724,4339199272455305036,4453048091372942798>()) {
                                    case 1996488144:
                                       if (var7) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2w8no3sau5ox8","l5v5mxq+7EJm037sqnA9L8+U0RBjvqKBUnDbbVcTkDI=",-4462254401622786171,2008709873576298949,961455142577203032,1702052629904333672>()) {
                                             case -1727708648:
                                                switch ((int)com.yiyiaddon.m.b.a<"sks8bd30ecbv2","5n2rcwFk+EFzgn1UjY6JcvsFkstShpzT4Bn5Srjb3y4=",-4994793333606500319,2269033631793795494,-507555044543794071,-3191218578496958292>()) {
                                                   case 643294564:
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

                              if (var2 == g.a.SELECTED) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s11v9aczqxjcqt","+HlY6Ni1IJTHHdo8OtfZTUz9c95KNV6pzBJE7fN35Zg=",-6090027446753058794,-2614725530125902427,-1218170939041032286,4724890711455724158>()) {
                                    case 1718021270:
                                       if (!var7) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1hvcd12h6xg7c","u+2y6WPK31utq3ptslFGdxbsxhc+uc7QrQ2r+X7Q1Lg=",-4199491098240168666,-2556841093820104390,8191019604715873564,8835954724492732530>()) {
                                             case -1490695442:
                                                switch ((int)com.yiyiaddon.m.b.a<"s142a4yhidc9cx","8C/g/1h7FwfZcVXQtTEKH8310WhSY/fN+GXlOoFtzPY=",-600799306724558498,1735073539344657602,-3712621375821395886,3813975100976494713>()) {
                                                   case 954719000:
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

                              ((List)((Map)var4.get(a(var21))).get(b(var21))).add(var21);
                              switch ((int)com.yiyiaddon.m.b.a<"s2o8oh2d6a4syi","+EmqHtDD4Nd+44qgQpT5NAcf1sw1/O+qSWhF0sb/9F8=",-689089096041120243,1559035022935557760,-898698128612618127,6099489166735460562>()) {
                                 case 436813555:
                                    continue;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  int var20 = 0;
                  boolean var22 = false;
                  Iterator var23 = var4.entrySet().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2gv0g7uykhpff","AfTwfEtnEn8T9UjucEpTGOIiWr1YuiPFW/vWHuuxdNo=",4717695395661461239,2794979579797323153,4308902491692186362,-9093923732333766407>()) {
                     case -193738302:
                        label369:
                        while (var23.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s31pbgnz5jie2s","EeM7duE8hjS6huuRN/jxu6s0zKziThihpn0iqvUacZ4=",876417101438079876,6285421820077069380,5480915214178836884,7393510963002368944>()) {
                              case -1556735906:
                                 Entry var8 = (Entry)var23.next();
                                 Map var9 = (Map)var8.getValue();
                                 boolean var10000;
                                 if (!((String)var8.getKey()).isEmpty()) {
                                    label281:
                                    switch ((int)com.yiyiaddon.m.b.a<"sicowzoqxh2hv","puY4MmRj94jpnEWy7bZ706a2J1xFH+N3DcWDtWdb+Ac=",7907621554764523660,-72521154254953028,4516327458851406001,-7487850800399575358>()) {
                                       case 702740958:
                                          var10000 = true;
                                          switch ((int)com.yiyiaddon.m.b.a<"s37mehq6xcpt6l","UiLbSzlii5qbWxvDcQD3pGb7Az34YeIruN8t8kUdA+k=",247856679101895568,-8144062124329055228,-1212695359458971802,-5426335563785631713>()) {
                                             case -271472409:
                                                break label281;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = false;
                                    switch ((int)com.yiyiaddon.m.b.a<"s33fkc07kx7dkn","g5i6JJIDvYNncv+A1evcL10/MBOiT8RZNSlZcX+fQFY=",6258314410672185696,6754603094876317682,-5273640888806871369,-1795133787269386808>()) {
                                       case 1960673173:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 boolean var10 = var10000;
                                 List var11 = a(var9);
                                 if (var10) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2i3xt2nqjc34r","5VZ3i0vnCta8bfakk+tQiXjYron6oD8g9AE9wa0wKlk=",9114560449867666831,-7457852888169749497,-4054636215061846567,-1241855865511516302>()) {
                                       case 655961366:
                                          if (var11.isEmpty()) {
                                             label273:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1hdwr9qnfjl0l","lG57ThORXDYs5uKJBzCOKfe/9VfZqt6dwEc3JgFUB94=",-5366237135264257396,752376151550092032,-7916847952599264320,-6998016197651835887>()) {
                                                case 1210200115:
                                                   if (var2 == g.a.SELECTED) {
                                                      continue;
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s33tftssud3nvq","kayTUkvRqHqC/KNPaYAkI2hHjnioJYsgVc7uQRVUcyE=",-9132518481884678334,-6930462856930613010,-2762435742100576532,-5581278008677230669>()) {
                                                      case 1437947156:
                                                         if (!this.vg.isEmpty()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2ox98xecsnzaa","U1xE0kDElJvSwh2F3wB1pVjKfr0FIIcHhFn91aQ68YQ=",-2228011094499250382,1193537266326032526,3951442232838495589,-4405064437479743815>()) {
                                                               case 1112010656:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3tr1xrh3zy8ht","ubd9qr/CQWwJtn/geZQC8BEOubZa6G/pog9Fg6SCNrc=",-4069863606558976792,-5895273402449903662,4705070033907930336,8720814699293304539>()) {
                                                                     case -1230318654:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break label273;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          com.yiyiaddon.l.b.g var10001;
                                          if (var11.isEmpty()) {
                                             label269:
                                             switch ((int)com.yiyiaddon.m.b.a<"sa86y0r025v6i","dITwT3KIz5Th5nQfS9ctEl0aROtfucJBfCpfD+q09KA=",-2042199240566568171,-2141111911483037068,-3602415338823492101,3687329557567622738>()) {
                                                case 1714708436:
                                                   var10001 = a((String)var8.getKey(), 1);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1d3k0zhvq1yb9","iLqr+FPRuzbnPUXAa7RNIF7iPoTATw4Ji8Adl3yIL0o=",-1095442049817748010,3842768503551036667,1521431434741697908,5039171768753963700>()) {
                                                      case 1466729236:
                                                         break label269;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var10001 = this.a(var2, (String)var8.getKey(), (String)var8.getKey(), var11, var3, 1);
                                             switch ((int)com.yiyiaddon.m.b.a<"swj58x3zcqwxg","1PQqxGIoO1T9msPvJYc3Bbqr8NM5nF7lmSG5Ax0+TPU=",297318373413703039,830030811153325300,1075698988097004441,-1913754908331817689>()) {
                                                case -547777469:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var1.a(var10001);
                                          if (var11.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2pt91fb53yuj4","sIpKhOZSyaYrBMQRqFX7OZtcSM3mjYsrPEytyyZl9wQ=",-6204710110957704271,-7466989025345631199,-1630584519197905662,-5819257485934572874>()) {
                                                case 1713270551:
                                                   var1.a(
                                                      new w(
                                                            (String)com.yiyiaddon.m.b.a<"s1nwersata1y3a","9NTHtRQJRJ1YVFVZl7kaYyFYNWnsTsQvBBOjMoZ0xAvqRMrZFNM=",4755594868275577544,-8534827399715495226,7186361100572708203,1492650565977457387>()
                                                         )
                                                         .a(20.0F)
                                                   );
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3gjva8sa8k54y","bnZk8QDznIP+SlRspnD49YX858AtykEQpR0ZJlWFEWw=",6886523031789909265,2799164364702657540,-539318242007324958,4759554067536266263>()) {
                                                      case 712409186:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (!this.a(var2, (String)var8.getKey())) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3d15qxzfsy8u9","tJsr8PC/1qYz7DKM75doNuld1o5hYBQXPB2LuFBxZOk=",-6010515605475115682,-3866914297451826706,543929679304246374,4835078460179981753>()) {
                                                case 556803267:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1kywpbsz8610m","M9J0LZ+IguaSqpYMXdx1j46mSiXL8kZU7BizlZjNpPM=",1507096579580136357,2694965430890650740,-7400226612535921150,7724605992621116963>()) {
                                                      case -124696210:
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

                                 Iterator var12 = var9.entrySet().iterator();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1gy23wotkkfyi","XdsTT13hpCNq8alT9MGJ9LG9YwqbZrtgvM0UbNmeW/c=",573055419314302121,-2155506279139976784,1432677693029337931,5267259860410324915>()) {
                                    case -1470727135:
                                       while (true) {
                                          if (var12.hasNext()) {
                                             label354:
                                             switch ((int)com.yiyiaddon.m.b.a<"s32da4cee0q805","hKmMw9ZhMyPRLBoMwpDaeesFhinIsyMOveFCV9Obek4=",5775048855237432516,-4499132899932674983,1560151710258354310,-7156868985582127977>()) {
                                                case -1047691938:
                                                   Entry var13 = (Entry)var12.next();
                                                   List var14 = (List)var13.getValue();
                                                   if (!((String)var13.getKey()).isEmpty()) {
                                                      label261:
                                                      switch ((int)com.yiyiaddon.m.b.a<"srx88wnkox06y","LI7C012i/1Uly9ob39oC3oQFOUXlR8/oxzary1ytF/s=",6004255326658449111,1550805253146444014,636848359914210367,-8473093095254842713>()) {
                                                         case -1963877275:
                                                            var10000 = true;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2r2ffhs8ylpvx","p5/GT9Q4s84K0s29XDz4dcGTh+c6g6TZ8wJco0+IC74=",3774195399577361150,1939234683052430052,8652397310801460999,956922102302361545>()) {
                                                               case -1558322323:
                                                                  break label261;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var10000 = false;
                                                      switch ((int)com.yiyiaddon.m.b.a<"smo9ggmid8xbi","139k2HwfLJ97hYUBGVbDi1WMNRXLXkhX2XPjmE0spUk=",-4894381996998679534,5537647850317237395,280157099376413841,7045410745893541641>()) {
                                                         case 700209285:
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   boolean var15 = var10000;
                                                   String var16 = A((String)var8.getKey(), (String)var13.getKey());
                                                   if (var14.isEmpty()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1ghuzwy5zkfhe","dcmqIi2B1UgHyv1UKx7wyiqLdql1L6/bn3lrP0c4yY8=",4520476612140672573,-3817708902848167021,664411218759433740,-8865104279218329240>()) {
                                                         case -1121820140:
                                                            if (!this.vg.isEmpty()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s18usms2djjpc","oc/aHLqHncMi4rhFNb946Rdm1PI90rcy+t0OA3J8AHQ=",5645521901916906001,4550186792804894210,-6378488813428446895,8125352529604624070>()) {
                                                                  case -1067813359:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s18wyblo4uh1tj","BHRosdEWXA8hK1J7a/bUVAwsxh25ZXFfnGGdTzYeg/Y=",-891205563553852047,2819308939009978616,-5996885662155010020,-7244171950640214207>()) {
                                                                        case 350592557:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               if (var2 != g.a.SELECTED) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"spg6thd2uq6rm","TWCrmGJzH3pj6k8rXSjkOaTBzM8sjS7sosbcl3QwgjY=",5688373184017848402,-6079950224926642217,2812967771260676309,3951743535255533193>()) {
                                                                     case 1942375602:
                                                                        if (!var10) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s32zz2xaztynj2","jO8H3cK0N2JhFnVH0TmqgwP2DRHHY2kWH3lt8zMq/Mo=",-1596373367898593954,6840727873148531207,-7813472410286906474,-3536530505620772333>()) {
                                                                              case 833166915:
                                                                                 if (var15) {
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"s30se0tkljufzj","/tNt7i1Nm5FgUE/EoybfiPqkK9IIqQuUA80nV50HVko=",4294681339541022692,-5262375905643400621,-2363767947855409117,1434901027814853812>()) {
                                                                                       case 2145449282:
                                                                                          var1.a(a((String)var13.getKey(), 1));
                                                                                          var1.a(
                                                                                             new w(
                                                                                                   (String)com.yiyiaddon.m.b.a<"s1nwersata1y3a","9NTHtRQJRJ1YVFVZl7kaYyFYNWnsTsQvBBOjMoZ0xAvqRMrZFNM=",4755594868275577544,-8534827399715495226,7186361100572708203,1492650565977457387>()
                                                                                                )
                                                                                                .a(20.0F)
                                                                                          );
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"sx54ou3fdkzvu","K31DOC7w0mChYyC4vKzURbL8JYsz4hCg0cay/QXb1Wo=",296271719099828408,-6272351882839761620,-1698769844637141051,-673765506638877666>()) {
                                                                                             case -1827008987:
                                                                                                continue;
                                                                                             default:
                                                                                                throw null;
                                                                                          }
                                                                                       default:
                                                                                          throw null;
                                                                                    }
                                                                                 }
                                                                                 continue;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }
                                                               continue;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   if (var15) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s8mexg1g5f74l","NvzLQ5bvuTEhzak0vdSsXKlnuX4ewCjsD4EvGrZKBAA=",7775761141230486992,1443865309016598947,5842715606890522523,-1348196762177600867>()) {
                                                         case 2007387143:
                                                            String var10003 = (String)var13.getKey();
                                                            byte var10007;
                                                            if (var10) {
                                                               label257:
                                                               switch ((int)com.yiyiaddon.m.b.a<"sdk9wwxy2ypq5","1932jk5u4bcissLbd42+u93JaUWysaOLpvMdIN+gVMw=",7637864130012934002,-3147912970945799086,742805917641609947,3414552866491169139>()) {
                                                                  case 1736911346:
                                                                     var10007 = 2;
                                                                     switch ((int)com.yiyiaddon.m.b.a<"sgdmkroc1xq11","0qRq58vgqeGodeFjwRJEK+MDV+GXpk713hl+iQ4Gitk=",-5673436689336123540,-3060338407342349255,9150412738346619617,-6515049798212695579>()) {
                                                                        case 861746029:
                                                                           break label257;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               var10007 = 1;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2oz3v8u2bnfcy","KS0fVZpSMTvpkV29y0Iw6+l0BeTjL+5Ce63/qU5bJyQ=",4661361357644540927,-1432354241159860022,-1752593606411643199,-2969501958354021549>()) {
                                                                  case -392195654:
                                                                     break;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            var1.a(this.a(var2, var10003, var16, var14, var3, var10007));
                                                            if (!this.a(var2, var16)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"sjp4zg21swwab","KS8N+A7fGrkRSih24ac0cLbyqmSRVxnEj2E83II4ZmA=",-8280749317232757917,5866049475975724611,-5078362044316695679,8229483605142728712>()) {
                                                                  case 1555663547:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3kq71nw8axobb","NZFiL7107Qbjt+YPNVn2NJ30HEfzD1UPKEpLVGRskIs=",6859049057549900095,-2493539543252647189,7773856554150139655,-2350691089073918723>()) {
                                                                        case 843468383:
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

                                                   Iterator var17 = var14.iterator();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2xajmhav9xec2","TLgD4Nn2b2HBoJ3BbJvOtaub6ct+7meIZMEDP4E3MWY=",-733921719808284494,3287329361877467823,8571432803779720947,-8310626016658685888>()) {
                                                      case 370769308:
                                                         label362:
                                                         while (var17.hasNext()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"sitptjfpcnesj","zi2Uhst6w2qxgToF5OdwOTz7VOFkNVoF1xkOlz3FXMU=",-8128622757968513138,-3105660639254894113,-8201632176275229327,-7616587949247778009>()) {
                                                               case -944046274:
                                                                  g.b var18 = (g.b)var17.next();
                                                                  if (var20 >= 200) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s33o9wdltw5s2e","xNcPnKFXaoBKSqd27KpoLHeKd3QZJ3h4jrbhi0ZZZ6I=",-6983321195822935933,6606316855135627831,4865387718484444018,3591927189955140143>()) {
                                                                        case -1823917540:
                                                                           var22 = true;
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s21eabv9p8sgs6","CvbHtjP8tARyUFRL2k7GtUXqEPkuAnhd/uEgDmJExNw=",-6246071149603027564,-4191629830044441505,-53905200867709694,-7695610673321301711>()) {
                                                                              case -1254619666:
                                                                                 break label362;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  n var25;
                                                                  if (var2 == g.a.PICK) {
                                                                     label248:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s64ju67y4w888","meEpmxhGNH9GLqRU8Upqraf7WHolTekWL+yTcd1LGg8=",-540388620927618511,-4914378640595153668,3322558458116857687,-8818546979345548614>()) {
                                                                        case -2135529708:
                                                                           var25 = this.a(var18);
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1b80pzj16doie","HTr0mPLB8XJscvFFktY4A5DTKIMo75ZP8rElmLqNw94=",-3646438543498621353,-2438456689645201621,3091111702800278018,4375094639163453812>()) {
                                                                              case -219917462:
                                                                                 break label248;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  } else {
                                                                     boolean var26;
                                                                     if (var2 == g.a.SELECTED) {
                                                                        label241:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s9rgsa3sctrwp","ke+bHKpxBFFn58LV3kYw+huKOMdkAfif8K42kEYk8J0=",-7208419012028614208,6339259820207301119,-4661132247797499207,-7249135230346875316>()) {
                                                                           case -914118651:
                                                                              var26 = true;
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s25ky5zf7072y","Mch7CWYMmnmRN2BSPfcfn9fbxcfAtgw9m0ir+4OAxHU=",8624490089542897585,-6026423118494595849,-4835044737980294415,3529036032000019718>()) {
                                                                                 case 992509148:
                                                                                    break label241;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     } else {
                                                                        var26 = false;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s31k8qmtc4npui","aLI+fZ6B5qx7ef1BFpRR/KgPz5WjRQZtGb9uSHDPRK8=",-1373990128250094079,-1269283942691913938,-6248979525557087600,6871944032118669140>()) {
                                                                           case 1908663870:
                                                                              break;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     var25 = this.a(var18, var26);
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1qgs11dxh2ngk","7WbqgTUNMcLoYqJLlfwpuFh9t01nSf5XlTWHK9O/B2E=",-5324555810313842542,4901861960433087248,-5447078019252045543,-4721063680114843466>()) {
                                                                        case -402700316:
                                                                           break;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  var1.a(var25);
                                                                  var20++;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3kes7u91tqxjg","Z3LoBC92XORVatat7VGlRdVS/w3HDuRa9ueqKRgcrzc=",-5091311487938163262,-1188277857107785207,-1126434701783078105,-1725683904232657339>()) {
                                                                     case -1333415509:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         if (!var22) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"so872ct384yhs","cJT5EV96w8c3FEU96OOYv0rosWmJSp/3rULH9P8r31o=",4539857580940282324,5640717985508928624,8092360704114522513,-7950215011424791074>()) {
                                                               case 799385432:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s3qg1ga6ejerva","xFADcXFxr25TmfqfGyhJv14Wqr7qOXZZxl0o6THY3rc=",-7990857753128988012,-1624177326458182706,-1933371319916596044,-5383813528175962220>()) {
                                                            case 1127788081:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1lhxq1b2aiygz","x5lqUV0CNPvcSo7mwHsWi9Kvm6JrwvYqXABTnx3rNQM=",-6276615124156924484,2525699235573263838,8202575382542151359,7961842442904886863>()) {
                                                                  case -2051053052:
                                                                     break label354;
                                                                  default:
                                                                     throw null;
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

                                          if (!var22) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1e7anv0mnmpx3","jUTANjeVkLVWRXcpnYcaIXKBdZzOyuBw6tN9zsz1UfQ=",-8192128842649490413,3208068642864852316,2589692670875917566,7503870727432175568>()) {
                                                case -609569200:
                                                   continue label369;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2px6wordwz22j","+hp7wrmZMgBUxicU2Ketq+Gwixxk1zVW2zHDEhoEzRs=",-8627742600202356552,-3878261048275123724,-9095707904272722491,-7442396149228744953>()) {
                                             case -151611793:
                                                switch ((int)com.yiyiaddon.m.b.a<"s33ohxl3b7ef8r","g2aNppqHI4xCKKUjPLE6fQMofBXYiHZl5DDdqZ1vwFY=",1309889744417812557,2696277213022090581,-7278791322073359715,7292287240569608874>()) {
                                                   case 631382838:
                                                      break label369;
                                                   default:
                                                      throw null;
                                                }
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

                        if (var22) {
                           switch ((int)com.yiyiaddon.m.b.a<"su6x13k30yxz2","HCyK4f5GGYefV2Gga12YMA+0Lp5yznsJA+89sdI9FNE=",25690442176784904,342587408142959118,2960032842357395467,4034415183870326030>()) {
                              case 754848088:
                                 var1.a(
                                    new w(
                                          (String)com.yiyiaddon.m.b.a<"s13dkbkww5so1i","Ge+fJvdMSimQMlTSbasEwZyxdVJT6MXHklHvTDzfmLWZi3fPg3BRb2+zfqEuUXoyhqVSPpefjFYEBvK/LCqW3zdlBFuY95dc3L/Jmw==",-4467181507785040808,-7774361017135107201,-5720162374592020007,-1942700025753360229>()
                                       )
                                       .a(20.0F)
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1xek5ly1qic0o","DfbjDvWo/J3noesckACBhIbG7GXlDy3uRpsuSc8tAGQ=",5168867413353503433,-8425057407387962251,-3922761563893785795,-5822405553610884273>()) {
                                    case 1240833517:
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
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private static w b(int var0) {
      return new w(var0 + "").a(22.0F).b(11.0F).a(true);
   }

   private g.c a(g.a var1, String var2, String var3, List<g.b> var4, Set<String> var5, int var6) {
      return new g.c(var2, var3, var4, var5, var6, var1 == g.a.SELECTED);
   }

   private static List<g.b> a(Map<String, List<g.b>> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1hdnze9hp7q4r","iW2+pfZQ9Q6SdL0gEE8z7VMS5PQB/6rx+md4aYgi5K4=",-7481297112569770801,-6218053989300190675,-3377332816324631544,-8336994545292512642>()) {
         case 98869814:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s8b2bnfn8j03z","XHAxx2dhZFm3pgxHoBjaYOPn0q9JVLl0WxQiJnCyUg0=",5131894034000595927,4065837972081693261,4889645636287028224,4340349083819716298>()) {
                  case -1316996446:
                     List var3 = (List)var2.next();
                     var1.addAll(var3);
                     switch ((int)com.yiyiaddon.m.b.a<"s1r4hwl3guj0a4","tpoPbxbbJqy2pg8aouRPfKevX+Yx0rlwZc58YPqD3I0=",-6814758816340096594,-5850675355112074923,6560800028211847569,-6567622631917841614>()) {
                        case -148621590:
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

   private static String a(g.b var0) {
      if (var0.bb() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"se1futaijvfsq","59q9xYl4Pnag57fk5q8koHGYFuMi+yOikcUrFzrTo/E=",-2020449340896482141,-5207942377671870166,5536945292518730447,1550916185675266233>()) {
            case -581867142:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1rqsu1a0kop1m","gzZBeNjfjzphO8Jv6UUigvkSDTXzqYVhVuVH5g==",8915044547096428329,-3326938568727127932,-4345746120026003887,5755067903937495743>();
               switch ((int)com.yiyiaddon.m.b.a<"s1dwvm63r93ubo","hUsih67hLG/5sM2FDShb8nJLlR8fA7QSlIjzLTgAQhw=",7994060101904698420,3348886367451782172,-4120820147051590337,-9044027425148319060>()) {
                  case -1357253387:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.bb();
         switch ((int)com.yiyiaddon.m.b.a<"s2736pc6w0a4ak","o3c6bQSztSn37HhZj6HLv8keAb3PaHgE8epHrE9lb+E=",6011871521660999836,-1544741701902780727,-6457270241741387867,224590897556460982>()) {
            case 1440175106:
               return var1;
            default:
               throw null;
         }
      }
   }

   private static String b(g.b var0) {
      if (var0.M() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2eqc9equpb1c3","SPYIdnhpxBiWMz5GcxRXGWlf7KbZOEo9tIElEzsnNE0=",-3876723307418145633,-1238244667878307019,2269878982454835177,-2962155778452558311>()) {
            case -372801612:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1rqsu1a0kop1m","gzZBeNjfjzphO8Jv6UUigvkSDTXzqYVhVuVH5g==",8915044547096428329,-3326938568727127932,-4345746120026003887,5755067903937495743>();
               switch ((int)com.yiyiaddon.m.b.a<"s1lnpjchvwy6o8","3dxFnXnQmEMpUiKx60FA8CYAqUU/qh/qH4LcEtuHFg8=",-5791303043663781676,1323058922139365560,3403782029088853489,8065712519822178255>()) {
                  case 1418313846:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.M();
         switch ((int)com.yiyiaddon.m.b.a<"s2j4f9q87dpe4a","Rnran6ZL+VI7ygiu2NJuteOY1LYcEnYe8IT34WY8bKA=",4549027037812572016,-8635289446570005270,7545849668670819457,-7864224071734614041>()) {
            case 269747727:
               return var1;
            default:
               throw null;
         }
      }
   }

   private static String A(String var0, String var1) {
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1v13or9ia2z0u","ZXMCphQmbKWbA//FNMoAgvNHg5LEYqie0ukcEnNutCY=",5718686072428933137,5374305936031495786,3213184201502578419,-6286087725482860973>()) {
            case -788832125:
               switch ((int)com.yiyiaddon.m.b.a<"s3sqx3o0xsfixi","H6DAuOR7EPgJ7dlZJVzwIkAMXgdIuQrodaK/FXbyk0A=",1205619998192271282,1309028831728886063,2747325189834171114,3301219796826108419>()) {
                  case 1107935971:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0 + var1;
         switch ((int)com.yiyiaddon.m.b.a<"s3mpwweeiswm4g","84sU9lGTZow6f1A0zX/g92Sc3tfr83T9G0L3c7kfq+g=",6902557898961655079,2078123170710701596,2876919413903916671,-7994072503110484019>()) {
            case 1674603304:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static w a(String var0, int var1) {
      w var10000 = new w(var0).a(22.0F);
      float var10001;
      if (var1 > 1) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s1d6gqkwuxoe1w","Ihc7qwUK7xH+0MfmhuRNDGeQ2VQe7a/jI9jcYi0Mea0=",-7604462957428351910,-8160349326453721779,1822691602573059984,4640481809612985793>()) {
            case 1149629249:
               var10001 = 12.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s19pyl670yzlpd","hpYxaZtcNsGP59+Gi1uoc4wmbD4gl8ts6U2lv87ptuE=",-4406065821288651120,7520583528358409400,-3420736788974952279,-5180638296762498965>()) {
                  case -400276977:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"szh5sjynu5huc","btfnLB2zX/GfKc9Z+oSpob9OdIn5g3y6s/GERim5Muk=",-2934556147707538022,7947023514562459311,-3397131952374131498,-7146954342400875773>()) {
            case 1797045369:
               break;
            default:
               throw null;
         }
      }

      var10000 = var10000.c(var10001);
      if (var1 > 1) {
         switch ((int)com.yiyiaddon.m.b.a<"sxvqrgivjd141","Y4/zeGqRjm/qw1lJ9cU1aaHKugx3RIJQ9Cxkcu0E3Vc=",-7785730631645160738,6070044701992422158,-7710667650427867563,-3560453781916149213>()) {
            case 860103692:
               switch ((int)com.yiyiaddon.m.b.a<"s1tzxhz8uxu9h2","yeT/A9KaoopxpM+EqECAdBRELjo5ehNX7B8urCzbj04=",-4341960457913412226,-2726510326367032957,-2004558188725649159,-5361268189731873699>()) {
                  case -1064837491:
                     return var10000.b(10.0F).a(true);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3et6wf7l49a6n","bACxkh4Lwqu8F7LB9NVJEXZWCufsQRX5RyYzfkL9d7Y=",-6620654241310743559,-7978188963451247926,5417890544700492354,5232177758415443719>()) {
            case 992296699:
               return var10000.b(11.0F).a(true);
            default:
               throw null;
         }
      }
   }

   private boolean a(g.a var1, String var2) {
      if (!this.vg.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ivjho864kied","9igbzWmnmvIjYZ59RR7uENo3wk206ZlSkH2u1xUfKjw=",-6560935310561769528,3562805646763351719,-1460727241370601111,4836422177253570982>()) {
            case 1491609713:
               return true;
            default:
               throw null;
         }
      } else if (var1 == g.a.SELECTED) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bj6v2x53gbzd","cFqMgkGSc97oslTl52car3MFIG4uRtMSWW85H1iaUFs=",8732579274009712964,-3834194182486582218,7423475560255612324,2401620747200962066>()) {
            case -256408367:
               if (!this.bc.contains(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s156ckmm8sm7ax","C20PtAncy14o8txQgIggZCO78RcIi5gnzfxd7wr2osY=",3471820787850918774,-3017204781414335865,-1285070576240118576,8495120845244490052>()) {
                     case 1265630942:
                        switch ((int)com.yiyiaddon.m.b.a<"s3uxrsxl3l5oku","D2j1cCXf7SHwVki7DlwE0ii+GXay89yxNMFLN3Z/kNA=",-5141914167945569367,5997271243965153243,1483578808520453020,5084573166173009754>()) {
                           case -1600493398:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1x955ec87008e","xsVa5yd2Z42OkExzTNEcirtBpkDZGaYKkIF22B2zXSs=",9146653401553752824,125766596423455459,8389503473498315637,8719637285547090353>()) {
                     case -1237784717:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         boolean var10000 = this.bb.contains(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s2oh9ml9xvmbcl","4ehM3ZFChx8hp+o0wpdga149j+6MqB4+pTG1Ut47EZE=",-5633736312006341160,8263539392477083219,494649837722078240,8393547150838033116>()) {
            case 1921253423:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private n a(g.b var1) {
      return new n(var1.D()).a(var1::a).a(var1::B).a(() -> {
         this.r.accept(var1.L());
         this.kR();
      });
   }

   private n a(g.b var1, boolean var2) {
      com.yiyiaddon.l.j.b var3 = new com.yiyiaddon.l.j.b(
         var2
            ? (String)com.yiyiaddon.m.b.a<"s28gnpzqr9xty6","rVja+sDiMEVj7UijvhNlK3+T2R1ywd1EV+6Ic0gz",6417107697504221056,264182419970318153,9174992466141015548,-4876917958663124792>()
            : (String)com.yiyiaddon.m.b.a<"spju2teous2cf","pz1C8Gdgs0uN3V5/2U+koBPmYXDUDPEoiCsnKVCt",6053264773543431477,7882060091377151639,9104738854735085149,387332692121726463>(),
         () -> this.a(var1, var2)
      );
      if (var2) {
         var3.a();
      }

      return new n(var1.D()).a(var1::a).a(var1::B).a(var2).a(var3).a(() -> this.a(var1, var2));
   }

   private void a(g.b var1, boolean var2) {
      if (var2) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s3t9i5zt8s3meo","yEAgTtAxHAfVgZa/I6Z6xqFeseGbwIc/jv1dEJu7Rbk=",-2460253394127903848,4621846962324769500,-3555637361793093924,-7327221143375429207>()) {
            case -2069991077:
               this.q.accept(var1.L());
               switch ((int)com.yiyiaddon.m.b.a<"s7ptoqs58ef97","FKx+m8ziLWzF7e2IX9R3gVWRE/t9GgpVskLFwD0de6M=",-6816013827789679406,-5511174502464809297,4384404167732025786,-812413572423541361>()) {
                  case 213849827:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.p.accept(var1.L());
         switch ((int)com.yiyiaddon.m.b.a<"swbr5shri5z7r","KJNy4BL9uSj6RleB1N+4RlLPa4y0XvjZRKXi2V7NM6g=",-6060337616062952273,149006549985866666,-4886767213047248200,3313077438915302894>()) {
            case -670219754:
               break;
            default:
               throw null;
         }
      }

      boolean var10001;
      if (!var2) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s3jwbal73eptg4","hyxo93o3jR8FmHuRwbm+PrThkGkih/4UvaL2OFNJJ2o=",5624869125292195116,4409683971616711381,-5805181767304751560,8239045683615389109>()) {
            case 112641713:
               var10001 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s2hy9008c0n877","DR9+pzK7D2BteE2SoW8vFiXqZGr5tDvlXiwsnq+zQoU=",1415690145796936836,630490930990197030,2586248801278175454,6466621005257484433>()) {
                  case 1028681810:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s3u3u1f779k27s","AWPatZ42bRYSgCr2SpKeB1do8yw5nu10k9ddUp5fPQE=",4363786334476833124,-7441423201868368341,279944009206313373,6976034049440041825>()) {
            case -2036028264:
               break;
            default:
               throw null;
         }
      }

      this.a(var10001, var1);
      this.u();
   }

   private void a(boolean var1, g.b var2) {
      String var3 = ap(var2.D()).trim();
      String var10000 = this.GH;
      String var10001;
      if (var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3azemsmzmblbx","ry/9AxKKA20eHu0t02T9/NOfgdpEE6/qXWUdrH+L6Ic=",-3105600569985143986,3569157965592446885,5556816873107776629,-6080321050630025591>()) {
            case 281956124:
               var10001 = (String)com.yiyiaddon.m.b.a<"s202dflan6wkyi","LzHg/OyAiKG+7EzalMFTp9NK7/hCfGNFm3PbWOIlRYv1ZDUmwPgb4sUAe/Y=",-6803117697541252559,-1212924091590815304,5595844907472790210,-7202459131071730142>();
               switch ((int)com.yiyiaddon.m.b.a<"s387fqyq64e4m0","8XCO2JmKC9fcmKANhRz2ZXg1nfysXUu3F7KERy407Gc=",-4189798324345544488,-3868407625076911789,6648512213496455768,-277823962908094889>()) {
                  case 707314901:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s1647f11dg0yow","5gTosI7C89zELywqH3j64UnlSiph5Wl9O1+mRe50Lza/v+jCA8AIP9K9ovk=",-3617602227074420668,8927136706379757655,-5275957897088990800,-9120700540938909917>();
         switch ((int)com.yiyiaddon.m.b.a<"s2zdnahape0zu3","HfxoczAJmpmI3iQKGp2tC4ePaWIHZ72AuhcoYQcwO18=",-2863600475456502490,-5651219193214498865,-2704113378760854287,30742731401595670>()) {
            case -1786237610:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001 + var3);
   }

   private boolean a(g.b var1) {
      if (this.vg.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sg0o54o51ipdw","rXcE8bh94rbjP2QnIDT1j9St6wUn19XRWanBIMTU0Cc=",-8829885164322829435,6505060866160476376,196444261611941473,-3336089049493378952>()) {
            case 809435971:
               return true;
            default:
               throw null;
         }
      } else {
         String var2 = ap(var1.D()).toLowerCase(Locale.ROOT);
         if (!var2.contains(this.vg)) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s2130dtexex697","INeRYjSMKRbWdXlfZTxDZKBBsfkqfQd7hhcq/qsb0NI=",-7569053192194201670,-5294478067984797377,460749455808658081,-7586374317777143051>()) {
               case -193273298:
                  if (!var1.L().toLowerCase(Locale.ROOT).contains(this.vg)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3o6crp5lnmrs5","9eScc+8VRdRXmGRYCkjDEz+RWFAXI/h69FNCYBa0jVs=",-7110451334085747001,-7408507171999280613,6817545279037480314,-8314840338270598140>()) {
                        case 791442246:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2h2b39ci3udzm","uKbBe+rgTB39NIs9vGJNfkNCX40rWxlnGpMHyLP7QWc=",-7093492935271302494,1734098078565936001,-8367648713965750096,-6878418894272090400>()) {
                     case -1499804961:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s2u7fag50axotp","OgBtMakOl4ASHJmsWxKTJT8GsPfXXeinbS4bK6/eLNY=",-5974062652916623125,-2315479695511072066,-3188040179548811832,-1750591740189938759>()) {
            case -821410391:
               return true;
            default:
               throw null;
         }
      }
   }

   private static String ap(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pjvxeqvin6i6","zyYaQZGmubNeOocPqm7Qn/ITUFL+QqJGS2I6jafiiX8=",-8918560106291789469,7913721445005135686,-1536609823472551536,-2272582255636565828>()) {
            case -2029317263:
               if (!var0.isEmpty()) {
                  StringBuilder var1 = new StringBuilder(var0.length());
                  int var2 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2mxxmqvjg0ipr","iWN9FQn1zmOLsOJWghELhouckfRoK2MdG+bgwgItHaE=",6582166681046075682,-8547099799908741180,-8025366724828660678,5096040443571658345>()) {
                     case -308494309:
                        while (var2 < var0.length()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ldkbkwt2qsc7","VR/73GCIfRwF6mUgL1tzFRfeNU33wy+VKW6N9iFSNZg=",8295857967875203976,1672240224765386525,-7973938848677009454,1891284962595365364>()) {
                              case -1564556793:
                                 label56: {
                                    char var3 = var0.charAt(var2);
                                    if (var3 == 167) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2yd6a3uo46qfk","Tl+SMFbbuZKaev63uAj3y1YXDT190Dhs2SQuJyxMbXA=",8860502313813836020,-6857313376757916999,-7887593843604665825,-8988129654059695694>()) {
                                          case -812641860:
                                             if (var2 + 1 < var0.length()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1v31gyo60fa5z","CUYnYLLB5x1B4l5f9P4KFgwwIQ/JH3bMUUXIrE+oDrs=",4128269704624285144,-9023964794585076389,-4969745559607141727,-2311807316031941647>()) {
                                                   case -124549013:
                                                      var2++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"sy8wmx1h973zt","hN+a58W0M582MpH4TcgoCPW96YddWjMihbXfLg0ml5M=",6022952646636823645,1678754650774851182,-2626738394740684825,4969570041511572868>()) {
                                                         case 493846834:
                                                            break label56;
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

                                    var1.append(var3);
                                    switch ((int)com.yiyiaddon.m.b.a<"sl5erqeu26qvj","TTgJyrNF4ZImAmKLbvNnHCFhf6fhTaO4D1m13Fg1ry4=",-5915657013577852565,2113770377057803900,5137802122198704892,5502351823400723259>()) {
                                       case 1899634014:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var2++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s31b6bxo2phf1l","HeroNUiaYDr5mRpnpaTSH4/gy39ElnEPv4/zkf6cN1Y=",-697781340526556871,-7048097886816174250,8338883581828067541,673541298534949071>()) {
                                    case 1545373621:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var1.toString();
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s23jc4rzw3laxm","K3uVOrjKTYesRzj3zlbB23ppg3TeHHgAuGtfnJBQHCM=",3242532793851059533,5299792165214227823,4811122774083857142,9094068559166607526>()) {
                     case 1535304061:
                        return (String)com.yiyiaddon.m.b.a<"s1rqsu1a0kop1m","gzZBeNjfjzphO8Jv6UUigvkSDTXzqYVhVuVH5g==",8915044547096428329,-3326938568727127932,-4345746120026003887,5755067903937495743>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s1rqsu1a0kop1m","gzZBeNjfjzphO8Jv6UUigvkSDTXzqYVhVuVH5g==",8915044547096428329,-3326938568727127932,-4345746120026003887,5755067903937495743>();
      }
   }

   private enum a {
      CANDIDATE,
      SELECTED,
      PICK;
   }

   public interface b {
      String L();

      String D();

      String M();

      default String bb() {
         return null;
      }

      boolean a(Canvas var1, float var2, float var3, float var4);

      default String B() {
         return null;
      }

      default ItemStack a() {
         return null;
      }

      default EntityType<?> a() {
         return null;
      }
   }

   private final class c implements com.yiyiaddon.l.b.g {
      private final String GI;
      private final String GJ;
      private final List<g.b> dB;
      private final int uL;
      private final boolean gA;
      private final int uM;
      private final com.yiyiaddon.l.j.a c;

      private c(String var2, String var3, List<g.b> var4, Set<String> var5, int var6, boolean var7) {
         this.GI = var2;
         this.GJ = var3;
         this.dB = List.copyOf(var4);
         this.uL = var6;
         this.gA = var7;
         int var8 = 0;

         for (g.b var10 : var4) {
            if (var5.contains(var10.L())) {
               var8++;
            }
         }

         this.uM = var8;
         this.c = new com.yiyiaddon.l.j.a(
               () -> {
                  if (!var7) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"sr3fbjrhq06ci","ImUZP8KY/SP/TuBOE1t6DuqUUsP2jfvZYSJAADLQ8O4=",185066670007693644,6081911289964172752,-1379976688923696772,7833367122760076717>()) {
                        case -955425088:
                           if (!this.gy()) {
                              String var2x = (String)com.yiyiaddon.m.b.a<"s1q3y9780xsajc","QZJQtN/6vi/VddWuBwip8OuO56+asuF4tLdk6M9MJN0=",-7451557868430219563,-1361115602610430258,-62543849117338097,-5940841609765723296>();
                              switch ((int)com.yiyiaddon.m.b.a<"s1g0ba59dfbjd0","mIuLGFfdLwGmj7yjKeZ4Yq5gAUkkG2kVVQYAGUlqk0w=",-3087368535585765277,-4417250881978749639,2022497110226566043,-8249625602854764470>()) {
                                 case -282960771:
                                    return var2x;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s9bioxlaw2wgd","96LPP9CPTW8NQ0nFCRrmne77RFTh3kOo8IbYYSz8HJ0=",-5110146272820386999,4472796534754534742,-7451057407234253672,1223117009642373746>()) {
                              case 591568281:
                                 break label22;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  String var10000 = (String)com.yiyiaddon.m.b.a<"s3tghjxmq6hdl","KsfXXtYSWj4kv5udzFEa6GfFBPU0m0NaN0GTxAflO1E=",5233920345694101170,-1996128474571777358,-6522952420579810908,-3445876725846805215>();
                  switch ((int)com.yiyiaddon.m.b.a<"soq7o9ipv0lt2","Cok9sKBC3sCnoB/7YquWiHglt19n1xSHeB1YFObU+Rc=",-6518605505170735356,9120589618048221796,6976628379787537430,7941859130204914316>()) {
                     case -958554235:
                        return var10000;
                     default:
                        throw null;
                  }
               },
               this::kU
            )
            .e()
            .d();
      }

      private boolean gx() {
         if (!g.this.vg.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2nz9nwz69wqss","r95/4RPxVblBTFECEj541AYrnHiOGr793JwnyKVgzyc=",-996289690881973942,3098501801524214760,2256605828145336972,3726040556431044193>()) {
               case 248525842:
                  return true;
               default:
                  throw null;
            }
         } else if (this.gA) {
            switch ((int)com.yiyiaddon.m.b.a<"s20qmdm86ye2to","BdeXwFKuTiqa65zcPltjtmqnufIZH4OFQb7o+ZRuOKE=",-8832394767709710854,-5649091291112195155,-7471334343984641345,-8290950949764944142>()) {
               case -110967841:
                  if (!g.this.bc.contains(this.GJ)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3n7v85szt2go1","1/yFuSfNftHMev1lrHH+9nmSMVyFKZ71wc5WlH8fag4=",6041026897945136711,-336832288204329396,2766114838295583232,-2345559526936265136>()) {
                        case -822384260:
                           switch ((int)com.yiyiaddon.m.b.a<"s2b3hwj2qj3qe9","KF7DKYZQyw4cvT8hm/pb7NGbfJLsdcMSM7h/PQoqTgQ=",-6434658458089750675,-7916406171832792454,-4222863457297144584,7161695803478134702>()) {
                              case 1336753588:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sv3178ln3bmmq","NaQwh251ftnpzts4xAFng+KHW7fFMaVQnic1KXZF5qU=",3838714974940952201,-3419421580690578345,-1717113447783672515,-821724835890308461>()) {
                        case -893469691:
                           return false;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            boolean var10000 = g.this.bb.contains(this.GJ);
            switch ((int)com.yiyiaddon.m.b.a<"s3et3bu85bs5n4","FHhmglzwHjWJW3JAuz4CoHyqLylI9F2vVTc06GuXJiI=",5474060605607772263,-7370976668236236799,-5734500504718244888,1655228068313562088>()) {
               case 1675575690:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      private void kT() {
         Set var10000;
         if (this.gA) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s3hhviqh2f3tss","EKw0xz/sxIosTm2LJk8Y0lSB4PlEX76Ud1hRkEeWaUY=",274370286629081604,4689300296724978016,-3015190706195448236,-8661594202465722517>()) {
               case -116074316:
                  var10000 = g.this.bc;
                  switch ((int)com.yiyiaddon.m.b.a<"s274js4bfyc4du","1YMBbZaMIuWOGxoTTmPnSOlPNGPvcS9ULTVxpDnD/54=",237811574794299422,7785795405972160932,-314958562177559614,5544717550215372423>()) {
                     case -2091264458:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = g.this.bb;
            switch ((int)com.yiyiaddon.m.b.a<"s2xuulq6nf1r9j","B4wRhEPE5cK0fXWykAlzldZDlzGUyVtyfTGcaUAnUIs=",-1369521583102205364,8351251163931807570,2837783172676985099,-7159153651126930538>()) {
               case 2084859263:
                  break;
               default:
                  throw null;
            }
         }

         Set var1 = var10000;
         if (!var1.remove(this.GJ)) {
            label20:
            switch ((int)com.yiyiaddon.m.b.a<"s1zx90bto1q80w","d436WudCkRTd9TqKaGB86tA1wGPK6onCXovHOyRR/f4=",4199797262040707579,-6896836541043419345,841498659614222051,-4880540152033728553>()) {
               case -1573969919:
                  var1.add(this.GJ);
                  switch ((int)com.yiyiaddon.m.b.a<"s15bccyrer2jg2","bkfUxL9lKjE1lym2271TNDzq+g0wBlytzWhk5FAmeVg=",-198272241188017109,-3537083296799841496,-678014872028872813,3144006110417450395>()) {
                     case -654879833:
                        break label20;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         g.this.u();
      }

      private boolean gy() {
         if (this.dB.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2zht538dhcvow","oMYoFoYbyxKTbVK4ztVfu8jnQiJtiw9pWauqPIh5vEI=",3137136144605113034,-842056820427720917,-8396211672379079237,4428383491520756226>()) {
               case -1397605422:
                  return false;
               default:
                  throw null;
            }
         } else {
            List var1 = g.this.B.get();
            if (var1 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3p2sno6d28mxr","Zo2fqBBiZt1lqtVIFSRVb/To6Ny00wxneVzgIRtrv38=",-4122901868358457966,-4238810553872206049,-557284256162528069,-2534237411237342691>()) {
                  case -285509217:
                     return false;
                  default:
                     throw null;
               }
            } else {
               Iterator var2 = this.dB.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s1zw19tfw8io15","Tzkr+mV08K+15Hvxe1EOu8/BDv/0lxr6d77/fSNttfI=",-1984813505622542013,454131807710869135,8105384380529227031,-2026770943729321807>()) {
                  case -1646990401:
                     while (var2.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s97o2as90sylw","HXFBiAoDsse86DejhwbQQsF2mcMaU5Qez9lsmyE2HLg=",7833609861661004605,7151746521874114635,-1052735755880955673,2392875033247880752>()) {
                           case -1623286972:
                              g.b var3 = (g.b)var2.next();
                              if (!var1.contains(var3.L())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s69npa4ulnq9c","sKLhWbIUCJ5PujmMDZoiGDQEL4HZtWCx+k4evL7Zk24=",3770800835543431962,7548438773787178609,-6648128556468667792,5706580105420799771>()) {
                                    case 718738020:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3c2ktzqqxen1t","fqpEdsW1A7btAvw8SY2dE//ozSuApf/bsl9lO1STSZI=",-6957867200291735776,-561781087831630702,572372841397204931,5300291370860501133>()) {
                                 case 1710968983:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return true;
                  default:
                     throw null;
               }
            }
         }
      }

      private void kU() {
         boolean var10000;
         label61: {
            if (!this.gA) {
               label54:
               switch ((int)com.yiyiaddon.m.b.a<"s2laxn1u8znsra","h7EeTDfaL169FezA3zObIapBoZLK7yvtgQwzGzyMDGY=",-4572777407678327707,5283287184741735043,-1414427973489833006,8355297911105956351>()) {
                  case 624330181:
                     if (!this.gy()) {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s2levk59fega3m","uwIUocC7JHROH0i0QibhN676/zpW+13ok97FQpKPCgU=",-314160754043448304,-1199356084163596900,-1306921139004487742,5086160803215994894>()) {
                           case 1725917652:
                              break label61;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sffbs48mibyjl","YFUfHtTJpzFaK7oextXwhhjf9mSZWMv/7XzQRmJb7cU=",-6285069356154750903,-6862904095779063642,-9038516077516471709,2631040037765882167>()) {
                        case 1197722700:
                           break label54;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)com.yiyiaddon.m.b.a<"s39cnoxfgysmqv","gsYfOpwsCmr6zRtqR6SIjs7R1FnaqMlyPAcMgOY29bQ=",-7224488700231580199,-1588952843808717020,9195083204018898626,-6974606015302418854>()) {
               case -824424670:
                  break;
               default:
                  throw null;
            }
         }

         boolean var1 = var10000;
         Iterator var2 = this.dB.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sttcj6qyjhayz","NP6eTUbAFn8OZxUgS9rrf7mnibf/cLIch8pSgG0pOGE=",-6766582830453953226,7519490408950852684,7147930251294486503,8326175694378445938>()) {
            case 1600498320:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sizvq8akjujoc","drY+5dJsdlBwlRRusqA2Yy5KX74gi3LmeN5Or9sXcjc=",8300285479125938600,-9104402279624101189,8525207454761484630,7296571789263662104>()) {
                     case 7624087:
                        g.b var3 = (g.b)var2.next();
                        if (var1) {
                           label37:
                           switch ((int)com.yiyiaddon.m.b.a<"s3uwb158tmhjxf","zE6dYB1y7B6ko5FOyX6hM3BJ47XlW4bvbpQcGrZLi5o=",-5588649086168184270,-3787553670841661858,-3031791302721831117,7451988656085983042>()) {
                              case -1430344677:
                                 g.this.q.accept(var3.L());
                                 switch ((int)com.yiyiaddon.m.b.a<"s2olmxd62gbwat","Eq2WeHV3RbHVBKTt/97QG8HIflHzf3b1gfUn9XDA6gk=",-391071531703862860,-8003297014756949394,1061691759831098278,2122264478987338903>()) {
                                    case -1293558078:
                                       break label37;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           g.this.p.accept(var3.L());
                           switch ((int)com.yiyiaddon.m.b.a<"sg4ls5r4s1uc3","Ua/pfvTC3yA/4oY0jChKxgtAJpfrlYsSSCezose2bcY=",-3054827014536859013,-8573991170385120396,7914436815577585858,-1146417013866568293>()) {
                              case 1120957556:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2rhappt64k82q","u9p02v79ezWGXlNPJkHL6bM0FTwETiE8VefR43HCJAQ=",3289424692216426047,-944353676011785870,-887969030633197209,3346453955214099033>()) {
                           case -1277697233:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               g.this.u();
               return;
            default:
               throw null;
         }
      }

      @Override
      public float b() {
         return 22.0F;
      }

      @Override
      public void a(float var1) {
         this.c.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
         float var10000;
         if (this.uL > 1) {
            label53:
            switch ((int)com.yiyiaddon.m.b.a<"s2az82b7wbcrke","LxOJ5FVm1v0PA5masd9GsSXjqdQQO7HIhz6JNRa7ehY=",179441866533424499,2886444823106030009,-4640128507335714592,-4234764240635903169>()) {
               case 1715039200:
                  var10000 = 12.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s2v195q52s5wth","QqMv5jGVpNRXfMEdb4HmqvVOp3SbBc1vPP1ZjkK4DFo=",-3258326419585237259,-3279080943641819546,-1385261548147928917,6928919109643799900>()) {
                     case 1376320122:
                        break label53;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s16ao3r9jtkua7","+jOMNFSvhk54GjkjtaeWT1MqO/N4UE6JBhhmf7be0pY=",3825201534404957608,7760549820750314925,9209020751512662755,4985904647102128065>()) {
               case 1861877653:
                  break;
               default:
                  throw null;
            }
         }

         float var9 = var10000;
         if (this.uL > 1) {
            label46:
            switch ((int)com.yiyiaddon.m.b.a<"s2vznunwpvnhvn","2brmxyU7Fi/vJ2FPdd3r95EuXbAhtooWmttlqjiMpcM=",1548285232260267160,6264465029804112021,6015403425727932453,-269126241245295906>()) {
               case 256271472:
                  var10000 = 10.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s1rp71aq511n9r","aoxivrv09RQQ59O8Wa6pnR1t+g0I3G6lBN0ElTx/30g=",-2320051356736585488,-7319682134151978474,-6073164204165394703,-885569536701720527>()) {
                     case -1663296804:
                        break label46;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 11.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s3q5dqwk9zdxpb","46+L3RH42ZVM7nppj5rWRyRhsAmTupj0KZZVshNiSZ0=",-3276250013240748929,-3186315076284556097,8073582408609676332,980716065863221190>()) {
               case 1445178943:
                  break;
               default:
                  throw null;
            }
         }

         float var10 = var10000;
         if (this.uL > 1) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s1jdatkbaprusi","IQGVUXyMtz1xQU2UMyvCzvlVsrbnJUOmsogkJbi2EvU=",7206007930525160143,-4340724718720366052,-2674498213947408383,-135966661068469837>()) {
               case -1256944600:
                  var10000 = 12.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"su24xqtc88jb1","0a1tvVqFw2cDyUaw3iSH0LI4fgdNzD6hlAppsg32tKY=",-261699973591719973,7436547513246223507,397064859688103246,6026499317483033341>()) {
                     case -1458129374:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 13.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s2eu3bxkmf4qhz","rcnZJyGLPuums4ByLsV1eg+z7PKq8biqeKdPjzh5dPI=",2974833806404843203,-2719683324474547461,-7349662020412526039,-8442683310418925824>()) {
               case -1980431826:
                  break;
               default:
                  throw null;
            }
         }

         float var11 = var10000;
         float var12 = this.c.c();
         float var13 = var2 + var4 - 6.0F - var12;
         float var14 = var3 + (22.0F - this.c.d()) / 2.0F;
         this.c.a(var6, var7, var13, var14, var12);
         this.c.b(var1, var13, var14, var12, var5);
         float var15 = var3 + 11.0F;
         float var16 = var15 + var10 * 0.36F;
         float var17 = var2 + 6.0F + var9;
         var17 += com.yiyiaddon.l.g.d.a(var1, this.GI, var17, var16, var10, var8.uT, var5, true);
         var17 += com.yiyiaddon.l.g.d.a(var1, this.dB.size() + "", var17, var16, var10, var8.uT, var5, false);
         if (this.uM > 0) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"s2wh4cr6z9eglu","oWIHkhSEucOswLQeRj5CWJvTKLyiuQio4UAoKKNqVK8=",-2928660050013024899,4191728075510293578,3219752316211899416,4712507804987335119>()) {
               case -1151954984:
                  com.yiyiaddon.l.g.d.a(var1, this.uM + "", var17, var16, var10, var8.uT, var5, false);
                  switch ((int)com.yiyiaddon.m.b.a<"ssufb8jxxbdzg","3ElGIZ9OYgIh0DidaTGvdYPtkL0W7xQpdzlVDywfApo=",8158646645903818576,4351097847926502514,-1796314823758340465,-8757423980345754109>()) {
                     case -1964782805:
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.a(var1, var17 + 6.0F + var11 / 2.0F, var15, var11, var8, var5);
      }

      private void a(Canvas var1, float var2, float var3, float var4, com.yiyiaddon.l.i.c var5, float var6) {
         float var7 = com.yiyiaddon.l.g.a.a(
            (String)com.yiyiaddon.m.b.a<"s297kx95witx8o","sC0pT/RxHJHFzAkQlmvTa5ZPOxmTCoGMJTzxkdWC",-5033052738232435372,8144981416509022871,-6214141593439705434,-2111953951796865293>(),
            var4,
            (String)com.yiyiaddon.m.b.a<"s1nvrxblbwh6p","NLghDa+5g8tJwD9eqF/DxnFUiJAoV8ij85YfmcPH3Zp/PpCL/PI5bDIYY/pRspJSjr1/pQrTyHXAHjjO",4166339103302225440,2501945328417715309,-8670602315554223354,1838109399263286866>()
         );
         var1.save();
         var1.translate(var2, var3);
         float var10001;
         if (this.gx()) {
            label15:
            switch ((int)com.yiyiaddon.m.b.a<"s33nxh60kp3rlh","hVahBTokgukD13Iq33tVNk7AfDYwq2KiFbydoIQ+Nkg=",-1733335082361466035,6144786135075168865,7031288444267818957,-4783815185297186116>()) {
               case 974534028:
                  var10001 = 90.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s2bp5976uoxbif","aZ4a49HfLAu08MY3wMzcPMnVR4V2tn79cte8sc7lEjw=",-6400443287131146828,1758841732315341439,6364478142019716861,-8111192956361845507>()) {
                     case -218880779:
                        break label15;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s1y931cfstf9xc","VTUha1GnC6GYTlonmH+A2hkcd2BI9kAOF7QkCClg7n8=",-4723584662093133758,-377025316329608966,-1499840086530832942,5267320410068869687>()) {
               case -1872196252:
                  break;
               default:
                  throw null;
            }
         }

         var1.rotate(var10001);
         var1.translate(-var2, -var3);
         com.yiyiaddon.l.g.a.a(
            var1,
            (String)com.yiyiaddon.m.b.a<"s297kx95witx8o","sC0pT/RxHJHFzAkQlmvTa5ZPOxmTCoGMJTzxkdWC",-5033052738232435372,8144981416509022871,-6214141593439705434,-2111953951796865293>(),
            var2 - var7 / 2.0F,
            var3 + var4 * 0.36F,
            var4,
            j.a(var5.ve, var6),
            (String)com.yiyiaddon.m.b.a<"s1nvrxblbwh6p","NLghDa+5g8tJwD9eqF/DxnFUiJAoV8ij85YfmcPH3Zp/PpCL/PI5bDIYY/pRspJSjr1/pQrTyHXAHjjO",4166339103302225440,2501945328417715309,-8670602315554223354,1838109399263286866>()
         );
         var1.restore();
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (var6 != 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s2833isx8f2dtq","A3fV0GFZCN6W3uOruDrNL2Z//hXKAifTmEOSeZkvWp0=",8889993179626313090,6658652236877771043,-5379660183538853864,3432970343672023096>()) {
               case 172086705:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (!(var1 < var3)) {
               switch ((int)com.yiyiaddon.m.b.a<"sa03ly7fesdp6","jJDA+27O1yjqhPznwyBSJ2TZagqAJJo/LMnyFtBhMaM=",1073518705968221413,-5292968794808571593,-8093538705483156128,2738197757704033098>()) {
                  case -917008431:
                     if (!(var1 > var3 + var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2mkb4v8xxp3ja","mbtQLi6ECPCJwDz5WfSrmQrLbAi+pnl7i+sq6bE5WGc=",2876644802021761023,2604038324855596377,-6510878349503791361,6757500638157516815>()) {
                           case 333448823:
                              if (!(var2 < var4)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2rcr2xp2xdogo","335EMnSy9ibMjRpHp/1CZITdidUR3PoMK1hHVf9Weqo=",2491108086364910824,1717824216464540393,1665728938861282900,8177515309818902007>()) {
                                    case 631734769:
                                       if (!(var2 > var4 + 22.0F)) {
                                          float var7 = this.c.c();
                                          float var8 = var3 + var5 - 6.0F - var7;
                                          float var9 = var4 + (22.0F - this.c.d()) / 2.0F;
                                          if (this.c.b(var1, var2, var8, var9, var7, var6)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s4pntkupr5vnd","H9CrQZIHvkLtVMfFPL1S6U7pEEFjAYV4F8rEW0lk0rA=",-4944208190510180624,-3662784574419533666,-6608919616425322824,-1222212136405313685>()) {
                                                case -1369141495:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var1 > var8 - 8.0F) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2f21oadio4z1u","Yc49uqrdGMSaL6W9GBquHnCh8cTPGEhgksyIIacH/YQ=",124629858625244784,3542035934309125042,8455027144760380446,8614718869215142422>()) {
                                                case -1227620711:
                                                   return false;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          this.kT();
                                          return true;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2oxjzan65stjf","6dYIRMkbESc4ckSOQjcYrWm9+BE5JJuW6R6AESZ6nVQ=",-7882044909507177621,8875857529284031239,7703258317408888751,-1030651944003853841>()) {
                                          case 1879408992:
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
                     break;
                  default:
                     throw null;
               }
            }

            return false;
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }
}

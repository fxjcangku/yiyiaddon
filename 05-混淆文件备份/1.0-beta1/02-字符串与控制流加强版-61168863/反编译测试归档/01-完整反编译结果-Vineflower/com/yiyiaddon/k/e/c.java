package com.yiyiaddon.k.e;

import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.q;
import com.yiyiaddon.g.d.f;
import com.yiyiaddon.g.d.g;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.ClientboundResourcePackPushPacket;

public final class c {
   private static final String ED = (String)com.yiyiaddon.m.b.a<"s3ishg525ffb92","VPlNkA88ESVlP0wPmDtkQoB9KppO7AIEVkFak1XqW2C+verqVHQ=",3397759027167554786,-6175440991097111158,4567414772259002252,1011890479395067933>();
   private static final int sQ = 200;
   private static final int sR = 3600;
   private static final int sS = 400;
   private static final int sT = 5;
   private static final int sU = 60000;
   private static final String EE = (String)com.yiyiaddon.m.b.a<"s1uyhisjvy22np","KFibYFtn5bVJ12FJo1bBPuqWFC/Mam5RGkrtzRJ76fQsH1HMwdQ=",-5863008051685235022,-3382749212442662655,1033655474852034969,-2671231879028936814>();
   private static final int sV = 9;
   private static final int sW = 4;
   private static boolean fy;
   private static final Set<String> aN = new HashSet<>();
   private static volatile com.yiyiaddon.g.d.e a = com.yiyiaddon.g.d.e.IDLE;
   private static volatile g a = g.NONE;
   private static volatile String mS;
   private static volatile String EF;
   private static volatile String rJ;
   private static volatile String yQ;
   private static volatile long aZ;
   private static volatile f a = f.b();
   private static volatile UUID d;
   private static volatile String EG;
   private static volatile String EH;
   private static int sX;
   private static boolean fz;
   private static long ba;
   private static volatile boolean fA;
   private static volatile long bb;
   private static volatile boolean fB;
   private static long bc;
   private static long bd;
   private static long be;
   private static Set<String> aO = new LinkedHashSet<>();
   private static String EI;
   private static volatile b a;
   private static final Set<String> aP = new HashSet<>();
   private static final Queue<String> b = new ConcurrentLinkedQueue<>();
   private static final List<Runnable> cW = new CopyOnWriteArrayList<>();
   private static final List<Runnable> cX = new CopyOnWriteArrayList<>();
   private static boolean h;

   private c() {
   }

   public static void init() {
      if (h) {
         switch ((int)com.yiyiaddon.m.b.a<"s9p5dzs4zinln","iBPJEEGhWdSO3UgG0ZNC6/dQz1U0+5bieDVUIGHO8AY=",-2150449747316196279,-7829210598869879340,-1958469888881566354,6157512699322961020>()) {
            case -985607902:
               return;
            default:
               throw null;
         }
      } else {
         h = true;
         e.f(c::bq);
         ClientPlayConnectionEvents.JOIN.register((var0, var1, var2) -> bn(gx()));
         ClientPlayConnectionEvents.DISCONNECT
            .register(
               (var0, var1) -> bo(
                  (String)com.yiyiaddon.m.b.a<"scpatoorq58mj","OnbYCuHv6GuiR+fNJf4aBOFwCrI5IDuwksYaSNR8XYAeBN3LrgU=",4197508881147944777,681819046905000832,-8341514309788921443,9136583415143967671>()
               )
            );
         ClientTickEvents.END_CLIENT_TICK.register(var0 -> {
            e.ae();
            jC();
            av();
         });
      }
   }

   public static void a(b var0) {
      a = var0;
   }

   public static com.yiyiaddon.g.d.e a() {
      return a;
   }

   public static g b() {
      return a;
   }

   public static String bT() {
      return mS;
   }

   public static String gs() {
      return EG;
   }

   public static String gt() {
      return EF;
   }

   public static String dn() {
      return rJ;
   }

   public static String gu() {
      return yQ;
   }

   public static f d() {
      return a;
   }

   public static boolean fr() {
      if (a == com.yiyiaddon.g.d.e.READY) {
         switch ((int)com.yiyiaddon.m.b.a<"s8bo72kg8oocn","mqsX5mIztKaJNSmTZb3GlpTSp+SHNv9CNKj3TLO0lUU=",4796278573664946621,1001915741612833434,1700142775595790487,-7694542657674459050>()) {
            case -1973481086:
               switch ((int)com.yiyiaddon.m.b.a<"s1piokxplsabm0","tB/Ptf/qNpjcPcaqVYQdUQ/aqM7KecVnprPppQ7h85U=",-9200266476304024625,7011971175446317062,8033983242150848865,8562352782619754889>()) {
                  case -67244066:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1qfjkrfcoducp","2oh7kni3JoGeqkbVrMEr6Jb3Pjqy3ccjh5iZPN24SfY=",-6526410899650406378,8884922575792187015,-1335247984850984656,713991231878824207>()) {
            case -1223100709:
               return false;
            default:
               throw null;
         }
      }
   }

   public static boolean fJ() {
      if (a != com.yiyiaddon.g.d.e.NOT_CHECKED) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"somjbzbhigrgs","kiu9wWE9+4sIWckSAtPBBIS3gHDvHF55EJDSbC/EmJk=",2866312772367133045,-4762835816846689452,-1714422044347987792,2817566893203096272>()) {
            case -1927880351:
               if (a != com.yiyiaddon.g.d.e.IDLE) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bf4ok1fknei4","UilyT8Sw/1wVr6zW2d2Tx6bBMmFfNnUvxHYL+5e6zig=",727454658041072351,-5033014286650456489,3702472739578711063,1204513654639826665>()) {
                     case -489270061:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s12m7vk0gl4e2q","6h29mJOXUU8vdMOQPndIAJjthMOMrTuirJYnhEvlpT4=",-997796774747180525,9206244221615223206,-3886601249614614496,-6967285437159781597>()) {
                  case 1445913508:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s3vtm0i668h0fl","JAHTV7biFdwgEi94MPZR5BDBRGsGn39uHToMeyNccGc=",7486945952149611962,-8246949487062607834,-8231091348202101773,8499891465197409084>()) {
         case 1873005518:
            return true;
         default:
            throw null;
      }
   }

   public static boolean fK() {
      if (a != com.yiyiaddon.g.d.e.CHECKING) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ngrqjsydvh76","N2v6y8yhiwh0xRyZgPkvk0j4SU0UoXJcyILYkRHLJ+Q=",-5742717062148977040,7260582096155844750,4460807551784643313,4005673613133963973>()) {
            case 1814953121:
               if (a != com.yiyiaddon.g.d.e.DOWNLOADING) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3rs3q83f83s9x","7hFia7LxNNA5vfc9idEFMmYWRBgMYhand3SKHN7SOvc=",1434070083999067823,7399973763805698328,2101844220099078310,562042494393426199>()) {
                     case 817087288:
                        if (a != com.yiyiaddon.g.d.e.LOADING) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ul9fnojsahuh","06dpg7l92Hi7s99Ulb6rP2cxIG+NQrVjucHDDnMvv/Y=",-1097107346145783189,-6583432094564889488,4741172928142927923,-8439062790088762023>()) {
                              case 1861309279:
                                 if (a != com.yiyiaddon.g.d.e.PARSING) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s6aiq5308ub31","CUPlp08GhKV4/LrooTSWGzIVnjwHUsn95ibUwKEmU9k=",-7163131483676741090,-8658762488864196253,-7239923786534898440,-1566141076004338652>()) {
                                       case 738777041:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s37fqxs9b7q6nn","UuZegRcMpZ2w98qb1WrzCkFnd7AjzlyxsP+puBh645k=",3780667087982401411,2251052621781403011,-3330807492847594058,-2573904993044221159>()) {
                                    case 44824902:
                                       break label28;
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

      switch ((int)com.yiyiaddon.m.b.a<"s3f81j2njs0egk","QDMD2QXf3gypDj5mgh22joALLIOpSBC7KRbLAK67LNA=",-5806580747616215583,6289026686566989945,-8908772005062411299,4573483350957197362>()) {
         case -897096586:
            return true;
         default:
            throw null;
      }
   }

   public static boolean fj() {
      if (a == com.yiyiaddon.g.d.e.READY) {
         switch ((int)com.yiyiaddon.m.b.a<"s36hfn2i4oiz7u","r/ZWWJn7axwckXbtgmDzgBHGU1Wn1Xuh1hMbXs8rWZk=",6388863834181116722,-3751051747481908433,8456303885298345650,4719352593962595202>()) {
            case -381834022:
               if (!a.a()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s32u6duyreh8u","Hn+xSPesxKDAGjpvcERLokHF4cCP9yeio+IXQYv4gG4=",5821096068700172092,-8467896588010159316,-1412032229132220908,7213915008575946932>()) {
                     case 1855501877:
                        switch ((int)com.yiyiaddon.m.b.a<"s22w6t56y9c5yr","w9fo9R0sKwEtR8bjvfayO0CR9PxLLUqbwD8QHoG3rNY=",-4969756566320383201,-6012962984261321650,8126969404930018553,5186860750409694230>()) {
                           case -1353595241:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3g1aggd6rjwnj","6q6CNlyCZseEF5WN12k10Zfb32+UJD+eCgdN89EijZs=",20828643221163335,-3489628080859899518,-7510335956775700178,-7552505494071866750>()) {
         case -34855819:
            return false;
         default:
            throw null;
      }
   }

   public static long B() {
      return aZ;
   }

   public static boolean fL() {
      return fA;
   }

   public static boolean aN(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"syqlobznej4t7","+wJeY4kP4bM8UMOnziPc5Lhkoq4PKuRWEVgch1nITJM=",1037149450895023732,-4219309787378217922,891357634529463801,1865613190588107700>()) {
            case -2000969852:
               if (var0.equals(mS)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2r05l4f49mzod","Kj6EyQWpPr4uTikLYVjrqymKZo5uhqqBOStqyyr+kOU=",-970711204374475443,7774126035521303245,3730942827376445798,-8328979337450598468>()) {
                     case -368642829:
                        switch ((int)com.yiyiaddon.m.b.a<"sn1fl763wqmww","CEbczdBTfsOVeIbouNXqtdRsGhL372DX8NcaegzSUdU=",272252969474931240,-4607943790187098013,-5434306682937887121,4218930315912311863>()) {
                           case 1529391921:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2b6cspfg3d1pz","CDHWv4I5gt6MfoBqvkGggACMXP9lN1923Uq1bNUeTxE=",7725961285959564966,3900327036005042054,5891257431541356815,-3318302559812763775>()) {
         case 320166902:
            return false;
         default:
            throw null;
      }
   }

   public static String gv() {
      if (EF != null) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"s2am6zmpw3bi7r","eGJjZwwykAqKKEJT3t8+ygZxaK6MpFyz9F3vb8lkf0E=",-8102344531817513462,3908968241001851519,-5984179025698794823,2753298691610969379>()) {
            case -1209622249:
               if (!EF.isBlank()) {
                  return EF;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s6pykkeueg6ms","04Dk9g2Vf6sa4ZtIG+xwOpshwPoNFMzWjfZFKWIZqcg=",1857674438782396130,-3266391958493852475,1365089562400860132,2544302502048522603>()) {
                  case 89370515:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (fA) {
         switch ((int)com.yiyiaddon.m.b.a<"sz6unwx32fbuc","YtjFXoCImVihPjpuWgcNW4bLiT1ul/ZIuNm3FH5bMLY=",-7707532674784429542,3202762703065905272,5512137513006566955,-7225276749230641142>()) {
            case 181873125:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sb8vbwgwadjbr","E4D+IHh+okUaDvJREQt7DaeVUXZwxFV0UMFIjr6M1MbAXU2lYf7jeCXtbb1uwg==",6444982825523077639,7227134361141931540,-4043282064417824259,-8351279511068449264>();
               switch ((int)com.yiyiaddon.m.b.a<"s3gthofss1ipfo","VvAhD+SRXcQUz7y1sDKN1gAUBs0rvghmvw8AjoANstY=",-1742579517051084401,-2206362324382122263,148195451245001275,3615251960601131458>()) {
                  case 130583733:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var0 = (String)com.yiyiaddon.m.b.a<"s2bncnltp2yspr","pKmGwBuWGXelGQDuM6VQOim/FsUdROmNz9+eIUQ+t/ezWsl5y4GTVVEP",-2428715933324840912,-627188049101004430,-8591705023709250153,-2294987747653612026>();
         switch ((int)com.yiyiaddon.m.b.a<"s219h7voxqijh0","WA6uBUC8I1QJR0EkzhBSHzhy//ru7iX49BRs6k+Zdiw=",-4071484295328159206,3626386164218942230,-4147635037448937557,-1905041476664378863>()) {
            case -1394027706:
               return var0;
            default:
               throw null;
         }
      }
   }

   public static String gw() {
      if (rJ != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s2eb868mw2n41o","NLLmTRjur1Rz2W1iGi28fAWtIlI98YWj9hJu1noJAe4=",-1084543339402141449,-8797383436274625925,855375604092877966,-7957662995319248>()) {
            case -1393164799:
               if (!rJ.isBlank()) {
                  String var0 = rJ;
                  switch ((int)com.yiyiaddon.m.b.a<"s3ejtq0krthq60","shMKJBhnlGk0YTEPEYx2zyFWiHF12mMsCK8mTMPPutI=",-2862714547020793376,-2889902874250358480,2589805208674907729,-7176098972245132671>()) {
                     case 1195247145:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3jxy9wql1vhe6","mh3tHnGftjG21EVZSXF2JtXoLAMPwfC1m1hOzaKtkIQ=",5387816356427771187,-255879917493526352,-8859874781275230322,2087459491403613241>()) {
                  case 1952812490:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s327wfri5e8ri3","q6rUcPuxqk5xBGOnN4BCMqBs9O53Z5tT0D1AbWEXwh0FEw==",6483134435793739220,-5209368528568567873,1248613478594315655,5441907795076244194>();
      switch ((int)com.yiyiaddon.m.b.a<"svo782uyelqtt","PMQr+Ond4TRHdLQBF68x99hOnePRsNYXAfPIOsctNnI=",-5511219332860026991,-482729851431616630,-2071336137851623282,-6994852954679962253>()) {
         case -670475483:
            return var10000;
         default:
            throw null;
      }
   }

   public static String dS() {
      if (!com.yiyiaddon.i.c.fo()) {
         switch ((int)com.yiyiaddon.m.b.a<"sntcivmrpvpeo","hxRwxfbkQdNVel6BBxV0d9nizxHmL1ngMNNdjU9KfCA=",-505943615544019994,-3069128650540346636,-5363041536143268734,6661057155505589090>()) {
            case -1732707446:
               return (String)com.yiyiaddon.m.b.a<"s92lt82us9tgh","zMIdeBKaV/n6EdJPb2FY25nffQFDQQEMmy0INdSXzt6x4P39x2g=",7896744347540891986,-7643053592988304090,-8020434481305926152,-738485578982109889>();
            default:
               throw null;
         }
      } else if (cH()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1btxmh091zrl4","R5ubNaAeWUGB4O+xBIwzrDL/UPp3PQDO5IdlbSnHLRk=",-2321205907560356041,5614337292427279001,-4088261074279855458,-8806131729288394309>()) {
            case 1593553674:
               return (String)com.yiyiaddon.m.b.a<"s1ivkscsoagmsl","35Icpgf4Yw/jdyPP0bZMnUu536QYcwigpPtDHgw9/4BMg6hvWTrXryyhxSqqvA==",-1646650426943113017,8602581200604490072,1803154065240257410,-2139173721575562570>();
            default:
               throw null;
         }
      } else {
         if (fA) {
            switch ((int)com.yiyiaddon.m.b.a<"s1yttaajx8yled","LYK2fBZs3IBSsd1yW2WXQq8nmw/WKVqYIWeGlHy0igg=",8754792598696817389,6803020804434926502,5490178635633878366,-7309525662892965295>()) {
               case 1252475369:
                  if (a == com.yiyiaddon.g.d.e.READY) {
                     switch ((int)com.yiyiaddon.m.b.a<"seala0zw9nx3t","UqxzQGdfX6nw45zjYma9SChxYDvnKI6fsRpBypAnd6k=",-7492144257283451256,7410364397002865867,-4770906095717892259,891087945011971073>()) {
                        case -883547157:
                           String var0 = (String)com.yiyiaddon.m.b.a<"s16gr5edc5988l","FIw6e5y0/3QVR/Su09POU5mV3X0JwDZ+jAUxkuNEQIr57lLU",-5337220577967245080,983713123319571519,-8116382674947843643,-6411249950146760596>();
                           switch ((int)com.yiyiaddon.m.b.a<"s1eenea571dixr","V4iM2KfqGQ0OJbA4i+hOOGKvaw7OEW3DWxPAZ3KjUw4=",8074917976906825316,250034040682978541,2913677255665395508,-7545967822130168563>()) {
                              case -853940216:
                                 return var0;
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

         String var10000 = a.h();
         switch ((int)com.yiyiaddon.m.b.a<"s2j5f55kj7h0ty","XGc61daKBMBywVNhnx3GeTF8wIiqS8dwmvQiifylkqQ=",2898674735122125227,-8953154364614654846,-6782913874830648996,-5601105935111522520>()) {
            case -1899506309:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static void l(Runnable var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snlfxx9qxkzwx","IafgzgvHnMiKgzExXhlfEMiOo8Mf58jv8ic0mzmlxjw=",-3882371875521055807,3922555806506855351,-5786621011683763545,352350512359045517>()) {
            case 707734439:
               cW.add(var0);
               switch ((int)com.yiyiaddon.m.b.a<"s31b7dc27wqz8q","lH+kpw3b7LNFNZFGVNIoE4Ui/praJK56T+WXz75r9uY=",6827757318974075762,-3960160668361429190,359779466154616481,5806398562248532777>()) {
                  case 1123659439:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static void m(Runnable var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lrxrtn01o4yq","wTV4PoTFCq+EqtyeiogJDIC4cPDI+uNxMLGhs5Bc6xY=",6078470873664043395,-1730727503789997598,-3898938838016406783,-7207238057356108591>()) {
            case -1594846015:
               cX.add(var0);
               switch ((int)com.yiyiaddon.m.b.a<"s37q5hmxa9ye60","Vd6EhUDIiZALuR1j1RSXMB7CwiVcpfnvz+SopWrCFLo=",6018398774825283158,4786275007186811275,-3723973053523727074,5556161633883015966>()) {
                  case 2052040910:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static void a(ClientboundResourcePackPushPacket var0, String var1) {
      try {
         if (var0 == null) {
            return;
         }

         String var2 = var1;
         if (var2 == null || var2.isBlank()) {
            var2 = gx();
         }

         if (var2 == null || var2.isBlank()) {
            var2 = mS;
         }

         if (var2 == null || var2.isBlank()) {
            return;
         }

         if (!var2.equals(mS)) {
            bn(var2);
         }

         d = var0.id();
         EG = var0.url();
         EH = var0.hash();
         ba = bb;
      } catch (Exception var3) {
         if (fy) {
            A(var3.getMessage() + "");
         }
      }
   }

   public static void js() {
      fz = true;
      bb++;
      if (fy) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wugisxolosri","XrLrmDBRhYz747syyUeyGyiXTbD1iN//6z+0CFfRO50=",-8027943222238053931,5778756772761355663,7192739484045252009,-6160174680302900593>()) {
            case -1839205950:
               if (a != com.yiyiaddon.g.d.e.READY) {
                  label21:
                  switch ((int)com.yiyiaddon.m.b.a<"s1v0581i2uupzy","6PNQ66Qmpax27npuhtmICDMsJG+d53Ka70x/0s9S1dY=",8361040456217268314,-6761035744448615197,9114706904352301879,-4387324851090689724>()) {
                     case -2107642503:
                        if (a != com.yiyiaddon.g.d.e.NO_CONTENT) {
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3mh14tmfl1rv","S1P9hdV2q2UpRAyS0F+NMMpGfBxxaqITvQQlPHVoJTk=",-9138309517142468629,-48251774332457215,6235720221401492030,-7919489911794273441>()) {
                           case -1731136584:
                              break label21;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               fB = true;
               switch ((int)com.yiyiaddon.m.b.a<"s9984b79zt3by","OlWlUcmxAmn8GzRYRjZ0mN3m2pwrzf1NzH+AzrOY1wY=",1559746368113041205,8449960199115406875,-344439108913456726,-530360560469944011>()) {
                  case 1565384846:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static void jt() {
      if (!com.yiyiaddon.i.c.fo()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rm1qyn9b1gh9","WfDkiYon8YB0yxSPmOhkiYzvTu0AG+U4DpxF4uwu7D4=",4244478043766344206,4945402116208704212,-8547095494491049634,-1820066268786120735>()) {
            case 2146632947:
               bp(
                  (String)com.yiyiaddon.m.b.a<"s3hmhtztz7udvi","AE3Z5UX3RPvTdwRxad12zEesP6RaVT7oK/t0ngG3DWoDxznvA3U5bXjeG2tLw8fZT6K5weukyzwxO21sF04=",-2024665486832581520,-2036033998412866787,-1812901346053140341,160945225283798265>()
               );
               return;
            default:
               throw null;
         }
      } else if (cH()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1v8cq5lc2vtrl","u/iuMn52lgB0Ml6c4JmslWAa9o+h93jZIK4i636e4WU=",-1377896771332455862,-7316770534993111496,6937867720027501551,-8001912673705952082>()) {
            case -1504751129:
               bp(
                  (String)com.yiyiaddon.m.b.a<"s2tykevbuh7kce","hnT71gSVPtBCrCIuNHqXxAx37Kts4vWsbzmzikUMbYw+fRUhrTsVFrOkPtkrqDmIfzO8FQZxcU/kmCNkZFQ=",1063080037181908891,8578329635385597674,-6839730197128983087,-7324407453120224789>()
               );
               return;
            default:
               throw null;
         }
      } else if (fK()) {
         switch ((int)com.yiyiaddon.m.b.a<"s34j5aqq8x27ax","6XYxOrWfrdIoFV67yKZJJeeCFs9xTtkLJ+YDF351ApM=",2173976187692267700,6924151644548162726,4270563171905490553,8193105427984482148>()) {
            case -1545624413:
               bp(a.h() + "");
               return;
            default:
               throw null;
         }
      } else {
         String var0 = gx();
         if (var0 == null) {
            label45:
            switch ((int)com.yiyiaddon.m.b.a<"s1s1uqrqx6qdi2","YC8jvDldmKm5tz1kLsysHOd0Cia5eCQDRVXE8bdkRow=",-2558429535723089648,-6586679622473398676,-1649168994599088982,-819542474765712834>()) {
               case 1812404730:
                  var0 = mS;
                  switch ((int)com.yiyiaddon.m.b.a<"s1umtk6n01uqes","xDMrxLe1UPecgRFGVG8KdYd4IeWWx06UqS1qJrDdnCk=",90542676185826355,-7855665855329724740,-2420886290397374962,-838958839560999199>()) {
                     case 1800617188:
                        break label45;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s6tuj0sm7vyzi","ObVlZ0d49N3LyzvUH/xwSDB4iii4+rcrKe28JbLcnG8=",-6891668692104227530,3236937177716501612,-7098549586159765357,8348500280492326282>()) {
               case 1272448576:
                  bp(
                     (String)com.yiyiaddon.m.b.a<"s3qcxf52oe3f1c","Yj/PGDXEf4smVu1XDqDohYVXKKn9VAjEH+xfBRdYi0PWPHH58LpjiW6RwiJYotpXjlq3lHg2Wsj02TzDrxavzAM8uoo2zKuk",2304878249850952376,-3283239081482525242,7323907936609803346,-5408509605349279501>()
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            if (!var0.equals(mS)) {
               label38:
               switch ((int)com.yiyiaddon.m.b.a<"s31uyah7zifqwa","PqaoDaxbaH3UWvfrKAaSf5ESSrSuLCS/S5rej8UuNZw=",-3360759643172793327,-8700415160750327960,32692543388133578,-5142406401330482025>()) {
                  case -1139355320:
                     bn(var0);
                     switch ((int)com.yiyiaddon.m.b.a<"s199ymu01nlt6m","XBaUT3XMf+bezdqWv8JTbugDhzjPCJc/VCIQoknN1+o=",-7184207435649079723,-1095516896089400859,5115939338710943431,-8969292969923101870>()) {
                        case 1448719276:
                           break label38;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            aN.add(var0);
            jE();
            aP.clear();
            yQ = null;
            a = f.b();
            rJ = null;
            EF = null;
            a = g.NONE;
            sX = 0;
            fz = false;
            fA = false;
            be = bb;
            bd = bb;
            a = com.yiyiaddon.g.d.e.CHECKING;
            fy = true;
            s(
               (String)com.yiyiaddon.m.b.a<"s2pd4na11mog8u","B9nZKs7VECWryh2nRCTKRViYuZycFi2vbTRsSwOA9McFcqsxr2c=",3505542685311310298,-7706257069908442109,-8669121789798684711,3490265268712785191>(),
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"s3pkevudomb81e","sPSGV8qDl00IFoNiMiO/NIm/hHZI9aZvmRF3c21SzCfCcZY5aVM=",-4489079568776605400,-5714742313495008633,1175029846290575279,-2694762912292384667>(),
                     bY(com.yiyiaddon.i.c.T()) + ""
                  )
                  + com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"s34u57hkx5onbg","R53cxC0Vy0uSm1afVeYGqOUxyVZJMi1ws4XL37F+zGo4J97HQ5w=",6955971104165919609,-5289698603978130561,6782565839715325550,590938685059718204>(),
                     bY(var0) + ""
                  )
                  + com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"s306zah5rz63j8","gPqz3UMVd7gfxmpTnT3srGZ7z2IrupUbhtQxo70wLV4=",-1577308204268453629,-2319061592677211903,-3250727203781920883,4007865533666113355>(),
                     (String)com.yiyiaddon.m.b.a<"sblnybioqzb3d","X8rv1aui3m2XMPpcwElYc65ytHw0g2DBobNb1M+jzx6cIXA20cE=",-7083162965129707470,7725163422913046631,688714011307870738,2517858441997702622>()
                  )
            );
            jw();
         }
      }
   }

   private static boolean cH() {
      return com.yiyiaddon.i.c.cH();
   }

   private static String gx() {
      if (cH()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wddi6xg8ibg","BSsGRp3EBIZ1pxHKhvNEKKwLeSpombLsFZsbLzd5M6Y=",-7164858053397744076,-3567667451454079886,3322952908561343301,-6151952208158011037>()) {
            case 780261427:
               return (String)com.yiyiaddon.m.b.a<"s305e8n6j1ffmd","A0TJIzedscJRm3hjdm78oSkwQgI39wxpOOGKAVVlH+0mR7aZ9bqjTml77islIJKVZqi2Vw==",-863930971436056999,-4676565269328587943,-7813870676677035581,-4813554813273833572>();
            default:
               throw null;
         }
      } else {
         return e.gA();
      }
   }

   private static void bn(String var0) {
      label95: {
         if (var0 != null) {
            label75:
            switch ((int)com.yiyiaddon.m.b.a<"s8miaf6rhfvyl","2bVuErb81F1zNsId8uA2yQrj5kqq1e7OEoh6CGd1Aik=",5988918367784492335,-2742352754176745484,65583516308926068,-5821353598372595051>()) {
               case 971117097:
                  if (!var0.isBlank()) {
                     break label95;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3axuoc9dw34d6","da531bVZTz4m6mXL6jrfNFaCQs+h1mm+cxY4hJ9mCsc=",-8267475909509160250,5066605596261219243,5628283690638314772,2098153993226363325>()) {
                     case -826150584:
                        break label75;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (mS != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3u2q4jo0hheki","pvmcXocdjbqUYp/T0ISM0Zs246sryWsdGB6N0VxpRss=",6472494166815078304,623909507528126852,2557973188559727728,-5913098874750067561>()) {
               case -1671308213:
                  if (!mS.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"szcj8ylmfzzxo","paJ6f5ET2OxxrBBiHfGUQWiXrFODB3fURSRlgnxC7EI=",-671883558660605164,-5541837024794904564,316226552044098171,-5127596584004604507>()) {
                        case -1679451886:
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

         var0 = (String)com.yiyiaddon.m.b.a<"s20z4m20lpjqh4","4T9nsY2yTX8N4n1UnvPbHC2OCjnf1l+ZIfldFmKJMh7DCdiRpx7HqypH",6223925082540876694,3950493655961912908,7114128773740440058,2607476021786610442>();
         switch ((int)com.yiyiaddon.m.b.a<"s3hx5m9yg7qww4","5VjZt+MZyWw7cZ/N+aUJ7nCx/u3+/gaq2a5NPoG90Ow=",-7294595143957949706,-645084362436836474,1605615487613536017,-4156811532061473019>()) {
            case -766378435:
               break;
            default:
               throw null;
         }
      }

      boolean var1 = var0.equals(mS);
      boolean var2 = aN.contains(var0);
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"sduqpp5e0597o","/drfh83JDqoPt2F6YhD4lLPL+JtP4kL+kPwMt2Ha2SA=",-1920757277059083085,2295060846682983375,5582649685669416168,5762602976135039879>()) {
            case 1030632966:
               if (fy) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2k6mdch0yyi26","zgdtbNFwwQwwOTNfdpXN9sxbwa3rByaK3wvkbXw0Xxc=",4037270490414734749,-4769633983460146129,5676088174745796154,-5192938267969128467>()) {
                     case -845437378:
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

      if (!var1) {
         label63:
         switch ((int)com.yiyiaddon.m.b.a<"s106w2bwrddyx9","tecUpxWZK3ETgu5BVXCyiDGvj3VwZzDOHxTQt1FNSRA=",4214476089550949895,4044229304834110293,-1647074886930480783,2622723891682137499>()) {
            case 1458746824:
               jE();
               switch ((int)com.yiyiaddon.m.b.a<"s1q4sj07kx2a8x","nTzEi5vRAt5GJ3THGX9FZSefW6Z78kBVOVqZKtxoB3w=",-9061721838362471903,6044031966528301889,5619318120777131240,-1199769716031319222>()) {
                  case -921090566:
                     break label63;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      UUID var3 = d;
      String var4 = EG;
      String var5 = EH;
      mS = var0;
      gO();
      if (var1) {
         label59:
         switch ((int)com.yiyiaddon.m.b.a<"s322xkiynsg6tv","Qe0OCxBAl63KbBT1LrhaBvU+z2L88JXNZq8V49BM1W8=",7959202041708143366,2907885025919166189,1180029630255579677,-1592984671289277921>()) {
            case -1655084281:
               d = var3;
               EG = var4;
               EH = var5;
               switch ((int)com.yiyiaddon.m.b.a<"s3cvlc8iw4zcrc","bGG4TogVGe5EZivJydlpZIESuEk53zqf1m18QkzjUoA=",-997315117502767389,-7460388199762450060,5517040561157780661,1536507551444462460>()) {
                  case 1631513308:
                     break label59;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m410cj87zod8","DzDcUsWcdb2LNYfZT+W+flf4YQpHZo3EG6Nl+qj/ZIs=",7387806786636379616,-5030300751950208856,889235888529195839,2910440505930246449>()) {
            case -1989846791:
               if (!cH()) {
                  switch ((int)com.yiyiaddon.m.b.a<"susxk1xkrcar7","/R+hQyCbCw2n6WFyztb92AD+hwnQKXMvZSq23bBbhKY=",-7865378999496919993,-7277347418980837215,-4870084284307762790,6611062561887898884>()) {
                     case 2000237107:
                        fy = true;
                        be = bb;
                        bd = bb;
                        a = com.yiyiaddon.g.d.e.CHECKING;
                        s(
                           (String)com.yiyiaddon.m.b.a<"s3mj0q5ihj3ycz","wqxN8Rvxg/MLpu+3PDLMmm8LzWj8w4FObMC6JgR73q3HzrjkMeQFxKG1anlyTVbUvyk=",1211128983985790643,-7743473958936456928,1961869866083781516,-8751280335236068358>(),
                           (String)com.yiyiaddon.m.b.a<"s13lz8wutq1xb","e9Jie5GDIlBhJXDu3paKPvFZ8wkWQoNolNxvDdJm+U+yP2pSuYAd88OtdGMpGdaiakh/neFDfgb9GfmjAlWopz5tqjdYLbpgvcN2sW8xZdsDyw==",-9222602247361386356,2742957123044347885,8080932659837882021,-9037954939601146592>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s1ceot7ti6wgtv","gLpPo396aftMq9B3jNtTVWKesY19jdAKg/cqd77w2hA=",695209784123798015,-4731855597104055606,-2703586657943917169,8028318319755733666>()) {
                           case 2028358770:
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

      a = com.yiyiaddon.g.d.e.NOT_CHECKED;
      switch ((int)com.yiyiaddon.m.b.a<"s3l0zt5sb6hgny","b6lIh+m84rHJ/7spcVl7WFMSlfDy3GXDi0caIKZUKq8=",5188774444105660445,6832577044091521450,-3686155884179522995,-2877356528995013812>()) {
         case -142385384:
            return;
         default:
            throw null;
      }
   }

   private static void bo(String var0) {
      if (mS != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"szt9824pk91ov","UZSXDzQRFL+bQYT7gN4SOlTVBNsrgCzfngm5oB4AgTI=",6740312490388700928,1474727543510392126,-3382782649371990351,-8739969198973783886>()) {
            case 1107282577:
               jE();
               switch ((int)com.yiyiaddon.m.b.a<"s34vn6mfo9req5","RVnW1cZ+r5PhjuPGsure6rLvPZCBAdCHk9Ci0o4y1go=",161898727410573734,-5822618530734827915,3563315359758298886,-5529961373332712950>()) {
                  case 1237920920:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      mS = null;
      gO();
      d = null;
      EG = null;
      EH = null;
      a = com.yiyiaddon.g.d.e.IDLE;
      yQ = var0;
      b.clear();
      e.jG();
   }

   private static void gO() {
      fy = false;
      b.clear();
      EF = null;
      rJ = null;
      yQ = null;
      a = g.NONE;
      fA = false;
      a = f.b();
      sX = 0;
      fz = false;
      fB = false;
      aP.clear();
   }

   private static void av() {
      if (fB) {
         switch ((int)com.yiyiaddon.m.b.a<"s3voeq5j2mrm9n","y3ZI0PHdgw8rngJq26U81XDqW+D7hAWVn6ZjKwYXzxM=",7333127151618019440,-5046129608283050772,-2246294951620292612,-6048901391373583249>()) {
            case -91300836:
               if (fy) {
                  label50:
                  switch ((int)com.yiyiaddon.m.b.a<"s2l3xj7io1e0gp","lw/RZeC4OXJLbsx+2TxtXphO9JCXum1SWo31+NY3DTQ=",-2206129176878897520,-2259417739193771673,-3416272105784176969,92617250918570602>()) {
                     case -1857683270:
                        if (a != com.yiyiaddon.g.d.e.READY) {
                           label48:
                           switch ((int)com.yiyiaddon.m.b.a<"sskh9e0n35b2k","ckwdGd441KH2EkZs/tjrQtPlO13ZJXOMsCA/30AF0GA=",6561924338237693178,367530423837066429,-7527722479791531379,3725736942357757641>()) {
                              case 49583247:
                                 if (a != com.yiyiaddon.g.d.e.NO_CONTENT) {
                                    break label50;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"sqeihraxrlmex","zlT8gKClGJmp0vVnsTrs4Pt76zP2+S4akuZn/qJJ1nc=",-2221781321465431327,1181034311675199249,-1449067318392074283,-1317328581043469490>()) {
                                    case -637648009:
                                       break label48;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ju();
                        switch ((int)com.yiyiaddon.m.b.a<"s2fsbv7bw10xfy","PdckG1apf1ro4wuZ9cZ2pIC9wmuY5rQ28tlprc2+o80=",3974403274868264576,-6546167241794048456,4008133486254263734,-467286320531529197>()) {
                           case 460416674:
                              break label50;
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

      switch (a) {
         case CHECKING:
            jw();
            switch ((int)com.yiyiaddon.m.b.a<"s3o8yuq337x0c3","cBvV1t5N36uL6hWhtBzYrAQ61cydTyIalfX6IZ4nGYE=",3820929402732889274,6030474201008613653,-851364256729681605,-1212481499739913823>()) {
               case -185505846:
                  return;
               default:
                  throw null;
            }
         case DOWNLOADING:
            jx();
            switch ((int)com.yiyiaddon.m.b.a<"s33f8b0d0hdoox","sEIu853SikFHkHwl8B6jF2zzaqR+SX1gMCRko4x+gUA=",-3991261665692173099,-8175016993022513782,-6310828366786733038,8631647977733227663>()) {
               case -1373414134:
                  return;
               default:
                  throw null;
            }
         case LOADING:
            jz();
            switch ((int)com.yiyiaddon.m.b.a<"s3tyl4vkifmqvm","AeRDBzA7truOY67wkFzrIDtaoCgqQXirZ4SfgzP4LPk=",1060600715344075242,-4575108460660947327,-2678057072780514196,5514930794610217335>()) {
               case 1549223181:
                  return;
               default:
                  throw null;
            }
         case PARSING:
            jB();
            switch ((int)com.yiyiaddon.m.b.a<"s1cv9qp9h5nf91","YoAi9X548XtPQ9mXyZKaPDKkiC2KKW4BJXAJo1mvCUI=",2003645710725657640,-7565378718568918775,-4332364637746465574,-5327329492419119828>()) {
               case -2087456154:
                  return;
               default:
                  throw null;
            }
         case FAILED:
            jv();
            switch ((int)com.yiyiaddon.m.b.a<"s10n5vvssl778y","El5appNBOMb5adaFRYQ5FrYEHEXnTP8ZlkCtQSbnnZo=",8558447254202818980,-9140208903605835302,-818893612820892521,931447614987117651>()) {
               case -712750338:
                  break;
               default:
                  throw null;
            }
      }
   }

   private static void ju() {
      fB = false;
      aP.clear();
      yQ = null;
      EF = null;
      rJ = null;
      a = g.LOADED;
      fA = true;
      a = f.b();
      sX = 0;
      a = com.yiyiaddon.g.d.e.PARSING;
      s(
         (String)com.yiyiaddon.m.b.a<"s1gpib1p4re6hi","xOE6A6MBLGGxUuLV96Boa2rQsIO8dvHdt7d8x1yd40KMgxwoPIeI2sDJht2Tx7bwxeXvDqOqCxQlh/Qq",-3314067170351024980,-134436967808738850,-898620793730816680,2309168348792163132>(),
         (String)com.yiyiaddon.m.b.a<"s2dwy35blsyyha","Gi6qI092tLSdE/owbUmyvn9fl/gyMAb8V1kRnU2elYP3ES6Zdtl6gZsTBfMIeWy+QQDocPjZyZfwSE+cwewEPSXoqTCUbmSlUVXk0g==",5692636744979768409,9192471262739927152,562836128303287314,-2239849113869279777>()
      );
   }

   private static void jv() {
      if (bb <= bc) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fe83hf42ffse","yyJLrcNWp1X9efMJc5FJLXOKZK3DF9aWyfVFPny9yR4=",-6379805594683628040,5292904386824368537,-939041145588973403,4286348484150578079>()) {
            case 1273878385:
               return;
            default:
               throw null;
         }
      } else {
         yQ = null;
         a = f.b();
         a = g.NONE;
         fz = false;
         sX = 0;
         be = bb;
         a = com.yiyiaddon.g.d.e.CHECKING;
         s(
            (String)com.yiyiaddon.m.b.a<"s3ceuuozogag0w","StXKMta7vB/lFYBP97NbrHXzA/XLxyz36msSauWQBBCsKbz6r9Y=",5360591742884644314,-5962667648532918937,-8020808332466579007,6190864874191371323>(),
            (String)com.yiyiaddon.m.b.a<"s2eyjyqdcz6vb6","vAUL4OCQ0OOUX01Bh6j0dGTRTOmA4qXQ+JYrhoCIh0qgv2faJWoNGewmA4XtevEDwineLxn42hO/OJII8eWABIElJ6OZnxbK",9199964098170061200,8897726808880039793,-4241453670068836973,-3809556463590818973>()
         );
         jw();
      }
   }

   private static void jw() {
      sX++;
      if (mS == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hou93skr1u1q","pFUrwPNz/ssAlRfbeRMJ+mos2WX8fbpdbrO74DEv3w4=",-1634802502800505352,8873165078470420068,-2447507207879807964,-8973193952845122118>()) {
            case -1259027737:
               A(
                  (String)com.yiyiaddon.m.b.a<"s1api9hcjytd7h","qaftkEXxgCHiThvBts6fwsRTRSQ1AeUf9T7JXbT+ZwCGvrWfrtuj67sVZeQLl8RvniKWaoYwoqNmqJs2mMSIpw==",-5026720582979951547,-4185838874533720535,4799870889146454963,-7570104219290937037>()
               );
               return;
            default:
               throw null;
         }
      } else {
         File var0 = e.a(mS);
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3kuox94kde164","iyEGpkYK2rYDwYnTy/rAxAu7jBFll1mP8nH0noI/2CY=",6433678216422845847,-1587093436493468070,-6482734869230709813,-5898252693910751344>()) {
               case -875945791:
                  s(
                     (String)com.yiyiaddon.m.b.a<"s28yqqvwd48n71","gKzuLo91BlPdt9RtV0fRecNkaVOBL2iKyiTHG05jspZV3Z8BTxU=",3930538256644607432,4442026529975903474,-6974004093408254187,-4714146990427112572>(),
                     var0.getName() + ""
                  );
                  b(var0);
                  return;
               default:
                  throw null;
            }
         } else {
            if (d != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1lk3kwkcd35c3","lYFPXhgcoeziZSbzz3dyz/L+jvLY7G/x8n88SrL4eAw=",1526555056844114589,1849212616380485771,-1704067091908928023,9082806568278617812>()) {
                  case 1969984228:
                     if (EG != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1bs30plxae396","YCNYK00RC/BBGyio6hIaIotZ3J929mHxLqWdlDhUkFk=",6167529013205423621,2911148016099146343,-3698758575429583493,-4937066454295220806>()) {
                           case 687178854:
                              if (!EG.isBlank()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s29rva5jp979qi","2wDCu2eHlAaT7I0vMugl4JrT6ZTITZ7Xiej7jNHF+wk=",-5166040416487691922,-1543327406565615359,4765224026906392353,-7629247290968823022>()) {
                                    case 685055966:
                                       s(
                                          (String)com.yiyiaddon.m.b.a<"s3hib1w0g5plr4","gVL7a8Fm0qJEvhVTnGVfF8zCJZ44922MBZDiBDeBKzjKui5vOVBW9DPGTN8=",-1797798392099212046,6220959347190729350,6731932649414217405,7526928023231714293>(),
                                          (String)com.yiyiaddon.m.b.a<"slo1s7nx39ntp","WuEry4NPEN3+mxL+bcNP1M6Z2p49rA2uNlqp1+p/tWzyoFG8M5u7M0Sg2eKO2qPVOBw3qNXmLdOjFA==",-569681505275703153,1469966172405817972,-1382148076746133867,-8706950982835280865>()
                                       );
                                       a = com.yiyiaddon.g.d.e.DOWNLOADING;
                                       sX = 0;
                                       e.a(d, EG, EH, 5, 60000, true);
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
                     break;
                  default:
                     throw null;
               }
            }

            if (fO()) {
               switch ((int)com.yiyiaddon.m.b.a<"s17lvu49b3d5l1","cKhWfCa2MQjHNdsY6z6Aso2maRHHPBAInj5wRT0WksQ=",-8927972353798859754,-7088161156665680448,-4025729696322622212,588250366671768095>()) {
                  case -1884817454:
                     fA = true;
                     a = g.LOADED;
                     s(
                        (String)com.yiyiaddon.m.b.a<"s3h6hvpox0gdne","u65FEFmS4e3rBdFpMPVZuvnh+NYBzX37lJc+iC5YyUcc/eg1XN0=",-2560975314778089781,-2535655690966279006,4337235433272611573,4609511836807773061>(),
                        com.yiyiaddon.d.d.a(
                              (String)com.yiyiaddon.m.b.a<"s2rwjk4zyckyb6","yuf+tlUC7Zvvqk/oikApdv+GaBXDQiB7I5yq4ft5491WWLio",-7800454702835473872,-1127846759773129688,-5831674545638990100,2816796251948830738>(),
                              (String)com.yiyiaddon.m.b.a<"s3016w9k517hy8","+u2+5rZKqSXKaiplPN3ERggrUa/u2ojl5WTDyidEnCfYSRD9XPnmu1n5bnBoNQwEVJXkugV2sCfGjokh",-3393186384799880094,-4028915434807293047,-8695321418752112739,-2564238619462767682>()
                           )
                           + com.yiyiaddon.d.d.a(
                              (String)com.yiyiaddon.m.b.a<"s3b7bkrp2rg5z2","3HeQ/W69a3BmhXc/T221mJFJjkqfWA7rMidkJkPACFGafrUI",2817824578505608661,2708107038057018464,-3686690194179686804,2589939286021127922>(),
                              (String)com.yiyiaddon.m.b.a<"s268dbdrovtq1z","ImVaiPFhrLmzNXpqHrmlWl5ONOqPVxZkjoUKlxlHRUpX2Pc86SnHUor5htGVj7MsAOQ=",3709179582230911549,-8005252053949482560,-5346252563094702398,-968315313018929832>()
                           )
                           + com.yiyiaddon.d.d.a(
                              (String)com.yiyiaddon.m.b.a<"sfbsbi5op1pnn","Wn494y9aahHzo20Sf9XCFW+E49gsnfEx7qsiBLErchA=",941617431234861954,-5863545817187763272,8569962484652881489,3297418889758725317>(),
                              (String)com.yiyiaddon.m.b.a<"s2l63c5g7aekns","miXEKoxug/qhyL0kIrkNzbrSC6hs9fiQF7YX+GSHPLunzFSXamrrBYvrByqbngx6f8lpR173L8Qeqygk",-2440627278745491724,1926057474553138488,6297670725780042785,3667010645649082242>()
                           )
                     );
                     jy();
                     return;
                  default:
                     throw null;
               }
            } else if (sX > 200) {
               switch ((int)com.yiyiaddon.m.b.a<"s1jk0954flut2x","XGgRxzI3gI+HbI9WzxsjL6wUb+L4tb6K8m1F5TIPiUI=",-2186256726788010810,5876553370567494133,3266131382438241432,9066117224688385478>()) {
                  case 378380948:
                     A(
                        (String)com.yiyiaddon.m.b.a<"s22rg11nkejodr","GhFGHWgo+66bYSR0OQqbZ70zmamTlLils3I/KFJlHBuGDv3iM5kYXOopLewG9alKy2Q7GZylR4FFERuAE4agWpVaKl/+LV11kf90LbxuRBERUBgqNGJYdrsl",-2168962580440116758,-2272106260257525584,3847152958735776149,-4730657560160144388>()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s2889pmygei65f","V2YAnLOs6zYkOrgnH0NrtpeEpLcKP1PlK3jwiFMBTho=",-2700212950879180943,-1710871525975800111,-8605721179253775090,-6451995519224765338>()) {
                        case 1272873253:
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
   }

   private static void jx() {
      sX++;
      File var0 = e.a(mS);
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1i6jqq2p7iqeu","fVTaH24xcmg5xZXDtUuZ54arA0vfRJL+krHk/TzyaiA=",-5726743448289344389,-2075842468691803285,-4450844109345671391,-4947248474142901608>()) {
            case -2138885246:
               b(var0);
               return;
            default:
               throw null;
         }
      } else if (sX > 3600) {
         switch ((int)com.yiyiaddon.m.b.a<"s1im60lzou0cq","+BAZwV/kL1KpHCjp+2I+8Q5a4XWKBcs2CpdWDyhbsms=",5578255643861121808,-3462572754066979010,-8367636109982901704,-6731330889898714299>()) {
            case 701111486:
               A(
                  (String)com.yiyiaddon.m.b.a<"simn9j77oplfc","NHT35Zsu4XB9FySzEiBDb6M5jjxp4aqASLnyuoryoQUoR3uus8ccUQngrIQ2cvALUxGy8Cfm/N7m78pp3Is=",-3570437178426059660,-3270854372924338652,-3476440293571393189,540410838198930466>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2qi1mkxoqnim1","zEx44e5GakLcJRp9xrmB/gCRsC4ou0KJroOGiye33oU=",-2138145094113756920,-4212375445047406440,8790236444602844294,-8027117855185567211>()) {
                  case -792037757:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static void b(File var0) {
      EF = var0.getName();
      a = g.ZIP;
      jy();
   }

   private static void jy() {
      if (a != com.yiyiaddon.g.d.e.LOADING) {
         switch ((int)com.yiyiaddon.m.b.a<"s22hz2yqycjswk","jOpQDjGwGD4dq0tC0pUIANcDgP4W0nTmC16ljxmj1ys=",-6276012792170289430,-53054834723312975,2723599312266545436,1845128171490662485>()) {
            case -1702866981:
               if (a != com.yiyiaddon.g.d.e.PARSING) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3vq40fnrq14tb","T7VX7MXyi64vvoc7HEWmBjS1Np2P5qYO3vjmsBBE+YQ=",4090261080469583134,-1122591807145778388,-9032389421615181039,-8972432571028327579>()) {
                     case -551173166:
                        if (a != com.yiyiaddon.g.d.e.READY) {
                           if (bb > be) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2byywei239xpx","bAbnK9iXeKNrJccvbw1/ovYlBKNVWy3S2aBB+Ts15Is=",8843999989495578769,8287565285411375572,382079360531985123,-2773114513261675530>()) {
                                 case 1677308471:
                                    jA();
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           if (fM()) {
                              switch ((int)com.yiyiaddon.m.b.a<"saztuh6cozlf","B/0/BJAyBTPXY+y3cjV94KyxpLpicheCfTlwNWJ2bTo=",-3706989918368518227,-5033128651159093989,-6000605415240018213,-9158161945628431559>()) {
                                 case 1821405831:
                                    jA();
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           a = com.yiyiaddon.g.d.e.LOADING;
                           sX = 0;
                           bd = bb;
                           s(
                              (String)com.yiyiaddon.m.b.a<"s39ph981cnr8wq","At/KiQwAP+hWxmCNLounvWZjsiZYNoyuQKAA16iSRomq3xfm6Dl7i++a",-8412096466257916108,130349903487405811,2495357133051458601,3086044499180358336>(),
                              (String)com.yiyiaddon.m.b.a<"sr1rscan5lqe7","05WEIqSgS870F3meNX5AASUTVA09G3Y3KjRTwdzsBi7ya4EbPmvbL6p8T6jIcTBf8iMBo2ou3ocylFrzocn02oEk",7343121026571154372,7226923784050395508,1201608502541628218,2045709536490417177>()
                           );
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2w97e2xaypy6v","zMtqewWK++JZ7mAnhX7GTAMIHm6JdXktfqHfAMDO1YE=",3806897919020949637,4367459007946602778,4077510932944908235,1120829604914969355>()) {
                           case -1812046445:
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

   private static boolean fM() {
      if (EG != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sf0cnyzbta3s1","4TBqP0mPjbjDnGJlJIjL9UTS73DpV+8FJb7mW2yNvZw=",1790692908881753282,-3564842404036142652,-2097153500661383475,-3900190029918376957>()) {
            case 1952122970:
               if (!EG.isBlank()) {
                  if (bb > ba) {
                     switch ((int)com.yiyiaddon.m.b.a<"s16boj0915wqun","em2YQykrmsjmZmj/V9bwmFHeSGVWUuaQbzg8hUzndZs=",-7925057637088536482,-447171648971107581,4145065943035758020,5809722922112798893>()) {
                        case 13104274:
                           return true;
                        default:
                           throw null;
                     }
                  }

                  return fO();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fbpfjn0udt1a","nM6hfbDZFpblOapHSa6BxxPwOJOi1m1XzBc4U0d/u4c=",8429809110094492422,-8774424814262237840,4691762294545771045,-7516678021230182711>()) {
                     case 1612047403:
                        return true;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   private static void jz() {
      sX++;
      if (!fz) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"s3jp23i29n0jt4","8dvOELp/1VomwspB6MSd2z9njxQuK0UydVyhqBnF+80=",-7487553087646182652,-5372324670484109786,4708518727161034078,-715230588059318918>()) {
            case -648253720:
               if (bb <= bd) {
                  if (sX <= 400) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2wfcxckoewv09","VL9IJ40WfAyKBOS6tx2hthAgxN14GBUYo9/hxE1GMtc=",-5481864058925784279,7473442801091069874,-4552213906311800165,8924652203375774714>()) {
                        case -913406297:
                           return;
                        default:
                           throw null;
                     }
                  }

                  if (fN()) {
                     switch ((int)com.yiyiaddon.m.b.a<"so21pj35u64k4","hYUQi5msM9satP9d9o6L8rQvWOxqx/KbmN5x6V1QFDc=",8365545414429863136,3510042060541014822,-4391015128506911597,-4745869375527467161>()) {
                        case -2045614004:
                           A(
                              (String)com.yiyiaddon.m.b.a<"s1jmt8wd44fd0i","89oItq7h3718TrD5AA0pi8wdzui60VnPKtLdur0dCtVw2OUo+/ItFl9WqjPubKTtpIr0EzWc/N+0ans2C53mwyVWj1e8l4wtJONhcK4FBKEsQE5RiDmqlYxYMj8gzA==",6413999000238394606,-1463891791926971714,7934939188658424123,7170917205208900197>()
                           );
                           return;
                        default:
                           throw null;
                     }
                  }

                  s(
                     (String)com.yiyiaddon.m.b.a<"s1vx3p8j5v2p7o","g5pock3aqgW0xr9yMVWrqubPa1CwhawBt+8YtkDXCkuDjYPurCgAYIEJgbM=",-5611781061142443344,699118683802980221,8373949509850138145,5007452904529710269>(),
                     (String)com.yiyiaddon.m.b.a<"s23or7a5kg7ls9","zWT7R/VjeVyFnIlSlytsr44KmPp8rDQILempiHqn02fVUbyHApUkLoTFb0JPmzf8jdRpM+/FWOZV8PIUpQoH7imjR7fKxL3wC33rWsTrLpjZR0p56/U=",2763739670068947983,6333196866362780738,-126532488979314612,-8213228536968315554>()
                  );
                  jA();
                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s78exth3nx153","mUjN7uKWL5iRBnzukx5gIncUM5lTxDenCMDf/FiRbhQ=",1213592141165816145,-4548883412870211603,-1126628001558192084,-6455338557617776570>()) {
                  case -1813565646:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      jA();
   }

   private static boolean fN() {
      Set var0 = I();
      if (!var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dqy3chzoxh8z","xHMQy57K7v91EgTBEaU9/NbDeSc5PZMzTKZys+UJOpo=",-7808688909264567697,-4526048515556377616,-1650421841199653071,7522039163186264144>()) {
            case 1861451191:
               if (var0.equals(aO)) {
                  switch ((int)com.yiyiaddon.m.b.a<"si2aqg4ggelk6","2DNy8ecr/cGr+AJzN+9PISBL/KXpDE5vwWjgbmjxNxk=",-8266623459190055110,-1948525481890542555,1243971520694580224,8769080287825548571>()) {
                     case -18421732:
                        if (EI != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1tns44snil0dc","AQwSbnI6DbU5yNKpKgs+XJ86JGMxFrO5JJ51u1TFJ08=",4185800884803695355,3608218724957616340,1660261775356142138,-2800949637485953585>()) {
                              case -742728350:
                                 if (!mS.equals(EI)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1eqseyyowbz15","Y3bFXIZBKF2Ty8mnn+Jcdmu3UWH7MwUMFfmckQVrybc=",-6811903675208595609,5327353982135257784,-834983703101112930,681714314527185517>()) {
                                       case 1937237013:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1msu2m0hhcszm","2jb89rlALumEt0xccFaZgpigYEPGV8lZMaagaW4VGVk=",-6596844781476416820,-8413324206563352646,5503271283992141014,5196075510583036396>()) {
                                             case 210446019:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1y2x245oqykyd","Sq/YXTIeM724LoIHxT+tnbmBrwdGZ/to1Rl/3+2tu7U=",1462703742004024938,-3266732810578979432,-1223316315641846990,-4916862329937063643>()) {
         case 339870254:
            return false;
         default:
            throw null;
      }
   }

   private static void jA() {
      a = com.yiyiaddon.g.d.e.PARSING;
      sX = 0;
      s(
         (String)com.yiyiaddon.m.b.a<"sfg9ee1gsxpiq","Ism9puo28HIl4goUIk3ZLguO3ZuXit6iDgQ+TG6RCBYMTzgI/jMtFwHH",5139131017687201511,1945953337871903830,-4031279911986795921,8442450228263694668>(),
         (String)com.yiyiaddon.m.b.a<"scxuhkl3bcda1","b+5mppu8PCZfc/Uf/0KqlcU8Fi92YSFbZEG1DpG+1D/1BY9wyKipDgksexL0QQ==",-6564638469273442395,2589446057636648052,8668412017726039517,6092803027023743288>()
      );
   }

   private static String gy() {
      int var0 = 0;
      int var1 = 0;
      Iterator var2 = q.aW().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s28ew32416l9no","TelK5xioDDEucRzfAv174mAWHyy1u1J1N6WTSicFPik=",-9118483421900650518,1523485723732809073,3270459845535900174,977857174790179467>()) {
         case 125684019:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1fcb8d74vt1zs","tOoA1x58Rli+aAguFzrus8fNIQ5Wwmipr8dZ19+fys0=",-4829592393261492663,1171067422299717445,8372571586260714966,-1375052463158138435>()) {
                  case -1655556117:
                     q.b var3 = (q.b)var2.next();
                     var0++;
                     if (var3.dl()) {
                        switch ((int)com.yiyiaddon.m.b.a<"syt4ipa4kcj7q","A0JHMYQEN5+bNoQ+OipqhnPILX54TmprzedzPeBrh0g=",6965750142397910414,-3108124238867381203,7614347684500745989,1090118131100590494>()) {
                           case 215105962:
                              if (q.ag(var3.dP())) {
                                 label35:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1r3apejfotk5z","B0Vd43x7OmuGpU8SV9i0RBCL2anl0TfJMYt5nS2GR1Y=",2888086151345575679,4507730108640436601,5458395969839928092,-4257191123297568187>()) {
                                    case 360700460:
                                       var1++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3pabheyapvnxp","MsGfZUXx+opy/EXbuF/GZ5A5UGz7PpQVJz5K2wzhZJY=",7794000383724927869,-7124209099195970651,7321728465974772145,-4518820924186107037>()) {
                                          case 1834111417:
                                             break label35;
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3q1l73chhphh5","yqzmKbAz15mt1psXNm+NkhyLitQZsETkfZQa8bAxdJc=",8541682020223644465,2386050396167831788,7336140835535311575,752617971621696246>()) {
                        case 873106651:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var0 == 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s1lla6y4cl4ksr","KZ2yBAoiUwPSFOj+EdWg5KmGiTYk5rAMNdFNvrcrLK4=",-8017270865261395919,8000144743381639763,-7304826075010014158,2864645039940014378>()) {
                  case -1236721873:
                     return com.yiyiaddon.d.d.a(
                           (String)com.yiyiaddon.m.b.a<"s13jcdvg5ji3og","dx+QIZeqz3lPaxhWKRYrPqbrtKLa32Ev9ZKCSNrnuYE=",-3059504792857068259,-224789945512794701,815453463630791555,7512173459458644253>(),
                           (String)com.yiyiaddon.m.b.a<"s21uj75gkl5fnq","h5uool1LcXRsZwp41yZdLtW6Y6+HMTQkSj7xsm/dyjXQPUsr13Kn+bkWbJ3Xll8uujTKkaWdWaIxcs/vRDPjvcE177+V21e7GeZQwS33JPOIc11qTBcJ5JaxCmX6SJDm",-8785269103858935070,1311091280256458874,-1415474486630532766,-4085105921094802634>()
                        )
                        + com.yiyiaddon.d.d.a(
                           (String)com.yiyiaddon.m.b.a<"s15k8o06jjtzm2","AuJNHAO1nzaR/5KOFqkHSJqGeV5h6gBxJf3xPVqHKe86Bjql",-1467201732490636760,3393464182647310834,-6072294196914853826,983692550669170243>(),
                           (String)com.yiyiaddon.m.b.a<"s3ubggjt2abk3a","Mwgd2ADcPN/UHHatx8ajrgQcSgZb8+3v+7sb/jB3z9u6KJsbp/u3FBH59m2XgAlf0eLuiUgQ8ZhI4ft3a6mvuOSa9bcZIzKLh77Gjw==",-4701955887734940530,497202909493552250,7893187448731869988,-6953246416146316352>()
                        )
                        + com.yiyiaddon.d.d.a(
                           (String)com.yiyiaddon.m.b.a<"s247bm1ifg643x","Se4pqktq4muPr6k6KtIU3IR/HjtIppX3ndvCtpoOpuE=",-6324807854450699311,-9074497470329831769,-8175992722239769781,-8499270567839925514>(),
                           (String)com.yiyiaddon.m.b.a<"s1uwn2jtygku9c","PADOvF+sA4qmlDae7eg6vpzbddISr0qe2g1tJR1hp2BelAKhGspfKjNaagNvtqTD7fqWkdEm//QWIZGraIuIy1O5Csk0L1R+j7fAN2Jzs01K+2KuiIXgmdol07Y=",-8050225225517392695,4390385586545868153,2004359033176628718,-7921270783355094602>()
                        );
                  default:
                     throw null;
               }
            }

            return com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"s13jcdvg5ji3og","dx+QIZeqz3lPaxhWKRYrPqbrtKLa32Ev9ZKCSNrnuYE=",-3059504792857068259,-224789945512794701,815453463630791555,7512173459458644253>(),
                  "" + var0 + var1
               )
               + com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"s247bm1ifg643x","Se4pqktq4muPr6k6KtIU3IR/HjtIppX3ndvCtpoOpuE=",-6324807854450699311,-9074497470329831769,-8175992722239769781,-8499270567839925514>(),
                  (String)com.yiyiaddon.m.b.a<"s1a8yw1h498jyj","ohaG5q0Z+fcvmUd4tFiDBPr+rj5PeU17pCWVslUJnOOmM4bObD2Ka7sbhyGyZyg7T8D316sFow1iMm3DBf2/O9aT3uLrHGFdFicZWWKLdjcePbUn7VdmbJfRwYNL2uVcPcsXdLCLnjiFDg==",1975704171691801728,-949595635767186999,-6984433149606792772,-5690206177943721454>()
               );
         default:
            throw null;
      }
   }

   private static void jB() {
      b var0 = a;
      f var10000;
      if (var0 == null) {
         label56:
         switch ((int)com.yiyiaddon.m.b.a<"s1q927h1du8w7n","d8gSeSjJpoPYTrXHXjVw9DkmiqTMyMgx8IpmYbHMswI=",2611561739897264605,-4294871909545929804,3137302401556714888,6032637380263831118>()) {
            case 2045374886:
               var10000 = f.b();
               switch ((int)com.yiyiaddon.m.b.a<"s25jw3cg28b6nm","J3t2fbXdXu0mMfWoZn/pfEyxDcLD1up+jWYJ6E/f9z4=",-6278161657229078511,3707346899084364511,9219480952624499537,1688434103057766006>()) {
                  case -1000237749:
                     break label56;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.c();
         switch ((int)com.yiyiaddon.m.b.a<"s2s6bz3yzr2maw","Xu/O7t0VrLegWPcVe3J/UXBxo6Hh9ZOCvfp++9+Q4Vg=",4101587800419529509,-6864321885875743209,-3012569302776387272,166335098988079680>()) {
            case -1489204010:
               break;
            default:
               throw null;
         }
      }

      f var1 = var10000;
      if (var1 == null) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s29mmvf1kwj9q3","mly+uY6IQP0fRDvnS37uEDcbsh7oiEEW0OkXQEyMJLM=",-180889761144168024,-8993571656903768265,-627739626324914401,1292339572221840534>()) {
            case -1259238206:
               var1 = f.b();
               switch ((int)com.yiyiaddon.m.b.a<"s6htrs06way33","Gpz6yfDmiXd08oOM0dyWfWoh1BWA0boTxKA8xYEZizo=",5159258851686406265,-6065748716732533290,-3616438515596823457,-8690154865360042276>()) {
                  case 656775661:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      a = var1;
      c.a var2 = a();
      rJ = gz();
      if (EF == null) {
         label46:
         switch ((int)com.yiyiaddon.m.b.a<"s2rb52gw8bwoof","5QgXLUrBhfsiQP6inpDjs01FjyaTRHizL2GGjHEaG9M=",2781510375898085485,-8346700642974062552,-1980139215576468019,4556238329760785380>()) {
            case -838590397:
               a = g.LOADED;
               fA = true;
               switch ((int)com.yiyiaddon.m.b.a<"s20ad6ep7adsj3","zQvDXB5uBWtSPalOLlwqWJ6OAMMkir5E2uhSQY5tABY=",-4965656133379246030,8119216618786945462,1941357954870087147,7683814353901974176>()) {
                  case -110398496:
                     break label46;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      sX = 0;
      if (var2.di() == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s186d3rci1epne","Re7L518fj1iUgmm2LHzOxw0JzeMkMQ/oHgmKkOA8jKY=",8622536658646850793,-751001722049638173,-8810097780086880692,914597077819266449>()) {
            case -440047486:
               a = com.yiyiaddon.g.d.e.NO_CONTENT;
               t(
                  (String)com.yiyiaddon.m.b.a<"s1kdd04n19xti7","piwAHhOclENkjOzCeQSNg3UMzZtTVnaArJG5F8qhi+tdOXWcdCF/8aImgIW8oN4e",-2573442340260149028,-5816313284514865296,1388896169821005678,-844554924216801355>(),
                  gy()
               );
               return;
            default:
               throw null;
         }
      } else {
         a = com.yiyiaddon.g.d.e.READY;
         aZ++;
         aO = I();
         EI = mS;
         String var3 = (String)com.yiyiaddon.m.b.a<"s3s03di0883nt","tu4mNnhbr1r4L1HALu416Fk1RSHWLy9eElG1fRhW8w2Oijx6FrwLSA==",5794093623663632176,3214916460862467853,-1118485278370510681,-344405417766558578>();
         String var10001 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"s2rwjk4zyckyb6","yuf+tlUC7Zvvqk/oikApdv+GaBXDQiB7I5yq4ft5491WWLio",-7800454702835473872,-1127846759773129688,-5831674545638990100,2816796251948830738>(),
            b().h() + ""
         );
         String var10002 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"s3b7bkrp2rg5z2","3HeQ/W69a3BmhXc/T221mJFJjkqfWA7rMidkJkPACFGafrUI",2817824578505608661,2708107038057018464,-3686690194179686804,2589939286021127922>(),
            gv() + ""
         );
         String var10003 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"svfun2at07hwe","wIDkXTcrq5V5Ftf+rIirblf7xVP2x9SPzdx9uDo+x2JTHfXX",-9131746159034359364,-3470724849627059429,2919571617206904484,-2124472863242046167>(),
            gw() + ""
         );
         String var10004 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"s24c2rvau5xi0f","yZbTBBciyygxOhYnllW9tJ1ulzKcV6rxynpNtf/hJAD/y25T",2367941495405756887,-698741053564411435,-5760743546954346863,5761635928400600232>(),
            (String)com.yiyiaddon.m.b.a<"szk8sgujnzf8y","80YhAOr7VElkMxOjAd+y0Tsk84YZQdLKcZp/SRg/vv+EkylURbxYmtQp",7736454625897962419,4142156919287735578,-8653331243701166249,-99473325992889078>()
         );
         String var10005 = (String)com.yiyiaddon.m.b.a<"s306zah5rz63j8","gPqz3UMVd7gfxmpTnT3srGZ7z2IrupUbhtQxo70wLV4=",-1577308204268453629,-2319061592677211903,-3250727203781920883,4007865533666113355>();
         String var10006;
         if (fA) {
            label37:
            switch ((int)com.yiyiaddon.m.b.a<"shmr4cu7rc8qj","YKiJpUvEpKS1Bev8UTrhXOd5nG4lU66nMD4K/wK3N4U=",8432730304657682470,-737453654634965909,1902405227078153445,-7925322615708133677>()) {
               case 1520990313:
                  var10006 = (String)com.yiyiaddon.m.b.a<"s2uw89zzlm7ub6","DqXye8kxiMo/cevkkFKEOjwF4fQHbMEwWNtTp5nt8N8Kia4MHrhN2g==",7329463747353181591,7834666966507324510,-730741526225672336,2117323115794215889>();
                  switch ((int)com.yiyiaddon.m.b.a<"sayjkfk5glptd","7JVsnbuCLWyfZhMzDuVpOpWUtGj7A0ZyiVjElMsZeG8=",-7066153352344613688,-7132369077691457250,5121612125834585288,9222892931566585541>()) {
                     case -1443180966:
                        break label37;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10006 = (String)com.yiyiaddon.m.b.a<"s22ptp14y694l0","fhl/OGJb5TKw04i9wFwPFN1tPTag0j4Qknx8P70jreFZz30L",8532343247549247975,3965925850791874176,-328161164084265270,3338435845868231184>();
            switch ((int)com.yiyiaddon.m.b.a<"shl1c96e8s3mn","kQ1ERrj7TC3+WYr6XwhmP96b42y+0Btv3S3cH0lt0/0=",7110064185613131087,-6340479049767148024,7585683427735741983,-594258153241406537>()) {
               case 1083343441:
                  break;
               default:
                  throw null;
            }
         }

         s(var3, var10001 + var10002 + var10003 + var10004 + com.yiyiaddon.d.d.a(var10005, var10006));
         t(
            (String)com.yiyiaddon.m.b.a<"s157uqj0zjfc6s","XSuV1JAb9rAcwM+md9KE8E66H/hmHwb5xEQsTUDRdle6HsrY98U=",-5178844148840092394,262053745135981297,153936874003637832,-4310956546111786449>(),
            a(var2)
         );
         jD();
      }
   }

   private static c.a a() {
      LinkedHashSet var0 = new LinkedHashSet();
      LinkedHashSet var1 = new LinkedHashSet();
      LinkedHashSet var2 = new LinkedHashSet();
      LinkedHashSet var3 = new LinkedHashSet();
      LinkedHashSet var4 = new LinkedHashSet();
      LinkedHashSet var5 = new LinkedHashSet();
      LinkedHashSet var6 = new LinkedHashSet();
      Iterator var7 = q.aW().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3bvw7e3m3z10i","BG1208bAqUiObECGT0sTrImK9XlHbzlNsZ3O4urNx9c=",-2563585056054209739,-423361136583713717,6510389943964078791,5435061185189518691>()) {
         case 1066160617:
            while (var7.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ugq7ph4wpsjh","GtpfE0Vp6Qg13lLPnJPEyprGyjp+Z8jG0X5bpEUfz28=",121195640686791981,2624590430418829547,6853072257943859841,-4866932278974556117>()) {
                  case -2100776866:
                     q.b var8 = (q.b)var7.next();
                     if (var8.dl()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1unflkfs2e8p7","ABORJwtrcm/KKwHsbPfgLiJbp51ICIvls54ERgp3sN0=",-5196427476498405440,7314145940101168379,1709576348791986298,294969049645929283>()) {
                           case 1411340466:
                              if (!q.ag(var8.dP())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2x7gar0hgk1nm","RLRQgNBxa0DNtyGO7ZGIzlsDOcQWTPJTOiQNZnHG4/4=",-3796704647208073603,-7335826248606031686,-4887681302797164893,-7696963529953448677>()) {
                                    case 1525504077:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1v1kfohut4w0b","/28+1ZYOsHj0zDIQ7C1NBY00x1GN+rGhHj6lKtydJbA=",-8131122307613597446,2867897969458623036,2485054737619677966,-2188955097674028538>()) {
                                          case 293546970:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 com.yiyiaddon.e.n.o.d var9 = com.yiyiaddon.e.n.o.d.a(
                                    (String)com.yiyiaddon.m.b.a<"s1vcfsdoacbxlp","nkUMSTE6z6keScWNfHWB7mLCJjBFTiAOAqYUTr3KOmrbCeDo0vxd85OX1DkdvWI1lQs=",-9048062267091759564,-6846800446657506294,4114861487478592499,4991156851921824577>(),
                                    var8.dQ()
                                 );
                                 if (var9 == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"szkp14xfpvty8","dLLA8gTSlEV0Vj+G4tm24/1HRxjpNDRWPz9lb1GSwDk=",730710191807060006,6143187343027096640,6676041333605231364,6401995938333510182>()) {
                                       case -923344851:
                                          switch ((int)com.yiyiaddon.m.b.a<"supo1n9xcun7b","Cofkg1SzC8VrIwfZbgGXFMax9ToHeMNKLv9L1N2BjyE=",8549026240492446739,315250954767217514,-875979004951736901,3979522779487954297>()) {
                                             case 360589337:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var10 = p.a(var9, var8.dQ());
                                    label73:
                                    switch (var9) {
                                       case CROP:
                                          var0.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3r9kpv1mrl3ej","eZ3syquh6tsTQ7PGOuUPwyfKTuMmCdTSIN9EHHZvigY=",7210815410569684947,3001628919055608219,-5032861742445164754,-2771369012339754607>()) {
                                             case 673614082:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       case POT:
                                          var1.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2mbrrj1qyru1s","UoLhCR4KT3JejT3z0K/j+5PZyzI00n0/bX+g4Fbo1mk=",-3195980712503975856,9162340626759614797,8045032292779939557,-5884961032773497343>()) {
                                             case 1742497205:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       case FERTILIZER:
                                          var2.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ee2t58se0ua0","LnpeiJ/mo/d3i3fTJW1q6OroQ11+4u/tnp1O9p3Zbjs=",-6476601574096729306,-7894475458322178459,-1900817166630506489,503420473651310736>()) {
                                             case -1082600944:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       case POTION:
                                          var3.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s24wqtmuszwlj7","wKxkH4h23rUe4f7dF+mdELucTLebx7FDJWOPWV4R5hc=",-1715980817197962918,3062607330814876787,-4578260377811720630,-5902329513717219776>()) {
                                             case 1423740332:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       case WATERING_CAN:
                                          var4.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3fg14791i5r1w","HtPv3i8q5k7qfs3Ll5CCF/4BtGVuq1b+O8KvtrQ8Cl8=",7315871269942953705,5593743499901137984,7667505992589509916,-6134904410402946205>()) {
                                             case 67850563:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       case SPRINKLER:
                                          var5.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3j0xdfm6jxgtd","HVen6bgpg7beF9pnpnwhnvVr6gTU3RoCb5HdGVHytp8=",-8121071446380211470,-5728942340695130780,7095330100213430794,5560383560238473647>()) {
                                             case 1917415011:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       case SHELTER:
                                          var6.add(var10);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2g0y6672jssa0","IvHAPzHa9CZzrppECTiQjRC9L5OI0ZxZdompizdz8Is=",8240163648160255538,-6804533657607072326,-8839901512881462377,-472933563768839836>()) {
                                             case 268644408:
                                                break;
                                             default:
                                                throw null;
                                          }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sam0a0th93gvw","q5quVzMvGNkw7/+16vGX6uwABzNoCBb1JRtYbBycDj4=",-5610988465742497673,-7001572723303745022,-2715998804971793543,-1811410668220706789>()) {
                                       case -1242591837:
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
                     break;
                  default:
                     throw null;
               }
            }

            return new c.a(var0.size(), var1.size(), var2.size(), var3.size(), var4.size(), var5.size(), var6.size());
         default:
            throw null;
      }
   }

   private static String a(c.a var0) {
      return a(
            (String)com.yiyiaddon.m.b.a<"s2kg4wy28s82wt","AyzN/LUDgJAXwz7fQZpnHMEqDg0uuu5p9fTgVckwHNk=",1548986226218783653,-4593929257319837831,4530517299129231005,-568708329874353307>(),
            var0.dN(),
            (String)com.yiyiaddon.m.b.a<"s1oyviwvm46dq8","TzKVsy2MIg2EmnOLObsSAsVUR/eHMF1mOnQG2p7ke4SgUQ==",1266527343020974805,8973757397090541192,860525610856993584,5862796915543755850>(),
            var0.dO(),
            (String)com.yiyiaddon.m.b.a<"s3ay0faqp8kw2o","JA1GoWWkXPgB4ZvLzw8hHkHvcf6qtftIFpFx88wNpzc=",-8333845489113378950,4097432074229776732,4001147620210561869,4693095682689371878>(),
            var0.dP()
         )
         + a(
            (String)com.yiyiaddon.m.b.a<"s1vvc2u08rei66","GQRkZ3rASdTtr30RIA7UL8VImLkv9HRk5HmHvht+xNQ=",-6227857218243686580,-3200406798418822379,6716036781348887829,-1737685673947685549>(),
            var0.dQ(),
            (String)com.yiyiaddon.m.b.a<"s3hzhyo4xe6qoh","Om32IpS48HpD7Xzg5XncBzZ0CrtPimfPeefOP1nSRAg=",-114366018785342707,-5929468872907153235,-4457439090118684825,-6457867281176416673>(),
            var0.dR(),
            (String)com.yiyiaddon.m.b.a<"s1ca21twrtcf6d","lMYek/AJvZpyn56++4v8T+xcP/HPBgrHfDz+smeYBIYI1A==",-7926559955054767983,-6889070999688976113,6808272702300037054,-4929537803733205363>(),
            var0.dS()
         )
         + c(
            (String)com.yiyiaddon.m.b.a<"s21ieggaqg67xr","HSfAW5U98vd0DPmDlDI1L6oWvjPBqme62h9SCyRF0rMTSrHl",8877993174435114777,5154875380057932817,3717409948224799554,8957561603726950436>(),
            var0.dT()
         );
   }

   private static String a(String var0, int var1, String var2, int var3, String var4, int var5) {
      return c(var0, var1) + c(var2, var3) + c(var4, var5);
   }

   private static String c(String var0, int var1) {
      int var2 = Math.max(0, dM() - Minecraft.getInstance().font.width(var0 + var1));
      return var0
         + var1
         + (String)com.yiyiaddon.m.b.a<"sqedy7m0c5c6a","r6XfijbZvUiW5EITLpYY7ZAFNCSq6Hcho/COCevb",3872472288689502325,3794572955856176542,-2251230121444082642,3688155048868776208>()
            .repeat(var2 / 9)
         + (String)com.yiyiaddon.m.b.a<"s1kszfkanbejlm","qO4O+16n8M7eKW7JRhPZLgKVrviFXbMO9KRNma3P",5780096771929756363,6409533170573040806,-8114331950834756523,7702494461426517524>()
            .repeat(var2 % 9 / 4);
   }

   private static int dM() {
      return Minecraft.getInstance()
         .font
         .width(
            (String)com.yiyiaddon.m.b.a<"sewaf35ydsx05","YwqttXnT+VPdHDFmMglJE5ozoTCdkoHxr2x4CVfi7gdwlTEUVebUq6oaguZ94A==",4263776542902347308,-3166797601699016294,-1544183893752840270,-8650931557039186406>()
         );
   }

   private static void A(String var0) {
      a = com.yiyiaddon.g.d.e.FAILED;
      yQ = var0;
      sX = 0;
      bc = bb;
      s(
         (String)com.yiyiaddon.m.b.a<"s2e203e1so7jaq","JtuCefoepd1vb5wmlm8UmUVgRtvqr+Y3zXAU3ovkf81C1Vbq",7317339862534775510,-8781971548653338889,7343095706889705923,-4131411560794045180>(),
         var0 + ""
      );
   }

   private static String gz() {
      if (rJ != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k3smle4ykqfx","qHxVgIdsbe4NegTusvYZYnrPM/VinWK+SQEBjaEEoIE=",4089751708263617139,7118958766234570035,855608022878725907,412308846030926604>()) {
            case -1386742820:
               return rJ;
            default:
               throw null;
         }
      } else {
         b var0 = a;
         String var10000;
         if (var0 == null) {
            label49:
            switch ((int)com.yiyiaddon.m.b.a<"s347u3picr01ig","AO87LyZpTiVYK5gPeWo/SUZ6P+qhfkQAlTq9sbD7Ob0=",3494649896246194629,-8977041757395277575,8956105636284456872,1994920171393109575>()) {
               case 50886576:
                  var10000 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s3forw0yxfmvwa","UPW6BhIeFosiAuBrYgI8XaRKwDqvzhu5MXfCG56JXac=",1192496486091573494,5419072703177162902,-2952126956000599569,903282098347288401>()) {
                     case 1198790211:
                        break label49;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var0.gr();
            switch ((int)com.yiyiaddon.m.b.a<"s3dn6y71qg79ru","g7XskLJsyCFWeFKZ7YT3TPmEjTjIAVdQbJ4/7vyAbuQ=",1090925009069490321,2812730594262074311,8583206269569736589,-656841079840565397>()) {
               case 1993788784:
                  break;
               default:
                  throw null;
            }
         }

         String var1 = var10000;
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3vz7zo9x3cisz","EGe3JlF0dyPhp+6o9COoR4XS1G/jAUJMlrR4nVUjurU=",6383324823028137900,6065998828091370338,-8539513635840024949,4226705461319188543>()) {
               case 1566154440:
                  if (EF == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3h1lxxxbch28c","+mG15XdotUxr2hSo728A3stu6R7RIJCwAy51pPmGhig=",-7035332100386515769,-3809350436045047811,5980823031670435935,8927872455167783292>()) {
                        case -980564029:
                           a = g.LOADED;
                           switch ((int)com.yiyiaddon.m.b.a<"s2lquqdoc6tux1","q2audsr0c0ozKnrJZ5emk1LCX5NI7OBi2W3GHKsKfQE=",3199731532273366456,8760348864776977905,-7585564360321156849,-6975884897323970725>()) {
                              case -1173538042:
                                 return var1;
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
         } else {
            File var2 = e.a(mS);
            if (var2 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3kv61hor1rwlq","jgSVgnILI2O1IoQjXuJibG2mEBTPgYVoRE4EKUNKLz8=",9061309044175094693,7322870789109338150,-2837250514132785154,2479734242516806249>()) {
                  case -1221730384:
                     String var3 = e.b(var2);
                     if (var3 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s38d5ptn8jerif","YuimzhoJZjh2xan6KC9/Zy9e532+awbrFE7ekmv6yLA=",-7652146973955879573,-8808419952830340692,1856394813319505440,7794351950647519900>()) {
                           case 722958524:
                              EF = var2.getName();
                              a = g.ZIP;
                              return var3;
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            return null;
         }
      }
   }

   private static boolean fO() {
      if (!I().isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lkk1ayvb632n","nwJXOq0M465oVfQkWKPY12zz088DQIN2iJDQDMcXTR8=",-6622281456596712118,-2249383639197512481,-8146882244069026455,8477065531169888678>()) {
            case 696679914:
               switch ((int)com.yiyiaddon.m.b.a<"snb4wypu6jz99","RJoOlMhEEIigA57PNB9MnK6uBvM2d1BqMFeRqz8qiG8=",-4243703438327052827,-6714378200504963721,-5259052143805867533,-4680257975655605631>()) {
                  case 1116164186:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"sx5jfe74b0kxz","SXWLrSri7pFGSfBQe+ilNoB0VC8GD6BP9NWUFHMX3gE=",-8921263557455518728,4703345019743033581,-7482786640430603421,7692955684744922358>()) {
            case -921545465:
               return false;
            default:
               throw null;
         }
      }
   }

   private static Set<String> I() {
      b var0 = a;
      if (var0 == null) {
         return Set.of();
      }

      try {
         Set var1 = var0.H();
         return var1 == null ? Set.of() : var1;
      } catch (Exception var2) {
         return Set.of();
      }
   }

   private static void s(String var0, String var1) {
      i(
         (String)com.yiyiaddon.m.b.a<"s3ishg525ffb92","VPlNkA88ESVlP0wPmDtkQoB9KppO7AIEVkFak1XqW2C+verqVHQ=",3397759027167554786,-6175440991097111158,4567414772259002252,1011890479395067933>(),
         var0,
         var1
      );
   }

   private static void t(String var0, String var1) {
      i(
         (String)com.yiyiaddon.m.b.a<"s1uyhisjvy22np","KFibYFtn5bVJ12FJo1bBPuqWFC/Mam5RGkrtzRJ76fQsH1HMwdQ=",-5863008051685235022,-3382749212442662655,1033655474852034969,-2671231879028936814>(),
         var0,
         var1
      );
   }

   private static void i(String var0, String var1, String var2) {
      if (!fy) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lq54dht0aacp","7MzL92omQbEOmWZJ/buYErKRkhDiwX/lTnQNnkcbfeY=",1596202871857415433,-7256429892048938940,2497291655295188963,4434302921876479234>()) {
            case -2055653999:
               return;
            default:
               throw null;
         }
      } else if (!aP.add(var0 + var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sy5f3ag37pev","jiKUGJeHnHtJ49Uhh6N8iWPX/hgMC1QT3konZ32wK4w=",-408471752402299902,7405452801028641871,-3575648313552168104,-1242456980998374331>()) {
            case 1157478770:
               return;
            default:
               throw null;
         }
      } else {
         u(var0, var2);
      }
   }

   private static void bp(String var0) {
      u(
         (String)com.yiyiaddon.m.b.a<"s3ishg525ffb92","VPlNkA88ESVlP0wPmDtkQoB9KppO7AIEVkFak1XqW2C+verqVHQ=",3397759027167554786,-6175440991097111158,4567414772259002252,1011890479395067933>(),
         var0
      );
   }

   private static void bq(String var0) {
      u(
         (String)com.yiyiaddon.m.b.a<"s3ishg525ffb92","VPlNkA88ESVlP0wPmDtkQoB9KppO7AIEVkFak1XqW2C+verqVHQ=",3397759027167554786,-6175440991097111158,4567414772259002252,1011890479395067933>(),
         var0
      );
   }

   private static void u(String var0, String var1) {
      String var2 = com.yiyiaddon.d.c.e(var0) + var1;
      if (Minecraft.getInstance().player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12rg58u2vna6y","mtw/Fhu7FQ7MFdmNzGYb0PdNvnbSQWMlqsqtPY2FiEU=",3385004687951953928,1550089134512438647,7821186524879471326,-7161919061028447574>()) {
            case 1385893051:
               com.yiyiaddon.d.c.f(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s3ohmeank7w0io","5GyFnuD2EE1ChHWbshJ5JXEdx/RPYfVpuNLD1CcaLDA=",8554794067197989178,359637191506020270,-8661704465161292218,3822150952738072587>()) {
                  case -830817501:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         b.add(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s271pvn9gkcgje","+HiHYFs3aSQWdo3f5kb7QH8uYMnXo3QtnxTwrBK6FRc=",4573142492688159838,6383692337876927948,4602021657418684557,276271154619201832>()) {
            case 1333989721:
               return;
            default:
               throw null;
         }
      }
   }

   private static String bY(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s17myj3c829mn8","EiXhHImQteTDt9Gw65U2V61+1MsresLfCeSzDZxE/ik=",3877517414638273364,-2227412331093791994,6469604258134346243,1605823568390578457>()) {
            case -621787318:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sdikh68ua5z4o","ctUdkAEztJGBT45R6gqB3nJW+Ue0AkLmRlaEEWucLeQ=",-1848939953611064245,-5340963996697228947,-76697916117813889,1012327417849873777>()) {
                     case 1783804936:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s10g35qmdhn1ib","4ljAMYPSvTsbLFEk9FK9XgSPHF74w28l8ss0EOFqlvA=",-1834514260821818085,4117978737272723662,5554844613968380809,3818710911661889182>()) {
                  case 1151564803:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s5brfuh0whphy","GfXmGcia/8sY8YizRgxQF36K5W/Vr96tgZypbZzk",-7153693139415634531,-7698177564147207946,5840693377148638227,1180425181511482197>();
      switch ((int)com.yiyiaddon.m.b.a<"s2vy08870t9k57","OgdfJR4vZdZezXZyqIFTG9G2OuvWcodRmsDSwrJORw4=",4436665924674509972,3803977345919613989,8122133975760915441,-8165033313778041983>()) {
         case -1952464515:
            return var10000;
         default:
            throw null;
      }
   }

   private static void jC() {
      if (Minecraft.getInstance().player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sm5fqicmnni7c","zYTOLqykvMvs3rgK5DjRny+LIIX2F4HXbxoanbmIoYU=",7660706212004242548,-172564557745071927,5163479184646405681,-4899181798562299822>()) {
            case 1833865715:
               return;
            default:
               throw null;
         }
      } else {
         String var0;
         while ((var0 = b.poll()) != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3037utva9itk4","VMj9tQ5yY15UixTz7Gn8rMmwXLhPZzxPI+kiMLVOYc0=",-3150374980362802914,8252155827233395026,-5490037338216944124,7296321884717087797>()) {
               case -234017501:
                  com.yiyiaddon.d.c.f(var0);
                  switch ((int)com.yiyiaddon.m.b.a<"s3j5psbtyxcyw2","1Yah3/ZH6wP8O630skXd3dv9zBMJ/hgOLxMxKNnFvGE=",7217825775187158328,-9141175581769161737,-4552603173664157443,7478891506413352947>()) {
                     case -1307004694:
                        continue;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static void jD() {
      for (Runnable var1 : new ArrayList<>(cW)) {
         try {
            var1.run();
         } catch (Exception var3) {
         }
      }
   }

   private static void jE() {
      for (Runnable var1 : new ArrayList<>(cX)) {
         try {
            var1.run();
         } catch (Exception var3) {
         }
      }
   }

   private record a(int sY, int sZ, int ta, int tb, int tc, int td, int te) {
      int di() {
         return this.sY + this.sZ + this.ta + this.tb + this.tc + this.td + this.te;
      }

      public int dN() {
         return this.sY;
      }

      public int dO() {
         return this.sZ;
      }

      public int dP() {
         return this.ta;
      }

      public int dQ() {
         return this.tb;
      }

      public int dR() {
         return this.tc;
      }

      public int dS() {
         return this.td;
      }

      public int dT() {
         return this.te;
      }
   }
}

package com.yiyiaddon.e.c.j;

import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.h.g;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public final class d {
   private d() {
   }

   public static f.b a(
      com.yiyiaddon.e.c.j.a.a var0, com.yiyiaddon.e.c.a var1, String var2, String var3, Predicate<Block> var4, Supplier<Map<String, Boolean>> var5
   ) {
      return f.b.a(
         var0,
         () -> var2,
         var3,
         () -> ((Map)var5.get()).size() + "",
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s1hm6nchte2d9m","t5ehr/honmkjv5rOeLhBZziNYAaA5YiUb66J5J59QNo4+RVs",1763920449528056261,6318764535828520996,-2183599338267531493,8557163423385409156>(),
                  () -> a(var0, var1, var2, var4, var5)
               ),
               var2 + ""
            ),
            f.b(() -> {
               ((Map)var5.get()).clear();
               var1.L();
               var0.C();
            }, var2)
         )
      );
   }

   private static void a(com.yiyiaddon.e.c.j.a.a var0, com.yiyiaddon.e.c.a var1, String var2, Predicate<Block> var3, Supplier<Map<String, Boolean>> var4) {
      ArrayList var5 = new ArrayList();
      com.yiyiaddon.e.c.d.a[] var6 = com.yiyiaddon.e.c.d.a.values();
      int var7 = var6.length;
      int var8 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2jzjof93ctsg0","ufKChP1LyrcPJ7nRL1jRSVwqAuul5rS5RM2vtO3YXXU=",-2889685157725417524,6142834185743142671,-158827685673686852,-900756409744009921>()) {
         case 1211338448:
            while (var8 < var7) {
               switch ((int)com.yiyiaddon.m.b.a<"s1vlnfemxa4ugc","4WjtbfQFihu3fRpTvyAle64RtsTc+P2PmK8OLVQQ9mI=",-3433496255232411103,3418663445835743361,586876199995847407,-7831502352076629035>()) {
                  case -1741714424:
                     com.yiyiaddon.e.c.d.a var9 = var6[var8];
                     if (!a(var9, var3)) {
                        label27:
                        switch ((int)com.yiyiaddon.m.b.a<"s3bie6oe92por3","JcyPZXkNS8Wi7K/omhCc/aM5PAJ+5nbR43eWRCkoubA=",-2770692141917531312,-1506214296405450740,-4791416080875318611,-2117118365694696333>()) {
                           case -429401279:
                              switch ((int)com.yiyiaddon.m.b.a<"s181c1q7abhebw","TBcVgu+Apbp1Cp8kPIV+ElypWJC0MLxn4DOWwD4q2Dw=",-6767901128235315810,-2425581354554732873,-5991535062238913506,6402574766076032461>()) {
                                 case -2137423630:
                                    break label27;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var5.add(new d.a(a(var9), var9));
                        switch ((int)com.yiyiaddon.m.b.a<"srmfo4wetk3ui","xqKDVY1EhtFzivNsRc39Z0oSDXUMdQrcQCWxohvoeZ0=",-1092091605648442471,4543557065192706130,5137562656150813289,-8648247482730752963>()) {
                           case -918873192:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var8++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2r26tdvl0nvmp","emLDFX/D7ft/lf8ZjvOckaqho9oHrvNEH+mOGLr2vFI=",4059782448094380769,-4853271906986970058,-2009288038640209015,-2487528109353709351>()) {
                        case 1583405213:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var0.a()
               .setScreen(
                  new g(
                     var2,
                     var0,
                     var5,
                     () -> List.copyOf(((Map)var4.get()).keySet()),
                     var3x -> {
                        if (a(var3).contains(var3x)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2hl774yejg72c","y1Jo56aOuew8iw2F1wWGxywCP/AcT85g+aQ4XcZRZlE=",-5101520373421170753,-533376294796332503,4711735267439599465,7091417232426873365>()) {
                              case 333550384:
                                 ((Map)var4.get()).put(var3x, true);
                                 var1.L();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3cj3pvrsp6luy","JvcznPP8qJ685IRWm8FN4JQLNRmlhR1qdPAX+pbAA4w=",-5458475469870391066,-1327218655024341374,-6888424617369622296,-8230604355067717159>()) {
                                    case 401163813:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     },
                     var2x -> {
                        ((Map)var4.get()).remove(var2x);
                        var1.L();
                     }
                  )
               );
            return;
         default:
            throw null;
      }
   }

   private static boolean a(com.yiyiaddon.e.c.d.a var0, Predicate<Block> var1) {
      Block var2 = com.yiyiaddon.e.c.a.a(a(var0));
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yhowq7o3fuj0","lrmdXfwEMGc/Hubd7IrENldTxRQkDO2WhHjc9VAaudg=",-3067694721380274015,430075594090178539,-5944090880190973433,-8479438820354528872>()) {
            case 445120777:
               if (var1.test(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fk0ildig8yb3","G/MUusGH8MgGx8kn8BI5BSOvMJT+AZzgFw6IxOiv2Zw=",3181830592057999083,-515245340909542040,-5431866942338970266,-6174252927585367698>()) {
                     case 1629524009:
                        switch ((int)com.yiyiaddon.m.b.a<"s33qnm2diid74u","2uBb7NcYiwb7AFEEOFWzmoqGMi7yiM1RsgQ0Dn5HMMY=",682197612043205952,351153685604992688,3827293129564141150,-8242702598634078555>()) {
                           case 515860462:
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

      switch ((int)com.yiyiaddon.m.b.a<"sj59izmvzlfm8","6SP9h0yrIxqQ1gFGWPVCfH5WTPZ+1Ku88nIQwy0GgpQ=",-7639644011861188637,-100088860084825610,2388886786324381460,8721476115709053547>()) {
         case -1369384883:
            return false;
         default:
            throw null;
      }
   }

   private static List<String> a(Predicate<Block> var0) {
      ArrayList var1 = new ArrayList();
      com.yiyiaddon.e.c.d.a[] var2 = com.yiyiaddon.e.c.d.a.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sm9bff02qnd6d","UExR6LkU3uNstQ0nGhJghFou2N55yYkXjsa0/NVyf4g=",-5092697785685216906,1237114962936031538,-1500179366245318797,-8247758465457465861>()) {
         case 2020444097:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"sqjfyc2wvm6ki","quwD27plJ5WbZ5D2v0gTp1XxkQ7dIcYGlAJRwt9CIdI=",-8782932927175553122,7628867596813672792,7982174725060392494,-8352431566149109481>()) {
                  case 540417949:
                     com.yiyiaddon.e.c.d.a var5 = var2[var4];
                     if (a(var5, var0)) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s3vxtomirtjmwo","JYVMmfPxEPsGGfHiNoYUA8wgyX3v+91NwGIY7wyDmGE=",-5006847810511547027,-5268428505957886041,-6398785045680249843,-8709060267364516970>()) {
                           case -2051666457:
                              var1.add(a(var5));
                              switch ((int)com.yiyiaddon.m.b.a<"s1ts54ysl4m67c","f+gsOcozxPBSyDvEs9qv02+1oZj085mKtiU+gNp+4w0=",-4120766550435471561,5979183925282947628,4377606361186883578,9129378246151446209>()) {
                                 case -2130148096:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2a5v1lmywhjgv","7SLSD8KfbvilTfdBuc7J6XGOneea4rVg8hnLdTiA5nY=",-3410961271276615273,84638089901971945,-3568220220250186609,7753092964159068140>()) {
                        case -1638301850:
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

   private static String a(com.yiyiaddon.e.c.d.a var0) {
      return BuiltInRegistries.BLOCK.getKey(var0.a()).toString();
   }

   private record a(String ce, com.yiyiaddon.e.c.d.a c) implements g.b {
      @Override
      public String L() {
         return this.ce;
      }

      @Override
      public String D() {
         return this.c.m() + "";
      }

      @Override
      public String M() {
         return null;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         Block var5 = com.yiyiaddon.e.c.a.a(this.ce);
         if (var5 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3rxyc14tdukzt","lYNb20ZhBZf6880YnU9RFKP9V+cq9ENPG1qNp9QEDR0=",-6010291716171595251,-1540448320696124978,5362233192763860052,1681877722419829378>()) {
               case 206008471:
                  return false;
               default:
                  throw null;
            }
         } else {
            ItemStack var6 = new ItemStack(this.c.b());
            return com.yiyiaddon.l.g.c.a().a(var1, var6, var2, var3, var4);
         }
      }

      @Override
      public ItemStack a() {
         return this.c.b().getDefaultInstance();
      }

      public String s() {
         return this.ce;
      }

      public com.yiyiaddon.e.c.d.a a() {
         return this.c;
      }
   }
}

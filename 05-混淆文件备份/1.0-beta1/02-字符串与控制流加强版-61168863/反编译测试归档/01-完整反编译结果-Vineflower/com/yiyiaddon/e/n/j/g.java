package com.yiyiaddon.e.n.j;

import com.yiyiaddon.e.n.i.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public final class g {
   private static Map<BlockPos, List<String>> ah = Map.of();

   private g() {
   }

   public static void b(BlockPos var0, BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1c983mct8uuu5","v8WcltEw0h0Nk/XdHZtA3hbh9V3BfHzPEnaapHVauYo=",8262019013122741154,5811317206003433762,8529814192165725612,1694890595626625666>()) {
            case 115159598:
               if (var0 != null) {
                  label20:
                  switch ((int)com.yiyiaddon.m.b.a<"s3m1lp0g5ehl6r","NymwLGlsYfm4ySkgyacmRlCU0X0pss8JxrAUQGp6dVc=",4760430811726970067,3195002008597028643,-3631478793239243198,-78314063391421399>()) {
                     case -1780057857:
                        if (var1 != null) {
                           HashMap var3 = new HashMap();
                           a(var3, var2, var0, var1);
                           ah = var3;
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3f5aghw5uqu7o","PKXrl798K3KE21m8qm2LaR+D+wdpCWSh4bXSXUr9rho=",-40420292470814399,-3788287447034033541,-2255922060555061015,-4858019824670934046>()) {
                           case 159244452:
                              break label20;
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

      ah = Map.of();
   }

   public static void r(BlockPos var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1d1ok4g1nuzj1","iUCKdNQk/cFY5RbzgdEhbiYSaXVxG/nATP5RNU9Prlw=",-1631699557733918813,7413583289203925563,-1040729714655428037,407058843819272161>()) {
            case 811161381:
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1.level == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ojxe75b2d5ga","fVq0iRqYkE9PAHpZ9tCRVKvDI3rw5COOeUavJiq6bws=",7190292727701416631,-7419245921231661007,2580719759092888265,-2537123047651263051>()) {
               case 2086476557:
                  return;
               default:
                  throw null;
            }
         } else {
            HashMap var2 = new HashMap<>(ah);
            a(var2, var1, var0.offset(-8, -4, -8), var0.offset(8, 4, 8));
            ah = var2;
         }
      }
   }

   private static void a(Map<BlockPos, List<String>> var0, Minecraft var1, BlockPos var2, BlockPos var3) {
      AABB var4 = new AABB(var2.getX() - 2, var2.getY() - 2, var2.getZ() - 2, var3.getX() + 3, var3.getY() + 3, var3.getZ() + 3);
      Iterator var5 = var1.level.getEntities((Entity)null, var4).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s35xue7xswxtmj","7TkqcUthF32PvXU0KqMdADSdbJI6g8jkmF7hhrwbBvQ=",-4867182226124045850,7961035495507287733,-7216107115703026868,-1321871389361760523>()) {
         case 1250501090:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2xowsc6hggqpp","BVKR3Q9Ymb3qPIL90Vq6M1EvpgMdQ+oIQAsxQNyJWrs=",-3338997531711515521,1346826823731210419,-4883313792714938526,-3859571449201194994>()) {
                  case 246512333:
                     Entity var6 = (Entity)var5.next();
                     String var7 = a(var6);
                     if (var7 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2zqxcggg2swv3","wD171SK8ZOQanBZG568rq55CqczMwMMqK+UUZxy3ouw=",-2795668589883149552,-1343536225154500437,-9091473213312281363,-894316629612875524>()) {
                           case 1715232904:
                              switch ((int)com.yiyiaddon.m.b.a<"s1kt6vhbam5u6a","UtXR9cOOV3RXbc1r5I0qv3WQvTXN6q1fTk1l9g8KXjg=",913123366643443413,5595988851385196535,-4352678570172031375,-675629896437558602>()) {
                                 case 1071956877:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        BlockPos var8 = var6.blockPosition();
                        a(var0, var8, var7);
                        a(var0, var8.below(), var7);
                        switch ((int)com.yiyiaddon.m.b.a<"s1lsqehdik0rwr","rdebWLCILLweLkG1Sdr5vvAnIofna6YwGo+PZI5YU2g=",-6667339775394606311,-6777793013047673690,-7174107989457609356,-1076320535636380757>()) {
                           case -34100478:
                              continue;
                           default:
                              throw null;
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
   }

   public static String a(Entity var0) {
      if (!(var0 instanceof ItemDisplay)) {
         return null;
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2x8d9p0tp1u3e","f3IjByEnT8UTJqpGTLdZyvK9OKQKOwsAfvofSVUd4a0=",-3611462873019862926,-497929041530083311,-450080615109285846,5459121138778144545>()) {
         case 1918905783:
            ItemDisplay var1 = (ItemDisplay)var0;
            switch ((int)com.yiyiaddon.m.b.a<"s1tmvks650ss9a","kI5NA7v1kUD5t/K6SUkszbF0ieN13bBvi434pvGOwHg=",-5994391540415547353,-1300508668053478936,4641464826621865025,-426264866980192084>()) {
               case -931630565:
                  ItemStack var2 = var1.getItemStack();
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1dh5rh98peo7a","I4pM8993jG/Gpm/HXvfUdfbGqont8OrEDuoPmYOtutA=",-4906333896199961346,-3612445310749709082,7658784157497968721,391092727551475680>()) {
                        case -348940534:
                           if (!var2.isEmpty()) {
                              h.c(var2);
                              String var3 = com.yiyiaddon.e.n.p.a.d(var2);
                              if (var3 != null) {
                                 label35:
                                 switch ((int)com.yiyiaddon.m.b.a<"sktfitj6z24ra","paomPDSCsl9OlbwsYc3gXpa0oCZfpKyvOU6XWyHdjbc=",3440056599586111597,-5864253318832158404,-2665437359969319661,-3338250076543954864>()) {
                                    case 1325419015:
                                       if (var3.startsWith(
                                          (String)com.yiyiaddon.m.b.a<"s1nfhcawjjigad","ccaX2e5UNMng2s9+8KPJ5GfRNTbVOhyjTMEU9UCr7mQ4dbWXFT+omkrVJHeaYc373T/mNA==",6476098827612541623,5574527611164410106,8723274918858649580,1285481714010523240>()
                                       )) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1l80gl01kq47","h8tHqDPqW09Z41HCiJRsYceZ+gNd1Fpx57GT0lOUYPU=",-2198818687344405990,-498500876504714357,5899934056524910414,-2437447000573354306>()) {
                                             case -1875048468:
                                                return var3;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2ycwogwn2x6bw","PGs5gCobpc0c6lz3fc3UzPzNbk6VfxY+V7QyMIhO61o=",576419857384167481,339299044613266821,80137553982685805,-9098484221032458704>()) {
                                          case -1015378554:
                                             break label35;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2mkzjw7vnfzp","tdAsA9b7ZePRdtvVjBzrns6puZ4xJ+MjEHXU/XKDVPU=",3565574892592573782,-1227098334892623935,3824627560688006380,2858224118792422401>()) {
                                 case -795591599:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2pydb6vf8lk91","15/iX6mjdt9753yrPmHVp5HeVdX38/rUwzmFWpYn4Zo=",107596964476850742,-5667011245918065080,2170290874441286169,2104290722469258062>()) {
                              case -487174459:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   public static void s(BlockPos var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gwhs9g8sngce","xs7oNaHY7oHiBvlrQu6aB0pXME9XytrYdqySETe9ixY=",6136563318888123009,8077924332258351000,5796068951282806139,-3335439190230462288>()) {
            case -750532485:
               ah = Map.of();
               return;
            default:
               throw null;
         }
      } else {
         b(var0.offset(-8, -4, -8), var0.offset(8, 4, 8));
      }
   }

   public static List<String> b(BlockPos var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s6gamf4pq2sf0","JP/SAa6jQrCeBYBPjigPzHfHhREVsfiHJfugPHqP/1U=",3610362617591180484,-4712048183217452218,6566983159163532352,-7645240574718846886>()) {
            case -474496972:
               return List.of();
            default:
               throw null;
         }
      } else {
         List var1 = ah.get(var0);
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s242ptdnmsudj9","1Tp2gLEobK25Bx6GN/a4mAXXej2aNPYiNfLrKKrvbSs=",-802294429243198448,2728763276042645624,-4060054558593036706,-676922579823459101>()) {
               case 577365598:
                  List var10000 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s3e4ytiekr4wmj","hkk6tMQ8sNcvoFmeX/DtRtQa5mSpCPQ9heysaNRSzlQ=",8362295883908790139,6129522985467526961,2866329845492850756,3609373394459490428>()) {
                     case 1556555741:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s1bozxipwqi4wm","iUsLBTmce2wqeuUsoFzY/cvOzdAJsOXDX4tIOvtWq+E=",5523724126782731927,5516529513248950056,3369872775765238867,1936996617635188525>()) {
               case -446829879:
                  return var1;
               default:
                  throw null;
            }
         }
      }
   }

   public static boolean dt() {
      if (!ah.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3lln8jtfkn2hp","qBXzVqRJEulgE0W25LpBxfxAWeItt6CKG819wRrHRe0=",-1429474981846742783,-8864050558532652128,-5913657028145390088,-4250414960160411631>()) {
            case 1064063816:
               switch ((int)com.yiyiaddon.m.b.a<"s3kbw8ebww37dh","4F6DomWa9Glh74npAR4eWoA/bz5vgj3NPW8PhNHEmIk=",8495645117888922228,-6475000833456552176,885637090554537714,4082136444695248403>()) {
                  case -118786345:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1gc0gqxthtvya","4gqujm+Tu6VVjJVnWpXoaHgbB7ExIezya8IlnVYi5DM=",-931113997859950062,-835114410689533972,4831917634115284419,7663853177219804756>()) {
            case -679451176:
               return false;
            default:
               throw null;
         }
      }
   }

   public static void f() {
      ah = Map.of();
   }

   private static void a(Map<BlockPos, List<String>> var0, BlockPos var1, String var2) {
      List var3 = var0.computeIfAbsent(var1, var0x -> new ArrayList(2));
      if (!var3.contains(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tbz2f0w8cfjn","QswPCm88TKaVnWHkF/RuSnrhBidjG1wNeQcXJn0KvLw=",162755945337348234,-7718833602434689139,4334628905755139041,6320292945842028272>()) {
            case -346105694:
               var3.add(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s37jmgxfa3tn38","gplu3l6PIjf/EwNHg/Nj/Pm4Rl59NB20XV+mjSlp7y8=",-5517772114183453649,6044602644245424413,-1386283539810857641,1897834323988718642>()) {
                  case 1082105599:
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

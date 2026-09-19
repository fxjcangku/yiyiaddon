package com.yiyiaddon.e.p.e;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class e {
   private final boolean eU;
   private final List<AABB> ck = new ArrayList<>();
   private final Vec3 k;
   private final EntityDimensions a;

   private e(boolean var1, Vec3 var2, EntityDimensions var3) {
      this.eU = var1;
      this.k = var2;
      this.a = var3;
   }

   public static e a(LocalPlayer var0) {
      Vec3 var1 = var0.position();
      EntityDimensions var2 = var0.getDimensions(var0.getPose());
      e var3 = new e(!var0.isPassenger(), var1, var2);
      Entity var4 = var0.isPassenger() ? var0.getRootVehicle() : var0;
      if (var4 == null) {
         var4 = var0;
      }

      var4.getSelfAndPassengers().forEach(var1x -> var3.ck.add(var1x.getBoundingBox()));
      return var3;
   }

   public List<AABB> a(double var1, double var3, double var5) {
      double var7 = var1 - this.k.x();
      double var9 = var3 - this.k.y();
      double var11 = var5 - this.k.z();
      ArrayList var13 = new ArrayList(this.ck.size());
      Iterator var14 = this.ck.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sryebrkob6676","as0+QgzCrnv/vrQ6iGdqDx2XkKaWoXGto7DPGCgPlo4=",-8119496763902719539,4442430878181496512,5443238646292119134,8030444747330062512>()) {
         case 1745522281:
            while (var14.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s223lkp5h64lav","ziXEIy5/K5E0Rv2wpvOZjfaETKIfYmh3oyYAmOif/vs=",-2542674586013525455,-8335693570723040202,782091297891778145,7776044794633442735>()) {
                  case -1559082629:
                     AABB var15 = (AABB)var14.next();
                     var13.add(var15.move(var7, var9, var11));
                     switch ((int)com.yiyiaddon.m.b.a<"s16h1hdyy1z2fu","53opyS19hl11kAVXKgi2Y1+cdeOtdRvpYAFxR8EQSv4=",-7422786034027118475,5861406266909896960,6801088402967047478,4570394449124265652>()) {
                        case -1390677750:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var13;
         default:
            throw null;
      }
   }

   public boolean a(ClientLevel var1, double var2, double var4, double var6) {
      if (this.eU) {
         switch ((int)com.yiyiaddon.m.b.a<"s345tfkosjv7jc","1FjmuEQH+Leeog8aNCvEvPgbg4pZNKhW0KwPlvAHNUc=",-5060745723404261769,-6188682475694495701,7144444695218811208,4551470586339386703>()) {
            case -1925714102:
               return com.yiyiaddon.e.p.h.a.a(var1, this.a, var2, var4, var6);
            default:
               throw null;
         }
      } else {
         Iterator var8 = this.a(var2, var4, var6).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2lelz5bup8tvc","ht3Hulah5Vf0UjJ9eqV3qtnlUXhjq/sVCh4BD3jsUQk=",4628741833812355915,-3049782603940679395,7440191262980198323,-7619646976220123383>()) {
            case 231773809:
               while (var8.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sf3e6lt0xaytc","qNuakI9rOh0ZpN9x5fXvACTBOBNQIi2FSg0uB4OhZI0=",-113096681640229991,-2087618684958351032,-7908357234143333673,9180422832793337086>()) {
                     case 1052385467:
                        AABB var9 = (AABB)var8.next();
                        Iterator var10 = var1.getBlockCollisions(null, var9).iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s202dc6fm6i9xz","vcSUElBDU+5iYEtSdGGeRQ8HK0qr1RQbO5K7tzioAxI=",4303409042202299303,8112842484533035325,-2286014867349049160,8267586471879113040>()) {
                           case 1331016645:
                              while (var10.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sxuxyd6waz1dh","XUNC5MQK6vft9jVhK+Ge3XT4xOLq2WCqEAwX70Tnffc=",7871461777686803838,5724810280024452324,7767950382072595245,-1779697700561864174>()) {
                                    case 55628621:
                                       VoxelShape var11 = (VoxelShape)var10.next();
                                       if (var11 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1xub6y2lyu5c3","p7rW+19WNKoWkX67luPI7RyF+5ejp/dXl9nlEp8w6Fo=",3584340823817677377,-5765806025196566154,2763619411866355665,2591446102179056574>()) {
                                             case 2083585020:
                                                if (!var11.isEmpty()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2v9tqiqmez49p","X+PsRJ6oTdi+HcVuTY00AjpxrW3IbVzwgzWLoXNxIV4=",2487853729956416111,6546188023324212515,4125010346033809879,6663341567544379475>()) {
                                                      case -1832731256:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s2hdw97rinz6dp","Ysa258u3LE5tN1RJPyelelQzdRTccOQf8UO1l+xsRKo=",-1567499527670076256,7743300674975941985,-9069509870207993207,7851313838522129705>()) {
                                          case -144493356:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2jy7nap814yes","og5kLEWecOfLy6YEaciV7ZsxxAWhNJpf9kD2pxHaxPo=",5852530464804464480,-3800585393749698481,3030578545953839892,-7333223776544389867>()) {
                                 case 1348920281:
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

               return true;
            default:
               throw null;
         }
      }
   }

   public String a(ClientLevel var1, double var2, double var4, double var6) {
      if (this.eU) {
         switch ((int)com.yiyiaddon.m.b.a<"s22on7fke58u8s","tnLXWF3C2fJ+RNABCEP1tO1PQlEwDB+LngBD+KdNxtg=",-4030698754463598136,6517651314746760678,8379065074455210061,-6042809390983556563>()) {
            case 1710225363:
               return com.yiyiaddon.e.p.h.a.a(var1, this.a, var2, var4, var6);
            default:
               throw null;
         }
      } else {
         List var8 = this.a(var2, var4, var6);
         if (!a(var1, var8)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2u2oa28l9i3sd","KM8rSp4Q76n32dj3hWh36tBhpywykB8IoSCysepFtZA=",-7300098665865306014,-3616196819120889182,346675791129464914,1620716290691672687>()) {
               case 1237843289:
                  return (String)com.yiyiaddon.m.b.a<"s230wqyvbf8aiw","eSrmbj9BHBeSGhXlNsEpgpluuhLTy6nTy9rbFvg8n0Ej09tquPc91ouym+j7KRLGSqQ=",-3047685583165665404,-1878517018620409710,-7830433228585870049,-7470304936961810186>();
               default:
                  throw null;
            }
         } else {
            Iterator var9 = var8.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"sv8r07ut1s7ep","wcOHvcxHUMP4Laz6fBPnI2UbS4dxavg8YcYMDDy0Q4c=",7554224631074964760,-3018407884482104415,8194561666973587928,338690494796155811>()) {
               case -2116827757:
                  while (var9.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1uujjurt4dver","Dy/dTkbOluzDDrMQtMxk1jTAfgOxAj0JHfePCh97KwY=",-2070287088211905533,-5254079486978856279,-1353560233096268188,-6743196528398228743>()) {
                        case -112285842:
                           AABB var10 = (AABB)var9.next();
                           Iterator var11 = var1.getBlockCollisions(null, var10).iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s17db13rkp3irg","uatQ6e78PLTtqkor5hp8GI6HyfGRIoayfa914+Wm72Y=",-6973903218144921560,6259025799605797935,3604910236734366377,6146696318812145206>()) {
                              case 1334348370:
                                 while (var11.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sadyk9ld44o6p","MW2JqKOOEQe3EHPrZKHtDPgvPEwtDajNQav2XAgEfes=",-2946042430609837561,3357000903634037341,3126994624029394182,-7410491796294642685>()) {
                                       case 1858495694:
                                          VoxelShape var12 = (VoxelShape)var11.next();
                                          if (var12 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"swr9yu6hyxdl5","BhaL2tfphrB7q8b5GGo62EqfIT3odNhrCtKL7LxX8t4=",-2981268383765518856,4510515008234731546,-5377681015004839239,-1583321072450643525>()) {
                                                case -45824593:
                                                   if (!var12.isEmpty()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3uxs0abtqev4k","OqTqoQbE1zGeyv8Hh6JZLSXgqPFmEyKnkJcyGslEXps=",-8062291016229235203,6910733651856934151,8407088821360037794,5861654653569327012>()) {
                                                         case -1162252062:
                                                            return (String)com.yiyiaddon.m.b.a<"s3k8741nat7r9c","rCq38TZQmJG+vY0jq6R5FiMimIr04zkonZM3dP7QJP7v/yu61+bTuP/S9xc8y6rqOA0=",698357834302901794,7315377582634354938,896705261885410076,-3145279817915873460>();
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"sd1lncysd49ol","ijXyldt2OgzGvQGYHwze/NjWf5Wga8+MhwMb10IJABM=",-2662584227384561395,-2319094343158704286,-7992910018083080174,-3710481556639678234>()) {
                                             case 1186690877:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s39556fh1lur66","6Cq7OuAhnxuqbnCLQiZWwQiIeQDs0FRXKD24hYP81CA=",-2958384861120116347,-6415890247217602951,7669199278168199301,8950416819495456185>()) {
                                    case 1417737369:
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

                  String var14 = a(var1, var8);
                  if (var14 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2q90e9rg7n2n1","vGhbcGUlcEpP6tXlFwwh0Fzc6jHDZz2g9so5cDxHdpI=",6708960976309564265,1407820561960671949,-2351792599078249728,-9189718600711906966>()) {
                        case 663290976:
                           return var14;
                        default:
                           throw null;
                     }
                  } else {
                     double var15 = Double.MAX_VALUE;
                     Iterator var16 = var8.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"smy4q68misfwz","2eu6gCW03n0tj43a9dzkuDPU0IjADcTRoJE5BThKPV4=",22200726999251793,-3406701134133411763,6269326918422009697,-5985573788301207508>()) {
                        case -1420727934:
                           while (var16.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s38u7f47gfr55k","kRuzDCVHlrSo4va0F+Uhe/euOM0m99iQgmDPLbEf/Bw=",5495360283651892850,-6864835853173601833,2905487365932206777,2095839493854054675>()) {
                                 case -1170494565:
                                    AABB var13 = (AABB)var16.next();
                                    var15 = Math.min(var15, var13.minY);
                                    switch ((int)com.yiyiaddon.m.b.a<"s2kgo2hwktzlkf","cI3QPE/B8EdQOUSwNeAY4ze6rA4Xzf50ZH6FxQ98sho=",1669606144815518544,-5238103681181647394,2721628113006655096,-5566115531766448538>()) {
                                       case -1632806927:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var15 < var1.getMinY()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3sakx9w9jl4z2","XUaI3uostuhQJ74gXJdjsX0ciqMGrm2wLng0c0nIGws=",-2513021595465274530,461112410047688763,7180087544020608634,-2300752241183421957>()) {
                                 case 1766329557:
                                    return (String)com.yiyiaddon.m.b.a<"s1d1q0r6moyyh0","3hYdHslmnLQGdJJtyKElGUTtCxAhb6NhRp67hMy3AZTvc6qyRLKJlv1NQOyM+2ZqdXU1AlJ7",8883297810776711247,7880524121917300254,-4589459476948033465,-1367355747103816589>();
                                 default:
                                    throw null;
                              }
                           }

                           return null;
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

   private static boolean a(ClientLevel var0, List<AABB> var1) {
      double var2 = Double.MAX_VALUE;
      double var4 = -Double.MAX_VALUE;
      double var6 = Double.MAX_VALUE;
      double var8 = -Double.MAX_VALUE;
      Iterator var10 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2u4bfcq4i539w","QbYBOrwRcLVz9nNlOWinhz/Se/71htETg1IzLOy1Tr4=",3169241296097741583,7379714719036758211,-6959085281048402769,-1129176070834943921>()) {
         case -2059979177:
            while (var10.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s31jlsgcls2hyb","6w0vZS+IuMlr0zrw0utvgB7WdOdBIEYH212uiobrJzA=",-4270372342818800746,1158829736030866479,-8209252056347836665,-5890108464563909678>()) {
                  case -1361938308:
                     AABB var11 = (AABB)var10.next();
                     var2 = Math.min(var2, var11.minX);
                     var4 = Math.max(var4, var11.maxX);
                     var6 = Math.min(var6, var11.minZ);
                     var8 = Math.max(var8, var11.maxZ);
                     switch ((int)com.yiyiaddon.m.b.a<"s2frahf89etmbx","fVgUDg6uujEfwfG8tp3RCQ0d6ScO/UurvAwVUA3VFio=",8094605908538088233,-6245497761023464238,2274630245961916425,5983666388268806931>()) {
                        case -211099106:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            int var16 = (int)Math.floor(var2) >> 4;
            int var17 = (int)Math.floor(var4) >> 4;
            int var12 = (int)Math.floor(var6) >> 4;
            int var13 = (int)Math.floor(var8) >> 4;
            int var14 = var16;
            switch ((int)com.yiyiaddon.m.b.a<"s2bpyrjhobzoci","C9LYvBzi2E6KEe+KAR84mkyfkaD5ye3IOsOdNG8rG1Q=",-3090995354950103618,6904816483385892153,8571398595665792539,9072075899133249329>()) {
               case 1113019009:
                  while (var14 <= var17) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1m7762qgm4l6n","Ylpot0asL3LGmioPPNos13pCtM2KrXB9dsMHCjcFn98=",-5355631934026028739,3013908202912524011,-6727781582618625325,-3717948710719410976>()) {
                        case 277513266:
                           int var15 = var12;
                           switch ((int)com.yiyiaddon.m.b.a<"s406ac3a7mfnd","FzFtw3WbJe92rm6IAcl4Mft55zZuk0ND/vmEgRDSs3Y=",5019953198844988733,-4609029935700559034,-3295774131783380734,5447335536181758655>()) {
                              case 714345449:
                                 while (var15 <= var13) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3u79s3md8vtob","xSlFrXtFYbUFLPbMmbekdC3PAVRns3k9UH5mNvkNZ68=",4660849503453527186,4091947847655017545,2250703952136029841,-6879867463905495452>()) {
                                       case -111996207:
                                          if (!var0.hasChunk(var14, var15)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s12f0h2r1yjvcg","DUvx8JPA4yklPOA0mxGr/7qQyQXgJruSstt/Vw07fss=",1707244418203954756,684206197863502830,-7566909429940791463,4477548372965296572>()) {
                                                case -1978670460:
                                                   return false;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var15++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s2t1cuje5kxu4m","q3CYkJxXkA+fF1QMAXSmkMsRay9/iXBFcLcwZ5yio0A=",7416058598082371504,-6366451187994396969,2217856571469731035,3321385837788615425>()) {
                                             case -1869594494:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var14++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s12w0459rs69p3","7GLfq1YZSl3IdYV874DoUolunIcJaTYVLXmlGULl9sc=",-7846148548690220297,-7434573731085251147,8781515946860562629,3727055109651736514>()) {
                                    case -886592450:
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

                  return true;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private static String a(ClientLevel var0, List<AABB> var1) {
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2kyq1japcivqz","Za7ikt0/6L5TJPOKcSsDr6oTzlS4lcfLC8qicD8Zn4Q=",-4255514452859086651,5530138422208592553,-4066682219183861064,8398190190223430587>()) {
         case -440117878:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1d6xztryl079x","nC6yEYVQeSVIOmsN+DCrcGbUi/CCjLproHaAAK0qEjg=",7792444110023714666,8405875877174338861,6631734739973988017,-4716227896001499582>()) {
                  case 1925284496:
                     AABB var3 = (AABB)var2.next();
                     String var4 = a(var0, var3);
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s14yyy8xbc7o6b","pBNUU+RQqotk9gV30ioxSe8Ht5siG8SornonjlUNjEg=",6244953099348477079,322172545650774459,4517262999897186933,7542589042526524852>()) {
                           case -897343748:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1y261c3efn4i9","8TNhdJTRXNF2O5J5aQeJg/Otiut9PGz0bAb3kcqos9o=",2464688847553569966,7089971501041914710,7522415933832427192,-2734319234878634622>()) {
                        case 1036904417:
                           continue;
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
   }

   private static String a(ClientLevel var0, AABB var1) {
      int var2 = (int)Math.floor(var1.minX);
      int var3 = (int)Math.floor(var1.maxX);
      int var4 = (int)Math.floor(var1.minY);
      int var5 = (int)Math.floor(var1.maxY);
      int var6 = (int)Math.floor(var1.minZ);
      int var7 = (int)Math.floor(var1.maxZ);
      int var8 = var2;
      switch ((int)com.yiyiaddon.m.b.a<"s19agzo9okkua1","fMMQkhc4/Aa9oxBuD5frUL9I0g3/p0iqFW52J3UFS6s=",-9141461452621797746,-2662961045376107609,-7677337045477424352,-7189655160312802214>()) {
         case 118885474:
            while (var8 <= var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s2bpg4za8k5ihd","K5T3aXsjSVBzqar/r+6yBvog10AN+q0cOUyCM8F79sU=",-685939500798712318,3430724048616196807,-1569556619363633663,-2985122575715379745>()) {
                  case -2070161858:
                     int var9 = var4;
                     switch ((int)com.yiyiaddon.m.b.a<"s2zwbq9hyycnyb","F4DwMnbhiMMu2cDwB4rJgqxpRF5sFjvdPKUr0ITpG6I=",-7336141220072179669,-1149362451991345058,-4898384615847052443,-6233901795753195699>()) {
                        case -1928610868:
                           while (var9 <= var5) {
                              switch ((int)com.yiyiaddon.m.b.a<"s20w7bf0y2x3hr","26/SCZikD3dQw1lFV9AynaEbIzoJGEd4As5YG1P16+c=",-4489921520274990640,-6332139029009379100,-1788464357283159605,-1258582657218088977>()) {
                                 case 1803072923:
                                    int var10 = var6;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2v45nvj1eeb5f","81LyLaDcQLGt/+CZqyPcN4OIO/cZLV2Qev5w9b0dDnA=",6361844337918788823,9166659853757459572,-3973179412547351361,4900428770496816552>()) {
                                       case 1170539168:
                                          while (var10 <= var7) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3r26al1ezzqh8","jzl4tOt1A741kyWbPqBVxrs1L/TvvvIER4InQyhFcbg=",7254017477697883538,-2533473438958372549,-2569435687958169624,1436473696489495733>()) {
                                                case 172861799:
                                                   BlockPos var11 = new BlockPos(var8, var9, var10);
                                                   if (var0.getFluidState(var11).is(FluidTags.LAVA)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sg1emp7n3mxyf","M2wz71EEJUMOHSLIrI97BJjYcIOvSVZs6rYEu3Wc7ys=",321791939164071179,-3448831337818576729,7004727363912278200,8253581388533671123>()) {
                                                         case 1401009754:
                                                            return (String)com.yiyiaddon.m.b.a<"snh2rrb16uv89","Y4neij5nL7ar3Ndgi2R1F5hG0Xd9ADFXJGDker4Y7wHDH13C9xsQF/Q1zmc=",-8230849567347610829,-470400241198207183,5863662681631309941,-5900212807587096966>();
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   if (var0.getBlockState(var11).is(Blocks.FIRE)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s34dxq95ogy99f","8ybSCoMVJ6XF8hvjJthzyp+p8W42cqSb+gLcsg0g++w=",7435394969436721210,-1110606219215962053,-8788983782928265711,4636721739775739401>()) {
                                                         case 1520117955:
                                                            return (String)com.yiyiaddon.m.b.a<"sqvv3qu0t311a","oKuGRLPzknKXfE2nYXTPZ7QdQhXpDp1w5H6VkiYvJGNivJpawM3vsQ==",723444057033925384,557614611284211433,8684494268632139709,-6665153361366159758>();
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   var10++;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s87j9o1iuxawd","CoZW59lOTifqs2Lb6X43o3Gw1Qdd+B6grNYylsfT6f4=",-3142359129792158274,5628219537498163445,6898291827406370185,-4745945887026315174>()) {
                                                      case -1211403285:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var9++;
                                          switch ((int)com.yiyiaddon.m.b.a<"sphzi9yyitm9y","FmvlJM0L7oXN14cHgJQpkuSMg2nEdS6orMuCVOU3Wf8=",6365804424747016817,5390789460613489605,6330995839561624335,-1130211357665092393>()) {
                                             case -1291409535:
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

                           var8++;
                           switch ((int)com.yiyiaddon.m.b.a<"s39278dzurzlkw","piJBMz0R2HCt4wgx1m4XcekBYE51EWOraLWlB+14pME=",-3132738434013010426,2962444216792144437,-6366168967959323569,-5009003046233789236>()) {
                              case 2085281874:
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

            return null;
         default:
            throw null;
      }
   }
}

package com.yiyiaddon.e.a.b;

import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.m.b;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class a {
   private static final double b = 128.0;
   private static final float e = 1.5F;
   private static final int C = 0;
   private static final d a = d.a(0, 40);
   private static final d b = d.a(0, 200);
   private static final d c = d.a(0, 200);
   private final Minecraft b = Minecraft.getInstance();
   private final com.yiyiaddon.e.a.a a;

   public a(com.yiyiaddon.e.a.a var1) {
      this.a = var1;
   }

   public void render(f var1) {
      if (!com.yiyiaddon.l.g.a.e.a().a(e.a.ADMIN)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vexr5tcjpi14","fX+A4W4Wdq0pGqBqkg4tLH9Aw6oC99vuYYeaNyoOt/w=",1209876980267925073,436055423161561318,-3032112889187378136,1261348936822766963>()) {
            case 876052258:
               return;
            default:
               throw null;
         }
      } else {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s37loif0tik9i7","b7MF4QgxkBEosGNNNmpyl0txrJoidY1aTTSoJ96TZQc=",3820178963952590982,-2150897825084356121,-8356883368792399138,8650740960314433167>()) {
               case -964063311:
                  if (this.b.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3jk7c56ug7w7i","8KqbenPc2gCpZPfHf+eK6nb6czG43d3ws7fZVqKCEmY=",2595813558791682429,7309441852759049101,2810693793754544984,-8957707995360986487>()) {
                        case -356008424:
                           if (this.b.level != null) {
                              boolean var2 = this.a.a().s;
                              boolean var3 = this.a.a().t;
                              if (!var2) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2jdrbvqa5hk8n","DJ6koTYc+uF2NNt0SbzdP/orrXCTi29C+vPwWiKSMjM=",1794590071802473553,8262425715254325614,1214149358762753245,3650570657729807970>()) {
                                    case -1312327574:
                                       if (!var3) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s347uaa9jq7go7","4HUB7GRiXsORf5gO86efXgfiVab5sqG8TyOg3CjLGs4=",1718144175084278887,1661154068393317638,-4500687509261419186,4372848799665822865>()) {
                                             case -415513691:
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

                              Iterator var4 = this.a.i().iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"sb1b3crvid0en","c+mzhEtiLDXNj7j+i/WZypytCOG+hjx/l5P/mNt6D7I=",-132637639995808923,5414235512739729864,-5556443320183167733,6575551760125758616>()) {
                                 case 817294717:
                                    while (var4.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sbmnsu5719vmq","1y6xomrXu4T76PtYhdUsTFBKcqek1u21QqEvrtTRKcI=",27212611490153578,-6149839808909319844,-7525855185978644434,2822086017206769752>()) {
                                          case -209506067:
                                             Entity var5 = (Entity)var4.next();
                                             if (var5 != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s379i3maeh6h8a","lpZfz/Enerqdmw9XhfrIDM/aWm/XJ7bTXizDDM0e6tY=",-1220842282113509585,-3997786901862950526,5354591801220261213,8875557242515850132>()) {
                                                   case -1470990900:
                                                      if (var5.level() == this.b.level) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s23rz43gk60i3h","AHKDeoGznt5gv3j+TdvJnJL5IXQ6TR0AeHLqiAdbxJs=",3131487607665025893,8958462603210662278,-487593851427674805,-744502093657956802>()) {
                                                            case -466078669:
                                                               if (var5.isRemoved()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3koyoxhtgj0ua","LhMcpkos0Pc8Y8H8LcXqf6EhMbCd6ArRtlglGzz2LyE=",-5736123993719056934,3640331770189499322,-4375531169514270617,-4005728751853572026>()) {
                                                                     case 699353888:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s1ejv9zqscwrl","60cyMS7fbjz0ODq/6Dt47Jv5avfykRoLGNl3/7It3jE=",-885484241954812028,-9076662799111240953,-8431410054212614111,4511315860088244455>()) {
                                                                           case 218014946:
                                                                              continue;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               } else {
                                                                  Vec3 var6 = var5.getBoundingBox().getCenter();
                                                                  if (this.b.player.position().distanceTo(var6) > 128.0) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3oyh789h4r3dd","hROsQqYC7/TC2t51SOUztXFZeGZ/AOI2C50/Cf84LCU=",1032469380201036542,-2920567347011286181,4415844339516767423,3803862894879522479>()) {
                                                                        case -30491637:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1s8lpkcanxao1","1OnYuZHgUK/VF9LLicarHuky9g43lsS5JQPOGGKGXhU=",-2752174722534099701,-4489628957390059047,-4579052117759735679,2154418796097685826>()) {
                                                                              case -673765625:
                                                                                 continue;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  } else {
                                                                     if (var2) {
                                                                        label75:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3v3sc013tl2wa","PqIAJXMgbHdDOjCDtIlGEPAeDUUNhYu58vzNq2+wqpE=",-4580860419553655498,1387614474625947600,-5348717560629228964,-1153801716503976609>()) {
                                                                           case -1862178322:
                                                                              AABB var7 = var5.getBoundingBox();
                                                                              var1.a(var7, a, b, j.Lines, 1.5F);
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s2lqx7zh66s7x8","RnXxOfWX5B3vN4nA+5UWdByfVlIbZZOwE/YlaPNNqgE=",5089101304832879528,6953681627134641801,-4399746431888759086,7749037632220238018>()) {
                                                                                 case -1726238776:
                                                                                    break label75;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     if (var3) {
                                                                        label71:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sp24kicq070zm","wo8JFbIbsYeMS1UCJqgL7tky628swq7aGOFpPnPL9uU=",-8506741649255722351,5855469051447751407,-2559989467469695439,5221512233329674684>()) {
                                                                           case -1107500763:
                                                                              var1.a(var6, c, 1.5F);
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s3jcb0vi8icv0g","kDksMCwRQnBSaFvsi8WxkGmjnyap1MbiN4zPIFsY0VQ=",4226596275614574552,-5076043505207707727,4019977661442703023,-8917960896370417659>()) {
                                                                                 case -1719432224:
                                                                                    break label71;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s39yporb3bbqqz","q3o556WrzP98tMH11mOpjtwzeHmEIojPrMGMqIap2ic=",4906314989899587115,2620426708206045279,4825038552789478002,-636275594687732310>()) {
                                                                        case -998997099:
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
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s4tt3jaywv6ij","Oz96WIXQy0Gm/xsOxZxJPuU2Ak01pvkXzrv8QnWxIxs=",7955643629557916292,3857493254621010622,3278180407558523159,2075833926674397873>()) {
                              case 561016860:
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
}

package com.yiyiaddon.e.r.b;

import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

public final class b {
   private static final float en = 1.5F;
   private final Minecraft av = Minecraft.getInstance();
   private final com.yiyiaddon.e.r.a b;

   public b(com.yiyiaddon.e.r.a var1) {
      this.b = var1;
   }

   public void render(f var1) {
      if (!e.a().a(e.a.VISION)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ri9s18wt6l6p","e4LRArEkedQByVqckcu5xLwjejnQydaXVuO4gwKJMjE=",5596368682165074121,-7646333863872896736,-4364031386500150824,8143941330442485443>()) {
            case 1455364150:
               return;
            default:
               throw null;
         }
      } else {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s34ia8lvmai4zq","Mtom2zLvCVl9vPflPF37gkmlW7oWNSbPy8AB0Yh7yNQ=",120555465805981231,5481457594944572681,7686467403085362639,8660754269660441244>()) {
               case -1287832008:
                  if (this.av.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2w2tjws0yn9iu","PaOGkL3iMjsb2O6Wftpz6Z8WdkPOSajilr44oNB5b/8=",884427241727457000,-5772893517153981853,840762482330478281,-3281463384897271843>()) {
                        case -2056585995:
                           if (this.av.level != null) {
                              com.yiyiaddon.e.r.a.a var2 = this.b.a();
                              if (!var2.fa) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s9xsxfoyqthbf","b3VzIxmUiZXZTBnN1tteEdMb+0FWCwRvXpdS5yFtuZ8=",9182613567639018519,-4023614355822179771,-4401712062367634216,6108659405044692195>()) {
                                    case 2044689033:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              if (!var2.fb) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sx78w5n2ip7mo","PMLbNsUpjEkGmcmw2nsq9Ww8HcKlbrGu014JJTyJiS0=",5397479437202974394,-580579129015358777,6993441082387600044,-2034659435969396116>()) {
                                    case -520256900:
                                       if (!var2.fc) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s31tgypmvugp1h","bj3S/ZpA85ipsDczQLqRxQ3ztAoO3WSWYXN8TWXMnj0=",-7793282101385916789,2684361941338818013,-158492974936310006,799240843941118897>()) {
                                             case -2076436279:
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

                              int var3 = var2.U.ej();
                              j var4 = var2.e;
                              Iterator var5 = this.b.a().bt().iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s3isip7bwxmg3z","BdJZIfCfW4YwjDOpmPpGzD+a0j6pnd+n4XZYKvKJKlc=",6371928542290008903,-4478983087037825559,2829655872672737978,-5383168914446813416>()) {
                                 case -175708029:
                                    while (var5.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1o6hqj47dw010","8pDLw7pfWu4tOnMQ0NBH6kCAXGalLp816+c3PO1lCAk=",5621949148422272658,-4123055443461584123,1105957513898352278,3091852688012057231>()) {
                                          case -1036153006:
                                             Entity var6 = (Entity)var5.next();
                                             if (var6 != null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s8wzq1jguptr8","r727ZbZ/lk+sH26sKLEWxWPzJc5E7zo7Rq+T0dfGIG4=",-5928784965763338264,5332666965219457531,-4266224982839368470,-2442951500211222344>()) {
                                                   case 1533879046:
                                                      if (var6.level() == this.av.level) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3g6gdfh67n4p0","rFUzv0dr9EQ9JOXKC235DOISLvVosZEAD8W/q42wDBc=",550114403651294299,3124315967861519852,6339202862845155566,4523001860100674824>()) {
                                                            case 1823204917:
                                                               if (var6.isRemoved()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1ceb03fd3itij","VFPcyLgx1qel5R7Q0Nlza43URDY6109JQgxCRNp5C00=",-1117946108493291273,3001063086689040930,-6445306417731496387,-5418095484628512403>()) {
                                                                     case 1705737425:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sz82dle06eiot","Rn3O7YaywKf9qtVfjJ6/Wli9CaMgsVb+BSB1VAZN7uQ=",-312507742399422965,7865334960888383618,4128787548672426041,7042926221165667100>()) {
                                                                           case 1540424836:
                                                                              continue;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               } else {
                                                                  AABB var7 = var6.getBoundingBox();
                                                                  if (var2.fb) {
                                                                     label75:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1ef1peblp9uz8","R2AYd9oijBDefAIL5iAnbzsmLm33UmzI0Xvlp9MnAkM=",8669567484064646297,-1732876404313900933,-3021714226846831437,-3995887028219429772>()) {
                                                                        case 450856017:
                                                                           var1.a(var7, var3, var3, var4, 1.5F);
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s2ch6zfu4e85t6","J30hZpO2P5zGWHPAK/ZIJ9JLzCxElbM/UVVbsWdIiUY=",-6312200336414202991,8071693538211002083,4084946830899762923,4610164913426664166>()) {
                                                                              case -2033159458:
                                                                                 break label75;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  if (var2.fc) {
                                                                     label71:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2j4yiuld0h0c4","oEQ65o2gDeu7bZU/pEOiHppVlquYfaCU5W//KU1Zh5I=",2997345941911944857,-1937002121748832540,7576384818418556947,6927147166459124203>()) {
                                                                        case -1848244452:
                                                                           var1.a(var7.getCenter(), var3, 1.5F);
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1xonu4m0bmh3b","JzCxRIc/q7FICVY9SGrxJlnk3wM8nnkFb6/O1sxKsOY=",-127631329330486478,-2889298570104593464,7323224954403078454,-9105358638820871226>()) {
                                                                              case 1445810763:
                                                                                 break label71;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }

                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1von3ddq1hivy","gxIoeRr8r2uP0jywUDZEnWDZHDYNlrhRp6fYtAbnLgg=",-736717689075304846,1332094129758495917,-6333394748048755433,-3668041393878276133>()) {
                                                                     case 2093626138:
                                                                        continue;
                                                                     default:
                                                                        throw null;
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

                           switch ((int)com.yiyiaddon.m.b.a<"s2u0zwgpjry9","LiNi3YObpiQAuq2DYehe/Al3rUPLX8EWB7TSibl+qGE=",-2182783853639132563,-3711103252702055963,-1623753971912950811,1741840324852584738>()) {
                              case 1958825946:
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

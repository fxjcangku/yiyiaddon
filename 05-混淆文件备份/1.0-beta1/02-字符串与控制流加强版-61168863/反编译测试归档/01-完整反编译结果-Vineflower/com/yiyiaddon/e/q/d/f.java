package com.yiyiaddon.e.q.d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.MerchantOffers;

final class f {
   static final int qD = 20;
   static final int qE = 2;
   static final int qF = 2;
   private com.yiyiaddon.e.q.d.a a = com.yiyiaddon.e.q.d.a.SELECT;
   private final Set<Integer> au = new HashSet<>();
   private int qG = -1;
   private int qH = -1;
   private int qI = 0;
   private int qJ = 0;
   private int qK = 0;
   private int qL = 0;
   private int qM = 0;
   private final Map<Item, Integer> aG = new HashMap<>();
   private int qN = 0;
   private int qO = 0;
   private int qP = 0;

   com.yiyiaddon.e.q.d.a a() {
      return this.a;
   }

   int cY() {
      return this.qG;
   }

   int cZ() {
      return this.qL;
   }

   int da() {
      return this.qM;
   }

   int db() {
      return this.qO;
   }

   int dc() {
      return this.qP;
   }

   void f() {
      this.au.clear();
      this.a = com.yiyiaddon.e.q.d.a.SELECT;
      this.qG = -1;
      this.qH = -1;
      this.qI = 0;
      this.qJ = 0;
      this.qK = 0;
      this.qL = 0;
      this.qM = 0;
      this.aG.clear();
      this.qN = 0;
   }

   void iR() {
      this.au.clear();
      this.a = com.yiyiaddon.e.q.d.a.SELECT;
      this.qG = -1;
      this.qH = -1;
      this.qI = 0;
      this.qJ = 0;
      this.qK = 0;
      this.qN = 0;
   }

   void iS() {
      this.au.clear();
   }

   void iT() {
      this.au.clear();
      this.qL = 0;
      this.qM = 0;
      this.aG.clear();
   }

   void iU() {
      if (this.qK > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"smunnaaw1bhxn","Q6F00LABnpWXW1WgymXOlBiOBrt/evVJVocM0eMCZV0=",-1709830331334252103,-1750816373338135527,8166380705108197596,-3764292621737866719>()) {
            case 1834854373:
               this.qK--;
               switch ((int)com.yiyiaddon.m.b.a<"s275axu6d9l4x5","VMFfom5PQb7s6Cm+vbyD4vZSQGtNvSTUve57IidOq4I=",5707985313401966063,4409648983566960507,6280352023732256299,-7345434707776971587>()) {
                  case 691079719:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   boolean eM() {
      if (this.qK > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s612hvjgwpmpg","x4uK4Jw1wQu5AAuX+fcghP7meXb4Wr1CmIyosk9YiZc=",2390084819497919614,3792126669390183344,-730923789096950695,-8160523042140198279>()) {
            case -97325970:
               switch ((int)com.yiyiaddon.m.b.a<"s1pb7qg4iiw031","vhHAlS4SwdDrRT9FpdH1dfI9+aMEueO1ef8nBu9mv3k=",-8676539981236823593,7238531140799782871,1525224007535826771,9112045827056752800>()) {
                  case 1012325572:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s54zrxepz2119","kqQBcNj6Py66vrTNyHQq2dYDotZu+E2+r7WKEt6PhZ4=",-3537135755505087912,-1846896762483840676,-2911089598112465666,5897875546190503985>()) {
            case 495578872:
               return false;
            default:
               throw null;
         }
      }
   }

   boolean e(int var1, int var2) {
      if (var1 - this.qN > var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s7tihr6asco7v","+9yhaHdiINaliX0yZIbU71PbEwtEo2omzyFfiSAJ6PY=",1878192194576329774,6777342272526189331,-183741362216097210,8539158317929837521>()) {
            case -1943383596:
               switch ((int)com.yiyiaddon.m.b.a<"s1j1dc9zuawqnk","vtq5q2eq7/88FtPuukYhheIck6AOK1iI6lGiW+gpsww=",5907019542277385621,1347766357539354859,-2402548471518433736,2407087160484804160>()) {
                  case 7202076:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3cmctv9hynj7e","nlRRI3bLpdoWLfohtC6WJPrWMv6Vb4KeoN3+0Djj2ak=",2141724204270776865,-7413313177901871574,-7574572076086023712,3523771963594788202>()) {
            case 198342742:
               return false;
            default:
               throw null;
         }
      }
   }

   int a(MerchantOffers var1, List<com.yiyiaddon.e.q.f.e> var2, int var3) {
      return com.yiyiaddon.e.q.j.b.a(var1, var2, var3, this.au, this.aG);
   }

   void a(int var1, MerchantOffers var2) {
      this.qG = var1;
      this.qH = var2.get(var1).getUses();
      this.qJ = 0;
   }

   void iV() {
      this.qI = 0;
      this.qK = 2;
      this.a = com.yiyiaddon.e.q.d.a.CONFIRM;
   }

   f.a a(MerchantOffers var1, int var2) {
      this.qI++;
      int var10000;
      if (this.qG < var1.size()) {
         label65:
         switch ((int)com.yiyiaddon.m.b.a<"s3ti0bey6vudi8","6vyoza3W87nzb5wGcn1Kse+CZAv58vngW8RlR7dV+aM=",-854671436074940052,-4615807224741348517,8515052683914742931,-5747420588065236834>()) {
            case -850939168:
               var10000 = var1.get(this.qG).getUses();
               switch ((int)com.yiyiaddon.m.b.a<"s1bi7lypa3xh28","yrYZymayIMczTAZk2isPhMoehIO2AFHPFVKLzLul+K4=",124381139639031408,2990395935852697791,-8639067029030484347,-2116086435723706813>()) {
                  case -1958863288:
                     break label65;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = -1;
         switch ((int)com.yiyiaddon.m.b.a<"s2sqsfpvodk7i3","0Pp18nhZlPhEJCOE0IgD5+y7hb6cgHYttAIDkKpz96E=",-4092013954399825727,8996343398281967179,6490075329242585990,4504826036308326412>()) {
            case 1029326338:
               break;
            default:
               throw null;
         }
      }

      int var3 = var10000;
      if (var3 > this.qH) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dt2j3kwe84cy","hRl0vZ8RsFAPNEth/H4u+pYsy9/xlLSm7Jx3UbLwmp0=",-5286544342465928481,-8488305047513089255,4049985975664555949,5109709934760342607>()) {
            case -1251167311:
               this.qM++;
               this.qN = var2;
               int var4 = var3 - this.qH;
               int var5 = var4 * com.yiyiaddon.e.q.j.b.a(var1, this.qG);
               this.qL += var5;
               if (this.qG < var1.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bmtyi22vbqfh","36zuN52229c3pp63wuMAPEbKo85i1D1q2PcumfiOYyI=",-5370765386061991884,-8938542535286707679,3643396369514457398,-2446347216525795098>()) {
                     case -158544924:
                        if (!var1.get(this.qG).getResult().isEmpty()) {
                           label42:
                           switch ((int)com.yiyiaddon.m.b.a<"swl51odw8y8ny","25O4nX93/ACcQUoJ0Ut8F4jUK/kSubxGEDEUBSSrhh4=",6899319960301733085,-866948839095659298,-8846792755303383620,-395604244701578757>()) {
                              case 1348773373:
                                 this.aG.merge(var1.get(this.qG).getResult().getItem(), var5, Integer::sum);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1tk1k3kx45rk0","T8vABkFQSGkrunBSt1SjmmrNwXWZ4j6L8Glq/QMoaTo=",-7548166604524718009,7707078528432780798,-27943392871728874,-5229173704878248769>()) {
                                    case 318350582:
                                       break label42;
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

               this.qJ = 0;
               this.a = com.yiyiaddon.e.q.d.a.SELECT;
               this.qO = var5;
               return f.a.SUCCESS;
            default:
               throw null;
         }
      } else if (this.qI >= 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dd6oakzy1xd7","dCUb5pneEQR4omxsc5swmOBfcNutacTtheYO+w7jyoI=",-2971818327775331017,-7701991987971213904,-7858167141864970276,2508962430338427058>()) {
            case -1201020288:
               this.qJ++;
               this.qP = this.qJ;
               if (this.qJ >= 2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2vd41l6bwwqbt","pUQ4AU7Ytl8YWpeZpZ4bxU2JJLZCVzZeBMEytsoS4bM=",2265052623422968028,3265905784461226104,-6588450140956209332,5082683900102643907>()) {
                     case -855221100:
                        this.au.add(this.qG);
                        this.qJ = 0;
                        this.a = com.yiyiaddon.e.q.d.a.SELECT;
                        return f.a.SKIP;
                     default:
                        throw null;
                  }
               } else {
                  int var10001;
                  if (this.qG < var1.size()) {
                     label51:
                     switch ((int)com.yiyiaddon.m.b.a<"s2xmdzuwfkdrtm","7OSTxy4aFzeVshYnWIrNkFyJ3o/7T05kQuNER40+Mec=",-9118129866303557270,8793393055956061927,-3140100965692843938,3498367104552804068>()) {
                        case -1896645608:
                           var10001 = var1.get(this.qG).getUses();
                           switch ((int)com.yiyiaddon.m.b.a<"s3kxghgvrpk0t","V539dIBu0L13jPuSySndv9s9//i1fhXYXBzwGRhYDwA=",3964804582425366663,7591884883101717205,5036879725997185567,-3261960767595034500>()) {
                              case -1197619024:
                                 break label51;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = -1;
                     switch ((int)com.yiyiaddon.m.b.a<"s5e6ljaku6vxv","bhB4LWqbthLxxQn2tTGqntbpmhCZ3DpZwWzd9kVIftA=",-8871010821081208328,-6076223655110267438,-6210522045847273570,2156683418762178990>()) {
                        case 133330770:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.qH = var10001;
                  this.qI = 0;
                  return f.a.RESEND;
               }
            default:
               throw null;
         }
      } else {
         return f.a.WAITING;
      }
   }

   enum a {
      WAITING,
      SUCCESS,
      RESEND,
      SKIP;
   }
}

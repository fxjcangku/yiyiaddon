package com.yiyiaddon.e.t;

import com.google.gson.JsonObject;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.t.b.c;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.m.b;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String BC = "water";
   public static final String BD = "水源显示";
   private static final String BE = (String)b.a<"s55bfa5r69zrc","ErZfz/aGTM1ANHrgtq2pRtfYUHEam92A7qJZ2IHL",-5161087058496850968,2121351765679133393,-2028917129323035094,-3026359789331236521>();
   private static final int rK = 512;
   private static final int rL = 40;
   private static final int rM = 12;
   private final com.yiyiaddon.e.t.a.a a = new com.yiyiaddon.e.t.a.a();
   private final Set<BlockPos> aA = new HashSet<>();
   private final Set<BlockPos> aB = new HashSet<>();
   private int as;
   private int at;
   private int au;
   private int rN;
   private int rO;
   private int rP;
   private int rQ;
   private int rR;
   private int rS;
   private BlockPos c;
   private int rv = -1;
   private int rT;
   private boolean fd;
   private final Minecraft aw = Minecraft.getInstance();

   public a() {
      super(
         (String)b.a<"s25hqvxa22n8n5","eCtwP2dHCK1cDYVruJ1y+F0pxQrwBEapYayJtlCQomMVNZT0OOs=",1092659403239437308,6260913211692202139,-3527186647395765268,1798829019966144322>(),
         (String)b.a<"ss88wmwxbr1m0","Fck611OAUZLMbuC0LCo8jQHbR57y8giYDip4kLJ6RDODp9V2",2894891730159263788,4853494580139325981,9100451269739435523,-6204725646763294341>(),
         (String)b.a<"sffvp1r3tuqzn","3ZshnhjkpxtywHvLNtgibANs156/HClTIe7MeO/28mJWTGmG2nNwbFYR",2175759326182689032,-4719050727384066521,7872934246884564867,3436700214495909289>(),
         (String)b.a<"s1wh1s8lzm7df9","aMSmZQBXkY2VqbMygMBf9V5SYwJ3V/ld0YqNTcnNbTyo+e3l+U+eV3wxZLuB8L+uxwf/Yv5pidVJV22pmgXEqNbsqtFr+nVRkWhZuSxF8lR6q7UY6P2eVmTTwB0ywiCX5dfXEFGHN0o=",797372926891670639,6983762722232425844,2459645538554652047,-4282421545990593651>()
      );
   }

   @Override
   public String a() {
      return (String)b.a<"s2tx7lflu7tpb9","ASGFjiSv/oOPdTl2nqiXbQVtp4WoJqBNgcBhuYxh3dYGy9JQXAl847zr5lU=",-2058995463022722807,-2470546336492977122,8761733907761527172,-8315620099529416481>();
   }

   @Override
   public String w() {
      return (String)b.a<"s55bfa5r69zrc","ErZfz/aGTM1ANHrgtq2pRtfYUHEam92A7qJZ2IHL",-5161087058496850968,2121351765679133393,-2028917129323035094,-3026359789331236521>();
   }

   @Override
   public int i() {
      return 50;
   }

   public com.yiyiaddon.e.t.a.a a() {
      return this.a;
   }

   public int dk() {
      return this.aA.size();
   }

   public int dl() {
      return this.aB.size();
   }

   @Override
   public void a(JsonObject var1) {
      this.a.d(var1);
   }

   @Override
   public void b(JsonObject var1) {
      this.a.c(var1);
   }

   public void L() {
      e.d(this);
   }

   @Override
   public i a() {
      return new c(this);
   }

   @Override
   protected void m() {
      this.c = null;
      this.rv = -1;
      this.rT = 0;
      this.fd = false;
      this.as = this.at = this.au = 0;
      this.aA.clear();
      this.aB.clear();
      l.a(
         (String)b.a<"s25hqvxa22n8n5","eCtwP2dHCK1cDYVruJ1y+F0pxQrwBEapYayJtlCQomMVNZT0OOs=",1092659403239437308,6260913211692202139,-3527186647395765268,1798829019966144322>(),
         this::a
      );
   }

   @Override
   protected void n() {
      l.l(
         (String)b.a<"s25hqvxa22n8n5","eCtwP2dHCK1cDYVruJ1y+F0pxQrwBEapYayJtlCQomMVNZT0OOs=",1092659403239437308,6260913211692202139,-3527186647395765268,1798829019966144322>()
      );
   }

   @Override
   public void b(Minecraft var1) {
      if (var1.player != null) {
         switch ((int)b.a<"s5lkprbulrnmm","8Rv/tizw8+f1bsPjVWD4XlLNHaRwDsw9mQ9kYiBcgLw=",-5665385780411319154,4554675659097510895,7839216222101809731,2678358857702512639>()) {
            case -1505806506:
               if (var1.level != null) {
                  this.jc();
                  if (this.a.ff) {
                     switch ((int)b.a<"s3f4zx9yc9hhnb","ioo5dF5o4f0Yk+tZ7KselpZJnU3gvbCgG3cYJAaUdHg=",7505060071857766305,5401133226629499702,7987329575900628983,-2603347815221864032>()) {
                        case 1935007524:
                           this.rT++;
                           if (!this.fd) {
                              label28:
                              switch ((int)b.a<"s16langas796gf","VcaJI3jsvymkgvg+e5jZDgghIUINdhVR8X5bfjvrdq8=",-5370796449523996763,601401964381786767,-8121814683470829089,4571516075605853822>()) {
                                 case -540439067:
                                    if (this.rT < 40) {
                                       return;
                                    }

                                    switch ((int)b.a<"s5g7p41792yal","yiCnCKdyfVNhQhpmIRKUG3c5p6WsxDkBHYDiJ0ZjQbY=",-5348635711558087485,-4070460818710841170,1726966069719919560,4605231924387067145>()) {
                                       case -264272788:
                                          break label28;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.fd = false;
                           this.rT = 0;
                           this.jd();
                           switch ((int)b.a<"s3f83firsj9ku7","UUqYq+3WZ9Zt99P4UyVUUcKYJBsA+Z924y2+H/6KESc=",8087690268563435823,4406267984418413949,2246843592546710050,-928814774693486457>()) {
                              case -573241225:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)b.a<"s2tmbzpooflygi","F8NdXErOxnYjoO0FVWf5D6U26BMTTkYE4at8qXGB1L4=",4919969899089448032,-3243078861877739047,-9007972950347609691,7050885324373283405>()) {
                     case -973394625:
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

   private void a(f var1) {
      if (this.aw.player != null) {
         switch ((int)b.a<"s1936jnnbltsex","Qe3D3ZEz4HXMpcQqvS9Dk6zFGxhdHXWOCQ8zqd/zpY0=",-1828291329058719913,-870560190579506233,5578745248983034936,-8000809242495126719>()) {
            case 1624417162:
               if (this.aw.level != null) {
                  double var4;
                  double var6;
                  double var8;
                  boolean var12;
                  label119: {
                     double var2 = this.a.rU;
                     var4 = var2 * var2;
                     var6 = this.aw.player.getX();
                     var8 = this.aw.player.getZ();
                     boolean var10 = this.a.fg;
                     boolean var11 = this.a.fe;
                     var12 = this.a.ff;
                     if (!var10) {
                        label87:
                        switch ((int)b.a<"s3raed994t4dit","a8OyatmMbPT3P4lb9TJTq6wgWaDtikgTwp0uXP4i09Y=",-7982396608677199699,2135218195245361819,90141298142608988,2922833664509706849>()) {
                           case -1686854368:
                              if (!var11) {
                                 break label119;
                              }

                              switch ((int)b.a<"s1mg4pzgwqh7sa","nuTPvcsR2Agk3daIz29EsMiGdpEmbgesmc3T0wKWv/I=",-5005693213148412196,-4749083760129295327,3262071543335335184,2928113914374271735>()) {
                                 case -776740858:
                                    break label87;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     int var13 = this.a.Y.ej();
                     int var14 = this.a.W.ej();
                     j var15 = this.a.g;
                     Iterator var16 = this.aA.iterator();
                     switch ((int)b.a<"syu1q1z963xsa","rX/9At7QWFRI8eNwhqjQZb1Yf487oL2RyOAjOciHAbc=",-6614048056305033670,-2266428376503590406,-7213894894858889620,7845882415548298896>()) {
                        case -1137702121:
                           while (var16.hasNext()) {
                              switch ((int)b.a<"sjyxdz9yd9k9p","Gel5JSt9F04oyrO2UzjCUMlsuqTF23J8Hrnen1Lrzh4=",6109660079273063249,-7360539526526522621,3910783435589559478,78826912353025265>()) {
                                 case 2048642781:
                                    BlockPos var17 = (BlockPos)var16.next();
                                    double var18 = var17.getX() + 0.5 - var6;
                                    double var20 = var17.getZ() + 0.5 - var8;
                                    if (var18 * var18 + var20 * var20 > var4) {
                                       switch ((int)b.a<"s196qk0dh4txws","BluJIONbbO/2Y1OGVLJKQgwU/gNjm8tWxN2XB7UBYDw=",-2485227068828156231,941694145051737959,5266813537761709159,8565716240946188725>()) {
                                          case -685145824:
                                             switch ((int)b.a<"s19nxkx44a04zu","Z0Lvae6G+XLHDI/85cq+FOERsC7Unm1lp5mEqhMk4CI=",1147932769939560633,-2344378588536865513,-3615901503772837716,-1089002594861276778>()) {
                                                case 1223350756:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (var10) {
                                          label74:
                                          switch ((int)b.a<"sxlso1ifadlby","H6dkdyomc0RiWvxLSj56dNny72fg5wj2kRmBCLw4bzY=",2810311120471877496,-4851668379427203851,3692676155242482590,-6191032588956837813>()) {
                                             case -1688312064:
                                                var1.a(var17.getX(), var17.getY(), var17.getZ(), var13, var13, var15, 1.0F);
                                                switch ((int)b.a<"s4cwfoahwgl06","sQ6lfwi1kybvp/c8AgrFh84zc/gl6tqNHEbmLKoewBw=",-7064247757527075431,3491633473637361540,-4314113834190357683,-1382424697892824433>()) {
                                                   case -19664355:
                                                      break label74;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (var11) {
                                          label69:
                                          switch ((int)b.a<"s37l76l09klr5p","IzAKLdUt8kG5Qv2YRzqGy4a3dNAV+UjUGqVXQL0nH0A=",3465702863148751649,2489332326601799327,3480464573936009115,-3504209680918030423>()) {
                                             case 1349390446:
                                                AABB var22 = new AABB(
                                                   var17.getX() - 4, var17.getY(), var17.getZ() - 4, var17.getX() + 5, var17.getY() + 1, var17.getZ() + 5
                                                );
                                                var1.a(var22, var14, var14, j.Both, 1.0F);
                                                switch ((int)b.a<"sx30enxi0chja","phOJi8ZrHh05Mxfg1SKjNLEo0NNCVCr1qTlKLbuygLs=",-1251471088279180134,-6478776469887464382,3853721541098840343,4955165055270812764>()) {
                                                   case -616231736:
                                                      break label69;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)b.a<"s107wbwq4xuybw","8T0jNeYZun+CruXNucY3WzGUMO9m76dSSpDwSmTpvEQ=",-1830174816593251401,-5665912812677950690,5337272413696623882,-6269363798114485216>()) {
                                          case -885021208:
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

                  if (var12) {
                     label61:
                     switch ((int)b.a<"s1ikjtga3blyee","PD2bLdKarTEdHxTaUwfMoxCOshw8iyXCrfpDQakVAvM=",6754914242376782649,6836877426412433654,-2080030489883034036,6558267033329869586>()) {
                        case -1706666708:
                           int var23 = this.a.X.ej();
                           j var24 = this.a.f;
                           Iterator var25 = this.aB.iterator();
                           switch ((int)b.a<"s3qye4ot2qrd9f","rNDoBSgVmDL8pPsH9lKt4RZSmPyUS9QQcaSyyUw4leE=",5635189717954645738,-5978050621311623250,-1801310320908999221,-2034884632696979624>()) {
                              case 946616262:
                                 while (var25.hasNext()) {
                                    switch ((int)b.a<"s3g6mkidrsik0k","NiF1PbphZgADKC1IhZuV4+iFVaFCiWquR3/Nip2P08s=",-4547504562855259639,-2291883355522357620,-790334382810958953,-5686920198837252665>()) {
                                       case 936381277:
                                          BlockPos var26 = (BlockPos)var25.next();
                                          double var27 = var26.getX() + 0.5 - var6;
                                          double var19 = var26.getZ() + 0.5 - var8;
                                          if (var27 * var27 + var19 * var19 > var4) {
                                             switch ((int)b.a<"s2v1zdecmml7gp","J1MOh/nNRwOUnGePS/SR5hbnWFkplTVV++r4FhOEBPc=",1330123616651376726,-4152735994499762212,-125262024841012638,-774448623179343063>()) {
                                                case -1456939489:
                                                   switch ((int)b.a<"s309xvr40i92uv","K5i/kQEDno2KBn2eJCfzK2Y7a0Z4E3nuoXVCaeSD1zA=",-3802460943474418663,-2005916264571825743,7024019946365840173,-7866628569705955997>()) {
                                                      case -1138800325:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var1.a(var26.getX(), var26.getY(), var26.getZ(), var23, var23, var24, 1.0F);
                                             switch ((int)b.a<"s1ha958kjjyw90","WhRRZPTH9RehdKPOk6UR2WlP6T6qaY5Hc9zdDcksHWc=",-5781278562684419440,-5052865741229870346,-7702083702240443030,-6458145846853755103>()) {
                                                case 261651278:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break label61;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)b.a<"sq19e7ykjsott","xXW19qMEtUv6XHHTbp1RJZWZ8FxEWtMRd/X6YzMH8O4=",-6358331461300497542,-4395978870786881163,-2950270873849316755,2517999917081781027>()) {
                     case -389059923:
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

   private void jc() {
      BlockPos var1;
      int var2;
      boolean var10000;
      label121: {
         var1 = this.aw.player.blockPosition();
         var2 = this.a.J;
         if (this.c != null) {
            switch ((int)b.a<"sn8yjakinlpmu","Untwiy4/UJBUO6vc9ts3MlArjvw5GZJPy9lWYVK2YPk=",7732300057415860939,8667970649754326914,5543333942392065938,-4022226285337553353>()) {
               case 1188680551:
                  if (this.rv == var2) {
                     switch ((int)b.a<"s17lhsppy9so92","LsC+ZQVzyRAWWnytYgTlKSHGX5+cDyAY7k4rmJkFU6k=",-989373417030624590,881145192773073692,-5538618757552748242,5275812075934493465>()) {
                        case 106225727:
                           if (Math.abs(var1.getX() - this.c.getX()) < 2) {
                              switch ((int)b.a<"s252ercxtxelp1","GgvJGWAuWsrs665VkcNx0u1YlezPq8XXYowX0ZHpNio=",1514211454532388473,-1014650733859394388,6877918196280890561,6522498345630650544>()) {
                                 case 1532136445:
                                    if (Math.abs(var1.getY() - this.c.getY()) < 2) {
                                       label90:
                                       switch ((int)b.a<"se56nfpsyfv4y","rJfv2wKr7QiBtOqB+FT47QF9NTACOW08yG576aIJc78=",1777391393502062078,-4561685569553516040,5172558299209492644,7123211533735907231>()) {
                                          case 1940410630:
                                             if (Math.abs(var1.getZ() - this.c.getZ()) < 2) {
                                                var10000 = false;
                                                switch ((int)b.a<"s3h9ndoat5wh0r","U0fqbMlCofFIXhQwReBhH52ZFtj6kKAI69MmOvs6yWQ=",-8245356478231826989,-3706053605226562203,3956037904758740841,-6431455928422353240>()) {
                                                   case 2025491187:
                                                      break label121;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)b.a<"s3vaabhjqzwfpt","IP4n1Jnr9+P+nuxloYh2KWRIISyB5rzQ2mJZdOnTGOQ=",-1198184974937335058,4293533484224053425,8282013463847905151,-936335963051880223>()) {
                                                case -1306763774:
                                                   break label90;
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

         var10000 = true;
         switch ((int)b.a<"s1j15tx0wgegoi","k8Dr2CWuhCPrhweXdyA1lEeHMBFz374GlAdwSMxHdyY=",-9098132277462825248,-4980527269377424782,8036277721935558069,-2913196118475780097>()) {
            case -240949449:
               break;
            default:
               throw null;
         }
      }

      boolean var3 = var10000;
      if (var3) {
         label81:
         switch ((int)b.a<"s2kwdfzogmtbg3","pazZxqFbyzhQbp9dRMNTpCRASmolehVZFuP4KBFh2JM=",2680377578817618347,8674397930786364395,8875894832663386827,-1227807659846263787>()) {
            case -1352379917:
               this.c = var1;
               this.rv = var2;
               int var4 = var1.getX() - var2;
               int var5 = var1.getX() + var2;
               int var6 = var1.getZ() - var2;
               int var7 = var1.getZ() + var2;
               int var8 = var1.getY() - 12;
               int var9 = var1.getY();
               if (!this.aA.isEmpty()) {
                  label78:
                  switch ((int)b.a<"s14abb9bhjjjxh","xli9n6hEyB40YCXOwsa5rxNAfdUvnVpXQMSMtXz9jKQ=",4233809177654276308,-3424491681160482193,1685640376920198199,8392747033431689400>()) {
                     case -1442114361:
                        this.aA
                           .removeIf(
                              var6x -> {
                                 if (var6x.getX() >= var4) {
                                    switch ((int)b.a<"spuc32fllk1r","/pBHJYa2q3bWy8mDDFyXZYn+sKCQKfnjIwtRTEftVM4=",-7169212769795896903,-3355310867436937285,-37216169567966017,-3178277841936282971>()) {
                                       case -1410673837:
                                          if (var6x.getX() <= var5) {
                                             switch ((int)b.a<"s3213qk73cdzrs","ayRARPHwjwXy3kTg5F8oqq9LoiYQzdV5rnzLHFvWtcE=",6478951993551027565,2787115636884284686,-4191462643807312128,-4485339141610449195>()) {
                                                case 1332496361:
                                                   if (var6x.getY() >= var8) {
                                                      switch ((int)b.a<"s3ez2zqaa80fmj","BpkBxBPIeEoBtCsEZFnHS2CQ8/WM2+mhpoJtVtwiep4=",90974161500704888,-2609540042149314655,7201413508181859749,-5958309892417052644>()) {
                                                         case -417673382:
                                                            if (var6x.getY() <= var9) {
                                                               switch ((int)b.a<"sw62irawvzzoh","5HDD4dGkO8RizrPcKdWt+iiLZY2jiNfJqJL++g9ZXsA=",3680582143802603076,7939926261683513806,7035139863214145692,-3074259037544687374>()) {
                                                                  case 495297641:
                                                                     if (var6x.getZ() >= var6) {
                                                                        label34:
                                                                        switch ((int)b.a<"s1r2612ib4qv9q","fWP7kXZmKfDoQu5DSsNm/MwYnV4/c0a/rxezN6M0SOg=",4526538942293806466,-3049841762179737502,901234117146169877,-280131608839225324>()) {
                                                                           case 1430450176:
                                                                              if (var6x.getZ() <= var7) {
                                                                                 switch ((int)b.a<"s3dxvhv7ljbm3t","z+qMuBRTbZ49aTJJ/8TBVXRjXmT+Xby9OlswuDb9/RI=",-1968957286527853743,-2052786046282762555,-2276146605711700702,-5010371840574435801>()) {
                                                                                    case -149397812:
                                                                                       return false;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              }

                                                                              switch ((int)b.a<"s22t76easbr60f","db1XoAOJ8EHnI4iaXhqDiBcblgDQRp7HeyeTSWSge8w=",4230048788320036398,4769846934503203646,-529315048074816562,-4121812526060475469>()) {
                                                                                 case -590111983:
                                                                                    break label34;
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
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)b.a<"s3v7gxhoo6vp4l","NofOBL7w8Q4bgXjJoqSTp751N3eMEWCBWUn5bJ+rqkA=",931304194172063895,-6685961872349217021,488588501785807923,-3567660078796708772>()) {
                                    case 789538717:
                                       return true;
                                    default:
                                       throw null;
                                 }
                              }
                           );
                        switch ((int)b.a<"s2ujq4u2jqw9pq","8ntMlizkHtAxal+Nr44SK2PSNOg5oWmTg/r//d0sFiQ=",3758220009789546283,-2239831450948885339,-1722474601004104944,424353702092929178>()) {
                           case 150449771:
                              break label78;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.rN = var4;
               this.rO = var8;
               this.rP = var6;
               this.rQ = var5;
               this.rR = var9;
               this.rS = var7;
               this.as = Math.max(this.rN, Math.min(this.as, this.rQ));
               this.at = Math.max(this.rO, Math.min(this.at, this.rR));
               this.au = Math.max(this.rP, Math.min(this.au, this.rS));
               this.fd = true;
               switch ((int)b.a<"svyu0m4jkjst5","MY64fG8FLit2HTyulgQqM8YPM88UQV9CY42eJTOjOaM=",-7320923767332273079,3407543203380982985,609222377416883464,-5528887984203533024>()) {
                  case 1120078530:
                     break label81;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      ClientLevel var10 = this.aw.level;
      MutableBlockPos var11 = new MutableBlockPos();
      int var12 = 0;
      switch ((int)b.a<"s2mgcs6wsr6sqy","Sb6YwKVGudz78hvPCEo+YRUx/LDTxQQ33k8lLPrRFT8=",7879240345338239137,-1934358767256535329,1399971923500218458,3960252776002629858>()) {
         case 1439884411:
            while (var12 < 512) {
               switch ((int)b.a<"sv6s6qhnd3b7b","Qgk9qcKu5UKfht0q98//EZW1nLkjEhrw0dX5oqNctGA=",2141939671088142079,-6102214494479144792,1798896435744903332,3104519231163835513>()) {
                  case 2054212883:
                     label105: {
                        var11.set(this.as, this.at, this.au);
                        BlockState var13 = var10.getBlockState(var11);
                        if (var13.is(Blocks.WATER)) {
                           switch ((int)b.a<"s1hu7mkwy5il86","hMV1dQTgzctI1z7lITF8BErQr+Tef4Ju1kFl3BTPqEE=",-6311438845402603027,-1263537238731693366,5546109574780649425,9126100539861822950>()) {
                              case 878245506:
                                 if (var13.getFluidState().isSource()) {
                                    switch ((int)b.a<"scll70q37w1jk","YVxAYvTnmmsORlhPuglNnHqV0n4U2B+1B4UBE+tcnuM=",7171491823810188485,-2800072114532654854,-1916716140207493477,9172177263099371455>()) {
                                       case -1020400022:
                                          if (this.b(var10, var11)) {
                                             switch ((int)b.a<"s2gkk5cz616qkz","lryFmxnjcqeCplM7hFAaQMzzc3zFP8Ba+E3aw21n6eY=",5447499420395053558,134395105565689735,5556949703519233348,5803583497906509558>()) {
                                                case -1561387327:
                                                   this.aA.add(var11.immutable());
                                                   switch ((int)b.a<"s1r1j30vbzzana","yyKoYGuvtQk3KNSckUgMmdSPt+TkQgE+7c5hXeVok+A=",-3715981351948352686,1438918564461795603,-3305280864041639616,7302746123309632012>()) {
                                                      case 2030273602:
                                                         break label105;
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

                        this.aA.remove(var11);
                        switch ((int)b.a<"s1halz7m1ydpoa","uGX/1sJg56N00hcbVg1dSYnrRULROQbg/BGb1m1kTDc=",4737760982071476497,2935175412410831621,7699672103293428207,897550214686448978>()) {
                           case -282453079:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.av();
                     var12++;
                     switch ((int)b.a<"s8lfv52gd2lax","1Y1vYySaw6j05ulvIQR8GZgRvy3SdcmCTox/g9Bb74s=",-1095354517844242463,-8397304467548113190,-8138493315429219889,-5196502973933611384>()) {
                        case 687356060:
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

   private boolean a(ClientLevel var1, BlockPos var2) {
      BlockState var3 = var1.getBlockState(var2);
      if (var3.is(Blocks.WATER)) {
         switch ((int)b.a<"s3js6hzhf2mylr","kFJAajXDqDQ3IRGtUnrWBgk2tNYhzAclU4JNGh8oz0s=",163589447896725325,7156201215302827826,-5108536915561584070,-7037004706797056789>()) {
            case -279581118:
               if (var3.getFluidState().isSource()) {
                  switch ((int)b.a<"s2xjd79j4gqt7x","8hpe0D4BisRgMo9LzE9ng+fiZwvYcWl98joWWeh4nNk=",-7652251539548237713,-6126228672022919798,2906952049403573987,3052510163263686518>()) {
                     case -304462874:
                        switch ((int)b.a<"s2aa77w4yf4icm","a8N45bIxRtrEP8w0O8WSfL0FwXDk98aFOGcSbhw6n5Q=",-2575858152021868614,-935874297897027313,-7274748281106582335,5431489238648894968>()) {
                           case 10266604:
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

      switch ((int)b.a<"s3qsl8qrx2pzb5","cGnb3XG/cJFixSI8Tas5gA9uKJEGyoNhKg+TPMvv+Rc=",-990037369446175984,-7262600103469588021,-5452874992234911694,2559064459895722484>()) {
         case -1370097379:
            return false;
         default:
            throw null;
      }
   }

   private boolean b(ClientLevel var1, BlockPos var2) {
      if (!this.a(var1, var2.offset(1, 0, 0))) {
         switch ((int)b.a<"slzgbbwoo5kg","V6rXg8Li+0JOa1HsuyHaPC9QQfiN0JqUC74+SGrne4A=",-1917124042447237722,-7062867781592803151,-7407404550073710428,3054330966680007064>()) {
            case -1211590195:
               if (!this.a(var1, var2.offset(-1, 0, 0))) {
                  switch ((int)b.a<"s2jkl7m84bai2e","9UCRezZoEtd327fDVW4S4eDhw177l1OqeAeUq01i/tI=",3305385049926562385,9095006341899984609,1377633506523151725,3707990379655695205>()) {
                     case 947427793:
                        if (!this.a(var1, var2.offset(0, 0, 1))) {
                           switch ((int)b.a<"s2oilm1zmtxscu","+tp/j13omtVtlSlOheRkRH7UEJVpAB4ucBCjCSFROAk=",-509955201229070661,9023887592440020461,2552203837783068343,3611574789739121135>()) {
                              case 472468753:
                                 if (!this.a(var1, var2.offset(0, 0, -1))) {
                                    switch ((int)b.a<"s35nnfgj70gdft","O5aDPk+SkoOBj29aihJAWeexFaOnrJWiUtEq3QVThb0=",-4394546151439772093,8013729561786363415,2268157494451769698,2569009193315611891>()) {
                                       case 42608546:
                                          switch ((int)b.a<"sixiboxfe9klx","FhNFya+6VzgYtxUsLVpPiGOWQWfsiqrjC594Az+nOvg=",362941454813293143,-9076388865333947487,2027313509818621266,323106223851049893>()) {
                                             case 1653561969:
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

      switch ((int)b.a<"s2f7tz1mtji2ue","Bh+u1LLibxbAtMIQQllfgfI9vxszNH3K/1lX8kJZAy4=",-4191790129564034021,-2451683860649414419,6128175675440680008,138741571850402549>()) {
         case 1587422182:
            return false;
         default:
            throw null;
      }
   }

   private void jd() {
      this.aB.clear();
      if (this.aw.level == null) {
         switch ((int)b.a<"sp2y5mynnb26p","lCIj+bKb90XaUaNchokjuzWozZfvI9Q+ZNW3JpzpcCo=",-555825832362400031,2631404316917175133,9019139316519225711,-6505864895662576984>()) {
            case -275703200:
               return;
            default:
               throw null;
         }
      } else {
         ClientLevel var1 = this.aw.level;
         Iterator var2 = this.aA.iterator();
         switch ((int)b.a<"s1l0a3qs1h4h0d","BkmGAZMT8j2YFCgsmOor55tHXx+9QgvLYFyoNo/NfKg=",7037364528061343726,8755673642561939603,91124201146022761,748508397131331210>()) {
            case -1896355542:
               while (var2.hasNext()) {
                  switch ((int)b.a<"s3987s7qfih2cl","teOpggYE/Jv8tb8Zzs17JhONGYwTfkJn2r0ypIHOHIU=",5714242671102326405,3501901094029831799,4488189153595372595,-8016392735159161535>()) {
                     case -1908768018:
                        BlockPos var3 = (BlockPos)var2.next();
                        BlockPos[] var4 = new BlockPos[]{var3.offset(9, 0, 0), var3.offset(-9, 0, 0), var3.offset(0, 0, 9), var3.offset(0, 0, -9)};
                        BlockPos[] var5 = var4;
                        int var6 = var5.length;
                        int var7 = 0;
                        switch ((int)b.a<"s2eoivv5fk9dzt","TE9m9klEk1enx1y9ASJK49et2dCCxsUlJ/w545LTFp8=",-365618793429501286,5018870214252434738,8913022163453312558,-936796594897821474>()) {
                           case -1299987110:
                              while (var7 < var6) {
                                 switch ((int)b.a<"s2pwoi1irvlhwa","6uigltKZe9DGyIe/GkbcnmU0hCQvTXWUQimQI7MYrfg=",7336526151496293260,-8363412330229562149,-9120527854468260715,-4924944518246218972>()) {
                                    case 439991584:
                                       BlockPos var8 = var5[var7];
                                       if (this.aA.contains(var8)) {
                                          label69:
                                          switch ((int)b.a<"smmyg6vr88xpy","iROhWxsK96+/tv+3e1CLCpw0wNHuznWS69xG2vhoqEY=",-8023354047175787299,5346952447316700011,-8665824847476246580,-3762452723447877808>()) {
                                             case -224388778:
                                                switch ((int)b.a<"s1irw57e4n5ykt","ZANEbniH+Kch+t4PCLWnt9M6WSTbp9Rr5ZdEX1fRtPE=",-7738233231412226445,3712894495513964269,4677063885801204943,-969782811387281537>()) {
                                                   case -208055007:
                                                      break label69;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else if (this.aB.contains(var8)) {
                                          label65:
                                          switch ((int)b.a<"s2s0kyk4wysfs1","FnuxhkOydxl2h2Ida837me3xWV3ATLFVBpiup4CwR8Q=",3527631515668416850,7181863220388455818,-8258600850572646714,-5430594027513741161>()) {
                                             case 1107574793:
                                                switch ((int)b.a<"s2ofyr63e3qru3","PnWfzjO5WAVXtDH2+inYIrUJVFPKXAhj2KihWUzc0/M=",7221043867111165345,-5949958643680234521,1103693686139917689,6584801033834507287>()) {
                                                   case -489323422:
                                                      break label65;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          label92: {
                                             BlockState var9 = var1.getBlockState(var8.above());
                                             if (!var9.isAir()) {
                                                switch ((int)b.a<"s30mavczjem379","N55y9EFwo/cCnCApEe9OczC5c2wqsf6yNhNZ+dyJHHM=",-6902994691182361908,-777002882693431832,7340824457600524962,5052346720024503022>()) {
                                                   case 1436507341:
                                                      if (!var9.is(Blocks.WATER)) {
                                                         label61:
                                                         switch ((int)b.a<"s3erx1tw9jyscs","8le0REi4tDMZkPDTDXA4goNe8Ku/WNibi924AWWarzk=",1558041300889120830,-8300585817127816428,-7724473518798961160,9078223555832412543>()) {
                                                            case -1928803145:
                                                               if (!var9.canBeReplaced()) {
                                                                  break label92;
                                                               }

                                                               switch ((int)b.a<"s32tci4lu6wu97","CiH2V1V48lZqPzTWc2cK6wjGQ5BVwrW1hNwIh39KEYo=",-8789455585831060726,-5236365713494877390,1316791436740306493,-9183886757111831293>()) {
                                                                  case -1969348638:
                                                                     break label61;
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

                                             this.aB.add(var8);
                                             switch ((int)b.a<"swzglbskpbt2u","BzmUSdxtalJsmeTOETGD9h/GZlXhzaYfm4vshjYCYsQ=",272573325511580860,-8953067363534987002,-6637045102405910512,8539401418218636767>()) {
                                                case 27498379:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       var7++;
                                       switch ((int)b.a<"sypi6nmtgc33y","V5THcwQCELkb4qep8zhYxVLM8JRrfF7XOoZFdST6FJs=",6404540176453027515,8782541879945829195,-6380070815171944941,-9050282212864435449>()) {
                                          case 1599170653:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)b.a<"st7h3avsu13q4","xkIuwsJ8bs1J+kYUnHxyUgefxRw3WiylCJyosTD6uQk=",7435003970180110036,-4721515699526187303,-8232619741150555328,-6699453263315096312>()) {
                                 case 2073405894:
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

               return;
            default:
               throw null;
         }
      }
   }

   private void av() {
      this.as++;
      if (this.as <= this.rQ) {
         switch ((int)b.a<"s26istiltm283m","bXVpjdkbR9k6Vh3rbjQsWUOhWoj9SXJuhByHJNRnYAM=",8613400274339402102,-3111438824807676417,-7172244142882505589,-8612334836914776778>()) {
            case -1833746070:
               return;
            default:
               throw null;
         }
      } else {
         this.as = this.rN;
         this.au++;
         if (this.au <= this.rS) {
            switch ((int)b.a<"s1tvvsrpzl09ma","/WzmXo15oapnAnIVHsxMKCkhta0guWIp8yd7cohjz5g=",-2102159579699178011,-5572398317720385699,3980012907846273925,-5445875580627340869>()) {
               case 1743037936:
                  return;
               default:
                  throw null;
            }
         } else {
            this.au = this.rP;
            this.at++;
            if (this.at <= this.rR) {
               switch ((int)b.a<"s3bt4ixts9wklm","CkfnF0Iddo3AKb1rE3sef0QYV9B6bCRO19yfsf6BxLM=",-8493762737467048090,-707238653090194406,360472940029573498,1587854459229118060>()) {
                  case 297894065:
                     return;
                  default:
                     throw null;
               }
            } else {
               this.at = this.rO;
            }
         }
      }
   }
}

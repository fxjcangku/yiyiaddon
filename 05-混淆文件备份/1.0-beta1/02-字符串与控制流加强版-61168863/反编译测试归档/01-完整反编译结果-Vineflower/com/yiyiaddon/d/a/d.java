package com.yiyiaddon.d.a;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
import net.minecraft.network.protocol.game.ClientboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetObjectivePacket;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.network.protocol.game.ClientboundSetScorePacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTabListPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandSignedPacket;
import net.minecraft.network.protocol.game.ClientboundBossEventPacket.Handler;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.phys.Vec3;

public final class d {
   private static final int i = 512;
   private static final int j = 4096;
   private static final ConcurrentLinkedQueue<d.a> a = new ConcurrentLinkedQueue<>();
   private static final AtomicInteger a = new AtomicInteger();
   private static final Set<Screen> b = Collections.synchronizedSet(Collections.newSetFromMap(new IdentityHashMap<>()));
   private static boolean h;

   private d() {
   }

   public static void init() {
      if (h) {
         switch ((int)com.yiyiaddon.m.b.a<"s13u1jgq8r0jt5","Q8GP5+IuAVsAmlPtzc1lRnds/fu80MzYcXzendalFv0=",9191730434749968489,794215612793933739,-5612295620414232674,-38201161436234702>()) {
            case -1387484113:
               return;
            default:
               throw null;
         }
      } else {
         h = true;
         ClientTickEvents.END_CLIENT_TICK.register(d::a);
         ClientPlayConnectionEvents.JOIN.register((var0, var1, var2) -> com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.JOIN_SERVER)));
         ClientPlayConnectionEvents.DISCONNECT.register((var0, var1) -> {
            j();
            com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.DISCONNECT));
         });
         ScreenEvents.AFTER_INIT.register(d::a);
      }
   }

   private static void j() {
      l();
      com.yiyiaddon.d.g.b();
      com.yiyiaddon.d.c.e.b();
      com.yiyiaddon.d.c.a.t();
   }

   private static void a(Minecraft var0) {
      k();
      com.yiyiaddon.d.g.h();
      c var10000 = c.TICK;
      String var10001;
      if (var0 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1liwtq04er07y","cc4R6dKZKFf2FUPla0Ztvg+rhInT3z+qXuSp5cMr2vU=",-5161719443463443958,-3555241164410682294,7696569059199895403,635453560235294319>()) {
            case -16328689:
               var10001 = (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>();
               switch ((int)com.yiyiaddon.m.b.a<"synjkpd2z62w0","kKynenyI+0Znh8abdkQAX8+Tip3oz4LficinblgSK5U=",7857206640969747439,-3898788052112671673,1267424938336468740,-7585338831728674759>()) {
                  case -72254374:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var0.getClass().getName();
         switch ((int)com.yiyiaddon.m.b.a<"s19zrjbc7qj9m0","ggFedlEa9db5VX5lZ9XAZBO1+xumsrLX08BgyGZGG9I=",-7248051480082550145,-8714054364475549630,5539965607554990788,-3892090836581071130>()) {
            case 1807477709:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(var10000, var10001));
   }

   private static void a(Minecraft var0, Screen var1, int var2, int var3) {
      com.yiyiaddon.d.a.a var4 = com.yiyiaddon.d.a.a.b(c.SCREEN_OPEN, var1.getClass().getName());
      com.yiyiaddon.d.a.b.c(var4);
      if (var4.c()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jj0yacufrh8v","nNTyqo/C7fSz2+J+fLbAyaQf8OEMeB5DGfAWaV5HVm0=",-37161298842665182,-3381727904979990393,-9007557804354076699,2942612882278816226>()) {
            case -1973146559:
               var0.setScreen(null);
               return;
            default:
               throw null;
         }
      } else if (!b.add(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s12cjedtfab27c","zR9izyYuOMmix2+URUjoZ6FW7q5lL4kRi8eE/i6dwuM=",-2776156885087904102,7316517616015673251,-4106917125253638353,-6403257098202113494>()) {
            case 966686866:
               return;
            default:
               throw null;
         }
      } else {
         ScreenEvents.remove(var1).register(var0x -> {
            b.remove(var0x);
            com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.SCREEN_CLOSE, var0x.getClass().getName()));
         });
      }
   }

   private static void k() {
      int var0 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sqqc2q4rte87d","QWJY2jxq3IuTPoNa86b/Ii5dfUVBfc2+p1rB5/SUDOo=",-3241160390640541380,-2586750032382144346,9110572122676202928,6847910830505928844>()) {
         case -1202354423:
            while (var0 < 512) {
               switch ((int)com.yiyiaddon.m.b.a<"sqj4sxyuqwh6h","neN4jXWw2mwL7Zs8g5b6+JsklfgQz+fc0AhiXLYf2Uo=",-2257165020496536316,-7101372825676305033,-1729157285614299712,6519585475955792629>()) {
                  case 845837163:
                     d.a var1 = a.poll();
                     if (var1 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2iydtcfxqjr1g","5vKUdhAp5E61mwNAlmfYJ6AvUBvm3m0JtJ+ZrBaSrFw=",-263405978581224557,-3410246383116532829,8086707217309580482,8792932851974084756>()) {
                           case 341910431:
                              return;
                           default:
                              throw null;
                        }
                     }

                     c var10000;
                     if (var1.d()) {
                        label73:
                        switch ((int)com.yiyiaddon.m.b.a<"s2hh1jaccedxm0","mpO/M/90iSY2a1JY89ZPw6kivA4xekUzNM19VmdvxdY=",5638989309973508749,-7130486784340799357,-5552114748574781573,1217886025758396457>()) {
                           case 1537581692:
                              var10000 = c.PACKET_RECEIVE;
                              switch ((int)com.yiyiaddon.m.b.a<"s1lp5vp217p91d","ygZjZFBHrsNaOjLVOP2Q3PekR+gsk3IaE/I4uwyYqRc=",6785373139891769186,-652986777866581960,-1489198137781349276,9077250540419744262>()) {
                                 case 2022454174:
                                    break label73;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = c.PACKET_SEND;
                        switch ((int)com.yiyiaddon.m.b.a<"st2pi01l46m01","rtYP8MD0/tf6eVa9yAxAT2O7ACqjoaTSCzmKBJ4yXgg=",6027871609299491666,-1923098483566005367,-6356337101335942994,-1882742054172659942>()) {
                           case 1707465477:
                              break;
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(var10000, var1.n()));
                     if (var1.d()) {
                        label59:
                        switch ((int)com.yiyiaddon.m.b.a<"s3dymekkywjk2h","gVBZbrbQJG4vDtLEsa8Wkp8SD8s6Kz6gSq+onEXnNGE=",-6225737731812635827,-8245854315281764264,757312880986634211,1460121245075652449>()) {
                           case -738350087:
                              b(var1.a());
                              if (var1.a() != null) {
                                 label69:
                                 switch ((int)com.yiyiaddon.m.b.a<"s19i6g5psijw3p","daRsRpuyCB78aK3j9zv7ruEbM8Ex6AhiNBthyl5O56A=",152614568897165935,-764402223154712924,8631245510022704760,2549224405140183709>()) {
                                    case 998623033:
                                       com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(var1.a()));
                                       switch ((int)com.yiyiaddon.m.b.a<"slm11jxj6i1uj","byeJwdwdYsNSsbHUbVQt+UM+dG8wqcAwmuXMkyoJAUs=",99642863790287180,234641937896379340,-2142274198437025882,-5968774658378895760>()) {
                                          case 236287927:
                                             break label69;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var1.a() != null) {
                                 label65:
                                 switch ((int)com.yiyiaddon.m.b.a<"s14i9w7ebmmpm4","2f1yv+Ulw9wiw0f9Im6wMUNjeF4reCbMCoffgFT+2lE=",-2886042553064786989,3456059224298134279,-8364574776675377337,-7845834175879127332>()) {
                                    case -897355281:
                                       com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(var1.a()));
                                       switch ((int)com.yiyiaddon.m.b.a<"s2auia6yymkhmu","PehGx1wHU3liBn8TcA0Z7AqUw4F1LVm4SwI9z+mW8qc=",1100305950443964167,9193040477153444726,-438800442715069077,-1836826812469858060>()) {
                                          case 582363977:
                                             break label65;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var1.o() != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1e2l5pie08fkf","C+5R3avu3Indd7zxTgzshGsD0Qp3WwoWi4TG93GHHW8=",3389957949628627850,5372256484435431915,-7686742129495103598,4390884808321943679>()) {
                                    case -1803342103:
                                       com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.SERVER_CHANNEL, var1.o()));
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ga0epr38gpxy","cuvNdBnWO3zYzYPfFH/MTdDO99LlF92GbAO0QYOfz7I=",3014009446523594123,3538396467065883055,4746830364368924037,-2796357623370834047>()) {
                                          case 800178136:
                                             break label59;
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
                     } else {
                        a(var1.a());
                        if (var1.p() != null) {
                           label55:
                           switch ((int)com.yiyiaddon.m.b.a<"s1gymz429zmsdt","Lt6FC5SmC8NyWMf1rQjfO3JmCFyk0xIimaKR/8VMXQw=",-5485024392460120411,2631449839020641931,4896336709990881978,-2375218779461427160>()) {
                              case -1767371757:
                                 com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.CLIENT_CHAT, var1.p()));
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kz0ww772rulc","LkKKOjDUnS1pVDTW8gXby/i3e4OcxAMHKwIdKVhOAqA=",4175534046321234547,5770675464857806649,-3566544786057404820,-7775708114727693764>()) {
                                    case 846123005:
                                       break label55;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }

                     var0++;
                     switch ((int)com.yiyiaddon.m.b.a<"sx0y9xntwoym","LgUHQTZhFCy0x+xPK8QSeb73n4ZOBjlv4jmBfzFeg0A=",-8461733480449841584,-2971584848311399751,-4222236861535912199,5780374005546646283>()) {
                        case 1140975598:
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

   private static void a(Packet<?> var0) {
      if (var0 instanceof ServerboundChatCommandPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s26klvk3pdbh47","SC6X89Am4lBgkfKuoyigo1TBtZHSRfJGJEFg2kdITwo=",-6332296610029367995,5844324984405171711,6908380127276584978,5816117737613683627>()) {
            case -279648994:
               ServerboundChatCommandPacket var1 = (ServerboundChatCommandPacket)var0;
               com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.CLIENT_COMMAND, var1.command()));
               switch ((int)com.yiyiaddon.m.b.a<"s3nkte9fzkqx7v","ql8cP83RIQ1j2lNVeX1zHfWJeSLfy4v2GrG9CsiK7+U=",8869790773174496603,4652415691500629048,7935639952260482663,-6401317698732291002>()) {
                  case 508601146:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundChatCommandSignedPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2c2bmagneq0wb","yspST6KYU34ihZAxGfMSx6CoSauFEWoFsSAQbiUb/4I=",-7804673535321820327,-4526367323521427225,3311039191765571319,-5448956764138500515>()) {
            case -1564382247:
               ServerboundChatCommandSignedPacket var2 = (ServerboundChatCommandSignedPacket)var0;
               com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(c.CLIENT_COMMAND, var2.command()));
               switch ((int)com.yiyiaddon.m.b.a<"st6k96k105wgd","yrGo/1JKDYdxSFXbyXQNlNJ7HicKCHa0mklDdreV9mg=",6847542672504670954,-6981930189700570138,3376086314971094094,-5141997428932731711>()) {
                  case -742863435:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static void b(Packet<?> var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37ykzwkh2ijru","N2PY40pXvLlwCCS9jx5ydGVDFi6l9uOGgAW88k6Cg4g=",2419998032807396180,7178859427968717629,-3223372012695133893,1920942409023071108>()) {
            case 1405573663:
               return;
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSetActionBarTextPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s1l8gw1qtmll69","GKmllsxI2+v1xPnGL2p7wy1eLwth65L8o6q9OIW4kcI=",6460598905216491138,2211773933553165862,6193558686314801008,-7767112449693648145>()) {
            case 1393710188:
               ClientboundSetActionBarTextPacket var1 = (ClientboundSetActionBarTextPacket)var0;
               a(
                  (String)com.yiyiaddon.m.b.a<"s23fo21w2g1prt","kSC0gtLKe8t5MhVCfHEeS+Sjy4t3yUhhajmhIKKfHCzd9dFY6b0qSV9/WmSP8pCY",-6312922338339418863,6126090786289691154,-4881192575144384668,-5428149968587954287>(),
                  var1.text(),
                  (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2obiy4nkvlqzw","RmyDXQ0Gi0Ju5xPX4xZxRbW1M7iYkFp2XWRngHC8Gqg=",-8988749507271280532,5491648678605678032,7555560688307160495,-4005764315477016063>()) {
                  case 1285631914:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSetTitleTextPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"smkej0ymgm3tl","zqPqhYkGfMXOR5WgER8e+HX1UKXAb4CqTXk62MGXszU=",-7629260923733957948,-8785735652134535886,-6388612156710381725,-8880523310250520619>()) {
            case 2118452679:
               ClientboundSetTitleTextPacket var2 = (ClientboundSetTitleTextPacket)var0;
               a(
                  (String)com.yiyiaddon.m.b.a<"s1irjv1fqr0tmv","XFsdzBn8BPowSbTcEXUz3apNrAv4X/57hDQJWxSG8Lxm9Dv+CrA=",-8871507707076035668,-9016107621496062935,474616604661389278,697976801380677118>(),
                  var2.text(),
                  (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1ha7fknubokyv","LZIM0VTbYXLQnbIetzYKRQJCNW6Q2Qb0rplY7w+HYEY=",8476819445718973267,-8704979578437055637,-1606545065663403306,4453308289085034124>()) {
                  case -853628992:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSetSubtitleTextPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s32i3fr1qaefe3","uOvhnBEEuoqVWFgZxNtlkhL1vqFBmevBgXz0I0kL3Tw=",-385980662796199608,7640571750788284690,-6657060062701356353,-5591119359297006278>()) {
            case -1924703235:
               ClientboundSetSubtitleTextPacket var3 = (ClientboundSetSubtitleTextPacket)var0;
               a(
                  (String)com.yiyiaddon.m.b.a<"s8n7nwqzoq8ty","ccbHAXZoMa7dshz6dzt5tCgv79jZyruhPB2aysPjCzMAgNz6ivRDSWZQKdg=",-348267104114124944,-7593430692738392429,1297922379786014202,-3895991758892113823>(),
                  var3.text(),
                  (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3uu264nrj14zi","gbpT8bMGLnKAVpWXzmHHD1pSRtUMDEtYp4wepSk/pf8=",1020904227273005145,-507815288555405733,7675094297876002743,-570857564295020859>()) {
                  case 1915968591:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSystemChatPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s12d5lehvuj7v8","TY2fQ7NnL1cFKkJbtxla4Zq6sIvyrtycrWtuXabSIjk=",-6039758517737333075,-5161853762547605576,261721712116986598,-3637197544218254813>()) {
            case 1908416342:
               ClientboundSystemChatPacket var4 = (ClientboundSystemChatPacket)var0;
               String var10000;
               if (var4.overlay()) {
                  label140:
                  switch ((int)com.yiyiaddon.m.b.a<"soi33fep3azx2","kl+qiKmxussdShT0Q737u+uMZB7QTz4QK8hkDqoQMUs=",8299238042501147330,8083857152008466667,-9089947382911361266,1512661728675193778>()) {
                     case 1822464840:
                        var10000 = (String)com.yiyiaddon.m.b.a<"s1qterz91x0ha9","eiObpES+ffFSo/rqxBHvK3WQ894h0MjfIcB0FZiXxWUSHx7SWP4/W8+HyFNeidYH0MgcIw==",5737056385746474744,5173754758409506970,-3350191995855101306,-1593058168999905289>();
                        switch ((int)com.yiyiaddon.m.b.a<"sbi9iung9sfpz","j1cZTSPNPzOKKr2GCuxjoBnX6JhCJXmsyv4zLoUyjdk=",6714810109041259670,602081333747343287,-3022231659065143032,6430304030298191960>()) {
                           case 670070232:
                              break label140;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = (String)com.yiyiaddon.m.b.a<"s3r3vz1cd3xmzv","QntehP0UzK/NzyBXVhVYRu5Hg67XW1qnxsRxbp8Z2OxVkykD",3112756926019681921,8887545680643439950,922592231891187841,-7922631896064063547>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1oqstxhfdlhe6","nXZyA/F6n7DpXmEnb/28bkZKs9i86PxgO+s/QFGQh5k=",8022139816742479135,-2741546234482189021,-254027850860058374,7198292348235888594>()) {
                     case 160399459:
                        break;
                     default:
                        throw null;
                  }
               }

               a(
                  var10000,
                  var4.content(),
                  (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1jtpnqo0t3b3b","F3fyFj8t+N31tS4MaiEzF2fiSOITi9wOsFV1xc+OQMA=",7930466011831720859,-2747514965759812463,-2666140984687261441,625550827474899047>()) {
                  case -1479907894:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundTabListPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s28es17xfbctml","q5D+d3Zm/OY2W6rCl+oIsAas2kr7yUMrgTUjg5IjFOU=",1037355886520469395,-1501720604278149258,-5291085619319267597,842154483237889169>()) {
            case -2046346897:
               ClientboundTabListPacket var5 = (ClientboundTabListPacket)var0;
               a(
                  (String)com.yiyiaddon.m.b.a<"s1w5ej5qf1aqit","H9EZv0nU8siX8P8hXZNE6BlR2fYBqOFKmFIBkBMwGRQqVz2PZlz3bts7cPewwi2K9MOENaodXWpNXg==",6595943737758537412,1082047800298496663,750729401156280092,7272311104877678102>(),
                  var5.header(),
                  (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
               );
               a(
                  (String)com.yiyiaddon.m.b.a<"s170s72yy8zp5m","pHJ8oC/bn/HyJVtiJ0A0yDcmcidA5LjNcdC1YirkP4vQl5IK75vkUwJBQY8HhHX/yj6Jc7onSTjCTA==",4654350840606976876,1425633007490116242,6647370070448443209,-980371298711522639>(),
                  var5.footer(),
                  (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1our8hmjljnt6","jlfNyGZjXn5Vp0pqKeNFCPmQHV1Jz3n34j2cOZrhleE=",-371997291613748996,-4761645758871852793,6341626620561435350,1288446624573654925>()) {
                  case 648344647:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSetObjectivePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j4lnjiv02qv9","gF3nhdnm7he+gtS6A0v9EshjCJjaJx+0tO8PhR3IBCc=",-7798674125888148098,1493178351849581148,-6629839236636538116,6674137809991665221>()) {
            case 1795582232:
               ClientboundSetObjectivePacket var6 = (ClientboundSetObjectivePacket)var0;
               boolean var13;
               if (var6.getMethod() == 1) {
                  label97:
                  switch ((int)com.yiyiaddon.m.b.a<"s2pywmvfuplg4i","VQdql8u7iOqXa/oW73u4a1xr56d6BWTBnwlpfbDo5gI=",-2951874204009012742,-8807506272474367236,4112247776641661031,5198747449940412340>()) {
                     case 1337386256:
                        var13 = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s352qzu7203qpc","a65faxZMT+sWR9RHZ0UvgLq1C3AwlNNRotqinRFuQzI=",-4664277527820525615,881062087817161961,-7587088885461668602,-8501801943976801290>()) {
                           case -1125941093:
                              break label97;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var13 = false;
                  switch ((int)com.yiyiaddon.m.b.a<"s2riwu932gbqny","1P1LHdcjo1J8r8nbX2jX7y/MyRokOhbycyWdZpU+Nec=",-4721599619144301297,1903990251220486549,-4966160344829079257,-5101365806676254590>()) {
                     case -929366274:
                        break;
                     default:
                        throw null;
                  }
               }

               boolean var10 = var13;
               String var14;
               if (var10) {
                  label90:
                  switch ((int)com.yiyiaddon.m.b.a<"s2fowgip4i0po7","0qJqyaBcYmcyginfw1RDCNMKmn4zPm1Y0Af4Mjr1FYw=",-7979116285736447165,5143216182527190198,4562239170772039062,4568074971219213559>()) {
                     case -1415273042:
                        var14 = (String)com.yiyiaddon.m.b.a<"s240vq0oov7tya","2U/REORUXIRHjlovVOBJEimEQsfGNuouOPzzdopHcQv42SCK7BKADqfdk/9wve2Ddhq3iPV5JcJftnoo/jk=",4582787154769266940,-6804605669699393378,-3863383738490529256,-3070299204444456943>();
                        switch ((int)com.yiyiaddon.m.b.a<"s160q2yd7qhvpd","Zhfl+eVqiyAJbyOdCrAi5S5Pj25AM8vCtIkbKUS3PIQ=",1221626685761636625,-5118939822802174074,-324533057116362419,1986499850528257298>()) {
                           case -1091939603:
                              break label90;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var14 = (String)com.yiyiaddon.m.b.a<"s4ox94t9zddv6","WxwGBP0GyBJlm04n4hBVqVnJPC27GatcN3b5lra4au5gPXTiluB87/zjahGwH1hwH2q1+pCrOB7wtg==",-7220434570053863523,-2013498403928805155,-8275424059476173463,-2362185105123081286>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3akmfwzd5ssfc","/mQ6Uc8QzkECqgVB621XfJ9Z5vSp4ovJ3OKC9EOYaeg=",-5442797515043232754,7430623307486228835,-6418647215035961140,-1249038261293588844>()) {
                     case -1444762071:
                        break;
                     default:
                        throw null;
                  }
               }

               a(var14, var6.getDisplayName(), String.valueOf(var6.getObjectiveName()));
               switch ((int)com.yiyiaddon.m.b.a<"szqvfsmo6bbse","QnR9KtPr4bFBJ4JrNTUf6G5SSkzXk3hasdX7VBNqahM=",-4826095243218477993,-5927930253786602872,4885283735179462723,-5566086314929561170>()) {
                  case -925578449:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSetScorePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s18e6aqe05h5bv","Rs18KXACesfCKFz+c4a16DAltxiRZ9qxCtRodBqUczk=",1084007994781202749,-1270627262234281293,5067624011262031509,-8553074636205211443>()) {
            case 1174464202:
               ClientboundSetScorePacket var7 = (ClientboundSetScorePacket)var0;
               String var12 = String.valueOf(var7.objectiveName());
               Component var11 = var7.display().orElse(null);
               if (var11 != null) {
                  label127:
                  switch ((int)com.yiyiaddon.m.b.a<"s2pzsz53xb6bkd","l8vhXDkglYfAYEa3k3oFMlAfFmXIlBwhiy1/hErrjQs=",-3501810833846511737,-1900676352227659336,-6611728663660943989,-2646074287808872804>()) {
                     case -2077574852:
                        a(
                           (String)com.yiyiaddon.m.b.a<"s1t5e12r8qdko9","4kzImSdwIEdE3P0uGa1bvQz5sRfUzD9Vq2j50obCwCAjlpfjGBZNq8Ooa7o948Hht4uvnir/",-2701906614186032734,102039899896252004,3107511329481424498,-5290370793636260794>(),
                           var11,
                           var12
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s23wsgyih7vv2v","QEGn65Hq+84/OnenlU8nmH20bOWTm9nOS/54OA9iD5I=",-8453030495642099434,1005175638895914445,-5230363988171288942,-1650654205862782734>()) {
                           case -727683476:
                              break label127;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  a(
                     (String)com.yiyiaddon.m.b.a<"s1e9uc3fn7zp2l","w08teRo/EfWPEBECsI0WfAoWdwzw+TpyMm+jaGkLfphOxqjiaapHYIFTOVEWd4XSj4Y=",-8845137605188661862,-8854420154784206530,7733963484544166456,7908533800695612631>(),
                     Component.literal(var7.owner()),
                     var12
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s29yyhha0bwmsu","MvvRcF0oyiS/2gPKcFRxAuWWLivWIlXDA9E9HzNi6HI=",2707793953166900584,3151305944439522104,-6806869249174527840,-7280489590656969901>()) {
                     case 1305692478:
                        break;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1snih6pkvjrdo","131SPfJIoHOSk20OkUV4zcKDlfV9GtmRGV4dsqXGokQ=",1661214903241218389,-3378622936342777039,7078622688082349484,1462047290511148758>()) {
                  case 636677667:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSetPlayerTeamPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s291g0alaqlm4t","QXs5i8tgzNKyKIS+rBETnEIPBNDFj+umi3nDUkr4vEY=",279898464484190540,5508817086856745679,4707170762717714305,9192703608974561836>()) {
            case 1353771436:
               ClientboundSetPlayerTeamPacket var8 = (ClientboundSetPlayerTeamPacket)var0;
               var8.getParameters()
                  .ifPresent(
                     var0x -> {
                        a(
                           (String)com.yiyiaddon.m.b.a<"s3kvoc4yemfohf","2vmLftSlLp0WQBaxKTs/pALe98xp4ebsMowyJRFyVGddhY2nGEUOrIRk8CQtvKygOkR1UA==",-2352433980379434858,-6350649607890911259,7285525019512465322,-500372393666246122>(),
                           var0x.getDisplayName(),
                           (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
                        );
                        a(
                           (String)com.yiyiaddon.m.b.a<"svbaiar03mui3","PPeGidEmPD7Sm9fEGCCcrZN6NcQly7GMP9uOivmRoA+33NNmued56QJ4dlvGxYZrYi4=",-3868317126022492659,8304017803436754942,-8866064363315524089,8847652311829408882>(),
                           var0x.getPlayerPrefix(),
                           (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
                        );
                        a(
                           (String)com.yiyiaddon.m.b.a<"s2llc8f11n184f","l351mECZR7M9B52GrFmE1u6kWlmZFHX6LyAz6sVV/VnpqxseD3fSXme738bq+EW05mo=",-6182021778400286554,-7637197619116741840,-2185656845973692812,9188317049741477518>(),
                           var0x.getPlayerSuffix(),
                           (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>()
                        );
                     }
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s7jw1x1a1a6on","MBtAjhG4qMIM4/Qg9F9nm2q8IAJZJsI9SN59ExlCvZg=",-2640475361281495006,4580505759956260127,4224346849566628698,-4286648494989663602>()) {
                  case -1052017101:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundBossEventPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ip01dlclatm8","MQE+0yMNQwwPeH0SFAK/aGvS72kgxQ88+gfRDUizpb4=",-7779933423526636811,-8340632162427257308,2188984510656025536,-6007873400248057315>()) {
            case -963973113:
               ClientboundBossEventPacket var9 = (ClientboundBossEventPacket)var0;
               var9.dispatch(
                  new Handler() {
                     @Override
                     public void add(UUID var1, Component var2, float var3, BossBarColor var4, BossBarOverlay var5, boolean var6, boolean var7, boolean var8) {
                        d.a(
                           (String)com.yiyiaddon.m.b.a<"s3pak310wi9kx5","VTOzock4T2ci7VzAqXh7S5SWAyxD/nOt8Pm51pBIOMk497sUDRYXXJnmtqE=",-389079173362553786,1738557058930557408,-439308449691922359,4865775950996063895>(),
                           var2,
                           (String)com.yiyiaddon.m.b.a<"s6pe83dgjbqn8","PNDUWMgWRQpwXltaicyo9WEARn7KA1sLwdKllw==",-820676394453749959,-5103803740505489285,-3966626224333584616,4241044992755896305>()
                        );
                     }

                     @Override
                     public void updateName(UUID var1, Component var2) {
                        d.a(
                           (String)com.yiyiaddon.m.b.a<"s3pak310wi9kx5","VTOzock4T2ci7VzAqXh7S5SWAyxD/nOt8Pm51pBIOMk497sUDRYXXJnmtqE=",-389079173362553786,1738557058930557408,-439308449691922359,4865775950996063895>(),
                           var2,
                           (String)com.yiyiaddon.m.b.a<"s6pe83dgjbqn8","PNDUWMgWRQpwXltaicyo9WEARn7KA1sLwdKllw==",-820676394453749959,-5103803740505489285,-3966626224333584616,4241044992755896305>()
                        );
                     }
                  }
               );
               switch ((int)com.yiyiaddon.m.b.a<"sn22en5f6bycv","BZnj98V9Nqml/cIy9BPMQVvfx8tsEqj+fJL/SnJEgdU=",-4865845251488894917,292893798762889240,8529437726211278299,2894999316052158349>()) {
                  case -1371745327:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private static void a(String var0, Component var1, String var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k1vokwkdvqg2","jHRucAug8MMu4VazHOtL+gibvF4/4uFg0UwhikNUwT8=",-6862653047736671448,6007063945318217449,-3591979998957604876,-852423969886091750>()) {
            case 905766078:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.a.b.c(com.yiyiaddon.d.a.a.a(new h(var0, var1, var2)));
      }
   }

   public static void c(Packet<?> var0) {
      a(true, var0);
   }

   public static void d(Packet<?> var0) {
      a(false, var0);
   }

   public static void i(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kpfexc02xyij","QmFlYPVXnfg2ztw0v5yF1j355xpZvilWOtDZtrrvlms=",-3423435267627958573,4677280848338253318,-8625521534115440377,5844011259424859092>()) {
            case 1430243521:
               if (!var0.isEmpty()) {
                  if (a.size() >= 4096) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1fid5a9ghav6t","u/fGbaqCvCRWHceMlvNbJIviimGEMP3msECeUHfIKpA=",4960644148854184051,8061282154761211930,-8292744777312466197,6809449388553132361>()) {
                        case 1868039709:
                           a.incrementAndGet();
                           return;
                        default:
                           throw null;
                     }
                  }

                  a.add(
                     new d.a(
                        false,
                        (String)com.yiyiaddon.m.b.a<"s32djpfzcsk275","+YDNLjGkyo38sLh0h4snHpgcLXHqZ7472CdVvA==",5549065417512176219,-7329748469059269587,3544246727511591240,-8508508479532794337>(),
                        null,
                        null,
                        null,
                        null,
                        var0
                     )
                  );
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sil73d6g4nokw","LMkF50YDMc581zTCmN69OuXpWuXPCOK9BmKdpa7zLKM=",1366900757048876774,1745051306776186412,-2041887180793276409,1744553110921347350>()) {
                     case 2076391256:
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

   private static void a(boolean var0, Packet<?> var1) {
      if (var1 != null) {
         if (a.size() >= 4096) {
            a.incrementAndGet();
         } else {
            a.add(new d.a(var0, var1.getClass().getName(), var1, var0 ? a(var1) : null, var0 ? com.yiyiaddon.d.c.a.a(var1) : null, var0 ? a(var1) : null, null));
         }
      }
   }

   private static String a(Packet<?> var0) {
      if (var0 instanceof ClientboundCustomPayloadPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2airjmtl7p1nl","G55d+yKGn4kOT3W3jA1S3wsCIw+ILKXK8so4C3Sv5mo=",8819935508685321589,2480634875438246035,4157760105977594183,-1890186987522443544>()) {
            case 82874922:
               ClientboundCustomPayloadPacket var1 = (ClientboundCustomPayloadPacket)var0;
               switch ((int)com.yiyiaddon.m.b.a<"s344e5ozopiaeh","/9jU7ZF4HyGSNcL7+LskwEXBIsDjVdBDdbk0U7gG9r0=",-4422857301526634528,-91336660385486612,-824006654915701294,931653242827660241>()) {
                  case -581612769:
                     String var2 = var1.payload().type().id().toString().toLowerCase(Locale.ROOT);
                     if (!(String)com.yiyiaddon.m.b.a<"s2k8flji9tujsa","A5qicAeyq445BfoqOhVB9FjOl8X30ppAqV5qSLCJdm6VSWQ5lYlSYaWZsXT5xKm4eoDKkeAZV32yiw==",-1050153695620382225,-3847879189488077313,5361498916004126917,4973661742689986674>()
                        .equals(var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2sg1zn78sgqb8","DUeg7t2Qq3tAmEIpJIzdAui4AfuGUU5nBYef0uu9bfY=",8922074648036808943,4117107656472604948,8745410765289634367,8138537795361224406>()) {
                           case -1181037369:
                              if (!(String)com.yiyiaddon.m.b.a<"s1ylzjiqg3kv3u","ct0o0YKF7f/cAN03gw18scHlaw8HY2XDjTWf/wjsQl6GZm881moLN2agIigpMssE1tQO0jzohbGg62pURv2s8w==",-6953644242343957770,-7160281032224090159,-8323593028040550992,-2864401541230778797>()
                                 .equals(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1xaz5wewmq7nz","+k36xJ8V4OTbCPwyFpSyAYkDDbCmncvxZAOyINj6nxI=",-5856835852971727100,-892920810232935606,650426308407564621,8665223015449012521>()) {
                                    case 1523071041:
                                       if (!(String)com.yiyiaddon.m.b.a<"s1hp6vr4gsv5o4","wAINi3ZgBzWK2eQFCLuumaSMNzZzgLEEIva5oFju5NqBxe0eYtmyHF+4R2h6i70YBgVB1e1XZyAAdXbyMxPXcc3dP7w=",-756382168011960639,-3355558160849165534,714277051921042172,-6309621741091345705>()
                                          .equals(var2)) {
                                          return var2;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s395uunze8uip5","74PblUL2wJiRLTXy53M+9sYCC3y2olbxkL2cZlYygIU=",7070557201876702741,5306074170266648085,6698867569596699641,4401245317041521576>()) {
                                          case -504269644:
                                             return null;
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

                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static g a(Packet<?> var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqsz3o4e2gj91","zLzL/TFY7j1+Ok98xkGXZ31Jn/ILzxxvhZX6zva8JLM=",7445403302697101809,-1118099717163550900,605321692866897600,-2315168612071353383>()) {
            case 1597122601:
               return null;
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundPlayerPositionPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o14ab34m2p2y","IAXfrhFbStrSoORt862e5CnL+M5nr10sYPEV7WpP0pY=",2628144809860054640,-253746908023747490,-7154965848929614422,-4422009532563253486>()) {
            case 894186757:
               ClientboundPlayerPositionPacket var4 = (ClientboundPlayerPositionPacket)var0;
               Vec3 var3 = PositionMoveRotation.calculateAbsolute(PositionMoveRotation.of(var1.player), var4.change(), var4.relatives()).position();
               return new g(var3, false, var1.player.position().distanceTo(var3));
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundMoveVehiclePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s1r8m0lr9id4hf","YifNi5skA1r4kzkYRN9lALaw/RVcv3ygsiGS4U1QmEg=",-4515662436744663531,-7684277461331140751,8062872670263504427,267348385820220796>()) {
            case 262706684:
               ClientboundMoveVehiclePacket var2 = (ClientboundMoveVehiclePacket)var0;
               return new g(var2.position(), true, 0.0);
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static int f() {
      return a.size();
   }

   public static int g() {
      return a.get();
   }

   public static void l() {
      a.clear();
   }

   private record a(boolean i, String N, Packet<?> a, g b, f b, String O, String P) {
      public boolean d() {
         return this.i;
      }

      public String n() {
         return this.N;
      }

      public g a() {
         return this.b;
      }

      public f a() {
         return this.b;
      }

      public String o() {
         return this.O;
      }

      public String p() {
         return this.P;
      }
   }
}

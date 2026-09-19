package com.yiyiaddon.e.h;

import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.h.e.c;
import com.yiyiaddon.e.h.e.h;
import com.yiyiaddon.g.c.d;
import com.yiyiaddon.l.f.i;
import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public final class a extends com.yiyiaddon.d.b.a {
   public static final String kB = "id_config";
   public static final String kC = "ID配置管理";
   private static final String kD = (String)com.yiyiaddon.m.b.a<"s87pcnf315410","APOVXHS5EZkVtUm3+mt4vmu+bToqePaQUAW8oUXt",-2149368167150049729,7120957594158942084,-3272211058965229433,2257821090566142155>();

   public a() {
      super(
         (String)com.yiyiaddon.m.b.a<"s28xyeqef3t01y","Xj6Pd3+A6N7+yW00YF+hiXliQNA4pe9ZTvrc5u/YTxnSpHniG0LDQ+ZaofTDaQ==",2746065377233369814,-1661457111761980818,4510716741336279537,2638191053801780066>(),
         (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
         (String)com.yiyiaddon.m.b.a<"s7gytgv10ro8s","F9vQXscueYwV28BzkOmY0DPI+fSxeBXNkjlW3Sy3N0unNJc3Fqmldg==",3586987274799795851,-6055664235271598309,-2748883423356646801,-7205093092222397568>(),
         (String)com.yiyiaddon.m.b.a<"s1dm8paxx17s61","wSEquci00iuw6LQY7MMwNAkxdPictlt4FXYXANy/URcklMwzKg1vqwH049Pk+2HW5b5IbfRoYA/vng+FpQiUDmIkLC4jki0/FiN9vTTtSCQ3wN05P7TKXQLlXcGglrRDie6W4y3FtH0T7SqmOUtfqBb6pjUcP5tN",-5609736197033522826,-3179002494492991239,3291418791795436373,4015283581051078001>()
      );
   }

   @Override
   public String a() {
      return (String)com.yiyiaddon.m.b.a<"s26u4oul1n3khe","GCqkUvWCTMD4W6T1kxz11uXPBPHnFU8tbr/9jESPLIjYk1KqoRTqSq77Yuo=",5303627438027341725,5684971636670217136,490853856567943323,-4072830920057799591>();
   }

   @Override
   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s87pcnf315410","APOVXHS5EZkVtUm3+mt4vmu+bToqePaQUAW8oUXt",-2149368167150049729,7120957594158942084,-3272211058965229433,2257821090566142155>();
   }

   @Override
   public int i() {
      return 20;
   }

   @Override
   public String x() {
      return (String)com.yiyiaddon.m.b.a<"s2ek2wwfukpupv","SwBMrRqF2JndyMPUjn4i5LXJ9N2b3ivoiKWOqZQlMe5U4a6qw/8=",-7471399327565373953,-5271677741494371963,-8642670925076918666,7188121338307957767>();
   }

   @Override
   protected void m() {
      com.yiyiaddon.k.b.a.a().d();
   }

   @Override
   public i a() {
      return new c(this);
   }

   public void cO() {
      com.yiyiaddon.k.b.a var1 = com.yiyiaddon.k.b.a.a();
      var1.C();
      com.yiyiaddon.d.c.a(
         (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
         "" + var1.dH() + var1.dI() + var1.dJ()
      );
   }

   public void cP() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19birt4flkwdm","uachChDDQCPEnUk0fefKam2N5OASeifInUVQro/AMoU=",-4670276324647912238,-5331687071675336849,8144173761961558060,-6939518047994758928>()) {
            case 1810488394:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                  (String)com.yiyiaddon.m.b.a<"s3vqzg607djmuo","MTMoPHryCHOqBQHzFi+NFi/nyE0LKhzm8C4IzaJvPYOPun5PK/dzYrmv2yJldoDowcs=",4804279197466792982,1144558371482852225,-8725100134589084123,-3649239033306429852>()
               );
               return;
            default:
               throw null;
         }
      } else {
         ItemStack var2;
         label73: {
            var2 = var1.player.getItemInHand(InteractionHand.MAIN_HAND);
            if (var2 != null) {
               label65:
               switch ((int)com.yiyiaddon.m.b.a<"s1vq37wvgu1bo8","7t9PaQiIEMEriflE4azQ16Ly1Ui2t+ffjc8XA8F9vqc=",6762985085267724536,5854024973614630930,2447084207826765080,7856099611953467535>()) {
                  case -83362161:
                     if (!var2.isEmpty()) {
                        break label73;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sv5ylfzk4xfso","KaVh/iNirr9LMVqxlq+tARmAvo60UKwblCN737clI8E=",-4036453443121582027,-8365698309837146080,561758486903577743,4116859390044945393>()) {
                        case -348637916:
                           break label65;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var2 = var1.player.getItemInHand(InteractionHand.OFF_HAND);
            switch ((int)com.yiyiaddon.m.b.a<"s2yffmz9anta","2Zl15EKfhLQhz7lKZJDJFT3Z9TH2crY1smU7UPRy4RU=",3045248333809112961,-537336413723035778,-9132626552615990842,-351714178497126309>()) {
               case 262716629:
                  break;
               default:
                  throw null;
            }
         }

         if (var2 != null) {
            label58:
            switch ((int)com.yiyiaddon.m.b.a<"s3mls8dnmdqhdq","H86dLVnHm3dzgNBM4I1f/yBNQ/6YZdpC1EXigMuFsNs=",-4465294460840014530,-2991494343550408563,8668021378558424073,-6721176296734340958>()) {
               case -805030245:
                  if (!var2.isEmpty()) {
                     d var3 = com.yiyiaddon.i.b.c.a(var2);
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sonhwh6hriyp4","UkEm9tqoW/jh4pHeMIjwhkU9ecCM0AzCtzWhG0SSpbQ=",-862824037804126220,-8668395807790977143,-4757270564383772145,7001656130059534098>()) {
                           case 1699395138:
                              com.yiyiaddon.d.c.a(
                                 (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                                 (String)com.yiyiaddon.m.b.a<"s1cz9bhfmzw2ev","YrDy+BPtgRs2txMaWpDtIegl2C9AdY3RWuqGjQnc6SUEsvaagllrhYEuMjx9Y+lT",5069387724898461889,8061095876427306254,1321865456565825397,4628509633565652829>()
                              );
                              return;
                           default:
                              throw null;
                        }
                     }

                     if (a() != com.yiyiaddon.g.c.c.AUTO_SAVE) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2flgf3srfg9i7","NdOF4v1WEbRgMLIajylexTazK8yFtt5t2X/HS5VDpvQ=",9069420393803279991,-547006954415752600,7099326066078925384,5109142044096235187>()) {
                           case 1449988575:
                              var1.execute(() -> h.a(var3, var1.screen));
                              return;
                           default:
                              throw null;
                        }
                     }

                     if (com.yiyiaddon.k.b.a.a().f(var3) != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1rvn0g8lt9lp3","4zrelEqMfjDHEUsRF7x4TGVkAP6NQrfPUadd7R5ywlw=",-7584255924489839879,3887713349393155176,-4624727573748834396,-3062652844347084195>()) {
                           case 790853717:
                              com.yiyiaddon.d.c.a(
                                 (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                                 var3.m() + ""
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s14jcni67o5w9f","uWqtgB7AbM3XC9hE9HEbZ9Lh8JZtWNU0i/KLDJdifr0=",6061250008597024525,8302532197677663827,7879503794477008634,-7858557103708886966>()) {
                                 case -1309850519:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        com.yiyiaddon.d.c.a(
                           (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                           (String)com.yiyiaddon.m.b.a<"s380eb8grj7trq","uyqcnziQIPdTlxYj+8b5cQ6o7KdjdwqGq7a1RzTcmwlm50vnAHK3YAV1oEpxh/rXB9zGBo4gPMCsNeMx4E0THg==",2055992033570985711,-4360996003110829576,-3231470351024828547,-7355253105306176390>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s2zrnx7gz6drws","qbZmDVko4p82jNULAPYI2YVZBVQLDPAuzGgTsy3Kw9E=",5291959264411445734,212871045562587737,2033942545525962787,-6637822853530683121>()) {
                           case 10402595:
                              return;
                           default:
                              throw null;
                        }
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2knzpgg70bdbh","JC3R5feYuTpZWOTT/PdwOoYh10LGic2EhHwmWZ5qcM0=",-4482344611356876287,-3929170704319443225,-2039763220612630915,7905180900310050234>()) {
                     case 510066170:
                        break label58;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
            (String)com.yiyiaddon.m.b.a<"s2bh9p12j56sb4","U6C2yUpuYTfwpGpmVdLU+u5Iwbe76PTSEXFJXnja6B2v5utaKehRXqUssXtOxy9YOL3VbukGh/INcrvvMTYk6MacS61T3dOmzVg=",-3899096434569780935,-5490166059932884138,5294359859204232556,-3533077275892754750>()
         );
      }
   }

   public void cQ() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2epaueibbnrzq","rJE9vhR+qhwzmjs1kaAUIuX6fzV71wdymPkTV7lRmJw=",5758731993573283131,-4665139686692565328,-8609250248148423273,-1546326107487512351>()) {
            case -320364290:
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                  (String)com.yiyiaddon.m.b.a<"s3vqzg607djmuo","MTMoPHryCHOqBQHzFi+NFi/nyE0LKhzm8C4IzaJvPYOPun5PK/dzYrmv2yJldoDowcs=",4804279197466792982,1144558371482852225,-8725100134589084123,-3649239033306429852>()
               );
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.c.a var2 = com.yiyiaddon.i.b.a.a();
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2cny72z9xh4v","Z3q9Imr/b75ssXeofFPGVCf6W8eSKZ8Aec2Il4yAWHQ=",6420038095621911410,1524314754190592948,-1112548785060467937,5189210790041123124>()) {
               case 495051704:
                  com.yiyiaddon.d.c.a(
                     (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                     (String)com.yiyiaddon.m.b.a<"s3uc1kz1aly710","vwUVG86TIzdL6GpTaJIFa6q/IPFQMHO4QxT/HhjOGtozRcJSI9ULg5tctOK32nxuJu20A9GL0smLVKUvbtN5b5ZZevqLpqXvDQ1e3B8x",-5875329301209718429,-359159728066562408,-1311204358286690022,-4130524996033935993>()
                  );
                  return;
               default:
                  throw null;
            }
         } else if (a() != com.yiyiaddon.g.c.c.AUTO_SAVE) {
            switch ((int)com.yiyiaddon.m.b.a<"s2hx2ocxpkxgy3","6zgmWa2BGX6uSz3Y5s0EP0UCoxadThU2Xp2U9lOMvNI=",5484898628457414767,370541520256698906,-4989364355576174392,-4043148920038717680>()) {
               case 1541613520:
                  var1.execute(() -> h.a(var2, var1.screen));
                  return;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.k.b.a var3 = com.yiyiaddon.k.b.a.a();
            if (var3.e(var2) != null) {
               label44:
               switch ((int)com.yiyiaddon.m.b.a<"s33e2yd8ic7pob","/4D3WVheBpYtoRc29wHMPKY5TVf3KMpp6xsi5sFjEpk=",-3074134076925548880,-5796607163825884868,-3507342814115312880,2010721125012240838>()) {
                  case 864657959:
                     com.yiyiaddon.d.c.a(
                        (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                        var2.m() + var2.fL()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s1kcrhyb24f7ij","4qRK3S3gm6PGHUAD7SabOYXOaHzXyIhBAHqsLy1cGf0=",8973760531444106349,5169743827853337641,-1291513544295536131,-5218109419018979070>()) {
                        case 509923670:
                           break label44;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                  (String)com.yiyiaddon.m.b.a<"s86bwr39vqjbk","lw1OrJooXYkbrrm6+Sw/mDHdHVV2ZAXZhYcYXRLt4m0IeETGtevKshZWsUKZwKdg5Qsk23KSoMDde/Vwed2k25sHPCs=",8208707320582111199,5866593248566527035,4190493381754710397,5394522670635302232>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"sqq9bu0s6dvd1","pocjWRelhbrWJ4b2wxtyK9DNwYIaAiL2PXNSlgvHD5U=",2734519052398399784,-5591234430859555123,6736222658101736670,-4192126870309373053>()) {
                  case -598952090:
                     break;
                  default:
                     throw null;
               }
            }

            String var4 = var3.b(var2, false);
            if (var4 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1epya1hoular3","rMODk96fw9m2ChOBHA3iG2GGyXsTtSxc3ekE/7ILuig=",5362806916085493889,1824933619301683692,-8416924441051355973,7412103004695583251>()) {
                  case 1001593447:
                     com.yiyiaddon.d.c.a(
                        (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                        var4 + ""
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s1cszffhni7xf6","QjryNsgsjT+ECpvpab1qYxa35glj0YUT7f5F3WgwB1Y=",6417239053500350448,3585130440098254510,-7187928189609771712,2656149711437174625>()) {
                        case 288925183:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.d.c.a(
                  (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                  (String)com.yiyiaddon.m.b.a<"s2ukwacjse2ass","spRKEjEwjAjRFQo/5hM6qy+IW8vJVUtSmnGM4dbYfKPwersu3K2WeuEJl8uX2o7g04XZNiidDd0dSeXu/HxqDA==",2482963659639224656,-4006572066450456167,8335383661198832224,-5531533943528329280>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"shpas69sy29mr","rux/OYu1cyMuqQDMSs9tTcTChaLMuZw8S13fcRM3s8o=",4679671614345501379,-101567930543403753,-1816866158160531917,9061683056066760149>()) {
                  case -1249003128:
                     return;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private static com.yiyiaddon.g.c.c a() {
      com.yiyiaddon.d.b.a var1 = e.b(
         (String)com.yiyiaddon.m.b.a<"s2lai7o5cupryx","rdXldNqZKwoYM0AdC+uhHv+DIq/WTOa8cLA/YNGZcn9SuolFYVSNNK8y854G7CxzUhU=",5460323314608067804,-3498526700364290359,-974614284057230679,8708994871322164810>()
      );
      if (var1 instanceof b) {
         switch ((int)com.yiyiaddon.m.b.a<"s17r4hon7mbdyq","RmUJvmqot9FR/AxoryeOTSgWTR2eUwiQuNJD6hY7fLE=",6496724363768096019,-4134841715071212857,2992954892345619010,6275658435849031902>()) {
            case 1340533365:
               b var0 = (b)var1;
               com.yiyiaddon.g.c.c var10000 = var0.a().b();
               switch ((int)com.yiyiaddon.m.b.a<"sr3z7adtpcxen","dYfsongYKFfdNvdbMd6kNe+1m/JPkLVtbtDwqS9wbxs=",-2799764087271572203,-8323839471959207297,-8664087652322931927,-6685040479710334075>()) {
                  case -1737951588:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.c.c var2 = com.yiyiaddon.g.c.c.AUTO_SAVE;
         switch ((int)com.yiyiaddon.m.b.a<"s3o3l7mfgf55t4","MxnsOOnxN3YnOFSpwzDfIvVSzMh/Ow7cnbA9D7ZSqQY=",-1299595743151958663,-9009903848558943974,7967582010100728724,1598903457660229960>()) {
            case 2091803590:
               return var2;
            default:
               throw null;
         }
      }
   }

   public void a(d var1) {
      boolean var2 = com.yiyiaddon.k.b.a.a().b(var1);
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var2) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2mrr6724sctcn","RvtDK54k6QUQ/XeL0as3frWmKslclU/UdT2b/yLMfNY=",4732125590832213180,-8830764368233911777,8443780248682686766,-7504618172717537146>()) {
            case -1607841549:
               var10001 = var1.m() + "";
               switch ((int)com.yiyiaddon.m.b.a<"s32pbui89q90pw","8BAJyMb2uoVQVHhkaRsyY8zmEMLTSZfcq5cCkp1Odj4=",-5619997229488728190,7060582141490544811,-7712074693732422575,1483773812330494032>()) {
                  case 1168734247:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s454a5e9dieka","E+2hG8XAm90kslzoTx+ZyhtvF9754ROoN4xyATZ0HTt+FhFwJ2YgpABhwo2kzDlqv9mIqg==",4839646866592667173,-7010160110702508078,-914771876254770435,-3012937555569595753>();
         switch ((int)com.yiyiaddon.m.b.a<"s23uxgnf837mu8","UEMOBm1BEbDR/3JAy+SFOXhN89vJmaUapETuugIDpVA=",8075269452618324870,8544512098456144576,8189790931005439097,3037129978021932377>()) {
            case -1250489330:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void a(com.yiyiaddon.g.c.b var1) {
      boolean var2 = com.yiyiaddon.k.b.a.a().b(var1);
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var2) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1aroezl1gf61o","7yQ7OGSZicJerY/rAnLEMbHHbNdhC+nZeGlo/l7uKeU=",1266414456448729014,-1773829247435708353,-3439048788416348332,-3504507148187230475>()) {
            case -1793437141:
               var10001 = var1.m() + "";
               switch ((int)com.yiyiaddon.m.b.a<"s13p0r6cvgvao","N/v0W7my5vfi8AKJvmj8Mkusi2ueEgZl3WQUjRDWgI8=",-6940112613417810901,8705386146844344005,3822448860905668173,7663019369184506917>()) {
                  case 1113294665:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s2babpedv9mqru","KOO8tOzYT3Y70d+w40aZQNG5G9Uw9S5VtrXD/CNdMd5bVcspZ2KxSD7bjSaL6GkLSHojCA==",2068267627230898624,4749501212639917163,7860903341637175446,1300073735880797308>();
         switch ((int)com.yiyiaddon.m.b.a<"s2u1a0mkdqomcb","/YyXHsHdwTf2i9r+zqm9NsK/eNljll42E+fV54GIKcs=",3525176632807768971,-1285576274430377971,-7544872406149361382,3402973010695413337>()) {
            case -120754366:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void a(com.yiyiaddon.g.c.a var1) {
      boolean var2 = com.yiyiaddon.k.b.a.a().d(var1);
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var2) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"sllilk7auuwf4","4beiv3hOAuFw20BOYVA15KPGUApJDzMU7Zz0JuTbbPw=",-8487263864045489606,-6853890138243611528,4221100719723289741,-8893256042378949821>()) {
            case 1043350276:
               var10001 = var1.m() + "";
               switch ((int)com.yiyiaddon.m.b.a<"s345tgevdjo8ah","RN1+AvzMWVbHi61fi0ZfDx6BDs8zw1xnV+gaXTHsglY=",-6475652458485196383,4970123092317149383,2424307078619167615,-8644357351021987168>()) {
                  case 991302045:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s1c8gq7tmnm0pp","2/6kwPeixo6g+goTbYjfUNpcQor9IH5gpcpJbW8umGk2npo2a+J4Q740ScdKrLTD0R8+eQ==",-630587172780017741,-280625746534567082,7129946616764928380,-8269210425363286191>();
         switch ((int)com.yiyiaddon.m.b.a<"s2e942o8pm9ig","pDAL0PEbZ8O92Gw1eNfsxnhmAgPNgUPGCOjo7QHk7DA=",5914767388833836773,8280685739584241057,1807417099204558490,5436148499784974847>()) {
            case 2121814996:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void cR() {
      this.a(com.yiyiaddon.k.b.a.a().m());
   }

   public void cS() {
      this.a(com.yiyiaddon.k.b.a.a().n());
   }

   public void cT() {
      this.a(com.yiyiaddon.k.b.a.a().o());
   }

   public void cU() {
      this.a(com.yiyiaddon.i.f.a.e());
   }

   private void a(Path var1) {
      try {
         Files.createDirectories(var1);
      } catch (Exception var3) {
      }

      Thread var2 = new Thread(
         () -> {
            try {
               if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Action.OPEN)) {
                  Desktop.getDesktop().open(var1.toFile());
               } else {
                  new ProcessBuilder(
                        (String)com.yiyiaddon.m.b.a<"s3g8mwz7is71cw","dQvfjhH3GUkpDVZh67KKCvSRkqeGJtlN5zx0tPyJDdH/fXwpgjQvDn5qUeGMi+1QldSiAg==",-5177306273497871444,-3964226707224095381,-3535640408546776024,-8049434092205371546>(),
                        var1.toAbsolutePath() + ""
                     )
                     .start();
               }
            } catch (Exception var3x) {
               Minecraft var2x = Minecraft.getInstance();
               if (var2x != null) {
                  var2x.execute(
                     () -> com.yiyiaddon.d.c.a(
                        (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>(),
                        var3x.getMessage() + ""
                     )
                  );
               }
            }
         },
         (String)com.yiyiaddon.m.b.a<"s2cbq449p5eo9u","Akrroh6Q2RHjxa4R2AImb3GPvW8C3dLhxNgf7N1UNM5gKISPQd/U2xlYfy4y2VS+djwUlWgcOa+Hvtmryuc=",-8334156504522530892,5746498074060781369,171137677952108217,-2477514791526777764>()
      );
      var2.setDaemon(true);
      var2.start();
   }

   public int a(Path var1) {
      if (!Files.isDirectory(var1)) {
         return 0;
      }

      try (Stream var2 = Files.list(var1)) {
         return (int)var2.filter(
               var0 -> var0.getFileName()
                  .toString()
                  .endsWith(
                     (String)com.yiyiaddon.m.b.a<"s3lwo9qxj7xs29","7SZO80IPoThCDncya9JEoI/pko+OV5qQJ/bHGndjQN7lauVjhRU=",-5629034743121795947,-3316338625500180893,-6744951756651501275,-1299660284724717403>()
                  )
            )
            .count();
      } catch (Exception var7) {
         return 0;
      }
   }

   public void cV() {
      int var1 = this.a(com.yiyiaddon.k.b.a.a().m());
      this.a(
         (String)com.yiyiaddon.m.b.a<"s3ijm4ak3k9p2z","zcsq8uIpbeVIHQbmVwGbPVujJHgEZQeuwjLE8/hMt6gA2Bu5thGJzwlfhOpMyg==",-1331363269918738403,-3885027647781901093,-1324059866059657896,-173974146644512333>(),
         List.of(
            var1 + "",
            (String)com.yiyiaddon.m.b.a<"sdc0qrr7wy5g","GDaJObqhF5Pq2LzG8bsllMt8gjuuYKav5NPBftUv4/vMQQB+wde+U1GQenauyJNEphfwZHu9GPSGpbypQK7kyQreMlTkYw==",8206231206946667834,6379522031771632759,5592651434427336903,-8555638524208005180>(),
            (String)com.yiyiaddon.m.b.a<"s36x98u5y4u99d","XBWs7AXc5G2JOIPdWxMMF46fALBKdl4VBYQbwwhs/uoVThOcxlThI4fqEds2QMN7wwt0UHJxy4xbHJqGn055xtBWkZM8AiyYXDlcWaxdfyHiwZze2Um8huRc",-529290409538828345,7895731740558215776,-4280661268794261234,5717687183494902897>(),
            (String)com.yiyiaddon.m.b.a<"s2kpkps2xn796e","5VKBVwEoIOn3ICYG/70MOiCghFkMTl5JMW2qORqkYWyze46acrC6+EpEPRHVlw==",5974727568619720107,1789935313382895117,-4549861292369401791,-1579254938562489176>()
         ),
         (String)com.yiyiaddon.m.b.a<"swywihsvzwyeh","90ldo7zlmJbT9kIXjmkouxvYlgVWSXVnEyEs3axf1J55Y+F3S9gDXKHB/6g=",-3649861777023681782,-8595769730812973326,-5867845516936526049,8794151065093024981>(),
         this::db
      );
   }

   public void cW() {
      int var1 = this.a(com.yiyiaddon.k.b.a.a().n());
      this.a(
         (String)com.yiyiaddon.m.b.a<"s2yphv2q9fmo41","aBSU89cbGfz3GXKVJ5p1dOvI47CoMb69ihzTF1RmsNOJy2UYraae4lG+b+Pl3w==",2216490946172491010,5863362267959156316,7969064387494697621,2143751695406104566>(),
         List.of(
            var1 + "",
            (String)com.yiyiaddon.m.b.a<"s1wrm9ud59ninh","4a0OWKgJ8/BAECoUSeNpkO1qIyokRqhwcmxuApYZQh9MstVTrJ5U3z8megc69KT1c3eFYwlBilb7k+3tTJ5+iuPnEe4exrpHnuB9mw==",208229620230808449,4433044929200290356,-938962885358069191,-7878569952325116281>(),
            (String)com.yiyiaddon.m.b.a<"s329a4k8anv3jq","v1LWlb2XVxdmhdxkpPZOre1VTDxJEuuqs1DPa7C1WuRJ8mnuSJPBHvU12c72jBBQHYPdRIlF0uYSGUkTh+Ld4blYVupaQbjt5X8n70fRQ+J9IS48uoRvjkWK",-8797651225804028797,7100108397429186623,3493684053392670656,1841577313111614902>(),
            (String)com.yiyiaddon.m.b.a<"s2kpkps2xn796e","5VKBVwEoIOn3ICYG/70MOiCghFkMTl5JMW2qORqkYWyze46acrC6+EpEPRHVlw==",5974727568619720107,1789935313382895117,-4549861292369401791,-1579254938562489176>()
         ),
         (String)com.yiyiaddon.m.b.a<"swywihsvzwyeh","90ldo7zlmJbT9kIXjmkouxvYlgVWSXVnEyEs3axf1J55Y+F3S9gDXKHB/6g=",-3649861777023681782,-8595769730812973326,-5867845516936526049,8794151065093024981>(),
         this::dc
      );
   }

   public void cX() {
      int var1 = this.a(com.yiyiaddon.k.b.a.a().o());
      this.a(
         (String)com.yiyiaddon.m.b.a<"s2aeehzon61mjm","NiyzfE8E/OcOLvpdXMOEqTrT5xuEt6MaVcFVX026X9h1tt9ACafd+IIXGAY=",-6644334389863169013,-9068126019087178816,-3298269165627101856,388081995152473438>(),
         List.of(
            var1 + "",
            (String)com.yiyiaddon.m.b.a<"s25oph0o03nz0f","bVaLEwfDrI7bKrbD/nktx92oSlV/qPsLq9hLprLI3iDf+MKorwmi9oJBqAZRKMNkHk/uyNlkQUL4E5VO1ocUCAUfe7pTgd1T",-5295212678723361964,-7548619208468459435,-3735563402740476064,3920544091054088594>(),
            (String)com.yiyiaddon.m.b.a<"s1qx0yepcb8ujz","7Ijb5NQmj+6l+4JLMOP4RZwxfId4+c8nc36PmF0NJrP6DaqUQ64B5c6NM2sl/FvQ+hWzMkxRwR97BHBRIptG+m2pYVG1FZCM0vZgtwtoMt7+HHlevNk=",3630968371351210696,-8588194853728483216,-7698565127829086127,3758360345856753050>(),
            (String)com.yiyiaddon.m.b.a<"s2kpkps2xn796e","5VKBVwEoIOn3ICYG/70MOiCghFkMTl5JMW2qORqkYWyze46acrC6+EpEPRHVlw==",5974727568619720107,1789935313382895117,-4549861292369401791,-1579254938562489176>()
         ),
         (String)com.yiyiaddon.m.b.a<"swywihsvzwyeh","90ldo7zlmJbT9kIXjmkouxvYlgVWSXVnEyEs3axf1J55Y+F3S9gDXKHB/6g=",-3649861777023681782,-8595769730812973326,-5867845516936526049,8794151065093024981>(),
         this::dd
      );
   }

   public void cY() {
      int var1 = this.a(com.yiyiaddon.k.b.a.a().q());
      this.a(
         (String)com.yiyiaddon.m.b.a<"s3xcgs009v4lv","oqp0AKA1n+FdfJG6vHk2ApIqKpBhsqeIsSxlBTdoC9fYJbU1gvNXFMsl3D4=",3021427360960434037,8265347274169766461,7791956712939166604,-6429933630894461932>(),
         List.of(
            var1 + "",
            (String)com.yiyiaddon.m.b.a<"s2ckcntivcruls","Pjj3jr3i2+/cpzS+gwE8nI2QHv3pKQcpDHuzJkGD+V+vzCN7sNoPPp8nI/pv7VAEmKc5ERltV8POwFEtp+uTsr40UU2e/AiouOMxzTbWNxJjzcWZimGO+21n",5919524542957825440,8623617873113351245,-6443839224873819809,-301449374959556687>(),
            (String)com.yiyiaddon.m.b.a<"s2ml48surzth23","sC4vJxVyG2NeDC0Yu2tSDqSyId0Ibc6W44ibaHPu6fSuhmTOsXATWNyfoK9/d1yr0wI6WIAWv0Vgrq3lZ4jWZIeSOnc=",4620571286479794438,8823256511582190973,-6312129307188151101,1065075840222908958>(),
            (String)com.yiyiaddon.m.b.a<"s2kpkps2xn796e","5VKBVwEoIOn3ICYG/70MOiCghFkMTl5JMW2qORqkYWyze46acrC6+EpEPRHVlw==",5974727568619720107,1789935313382895117,-4549861292369401791,-1579254938562489176>()
         ),
         (String)com.yiyiaddon.m.b.a<"swywihsvzwyeh","90ldo7zlmJbT9kIXjmkouxvYlgVWSXVnEyEs3axf1J55Y+F3S9gDXKHB/6g=",-3649861777023681782,-8595769730812973326,-5867845516936526049,8794151065093024981>(),
         this::de
      );
   }

   public void cZ() {
      com.yiyiaddon.k.b.a var1 = com.yiyiaddon.k.b.a.a();
      int var2 = this.a(var1.o());
      int var3 = this.a(var1.q());
      this.a(
         (String)com.yiyiaddon.m.b.a<"s3697d1f330rfs","YYNYYt8DXvTe3+jTjSs7XKdRWJG3ibFnzpdWE5ygUhUUCiB6Lkev6tEvEJE=",3722978589944949899,-5881618338030986894,-1167209295751434127,6547353172250665495>(),
         List.of(
            "" + var2 + var3,
            (String)com.yiyiaddon.m.b.a<"s2flyrr7mlst36","nxsQ3pk3VVg9WQ59Tc63qSALq+SWv3E48/7+TTaYd2+dVJzjBTrruA3vNxS1uXPbJyLp3WNLkDzXg0mLdLnIS7Eh30/mODUODpApsY+iQ0F076w0Fkf6zumZLuFAzaOcYZveQrWRowlm+7t1JUDfbts6+fv59tzRUgI3MsZG6FfHmg==",-3573644480202305900,400103549440088976,6127779894489968266,5852034643322455335>(),
            (String)com.yiyiaddon.m.b.a<"s11dju26j5pflb","z0uJcBAksL4kVj2LF4ueFgOuNkwN00UZyVAYJIvmiQoD+bGpVNSfhkjVTQ3hsU4XVf5Hhfkk+wCEuCWHAMVSSjKhLzrrS3GlT/cQF2U4Kb4jI4dswraYFI6ofKmUsnC7",6241590126748377730,-7850914664660274373,5395233717610985473,710491291148390608>(),
            (String)com.yiyiaddon.m.b.a<"s2kpkps2xn796e","5VKBVwEoIOn3ICYG/70MOiCghFkMTl5JMW2qORqkYWyze46acrC6+EpEPRHVlw==",5974727568619720107,1789935313382895117,-4549861292369401791,-1579254938562489176>()
         ),
         (String)com.yiyiaddon.m.b.a<"swywihsvzwyeh","90ldo7zlmJbT9kIXjmkouxvYlgVWSXVnEyEs3axf1J55Y+F3S9gDXKHB/6g=",-3649861777023681782,-8595769730812973326,-5867845516936526049,8794151065093024981>(),
         this::df
      );
   }

   public void da() {
      com.yiyiaddon.k.b.a var1 = com.yiyiaddon.k.b.a.a();
      int var2 = this.a(var1.m());
      int var3 = this.a(var1.n());
      int var4 = this.a(var1.o());
      int var5 = this.a(var1.q());
      this.a(
         (String)com.yiyiaddon.m.b.a<"s29qzpfn0pwyt7","lDQczoJWG/2zsQbbOskpqsn5vAeM4bWEGADMKtq+i2RPMHHR0kAyQxqIPS/k9HRw",4575736178872380932,5350958409556893553,8868316873007207055,-8355846345798437949>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s3xgwjej256rs","fNi42KYSdcKxXW03RiATWahgBCYeoZGvkP0X01fx0RoSAT+aiARbUFbgpQfW3dNpv9scMwN/8E/dtx0n+ryqwTcXLutTKg==",-3872659330594710509,-5326203829268106056,-1871181480916209815,8989282254703023772>(),
            "" + var2 + var3 + var4 + var5,
            (String)com.yiyiaddon.m.b.a<"s2t6bl2z1v058i","nKIcSr9u2wKVMiqTloG5j26dXkDqNVVDCX+EXofy1oSzbgbZsRom2MVsCwRfO++Os9KWmV8yEVNKT4jkj2hcJ+2R5SPyI/i4zXZfY/dSqp/+V+rkku9WQgWuBw44wviIbOrpz35TGQgslvFOrlWA4iKUIU0byApHO69p64AwgtWMHTe/qzFLPXrsmryu2QXUuGxPemliPC0=",9177896809150275256,9125432706038544823,970801287832739113,6903361127167622419>(),
            (String)com.yiyiaddon.m.b.a<"s312gqd3eim69g","Xogja6/puC1MVW/WLs9eFL4cZA0RnmW3t/V9UvDRqz5MvPghhYEIPLiwlZcqa1+XZPp1W+UcQ7z6DKOyclExGdL7N1QRHKlBnKr1vB3q3MrkiYrdwmuVl4Q7Dr5lWLN8ODFbyYHg+i8AUoAb/H82irtZHAILI4FPPwCxBqhf6y0=",-2469501370094004458,5148411609183261744,4732871917129768093,-1542219163351016402>(),
            (String)com.yiyiaddon.m.b.a<"s2kpkps2xn796e","5VKBVwEoIOn3ICYG/70MOiCghFkMTl5JMW2qORqkYWyze46acrC6+EpEPRHVlw==",5974727568619720107,1789935313382895117,-4549861292369401791,-1579254938562489176>()
         ),
         (String)com.yiyiaddon.m.b.a<"s8yx6uv0vfy6x","N3QkhnZHTdQPniLPfD7Av46rVPzmzT9WO95ItSH4xLQWLGuUiWqbBFMELevEwaw7",6980568573893931748,-6862363378460288218,-7843307448465136333,8014972024668344937>(),
         this::dg
      );
   }

   private void a(String var1, List<String> var2, String var3, Runnable var4) {
      Minecraft var5 = Minecraft.getInstance();
      if (var5 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bhcvohdyhjr7","vAT5RewSHrh07PJbS4gZsWy4Hu57L8qceqVAPrBWliQ=",7299909270838776094,-5076136989188185215,-8674142278997540506,-2958662617127894009>()) {
            case -550151676:
               return;
            default:
               throw null;
         }
      } else {
         Screen var6 = var5.screen;
         var5.execute(() -> var5.setScreen(new com.yiyiaddon.l.h.c(var1, var2, var3, var4, var6)));
      }
   }

   public String[] c() {
      return com.yiyiaddon.l.h.d.a(
         new com.yiyiaddon.l.h.d.a(
            (String)com.yiyiaddon.m.b.a<"s2r3c3h5gezepx","FdY/cuvAPgLE9vmTMGplbKFMnH3ltiIXOvoLGTbo9uHKT4Vh",-7676675565302476465,-275365129323991929,-2710551497751167006,1905140888862388022>(),
            (String)com.yiyiaddon.m.b.a<"s1077z9l0ces80","fjkiyHkqDeAo82xa4nGhm/E4H4VuWC/olu+bKfCuJmiUJ2B9pBx6/CaTvKQpdaIuwR5ce+42NDojdzIF+XRmbpQS/XQYmAXt//SaC133TRiqbFXNQ8Tc7BIN+lA+GEOCDjWsDbZm4NwpEQ==",89109701592409847,-6716166171147058568,7857949396675818681,-2264263942262634301>(),
            (String)com.yiyiaddon.m.b.a<"s1enxkpls4l1be","56VFddyGK6GWx6geVnuvplpv9tFb8V6IvYROs1Grd5c8OJp1J97VYR4L+jH0ckSL8jZ04koxhmKVuAHH/W5mzp/6xRqjgMnCEVYCXN2Dn7xE24URk/qxSpF+Zns=",-5352179665897635123,4107174873188069297,-8206444930300617868,3613069127926529809>(),
            (String)com.yiyiaddon.m.b.a<"sv02ks89pnhj1","39b3TM9Kmf+uzkM21SdW9OmBb5/F+rL9VG6OWw+u/f5DPZhw3gG5X8jJrRU1VMAETkZuRywZ+MXJlA/n88PjTfPqlwOC04Tfmv2S9e0JedfGC9h07l9lnc6V4a16xlyr1b5buUO2",-2442085412271996421,5131963318026682101,-9020976339745909437,2325900810350764167>(),
            (String)com.yiyiaddon.m.b.a<"s3fugkbkjnmqya","S08Wr5/1KEZsR2NDCoNmeYUf4jixBgEl+/DEgNX5J7VTGetyOk2ovvi9LSj0SE4KV7i0jydB2UZnvDOpbkAGGN+CsFe4txqjGmCWkGakqfx8hk15wNwxRnP9SnA=",-2885663434788787801,-4136139764078695124,8458351537637390785,-1724303399850412439>(),
            (String)com.yiyiaddon.m.b.a<"s134c5pphylkbq","akuVVA/x4ZCDKQUdBZi7ws+E9YoK4WKmW4LhLlt+hZJuh9ShsYjLnOO6/agupcpuI8f8U1NdugDyjz1HQsiUF2VvHE7USjac4b6HLMKwTaraecwWlqEFtBjKLULkbSGTQkwNJGuhjQ0q7Vr3",6344636788222443531,-1851506159990940173,-8922793239995442877,2521830515350964937>(),
            (String)com.yiyiaddon.m.b.a<"s27j4p1az15xya","jH9cwBpgODrN5m/LaN/JTZT0JkA525IIVzhqMKLCZuYELuqkVkuKI+j5Y7vxMYzASgfMgy3z13NqWP32kAToODgYgYEcovd/JbB9UrN8M2yatvVk",-2179428605410205205,-452779364541403025,-1435425981585816737,-3303700479485152828>(),
            (String)com.yiyiaddon.m.b.a<"s1v0q7qpsxh5g9","NoDz2Ap0RqPR+6g9IrHNbz4vu/i+rAY06Ccb6QL5BzAQJtxHjhC4vcUg6AmVJX4cROjhmN8aCnK+FlQKdomKuCeqMykr60bgrXT1nbliRKVPWhtoumeCnCypzATEnw==",-4765719833419248237,-2363814944629598398,-6509079156078105027,6100875701978834134>()
         ),
         new com.yiyiaddon.l.h.d.a(
            (String)com.yiyiaddon.m.b.a<"s35eas8a2aqiqd","KWbfTcNqhqE0/+J3bT8q24tZ5O8VzuTfr//QxAGRU6eNMw==",-7587310280749464331,6569633317690349800,2487002090435110777,-8713156443587672316>(),
            (String)com.yiyiaddon.m.b.a<"s1hskoyh4xjg4m","SD5QSiKk96DO8FidgVNN+bnQul8PPprYYe6447VJ4V9OcV9Gg+qdZXav/85O0FRVfBsPLKHxXu6hq8MKepTTpQjVDkYWcG59zUmlmXqWPnNzihWjIZh6aBmLkiFgME7Gf1IbCsh73iakxs0RrUPLMg==",-6723528321078205639,4617011986415828860,407233389613642939,6549048146894704825>(),
            (String)com.yiyiaddon.m.b.a<"s10xdc62no8c4m","adwgRqYyD35w+Gw6Ej3tm+CubnykHX5YaxuxnOcG/4x/llsfRlGDOdxz8HoiYb6ESidUhZu5CFg0BGdB13bfMFB1F4L2F/vGA97nzJ9QcnsFy+bvBK0EHR7RzeKfMvTf",-7630468057860395436,-1588600255892680721,-3189298604018652251,-7112986209791675889>()
         ),
         new com.yiyiaddon.l.h.d.a(
            (String)com.yiyiaddon.m.b.a<"szrzcvd7dv6ul","KS+Uw5pc0wMvNf3yHNviiRRsuiwru7wRHCVvVCvYVqzqEo5k",7069893437326510940,-3669966151591159912,-7131596271757564249,-855190898019680689>(),
            (String)com.yiyiaddon.m.b.a<"sy2j24qzk7jbw","Xfjk7X9AncuyCxk1PatEcSVgOGPzy/A0+ZX3FJE75SRWEXWVxEZ36UBVdjgY+BC+905xl26icRgTGrAWm/6emearzI0FJzjE3LBw1N3TVZxLAA==",-7873291609359886944,-8261904217496533851,-5468941528447357321,-4615743912074139516>(),
            (String)com.yiyiaddon.m.b.a<"s3uwu7ak25xl73","qMSyV5u4dsxGcxu7qpb0SK68VWgjbDEup4mvpz/eKk8PMShrwFSBhx2X7cMfKsGuWbU0QywdSaJ9k0f1+LHqR+Ks6aJ4rzO4/+DUOIN1YswsNyC32N+cMxoBFqoyYjHOxb/q4Coq",7793248714301005881,-2086202938860833556,-6467887844304623093,-7530103136073003657>(),
            (String)com.yiyiaddon.m.b.a<"s25zve2p38w1vq","DM5P4QGaedZ0ctPGm8AGjGN33eXR3Il91rfI2hY+gM/9HvJoGsRVWat9krvC3nIbVNeVi9KOBAcYFGhU7bacH9mce/6MrE3boMiHiJGePszYDRxBUDnl3qufYdypLlpXkAe+4uDL9nOoLspL",-3748530910363297692,-1984528021165312892,2844703552842134118,-8336671488636498122>(),
            (String)com.yiyiaddon.m.b.a<"s9qlvylj3bggn","37sLCx7dQz2m4IIKbyKfshkWk7hPIKj3TeEbc3mqOolbX/WUwGGdaxtMIXvKRTCSkfjawTjpVSfMrcEmt/6GMYToyCH9MA9F1NnRI3CMWqbelFewoyOMzYUpwwMOgvLKaN63+zJq",9009724271900973297,-8111941107603312402,-8546435150382976105,-4643432755381981220>(),
            (String)com.yiyiaddon.m.b.a<"s9x3lsfvexa9d","SSS/6jp2mHY230gjZYm4asB+xcRv+GmTduq1VZHJyWJ0gzmO6FqRORVOupPa8XbnFyNzlmNqCJXPTGciC9vLSPQEVV7cprnlfVG7gh9oTSHBET+rd5NeUxF7i/Gw2wFgeftWcRbPZxKoJa569wJnuf95/k8bNz9A",754353479507601061,1161639139433148621,6816629545480868544,3034568484817876324>(),
            (String)com.yiyiaddon.m.b.a<"s16i3dvd9ywbo4","cDX5HnbHTmbxGgBAnJBIAtzrMHKMkNU28cio27n/7/q8CjbAp1gXzQkTyOenPY4PPNk0xT9/fisaAVMmcAmpOTrHQMiZ+SBqvyGZJ3ugCdYOdna8M7A9E8D/",2637153282714430357,-5374087901008215724,1593919926932130867,6915347095076506105>(),
            (String)com.yiyiaddon.m.b.a<"s32tefdvslrz98","AhyZzpRzg/5FIJ3onOTtVGhiutMHNyjdtqqj3m2BZjjmXjtJzbLIRQl/6H3oN9/Y1Q6L+k+cDTJUkug75QLBnpfNtKbQdkLH3vXNPbx3zIML9+5erZmo2ynbbBwrpIv+jhhOD203W/GTAuYX9B3XZCUYj306h7wvht5mjqjOPXDXOdNyRtF6/X54379qavHt6ZK5+2Pg",3035230536918761243,-8838530470485841346,-3187634432294549681,7398548428776824491>()
         ),
         new com.yiyiaddon.l.h.d.a(
            (String)com.yiyiaddon.m.b.a<"s1sr9lxcp944pl","v3jJByV16k6CaCfjkLWAtgZsDxEh2Pm1VXqH8SsE3XeM2PLI",6327166424309297371,5324471006280281420,-7700716242388986199,3132921413187423660>(),
            (String)com.yiyiaddon.m.b.a<"s2s6sd8sdp02bb","AORT6lOuoEdGOZxrGSp8GFYvDa0KWQSRs7p7OUPDSPv8Kp+rjmMrx8rYCBQWqvBjrOOjYi/NsBDD/k4d8wtt5G/Y2FORB6OKCrDwXzcUpb5YuJpgntU2/FEGf1fnTCJvTMk=",-8625118900568641286,4759734600710881199,3481959706994471266,8378671601096486363>(),
            (String)com.yiyiaddon.m.b.a<"smldm8xuqkad1","Fa8tP20P9izZs98nziPKBxvRlS9syDGPYcChpKhVfTFIvfwzdn8SP4f+8/wxscTKjP7LVmSF9KTqA78KXEpdERmU85QAb285TycUN+izPhadRRAOwxdvHqd4eV8ErUZPHxDaOre4UfqQew==",1643067417073160182,3456622447577090491,-2757691704933142748,3081119295150540737>()
         )
      );
   }

   public void db() {
      boolean var1 = com.yiyiaddon.k.b.a.a().fC();
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1umsffkhnchlq","1YEAkz0eSvDWoGW2QHe2fpapsFNdYgjsyd9Ww8p+0IA=",-1349756047695618209,-6050593211879451081,-5678619164442894006,2953995577123269934>()) {
            case -1392412018:
               var10001 = (String)com.yiyiaddon.m.b.a<"s3hv51i869np1o","nZvNFjO1wg6OXvnzlCKUT8gOUYyvpsOQuvTNddM0FBEtLNtEFstz9koveJPsZzCZCYILSYNjmMInJFvyXAAa8By1I0zoEHJ/XMRN4Q==",8485130565626214083,7212426360330193446,-4227476741467931577,-3498835715699750780>();
               switch ((int)com.yiyiaddon.m.b.a<"srgxswqgptjvv","hSE9aGCtsK9rH/gvOb3xq0eRdviGhIqIKcAftDkKscg=",8068223687839393943,4473930848755823710,5639116372628987796,-2406739988009690819>()) {
                  case 1042809310:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"sqpdtdootqqtc","rq6I9459rJ/oasnt1hcELZ3dlmUlBLR1cU51b9gyyVYox9sKkRq8t0hdiYF0oMqPqVsDKD+hwbdUZExqPzOPt3JDWJLn3YvbwEKn9xV9yWBfDveQGFDAiD4q",-6684030670986651192,-8495192108952154326,-2974248515717480787,3389109887763218202>();
         switch ((int)com.yiyiaddon.m.b.a<"sxjc9ymao5e02","M+rd6TzTqVP8txfPUpV9u08FBgxd46gdif9V10Il0Is=",-7979818721328716531,-3051547240934840492,201383605737210463,-2272345198366920924>()) {
            case 264594704:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void dc() {
      boolean var1 = com.yiyiaddon.k.b.a.a().fD();
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2cv8i3xrsnhvi","v2thwA+FkfXkcZ7ECYLWcoVdwiv92fCu/bncTjjJHYs=",-1971913436869561845,138092091342024900,-1331469542909670367,-8905878622614088650>()) {
            case -2087131650:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1uc6ut1f08sr9","NazADBXoac/7C39rcaDXy/ypH5hxwVGpUl1blm9vbRFsiMcvt4bxWrCtIZ9GipNaQ+UaRIByLGMczndmFkVa3xzUeQjkyEgfwN3NIxMh6Ujc0Q==",-1155502456025043025,-7939470650009605072,7466805492905292219,-3963492255596785815>();
               switch ((int)com.yiyiaddon.m.b.a<"s2lz2xpyjkkadi","2XdbijbZw8UqvJqTQtEQPNbXUoIRd2scZCm1uN7W6xo=",7089922830167591702,-2779184930028161458,-3251456105365956428,-1068568551275541860>()) {
                  case -2118764954:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"stgjmn56wk5b4","rxq3zGSHBLhX7qKtekSdFrOPS53/tVSj6HpDjesXILETTgQEV78FIdN/XlSXes4RUMtgjCVFc2o4I52uoSq1+TIhd9qucsXcKXYjHqa5lPAfHBS018jFoHV4",-2648191561071327112,-4030624892153409391,3624942938850253753,-877920710641117932>();
         switch ((int)com.yiyiaddon.m.b.a<"smq7jnglnqr9y","7eKCzLSOinmY1v7swBait5ga4y6xqAzJENfiDW7GeJc=",842506276293609688,-6911189121628942892,2231201168358986084,5474995103224057937>()) {
            case -1867651365:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void dd() {
      boolean var1 = com.yiyiaddon.k.b.a.a().fE();
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1ydpd23zp8rje","l6nkPPNFCvgh+5eb01KcKJ51OvLlskpZqVKJE8BFXc8=",-6762666336846803370,-4735220817344873074,-5833027798521513220,-1557404320706576698>()) {
            case -1308222031:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1ouafq1bi91j7","H61iXibYJ0zK8zGxYXD+iVytLB13AfzJ44CxbgNYEXzMdYC+AH7S91hWHSO70zb8oS5n+7KweWXym7eqISkw7Hu5+5V3qaNGl3n13Q==",-723828492200062492,-5852474123697818549,-2551862343994927895,3068437242559025267>();
               switch ((int)com.yiyiaddon.m.b.a<"sx98mxobykuw4","h5qflDju1b1sxOaygg1GTAfjxHayOxK7eSQAu84nb4U=",-350566651953838459,5341092792875410433,-2067964731016232378,-6399346140172479328>()) {
                  case -1409113918:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s1nniblif2tan1","uebSJWjk/gEuVhgfgN4jN/E/26s5Duh8fnHWG7dJcxYzP98J4sEUyu2R52JvXhaig2AZXMBaLV9Vlr+GDXk9bO/aEsbcUjEBAhTyTBIR6rhLHOenakL86FSjrTZHm0o1CDOpNDo/8pmo4Oy3",-7200074516210424453,-5868749222483870674,-6028050218426027481,-2558534914175472662>();
         switch ((int)com.yiyiaddon.m.b.a<"s2tmtczbd55fo7","VgH3PVSO7hYPLjamffu782a9oF92+a7BCfFmV0eLn3A=",-5595810097714506938,5077297788863700090,4404200412301961820,6244218063386599319>()) {
            case -1030233651:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void de() {
      boolean var1 = com.yiyiaddon.k.b.a.a().fG();
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var1) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"sahjggtgmreqc","/cNgljm/WaAI1aXxSQrYmg8ib4h53Fnf78U/S0aeQtg=",-3813400364426107675,-3032387649234354376,4871404127784725645,-5354427511252206477>()) {
            case 1143433648:
               var10001 = (String)com.yiyiaddon.m.b.a<"s12k54hdza3949","Owv9HsAHtShTeTZtJnlg+cAH+qbBB1L2Ne+rpd1B5G95s32HH0Pw8FWMUKfsT0VVEMNrFbg5slk74dzThI+msXJ53OUh2MaX6O1pAIx7K7zME1OYVUdoZ4hwImC6wQ==",1765600692413037887,4127766482626149411,5109228359024687103,4123399708356098678>();
               switch ((int)com.yiyiaddon.m.b.a<"s3qimehp8c40y4","jpdHLYlw6Ytpr6KJpY6JZ6AU0D8mvn9PUgcLLCqIM2w=",-1079896785384412815,1285806949700168144,4432646079776178102,-4901152269883155633>()) {
                  case -2144533791:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s2xcdq7g3c3mtw","n3b/qnFxV8XYFLoL4VECJBJu16vz3f8s30W+59foos3C1TKirKhHZaF4UYpNxSFdwt5Q4UFNDKLv3UwdhQ9InCdQ7vCVlGJr9pKreciy2zZZX9iC0y0xF+eejBQOpyzwNEfv8XHxaKOXW7YBJYOA0FJ+XkT+iTBDElZrvh9b",9032101529759803921,153170033065645541,-654600532536422016,-7354161884193003019>();
         switch ((int)com.yiyiaddon.m.b.a<"sk9v03un0gto2","ln29R7w11sl6Is5+8EuyYhOpAIenKABXxR4ijQ9zoOs=",-4891975385327174390,-4809819068146454018,-5286041292038290370,-5308230813520665071>()) {
            case -738865911:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void df() {
      String var10000;
      String var10001;
      label27: {
         com.yiyiaddon.k.b.a var1 = com.yiyiaddon.k.b.a.a();
         boolean var2 = var1.fE();
         boolean var3 = var1.fG();
         var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s2jv9l68lvakph","opkG8EDbWf7mLZPhs/5IAa5GczGQ6BObv9Ztwpz6rF8=",1676096800196488450,9086473044297118971,-3240007863727883057,-3125344042675193144>()) {
               case -906678330:
                  if (var3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1gt7zsc9v2a5b","NmZAw28nRglVRAyJprlPMlY/ZfUFxNZ+Yn9dcIEv2LM=",-4146430041676074920,5820490085199774108,2713866059190768822,3560796289487481677>()) {
                        case 575352219:
                           var10001 = (String)com.yiyiaddon.m.b.a<"s1zhmzfb3b6s57","H8BqWjDiLAjAY2+OktuDjfD/fRZATNqKacHcRkgOeWgYdeCAcUAKEAa4QWdKf4DAD/1FoxyyuChCIidf3wglLQYel80NY1RLjRi8TYirYK5Rb4jX0NaVtjjrGRIMdteto9lfc/r8ijZvpwboSOHoYRd8",8771535477411395505,-7001261753961229522,8021369569852519629,3184232335480746256>();
                           switch ((int)com.yiyiaddon.m.b.a<"s31cw2lc0imaj","XE+DQyF/2Dwy08PJsSo/e8x3c0yQEc4M9z2ecZWxWpA=",2782622307295465760,-527211226048547457,-2324447792558394499,-9199510516501072704>()) {
                              case -1259341636:
                                 break label27;
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

         var10001 = (String)com.yiyiaddon.m.b.a<"s2g0lynp0r4c2t","pOiVM+oUg7kFVzFtt5Vhbo5bEEUlOgXqtri+iVDN0AqhX1Z8i1dsi/vvxE+rjxtOm8eL89jcqHHbgE4wJ9IqzNWTj1gCWItt46g=",-169981640902669512,2929038151637607882,7882775153184020061,-2951058155943527703>();
         switch ((int)com.yiyiaddon.m.b.a<"ss1szvl5fo03e","+3c7/rq7GoXh8L87J/2vLXT+nZ6X0E1JcBxSOOJsmTM=",3951842646244668060,2923375662360920167,5167492218123407335,-1870771383411372781>()) {
            case 1519279510:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void dg() {
      String var10000;
      String var10001;
      label41: {
         com.yiyiaddon.k.b.a var1 = com.yiyiaddon.k.b.a.a();
         boolean var2 = var1.fC();
         boolean var3 = var1.fD();
         boolean var4 = var1.fE();
         boolean var5 = var1.fG();
         var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s1fkmd8frc1l6h","3MffMeOmHd42+Ro6MdCzwI8+DMDA1uLS4q2oW9nK6gQ=",7201341335675755582,-6315811700068983214,-2904518732016507850,2656795608892757913>()) {
               case 795603678:
                  if (var3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3kkwwddaduozp","khwxn7/p6LPWGP957O4x/yJfKk5ACIJwpGLpON4lTUY=",1277981200894766700,-6095986536331151912,-2377850451688977689,-420057562031537129>()) {
                        case -1110808480:
                           if (var4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s7zqqjdzm1j13","L+6uj9glG+F743lhdMm+BFDSP2peQra+JbRuGEWE1CI=",-2764110246012637686,-3763640276677894576,7796615427553005749,2140074949088089381>()) {
                                 case -1686200662:
                                    if (var5) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3cerh5f26xe7j","ymR+0NmHgRVjZozqn4NbwlH4zkgWOsx+21vmvvCGOmw=",4004656436752712640,2316676347845759199,-4111816297737442197,-1511555499609097074>()) {
                                          case -174729243:
                                             var10001 = (String)com.yiyiaddon.m.b.a<"sye0lb40ela75","KKAC9halPdD8Wp1sdH6MArwzVafgSt9LuNfSh/TaLxiSIh6Sv7oeOGz2P65bqbEg0nA2Q2T0GcCkVL+bWsBAq3fnPSgz6/U62uNXaMJeElgO13UE9/ykPimeFrWLN65NhTbKRhJtWiTerX4dKCB29Q==",8221218607752957896,5369334321382747458,-5842569071854312904,-7092815501352780814>();
                                             switch ((int)com.yiyiaddon.m.b.a<"s3fre4rsm22fqb","CTOiPj0qCQjOEthJzd/6KbADIHdWDraK7VTi9EK+YYQ=",2593520026849821922,757438774283218809,7608406384915738397,1456677980902843619>()) {
                                                case -1301830343:
                                                   break label41;
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

         var10001 = (String)com.yiyiaddon.m.b.a<"s260e3775jl22a","8v68XVIvZ+j+DBPkUI9c+wiDd8AyKWjg9s6nqFxr3uHHEtpFwZh39jeXXnRxJ7s1ftZ8lXlvqmVz6U1b3V7GsJWFrN2o8U/1iCRX62f7EYn1PnQf416lcfqx",185331795742308732,-5713670213611699097,5551843549385051604,-8306674459266294646>();
         switch ((int)com.yiyiaddon.m.b.a<"s2694hyldsd9r6","peLFONf+RPXUJ9Vi3QpzoZMRfFiY8z3jG7tzvuPQDHU=",7322887177057530418,4647986231075746250,4138015149376079743,-4215859409148338301>()) {
            case -1574503583:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }

   public void dh() {
      int var1 = com.yiyiaddon.e.h.d.a.aL();
      String var10000 = (String)com.yiyiaddon.m.b.a<"s5er1g6j5tuxa","kLLcRqg3HjyQ6Jl7XaLsvY9ykyELA0/ncHuSqDUKyfGWAksrtmBMZA==",-6888695327103262376,7097700423463641199,-5004318452495500795,8257160734318531437>();
      String var10001;
      if (var1 == 0) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s29ros9x0zv6ky","IBNr8pJQ5P0vWjHhI8PdklHu/z0OplknnY7F2aLEih8=",2566407794345963401,-5614927938196483682,7061436843918280570,3726975554217981576>()) {
            case -1779697498:
               var10001 = (String)com.yiyiaddon.m.b.a<"s2fkcow0m2gmno","Cd2FuWbk98C3PFkbYzSTRiVoFE30345wMnh2GY7scadUVaWd7B3X5hPiiaJQVaRX8/c=",-1568961780185499186,1291696647800283636,-8166988833158162150,-1975824964708582358>();
               switch ((int)com.yiyiaddon.m.b.a<"s1pzuydwa5lwm0","BpFdht8Wxl7L0tKhOXIort5nYArv5w5T4lttsaTL/FM=",671171087547183793,3491212747628331443,1966455779388354855,-7724089792564164019>()) {
                  case -1842412005:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1 + "";
         switch ((int)com.yiyiaddon.m.b.a<"s327yivjlif1z4","oBaZZrla85iOdAD4Qe8d1I/c2tQJ+sKWgWwlGyZEwZI=",-4737256155422123279,323748321738585031,552251590048489275,-8869623754390151359>()) {
            case 145563362:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.c.a(var10000, var10001);
   }
}

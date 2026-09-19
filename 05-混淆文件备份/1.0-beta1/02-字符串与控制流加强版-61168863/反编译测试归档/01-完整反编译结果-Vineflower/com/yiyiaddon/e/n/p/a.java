package com.yiyiaddon.e.n.p;

import com.yiyiaddon.e.n.i.s;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;

public final class a {
   private static final Minecraft ad = Minecraft.getInstance();
   public static final int mX = 40;
   private static final Pattern i = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"sp5k5etbav16s","XiFRZL8LBotexE+HywV5WR6Dq3zt6vQ+Dt5Prr1hRt5v5F4beUtmLWQKZPxRJ+2lvn9SYOGdOE5cF22WDL0NZaCVy+ND2o/hzFOK7U7p+S0ZNF5Y",-4315680309212371600,3879906399416763728,-8720505275482853149,-6963153474467591477>()
   );
   private static final Set<String> Q = Set.of(
      (String)com.yiyiaddon.m.b.a<"s2y3lzg90sq7vg","ZvoFkv4QJ62i0S1D2E/Ee4zDaWhU2s93VO8wntoHMm/1x40ONvapmRX5XS+JaQ==",6388491952028762827,2702615291546376688,6824354219206087464,-2005658570806944939>(),
      (String)com.yiyiaddon.m.b.a<"s3qi3w0kg23k7l","QWbJdLXcTS6Lvv24rAW//i0dbxCNzlmPj9brUr5fmhDI+g1UrxznL0DYqOw=",-3463667605279310539,1561417434489695709,4287941863104111032,8324266553504471749>(),
      (String)com.yiyiaddon.m.b.a<"s1pv7un8elebxz","YUpKzkq38vPDdeD+ra5Wq31uugnkJUpRPeEVAiqf8qRqag871iC6iLCY3PhXCg==",8196927081271944731,-3510545330891309202,-6957286591524222254,-8445977014337061037>(),
      (String)com.yiyiaddon.m.b.a<"s37d1um3m4t4zi","iiMmBC+WhHqNFaQS9fpiJ76onOwtWrglojge3kVJvg6i1tCZHqZv7HIqUUs=",918867751994046894,-2739160890238664006,-8134154596572628730,6863607882407725642>(),
      (String)com.yiyiaddon.m.b.a<"s2lcuwxs8wx0i8","EiDBFr3NyN5mG61whFRu9T9HgnG2va08YIty3Ly8R/pX2yxbFWRuhZgclKM=",2621768791576653424,4892704312099291533,-2274413856298036995,4642385030621366197>(),
      (String)com.yiyiaddon.m.b.a<"s1i4uobhh7inql","3hRvIegzsEwCCllAfY0XFhFYEjDqQfpbIEK+FZUdHI/eVqAcLcyhta2Pdtb/KW/J7snIKRssWvQ=",-7444681409480882155,-7116311049205453304,8601837383454743378,2411243176141681368>(),
      (String)com.yiyiaddon.m.b.a<"s6xl53g7yys7x","aih2B8Z3oG/tY4UEhPbFUlB8ctY1iGuUdEn9FdoQ03Gn+NvixaEEM0SuoKfjYKxKR23goQKF",2387453994793560266,685705859732271073,1869402474589675119,-2157403927645727481>()
   );
   private static final List<String> bT = List.of(
      (String)com.yiyiaddon.m.b.a<"s2o84vs3vpyy4w","SkXRikbjcsxmo8f1/BqXat/oO5VDLpbSbpHZs248WHOcGg==",4162351149908080266,-1254150575914841619,-5215848541398741185,-8981303331299973616>(),
      (String)com.yiyiaddon.m.b.a<"shlp4cs65a7sv","cMdv1Tynev7aF83va2waGz0WLCAs1G8Frg2G4bpJh3eY+bG/zufgcmIo",6497067881987348151,9005284369817968171,-4343375526082804735,-1403458374026326730>(),
      (String)com.yiyiaddon.m.b.a<"spbm2m37tkxmt","tA5OgMPD6zSf0wZ3OpEqUTr3TnFl5A0ajlGqlqd+oULYQlkRWgpp0ZilEwHj4Y68",6617146329301806751,-3785572185948321718,840591558510045782,7673529528455656387>(),
      (String)com.yiyiaddon.m.b.a<"s2bog63lfh280c","KJNTAnY7j2CIty96Qn25JENKDfBaE5tgojKdF0dwEFcuilOG/D7fQZlCu13Kzw==",1432358012832678479,-5912384553798994430,202151061546565176,-7978343149181565626>(),
      (String)com.yiyiaddon.m.b.a<"s151qm2fe4ig56","OfQwmHywHSYHYAjSTgo8KdOuvY+OIByPxQxluIXdYWoBrw==",1056992103997228950,7561714905575274176,-5405364878481700998,-2218237409680945234>()
   );
   private final com.yiyiaddon.k.b.a c;

   public static ItemStack a(int var0) {
      if (ad.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s35ni1kytxee93","TRdzJ+xUaZrEnKqxNfXncEoESKMELgBqWM0Cg6Xed34=",5174963713620378467,4188974193022483115,-4502957602338825702,-8252247772839461240>()) {
            case 158845975:
               if (var0 >= 0) {
                  if (var0 == 40) {
                     switch ((int)com.yiyiaddon.m.b.a<"smqmmm0m0v2fm","LG2zWgG0bPCmVLbMG63oocY3AWOMOPO5vpqCznKGnJo=",-8526052977405791479,-1815674865390263352,-6577376328166917260,5384545677378469232>()) {
                        case -1046597961:
                           ItemStack var10000 = ad.player.getOffhandItem();
                           switch ((int)com.yiyiaddon.m.b.a<"s108gkyrb1z0dh","/yKdjNjKVeh3VH/3OstaiQKBAPKJtrbTL6lorm8hyQg=",-5960587018572695631,1306229932396745868,-8622325038556623900,-2287561206226200752>()) {
                              case -443166354:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     ItemStack var1 = ad.player.getInventory().getItem(var0);
                     switch ((int)com.yiyiaddon.m.b.a<"s2drllw64rjklt","I7VJp+sRZL7ecWrByc/rKpKnRc+nYNPuTTOtqxedg6Q=",-7485571602915490026,-8711754791306635121,-8909058123811403845,3898494231140018769>()) {
                        case 1314052843:
                           return var1;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1nky0yjw2c72n","gdDsFwp3LgG5/USWg6GSF8Ky6ZbkJG4iOJnOfUmbCfU=",-6085715934664842839,6721779681435242852,-4418163113464818610,5152994487758651961>()) {
                     case -478300750:
                        return ItemStack.EMPTY;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   public a(com.yiyiaddon.k.b.a var1) {
      this.c = var1;
   }

   public int c(com.yiyiaddon.g.c.d var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ll58glda1aqo","3kJ0wO2K3r7eST+kPKwLLsE6FoPAMODLthI0kN8XWzE=",6230722503886434377,-693488454784410247,4091361274833272993,7811131622418279839>()) {
            case 1887767271:
               if (ad.player != null) {
                  int var2 = 0;
                  int var3 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s5wb0adg3700p","BgJE4MvlAigIBHceBAZxydqyAB6WBE6f5T+ioTaVLJ4=",-5813100584188452033,-8177077142567772950,-8705449987337229460,1933383911389885246>()) {
                     case 782694512:
                        while (var3 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s8i9ktn2cp9bm","Jnnyp2oRAMxXX4P4ZY8IxncBqVFtAYf4sj2yrKeA4m8=",-5046469213104356295,-3192840948895385948,-91073047482292271,2367923237915464174>()) {
                              case -1812902923:
                                 ItemStack var4 = ad.player.getInventory().getItem(var3);
                                 if (com.yiyiaddon.i.b.d.a(var4, var1)) {
                                    label40:
                                    switch ((int)com.yiyiaddon.m.b.a<"s37biwzu9msg5q","6AloneP24hCNtpyqYVYh8h/EnEKbujQkC61y4BTQVbM=",432171690934015879,5545208118965139104,4208375019882272598,-5334827871988526413>()) {
                                       case 1742590296:
                                          var2 += var4.getCount();
                                          switch ((int)com.yiyiaddon.m.b.a<"s3hlnx1phayzgu","S8yg+ItI9YFBL0gXu1XUdKcGiqYLitsVwxx1mbeDXwM=",1150222691808470771,-8647768477580749493,2739119788141635095,-6440825079763828488>()) {
                                             case 1521239699:
                                                break label40;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ofwpqee9wtrn","/w4BBGVLd3hJHurshgk4SROVAkIuQikT9FIFJLV6vnw=",5109158087614946459,6504431703896896994,7239619234578366575,684790921058664366>()) {
                                    case 2039411624:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ItemStack var5 = ad.player.getOffhandItem();
                        if (com.yiyiaddon.i.b.d.a(var5, var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s21sag0dekf051","CmijLTK+OzlHekVwgqTUaJvQajZHhRw1NjDCnofox8Q=",5922419739566070752,-5147784770868580181,7603395091012499538,8216410239228955383>()) {
                              case 1596766323:
                                 var2 += var5.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"s2hsmfyfjiwk7g","x5fIjPlFZifvtqyDAOk+JWW/XfznaVcv3ozQRmG2Y2g=",-8004964603925542141,-5025641994028845111,-1875187184038352677,-4840681688139109795>()) {
                                    case -1150930630:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2takcxv9nc0nf","OnnHkvlPNC4R47TPg5r1oVNI45suKkEltyGDEVBdz10=",8562794858043862396,-4408125366475148558,-2712891010964800047,-7951111825393575583>()) {
                     case -949755679:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   public int k(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3a0eg4ir6oiru","F2Xhfc3zhYGGaNI+oNEb50st5DbeDE41+V2zRw9LJgo=",-891417277383101057,-2448507775752604210,-339401281058093738,5188838802025262421>()) {
            case -861717981:
               return 0;
            default:
               throw null;
         }
      } else {
         return this.c(this.c.c(var1));
      }
   }

   public int e(List<String> var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2i379rqze415k","S5P5tBmiiWGuAd7z7p3CCLUT+GHuQWcncp323z8vOAg=",5898914515693141832,8756777960329845453,8486751616070253971,-6839901149069347925>()) {
            case -891422482:
               if (!var1.isEmpty()) {
                  int var2 = 0;
                  Iterator var3 = var1.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3c1lvggbmk9oj","xfg0iuETDLTyzRINVwgEZEsrbea6O0mp2klFx06OBWg=",4093736742377344848,5729531652815552441,-8244029399375222731,-8867261438412981776>()) {
                     case -1027748663:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1r9xqo2u7mkyz","VEmAprE47TkU8MDMNWzPmcIbgW0BcLVu6A+8jCPEpuE=",1619614780003183748,-8604544438801117880,6358146213184349161,2886482408962821018>()) {
                              case -1947817595:
                                 String var4 = (String)var3.next();
                                 com.yiyiaddon.g.c.d var5 = this.c.c(var4);
                                 if (var5 != null) {
                                    label30:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ottsboiszdfp","njGJAV98h0h4Og7J5ntLtOVdhylkrGKkx0uiYqYkzV8=",5186616893129294151,-4221941977777711315,1190320810264484162,6973433024149046425>()) {
                                       case 1371929094:
                                          var2 += this.c(var5);
                                          switch ((int)com.yiyiaddon.m.b.a<"s323mpqeh0c47j","ddoQZGvl0PpDSwIkceFzTtpVpgXx2VmvnrFgikLX2SU=",1710521874168831043,-8313549775446809599,-7281880552673356888,3129581228343776820>()) {
                                             case 1334012922:
                                                break label30;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2mvz6b4pvdkrf","exFNvl3xE/s/LhrijtDnMcZHC7QlIDpdSAQlnkmqGtg=",-6205084216124224832,4827316086718177529,344622908767147748,1145265076069647771>()) {
                                    case -844858375:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3mp9zq0pvydbb","lNJbv1ol1C5Vpd2R3BCH+Qu7irYcoiReJrwEw+CLQ14=",-5573148595633466086,2629409336069901883,8526528701070477888,7224323845869178562>()) {
                     case -82729677:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   public int a(com.yiyiaddon.e.n.i.a var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3jx8s7clqik7c","p3hdFsSn3kqsTpTCTVgXKfUX8SnUZCsdE9p4brUPobg=",5247166559754180804,7889859298587347434,3669104680540454135,-5516219687658957717>()) {
            case 429977949:
               if (ad.player != null) {
                  int var2 = 0;
                  int var3 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2s18u21voffsd","Kwa35dJ3gToaUAqgjFOE5sy6UyuYTM+hq9Nd0/8EraA=",-5506391658434045700,-5653165841557708463,4641337080100903928,-3273731750393452660>()) {
                     case -585692016:
                        while (var3 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1tqbtlm1lk0hg","c2qo6XejEUrMjM6C+tXoXssN2P3KRK5qd2Tg6blwm3M=",7910688035887761146,-8377936894429346059,468914892741164126,-2408139047234155925>()) {
                              case 371706377:
                                 if (this.a(ad.player.getInventory().getItem(var3), var1)) {
                                    label40:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2zyq6dfhu5s5m","ea7mt4K7i9DNRJzED9lmgzxiPAU037Xp/gIz1vUNptA=",-3294664427434741442,3987540794019365361,-1300592814195164473,1158652435600568539>()) {
                                       case 193800013:
                                          var2 += ad.player.getInventory().getItem(var3).getCount();
                                          switch ((int)com.yiyiaddon.m.b.a<"s3edilurcz3vhe","LDvAyurnBwjGTS+6nliMEsjphiQjf92UcM1DSFx8K8E=",7067480940694540567,-2625784384495446202,-7531622966389602612,-407569203640233380>()) {
                                             case -529270413:
                                                break label40;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2eetvvto70awr","sApupijC52RUEtpTnvu9aWyzZb56DjiJBbNYjiDeZKM=",4017892522434857570,1642619532825979494,7635009570237204298,-291325436834746906>()) {
                                    case -2128429429:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ItemStack var4 = ad.player.getOffhandItem();
                        if (this.a(var4, var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s27mpjp9vmw5im","wOpb95IF0SAGPqnYQdYlJxnvdJ/7PN6GZbuHK+ZU2GM=",-7923752592243231919,-7932932619080865114,-5532031861774390524,-7729967547478912736>()) {
                              case 943472057:
                                 var2 += var4.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"s2vijzvaw0m7wl","E2nXKa89815ovKMDycw9FSwgZPGDb/I2gIQJLhRgVcM=",-419301506860174961,-4688120737433414392,-981598273215272887,-5846908865126010920>()) {
                                    case -1605266762:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3v4p6xymuwr8j","YWlx244kZrXvGvzmucLQyL5qNnYaph1ODcigZXIIimE=",1327397869662677240,2197373661987964369,-3349597293577277298,8232539511887828753>()) {
                     case -1413696673:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   public ItemStack a(com.yiyiaddon.e.n.i.a var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fz6y6yyl26vv","yY1p0Ar9WmDP5gmvF8P1pZfKaK9F/P6wxXPXlRCcOwI=",-697204457103528296,-143961841075332469,-8831247991949194843,-6899697235527138232>()) {
            case -1316527307:
               if (ad.player != null) {
                  int var2 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s22a0wu5kkq637","0sQLqkizphzf6MwbG4XZdBjeyxGNcHRqnK0qscjWAQM=",505836949124986730,-2787976478401009534,1975185201527193026,5881515247682689737>()) {
                     case -1023821743:
                        while (var2 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2csf49ax6itks","dls0BSLY7YwERucmA9L16e7ZvMYvoNuMjzDDGZCfHOg=",15244927877651754,524745911284969348,5475823566369754139,-4011368121775004313>()) {
                              case 1997396420:
                                 ItemStack var3 = ad.player.getInventory().getItem(var2);
                                 if (this.a(var3, var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2wfk2n71o142s","pg2uQdI7jVZGGhh3RTtpra8ODrxeYDTCeIdk24yLjrA=",3935054935988654100,-2517318107613805956,-180863263505719916,4159591166158493333>()) {
                                       case -256465695:
                                          return var3;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var2++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3oaz0ljfmoq4s","O2xLRUh0cGzgXyFjNblKQEPLehTiLcw7iOA0Xe6QDKk=",-2412178884248696956,-5349771276536870004,6688054285893521235,204883883232273444>()) {
                                    case 687150838:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ItemStack var4 = ad.player.getOffhandItem();
                        if (this.a(var4, var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s16scr8mvmbttp","IeOMFZrhKy8rd0odEL7SAEcMBYaboJfuIJlv2CwseGo=",8522177342199579553,3032736183378667748,-5446668914095033721,7979585651845081733>()) {
                              case 65693701:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2c0wwk019x385","FGDeHn/iqz7nHtD8MB/CxPpGwn/8sc4w9UGIjEUsah4=",-2690395377762542098,-7737344274214390887,-2787799302952836718,4721965182842301162>()) {
                                    case 1728352786:
                                       return var4;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           ItemStack var10000 = ItemStack.EMPTY;
                           switch ((int)com.yiyiaddon.m.b.a<"s3fmfeieiw436v","aJLLYkjccigBRb/nvuBzzPso0U/Rbbk+gxpDFVLs148=",5999552810214462831,2214128179921292687,9107408682475668723,-4384198659972436470>()) {
                              case -188521231:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s9d68ez11fele","8LOiRUOOpkG02c/083wxmJcix0a845goi+CuHmWCejM=",-7271547151514696592,4543386593934028679,-4367315228629445614,8711412264062885276>()) {
                     case -1827392093:
                        return ItemStack.EMPTY;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   public int b(com.yiyiaddon.e.n.i.a var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"shl7m2qkxw5zk","ECtOGJaxaiC86rwG2XQ9QDw3TCX+dRjvGPoi8QTnEZ8=",2047231071434325812,4356770161032547372,-8378609209832091210,-8816657381978381883>()) {
            case -1519903237:
               if (ad.player != null) {
                  int var2 = 0;
                  int var3 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s160ax3am1nnf0","6Gu2C3YUYJmASm7GHdT+W9lFqEGCmRbfZmCxlgiJBPI=",7554386532076995261,-1274844568996018445,-2803440043328899860,5748842241226625779>()) {
                     case -1400361891:
                        while (var3 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3emdcq5vugx29","fVuQiB/+Lly1kFO1WYt2l7DF+ilGvHFK4ehSpRWGB2Q=",-8328710809636176470,-8067278357090414800,6076134377450550929,4777463368713811049>()) {
                              case 1252062117:
                                 if (this.b(ad.player.getInventory().getItem(var3), var1)) {
                                    label40:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3t708ffy7x5tg","IgNapTSAihcw+DXXqCBt5yFJdgtFPvFqK5KSIgJJ9rw=",-353471453306187179,7457054819843131272,1506204264242456252,267050159611343649>()) {
                                       case 1920015228:
                                          var2 += ad.player.getInventory().getItem(var3).getCount();
                                          switch ((int)com.yiyiaddon.m.b.a<"s2wff4vamze0q4","J5+d5CaJZuyMjA14zMbY9sgc/JCS+XLd0anrKTEv9EA=",-4739155333728811318,4232142274811019668,8394738856563212840,1658156487961189479>()) {
                                             case -1030967751:
                                                break label40;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ckhk3tblv4tf","c5lFmHjAKau4fRoV37H9KAgGJ+tv+1zfDhCh9AqGyIM=",690092419528632772,4567560889287843267,-7469833889891793653,31711625179564225>()) {
                                    case -365818703:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ItemStack var4 = ad.player.getOffhandItem();
                        if (this.b(var4, var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1ulc0owzjiq1a","gDYU35U0EmiUsSPSFW4PwLK/mEm2+KdZ6TcexMXkpEI=",-5535107039373118560,-8987924771670114644,2938931128300191154,-4665504282017223387>()) {
                              case 2071032231:
                                 var2 += var4.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"spm0vvi9mm2ik","h/XSdnDyjRpiuGmGu3PefFVqv3b1y2kwqqj3PSq1TlY=",5302575948016766006,130777251848342082,-2754453808218402521,-4693070674848408915>()) {
                                    case -1806953539:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"snmcya7o68ru6","h/COrh8/g3jL6M/l2kHy1FTANXooY/28hpx9bF8JNmk=",-6050004283531125499,5061133631584394480,-4995465570065879650,8271505147330770436>()) {
                     case -1246970402:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   public List<Integer> l(List<String> var1) {
      ArrayList var2 = new ArrayList();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5dta1lrg07dl","7suqZdCLUJK27ET8rfBaCW9RyP+JQMJTd8AsfbrEIMQ=",2652919997850865197,8986742069146870585,-5842370182939681193,-1612962210156150754>()) {
            case 1735677498:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"ssmuaovjienug","Iq2kORlqxFVuBfvc249g9QqthuX64I9vmYpb5koapXw=",3677835427687782186,-8911670681581110019,2671353672054156506,3798514020479135341>()) {
                     case -690441656:
                        if (ad.player != null) {
                           int var3 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s31539r3jrzk90","rEAsU7vBcS2d6DzHxQaaSAkWNf2RomqwXd3XoDKAalA=",-672815954818721721,-2225646618479889579,3710489625866828867,-50651814959081346>()) {
                              case -596803550:
                                 while (var3 < 36) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ul4gkti7ty3s","homUZM2zNYEC4Xn4AgasbZJUPeSK4UBjkCKIXX18L8g=",6536915193924212285,1270776594826883399,9188176558106091545,1282040187605034805>()) {
                                       case -1682871048:
                                          ItemStack var4 = ad.player.getInventory().getItem(var3);
                                          if (var4.isEmpty()) {
                                             label60:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1gqy5uvqm3ydu","rnaR2JA3VvSeEczqgoW9tl9S9RJEe+IwX3PsMihL0XM=",-4774653552346301894,-53855088530694984,-3324275654949633468,5480037789036646844>()) {
                                                case -279770776:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3l3g2bo9jqfgq","HcwglaMUiQcqF/MAWhFcfdx8zV+H85OdHjJ7uHyeSQc=",6716357404625463096,4024714081972323068,8837675378744125388,-5664439986520651470>()) {
                                                      case -1326030509:
                                                         break label60;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             Iterator var5 = var1.iterator();
                                             switch ((int)com.yiyiaddon.m.b.a<"syltmgppy53sv","ADi2dRUFkbUY/SFRlVX73Zv1FJkS5PsTXWnb0jvhmtM=",6440416060547559073,-2747379104332451175,-8645765212097019349,-8639789954435185015>()) {
                                                case 1330532394:
                                                   label83:
                                                   while (var5.hasNext()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2d1wo30zh4rrz","+2KlUfP/y9W3yEdzwKKM6I+o6yD7F/Uscx36V/keh9Y=",7443167585393773191,3234695994589389648,8721968054872030134,3913151761499543461>()) {
                                                         case 895088024:
                                                            String var6 = (String)var5.next();
                                                            com.yiyiaddon.g.c.d var7 = this.c.c(var6);
                                                            if (var7 != null) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3ew7us824c9ob","fPtj+QocVNeeiTksKde5mHA5sRfwYB7IHBRZbhI2uQU=",7941924875755391817,-6439138121027759848,8847635163658319423,7586623247791736042>()) {
                                                                  case -1635618764:
                                                                     if (com.yiyiaddon.i.b.d.a(var4, var7)) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s2g849g4r0b4on","fQg4hf4EJb4Y+msMEezjG/UPSY7N/jEI/ZM0zMc9NBY=",-7784330420401287792,-7554314886976922190,-6389173680542109084,9208155783812113488>()) {
                                                                           case -1491627741:
                                                                              var2.add(var3);
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s13rkbjstg70ja","EE9iCkmrVhX1qP02X156XrANAm96xKjFH3wnbz/LTdA=",-3446776290886254194,-3742156559333055827,4418826715133781688,-2042812870815516826>()) {
                                                                                 case 1669532586:
                                                                                    break label83;
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

                                                            switch ((int)com.yiyiaddon.m.b.a<"s25ngm8g2111s2","oPeP3oaQJLLVakN3kA90lXuQtauZy769YGteJORz0Xc=",-537151225833762118,637857514138543328,-7274793991446182932,-1365309653168635941>()) {
                                                               case -899947769:
                                                                  continue;
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

                                          var3++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s29andwwhozfjq","suQ8dLE84+gog6D5uKlaVdXjSyeA2zej78AwjTbUAZQ=",1097943542697508027,-44024096915230142,-3487661354645594030,1331780998101290077>()) {
                                             case -232172721:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1s2f2498c3sxw","y5+KQevRuu4iQvq0oB9zd12zRxHXILST+kh4xjDU17o=",473825952297533925,-4883221394382534507,-4515502736917209196,1620824394074296493>()) {
                           case 486916420:
                              return var2;
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

      return var2;
   }

   public boolean a(ItemStack var1, s var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13s2v5d5xr16v","qGNSauueKuwI0cBkoWYE9uMBi5aK5t2l/wDGjpsqsHI=",8581467962657827487,-386385967915084611,-5756950820412912315,-8341366798420863296>()) {
            case -259818905:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2wu0jy5v4mvsn","9jJUOhEwr3/M8rUh37jIGQiDkdVZpcChefnqh9s53Q0=",-970355004641656111,3313647749832437731,-7924296421507016436,-6476435873451771236>()) {
                     case -306880394:
                        if (var2 != null) {
                           if (var2.dm()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2falbopgcna","9KdQjpm6JXjpq/vUNlCjd1bQOVwU63VMCLCgaNCoUhs=",3641082307456622882,-5025485296257470805,-2824345193174021117,5074951191569633189>()) {
                                 case -1516194285:
                                    com.yiyiaddon.g.c.d var3 = this.c.c(var2.dF());
                                    if (var3 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1v2t4t3sn5z93","H3tjqcJAVDMutfv63BipHAjSC/K9+m7iHSDWDvB+eZg=",-5748589750714574260,-1389003265224848942,1480391142590168475,282639470465473825>()) {
                                          case -367092465:
                                             if (com.yiyiaddon.i.b.d.a(var1, var3)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3gintf5r1v5w1","l/sAjb1mIJm3ZxhsR5MHD3frAxAoVgBfizGt7vLoCaE=",8727893889644201213,-8461927926659807848,4131642570341870684,-2120487809409747805>()) {
                                                   case 2095567336:
                                                      return true;
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

                           return var2.A(var1);
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1vhpoatdoeaq8","28JOhstr57EqtRVXb5hHzE5ZFCBcwbZfsKgMnP4gWIo=",4068437594285545150,5628543844072023161,-6963946089596206124,-5324485743912878713>()) {
                           case -504863911:
                              return false;
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

      return false;
   }

   public boolean a(ItemStack var1, com.yiyiaddon.e.n.i.a var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aedzobjq6gqg","TMkg610uaA+t9iHhmwKoDZO3y6w7g+qA/mmPdGJYbCg=",-3411867186390880303,9032507256963494209,-9142072090447027499,5175099118852463190>()) {
            case -1343184101:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sgwxxoruleefy","dbwRSQ5pGBEZHozqtIj08mtw86puqFUpt7FO/KEE72Y=",-4308052936997248317,-6913315171343981388,4209082442812394520,2932092889605090340>()) {
                     case -1015695008:
                        if (var2 != null) {
                           if (var2.dB() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2lhv7j8wrnlqe","aopHnAU+B5msIITkVNHNl9u2lSakhnPYTScbM/RuQxI=",-6207105427215656384,-2105759640889327930,-1563323503262440944,5378119483352205518>()) {
                                 case -1700298340:
                                    com.yiyiaddon.g.c.d var3 = this.c.c(var2.dB());
                                    if (var3 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2af6xku1i4vrg","25EXBS0cfxrBQIjsVEYOV2RSQhT3fMJhIh5n8f13AF4=",-3666828885256855452,5689480641343034065,792664463689393060,7332583391076114517>()) {
                                          case 564224857:
                                             if (com.yiyiaddon.i.b.d.a(var1, var3)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3ebmygzaj9s1r","JVN2jAQyCHR9QtOyHItW5gw128s2cEH22LQXBio8vCY=",-2064497310255545281,3464677467502914450,8750704726711367996,-5662717587626195451>()) {
                                                   case -1936170223:
                                                      return true;
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

                           return c(var1, var2.dC());
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"srwlzbv7qohmi","rYThPTCd+GPTu+7CVQq+2XkOrIodOQYXezgdohDa1Vk=",-2525454300532446268,3081224861118889771,-2191512451975841091,5775716606665571363>()) {
                           case 1400344934:
                              return false;
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

      return false;
   }

   public boolean b(ItemStack var1, com.yiyiaddon.e.n.i.a var2) {
      return this.a(var1, var2).dA();
   }

   public b a(ItemStack var1, com.yiyiaddon.e.n.i.a var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s307ozdy9a31e5","tX7xkAZxThTERkPtLWSXvQuiJ5uWq/cixXuw3LcYN0M=",-672956620851263297,7267497343440975122,8539898897760223139,680408075238546640>()) {
            case 721439090:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"svv8myqv0gjei","xrD+5Tul945dJKRoTthCkdLJi7zHMtliq7iqbwemxHw=",-2278501993588573262,2178287121853043896,-4205214114315934592,1460301399042872604>()) {
                     case -1847379955:
                        if (var2 != null) {
                           if (this.a(var1, var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s27415dkehwftz","yjtEkS1NqGT4yLdAG6laMU2OwpueRomgt8MIsb5LgTc=",-5744618681358639008,3985818005745897831,-9065859522624228592,-4688932456732559709>()) {
                                 case 811054838:
                                    return b.SEED;
                                 default:
                                    throw null;
                              }
                           }

                           if (!this.e(var1, var2.aQ())) {
                              switch ((int)com.yiyiaddon.m.b.a<"sf72hisec4soe","WvHEq1MS+K3eswgUAlskhZxLDfZm2hmLiwh6WAIbd/U=",-8669107667241361782,2826166620245519101,-727622546105928074,6660146711651363202>()) {
                                 case 1528778918:
                                    if (!this.f(var1, var2.aT())) {
                                       if (!this.e(var1, var2.aP())) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1kqdqb37nixod","pZ/CW1vTsOrwRIlmVjkaGjoR/KtJ9eNAPZ4st9IEScM=",7251957150569593672,-3238710764160931690,1252693673546925231,-7094984698714755758>()) {
                                             case 1932060604:
                                                if (!this.f(var1, var2.aR())) {
                                                   return b.OTHER;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s2s1u1gef5anq1","jbYFdWDJd/0VexgXRStQyyrno8LHgW/tWIPeB17tUWo=",8778373465015334739,2694801235082420651,-806444794810771652,-3271780464041619396>()) {
                                                   case 1744313315:
                                                      return b.PRODUCE;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       return b.PRODUCE;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2oi9tyujl660q","XByxK4BZcd3IbjsMyL2U99crFiggIbEGs3vaNwemXQg=",-3270827619746472987,-4183353000184483631,-7985179207918246781,-1665333803159301543>()) {
                                       case -641502862:
                                          return b.VARIANT;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return b.VARIANT;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s8tbyfq7upj0n","oPBc+FpncUIxCqOBs+nL69YiPUM+s20wgO76l/F3XBk=",-6661587046043653279,5810957934266776109,-4665857027093551978,-4641320908253636966>()) {
                           case 1072677227:
                              return b.OTHER;
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

      return b.OTHER;
   }

   public Map<String, Integer> a(com.yiyiaddon.e.n.i.a var1, boolean var2, boolean var3) {
      LinkedHashMap var4 = new LinkedHashMap();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s31vx3f0mu6mfu","55mPQvwuhF6knq4JP9/7eGjjtNdVZN4pCRaG/8BNOtw=",886112775286841041,-6027845820734008097,-3723289150730480011,7137561669950251641>()) {
            case 147747583:
               if (ad.player != null) {
                  int var5 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"sss7md8re5p03","Wvv3oKuTgJUw+0HMoGX4w5vQQd2QlzSdrIEbVrKxFF0=",1721735750376180237,1636697687585279250,-390118702123346531,-3982402730606291964>()) {
                     case -2140599557:
                        while (var5 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3obs4nyyvs4wx","qWxa1/gme5nabKi+dla5C4036SWESZoKKmCN44uBg1A=",4863071665347590817,69396537460759946,8232720569320318630,-3553924233322228314>()) {
                              case -262304315:
                                 this.a(var4, ad.player.getInventory().getItem(var5), var1, var2, var3);
                                 var5++;
                                 switch ((int)com.yiyiaddon.m.b.a<"sih1apg0k0qzy","MFf26HY3EQ/jIie3PeqKUzV0w+iZ+CHYjSdxuBa5Dhs=",5461309789441365269,-2180758510168063363,-7547281983915833867,-217941167052390322>()) {
                                    case -806436919:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.a(var4, ad.player.getOffhandItem(), var1, var2, var3);
                        return var4;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s45xs51b3r8rp","zfbiB7rpHucGW44q0xftJ0ktlUifwjptmUimvlv11ac=",-8613904707042849068,-4466010799376977239,2678339882429559069,5708135059658184808>()) {
                     case -397528943:
                        return var4;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var4;
      }
   }

   private void a(Map<String, Integer> var1, ItemStack var2, com.yiyiaddon.e.n.i.a var3, boolean var4, boolean var5) {
      label40: {
         b var6 = this.a(var2, var3);
         if (var4) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s3fgs7j3alw09z","xZcIgfPlKmtgczUPsgC4qB7kaQcTJwZSry2bFPI74U8=",-3402576146438480128,-6927633637572101530,-6774684128657461271,3558725592371249333>()) {
               case 611463753:
                  if (var6 == b.SEED) {
                     break label40;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2mhw4syr4rscn","eiSHVJ5kdEH3lsPJIVMJNPZFzuna9nl04SL4tBSdXoY=",3305629189196234481,-7200732727513209482,3381692963283635147,2924946607851347455>()) {
                     case -803025663:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (!var5) {
            return;
         }

         switch ((int)com.yiyiaddon.m.b.a<"sdfbqkqb6ve04","4QUO74OctreuomWLYr5VIBRZzArVJylqn6GKQiiBwKE=",362535552664227963,-6115272120873709003,1512466574704893342,-4762405550216322011>()) {
            case -1251439492:
               if (!var6.dA()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s285d2nhojjjhr","aI70DrDDEc2e4WZlzz+9e3bBpLS5OMz7Lfk4NtvQTD8=",-8455507571286788415,2629453094216862357,-2707757768884670316,182316563950427377>()) {
                     case -102028854:
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

      var1.merge(var2.getHoverName().getString(), var2.getCount(), Integer::sum);
   }

   private boolean e(ItemStack var1, List<String> var2) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ulvr7sv0f7ai","rB02Y+3MYvncSZh9HHZh/A/9lgmeFd+IkOU3vOVT44U=",4877952063539821352,-5920432553053432888,-72914650938595841,-1135324208114453864>()) {
            case -2080157700:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var2.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2fnla8zsxz20d","xNVIiLxOaI0zbYES15oRlZwFDqv/boVS1hSDEJDE4zI=",-8026789436789731135,438339993437627647,8812949250749376269,-2136102168744874123>()) {
            case -1546455363:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2m6qy49n1ox0m","gDtf7xxpQZ4SZhZ7TNjldkrv6Q+r/j51NSqyn1V+Teg=",-8790787409316317920,-278971602143598650,7160827054109641534,-6928715850333240734>()) {
                     case 1410955141:
                        String var4 = (String)var3.next();
                        com.yiyiaddon.g.c.d var5 = this.c.c(var4);
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s5y1v87hmgrrj","VcWPcCBiNcZhcfH4+ptll4M5OnSWKVs1lQ+1GKKzy94=",4947282579244480991,-769256986045179117,561091921327127738,9079705963099332757>()) {
                              case 930909099:
                                 if (com.yiyiaddon.i.b.d.a(var1, var5)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2bv7w2vp34faf","GdbMEfAhl9EjKxlcGU06nZlV5dL9YsjmoeHxdWreaHs=",-7893372135246818995,3289887702944592957,4784640533215143921,3984066810007496860>()) {
                                       case 365969533:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2n4eg626hnpds","t/9nG1DS1delv95Sh1MOz5RgZ5fd/LuQ/xrPKJo1lT0=",4389447163917953963,377363256516356865,3877792799363722122,-8432637969744610187>()) {
                           case 370355052:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   private boolean f(ItemStack var1, List<String> var2) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3t2x5geslw9ed","L5IHSK52TeQx8xHtGk+OICQ2z3v286aUyBSKsjAiAbQ=",-112191405962145414,6527726138659259077,-8991145718027103061,-1038241519480541665>()) {
            case -1128065128:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var2.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sngt3c5x72517","ympctvH8YWmviiDz8ikV2D3smkKi0LDsPZAbWERJZ5Q=",-7390126499197342582,2299718203029929618,-1377455546562749664,8296094120458221289>()) {
            case -1325287235:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3a42b7xprgfuk","E6NPuOlniqW3vaUdynGMdqIxK2G0c0OgDaA0roozIhA=",1606752132540018962,6163091590180738575,-4138872601864983914,3543387879693355240>()) {
                     case 654423917:
                        String var4 = (String)var3.next();
                        if (c(var1, var4)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ranib8hrsyiq","MgvvX5rhsOquRdtIHeBmkaJs0xQoUAqapp5x7JfqKuM=",1433578750798993534,-3123193513512206049,7204114320309069819,7436190498311881651>()) {
                              case 1154474611:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1l7wvc46i8zav","CtWp6OEi1olTLqRboBro8XXHtgkSm44qmZKlzspUj8c=",-2373708608055989761,-3136854283474202049,1247522646033681235,606946067474698574>()) {
                           case 1674736651:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   public static String d(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2a2k9r9ccm0ps","vu2poh7Ugz/2hKDQ0HgA1MSwtZt9gUMa82nHlQvXFOU=",-6041657334665896512,1648709008599157966,4192204243434841428,-5060833941163943638>()) {
            case -1912930286:
               if (!var0.isEmpty()) {
                  Identifier var1 = var0.get(DataComponents.ITEM_MODEL);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2c65awg9hfxt8","AhYMwz6J0zoQgkeaQq1WuwF3KGqOzU47c8a5KMOwd20=",-707194636311371412,-6725224113521803249,-1476704309658526495,-5530454931557355972>()) {
                        case -1560691655:
                           switch ((int)com.yiyiaddon.m.b.a<"s34qpqtnq165d","cW8F4om1QoW7GGWqbzdzICzR3SbJbvA7ZSLssrDXIpc=",8385347855652599266,-7064690982509264756,-8555675962255415827,992678698486225253>()) {
                              case 2101311009:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var1.toString();
                     switch ((int)com.yiyiaddon.m.b.a<"s3lxlkzcuhgy1w","EbJH6LvZRF1EjxTr3vbZYFzGFnSZ+FgxxqVIXCDbuBI=",-3423052567260167289,-8825357179032170551,5265480214512481578,-7966837017795220201>()) {
                        case -1033957333:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sdz15t3gflbz2","KaXcbjZMPAktENyk/0jqfffmfWk/e2MWVhMU48UxAlQ=",350062810376170652,3902775672519159718,-5168372822716377564,-8738249349223841358>()) {
                     case -1762430212:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static boolean c(ItemStack var0, String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"snk418cajxvxd","mbHZtQjXd50cdX28sfD8sBiJsiTQFYLZ/WMHH7WyUYQ=",4822353019776402858,-5321675353950690880,7251813788465552964,6544548571011683404>()) {
            case 1598606808:
               return false;
            default:
               throw null;
         }
      } else {
         String var2 = d(var0);
         return var1.equals(var2);
      }
   }

   public int a(s var1, boolean var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zwxbjlifo42h","k8QDIoIcoc8Pn0WJuzmlRfdcFjdYid6D7k88vGidkPU=",737824440595971267,-7110126416022558884,-6195783261328701824,-3689794519091436149>()) {
            case -262100802:
               if (ad.player != null) {
                  byte var10000;
                  if (var2) {
                     label58:
                     switch ((int)com.yiyiaddon.m.b.a<"s363tkgtaqmrx7","7ZhlRVPraCcj66MvWZ6wv2eNxjGjgSkmjQfH+j6g90I=",-9198611450274735835,4180882172540889921,-8545161956959489771,6170445939983825114>()) {
                        case -1634657769:
                           var10000 = 9;
                           switch ((int)com.yiyiaddon.m.b.a<"sykh041entz9z","Gv/qsRiKHniYDGCTx4t3ytouAkYmWrxOSGPMqDFRMNY=",7721414693600264358,-5286548539486841678,7189096501648323733,5174010805966863632>()) {
                              case 1401667811:
                                 break label58;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = 36;
                     switch ((int)com.yiyiaddon.m.b.a<"s50xmjih8ttmu","nVg3AAcZJtAaMYQLu5nnAVkQnByMjBBrs21KW3sZPM8=",-5554529583441856991,5733190815068608484,-2046829789163189197,-1199298521224008501>()) {
                        case -1014600004:
                           break;
                        default:
                           throw null;
                     }
                  }

                  byte var3 = var10000;
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"shm26kk8p3lz7","uPTbDCvYWjfZEJajmy+M8OfeBmFMz4wqR7zZE5ZtXO0=",8233545999800876119,-7241158665013098760,4647937173765799389,-5531965054557043661>()) {
                     case 713187412:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2yuh372ahu43w","iYqmMouZ+yDDPUFavcjP7X5kxBoXxuJ0MtAH1/g+F8s=",-388330021309701664,-7030981344652979528,7290492767368835619,-4121171447222882030>()) {
                              case 333482556:
                                 if (this.a(ad.player.getInventory().getItem(var4), var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s9adtf0d1tyfu","ipkMcpestGnSkeei4FkKo6BgWXZJuRJ6ZpuMEKmhRx0=",-6050918349868797105,3948548987398218216,4688064117885505117,8301664292555145298>()) {
                                       case -593659951:
                                          return var4;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s15fy98iqz0bav","fKr3uJ8GX19mHCNGn0w3E/J77e9ECiUboHKLg/CD0Ds=",2919304026106819976,-9046243497327038708,6880739952655919078,5222447601267415216>()) {
                                    case -976633423:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (!var2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s17yumwso26ri8","WYtL5+4fdkNNaymi8TZpSXgMC3osiJSJADTvBsPOLxY=",4616333962293129451,3020362634179218229,-6281872257367952614,-6167683287556653421>()) {
                              case -1890874993:
                                 if (this.a(ad.player.getOffhandItem(), var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2nm3wz2t3qwbq","mYvaPMc09M/B41Gp86w+/ZcVDBLO8nKCQR900r5ZFR0=",-8855017317321652132,3844065058320621695,123784298684908208,-5907439006638238037>()) {
                                       case 1943736353:
                                          switch ((int)com.yiyiaddon.m.b.a<"sl0l8aiob0kie","56NS1UCiNlG1gDdnkFNGDTJXk9SaGUF8LWwfQoXHG+0=",988112313066882527,-4980530184649305930,-1981792731974210560,-7128702061837909488>()) {
                                             case 1118940284:
                                                return 40;
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

                        switch ((int)com.yiyiaddon.m.b.a<"se2iroc8opvbq","pkgKgHJoLPgACVsV3fSBG0KibgCK97Xu9GDQ2sO8rIY=",4486956072944398340,7688304958541591915,-5111030288150926804,6898471356841939463>()) {
                           case -1015473532:
                              return -1;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s34blakhd4msr8","dRwbGllOOP8y4kc8p+ABD9LABvizoP0H/justroexgU=",-2012963864983206837,6236354754734401846,-4984923343038142859,1261794992652142817>()) {
                     case -162130952:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   public int a(com.yiyiaddon.e.n.i.a var1, boolean var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hwry3glvf7pw","32mqkfb2DfXoQ1iWhnDWmIfBXJbKqsYSkS9NR5zrMyg=",-796893914048015845,-4850210551773483168,-5285349396666824786,-1439531956134144809>()) {
            case 2044922934:
               if (ad.player != null) {
                  byte var10000;
                  if (var2) {
                     label58:
                     switch ((int)com.yiyiaddon.m.b.a<"slrss9bb61m3y","xpQjRwSdew27jQlaba19OXhYWLii4zKfqgDZKMRuXco=",7354487292120611697,5001773952165432662,-8081011206148724517,-3380223838089425134>()) {
                        case 1528099575:
                           var10000 = 9;
                           switch ((int)com.yiyiaddon.m.b.a<"s3ek4ffs4w489s","dIBdN6NuGUeOLXEiP/NO8/jTcK5RjPPyQaqYRxGzyb4=",-3043774105761756406,128945448183520916,-5648165484871711621,-2025464330138884332>()) {
                              case 1337679045:
                                 break label58;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = 36;
                     switch ((int)com.yiyiaddon.m.b.a<"s3eo13czw84urd","ibwCHAp0sQ0+EHb9tyni69UowtkRpusXmMEbGUnsviU=",-2995582665763007920,-6534396946048977384,-3722693576602065985,-8150580734719370008>()) {
                        case 23063472:
                           break;
                        default:
                           throw null;
                     }
                  }

                  byte var3 = var10000;
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3rhy4ozk4u1hh","IfU4SUNQYOzNBxyK1YbUBJNDPtJxYwyJQLkwGi16pWw=",-3321674615000067095,869076708234375926,8741303941903983643,6150018247327705653>()) {
                     case -1198103422:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1h6hs1hk3kj0w","VK1SDiMnPCpJSQfWdx2wF9p07tkt5YQMjeqTH/o7U7w=",-2488583312190503974,5690750714715164833,-4211014078627784767,-587622864404235353>()) {
                              case -1087236793:
                                 if (this.a(ad.player.getInventory().getItem(var4), var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"senrsh9e2vgnc","nG0Hfi3roK0cn7Vu/lGqjjf+i12unJMffg92as+4uns=",-2960443995597612125,951116143044935328,-6765899247810436647,1778731757260430949>()) {
                                       case 1722757725:
                                          return var4;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1r9530nskf2n","zQ+6UFCG4EGbzqBXs50QY4XX1iv1r2DNe5gvK24Z/vQ=",-2707531498147818606,7340057631873345915,5232931102248497559,4567259044602896078>()) {
                                    case 121198491:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (!var2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3t4b5ui1wy1qo","SqzCJqtONgfDDKWq2964HmpmJZoVZX/BumFH64JMhLE=",-4225415460696399762,-1180155145758514860,7025936307142783666,-1294877931338606293>()) {
                              case 1702717199:
                                 if (this.a(ad.player.getOffhandItem(), var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2hea3ks3o9sd1","hbv/IShWeMy7MZWi2oLmPwUAF78Tg2Bp1I9ajSmyywo=",-7696074931133407601,3017880887506231127,3420355651318408182,1771706015917739746>()) {
                                       case 1553934665:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2z7e3mncivicp","UJj2w2DxY5A1eB58NV8jE4cCKrFCMUJ23kHxgW2c5+k=",-5666251758575632858,8609116905290265304,-3106992077844278985,905939043131829600>()) {
                                             case 260796908:
                                                return 40;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s28gdnswpmmssh","+JXT6oSRL9E0g/1TPwFdVEbYJnYT3K13ESPLJUg+5UA=",5794951865250267554,-6385741581741026600,-8922953186791645133,8937073240662522975>()) {
                           case 1902122179:
                              return -1;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1rvlfdd1ll7bn","vAGntTeaRdlnhgGL+ARUZhoYB4cUT+pb5w7IpSnNQV4=",3871343514017346419,3015024636222608429,8447769109602629588,6713518563818786874>()) {
                     case 733536658:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   public int a(Item var1, boolean var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqcsyonfvml7","8vkYv24+4vVngnLC5vawlODOsCY1dRQfc42SvO+GGkY=",-2372268769979982787,-2394369975154154216,7664711324147729625,-8771529475893504210>()) {
            case -296452964:
               if (ad.player != null) {
                  byte var10000;
                  if (var2) {
                     label78:
                     switch ((int)com.yiyiaddon.m.b.a<"s2e1td6f2au4jm","LigKm8klpqIpMlrAKf16lJrzR83hByEmFwnI87UbcxE=",-4631075773463678174,3454114705659829073,-5065701091408035901,-2944318951845945373>()) {
                        case -998933985:
                           var10000 = 9;
                           switch ((int)com.yiyiaddon.m.b.a<"s2ox8a5to9b106","Rg/F7pTkerppmIkVaQuAA8mh2D9fnI0xuACayykSQAI=",4571523653716161050,4515835343594595399,-846603037435149083,-8747084844660298325>()) {
                              case -1757954774:
                                 break label78;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = 36;
                     switch ((int)com.yiyiaddon.m.b.a<"s1f18srihctc2c","iXISBK1nKsYsXTaf0q8wVhqqMI/j3GjfdT2WVm7ffhQ=",-4015345942525213634,2894117219809470163,686023860293415459,-5516039519689042152>()) {
                        case 1931017422:
                           break;
                        default:
                           throw null;
                     }
                  }

                  byte var3 = var10000;
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3qx4fqwq4xkr2","bQ1UJ5HcD9uwcjliRY7+U4pOFLaSs33nEkQyLrxRZKo=",-6041763199866446299,2722693145202214754,-8316256243716933925,-4880244821022227325>()) {
                     case -486067606:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1y995s9go01wa","b5Jx7iH8JQYRWoZWfqIIKNRmKEehDeHvpGtAyVoUz9Y=",-3638515908878814622,-2907474815999878897,1313847937232240459,6748431604852456315>()) {
                              case 427409159:
                                 ItemStack var5 = ad.player.getInventory().getItem(var4);
                                 if (var5 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2vj6kuo3vt9z9","mmQ0G9TTDR7S/cg7wOLuov7PZaVeORggGQxWpGzIhIY=",2686106426870582611,-3203513057109333353,-5289494193915894493,-5206574963758038164>()) {
                                       case -1964585302:
                                          if (!var5.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sb59qgl9i7yfv","buQecVHiaA765lbClh00y8Su/AGJnNZI+HisbmBNeX0=",8344207926868211068,-3650112022283986297,6831168123807366055,-2659287095554691558>()) {
                                                case 1297156159:
                                                   if (var5.is(var1)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3o9nb6v5e983q","97iHWQ9vQlBwr5TVAWY1S1G5hTd0Kz6ZubkNs+u0UBI=",2426845328788215399,-6986534382728490320,-1870110137338784208,-8858051966365096506>()) {
                                                         case 475935072:
                                                            return var4;
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

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s19ep47rjwsq0k","SOJ2MXY17BOCOpFp5kdlcXFnLTgz5qR6CpMwH3ByhWs=",5271092741861525364,-6555585799138645218,-5018297786015399671,-5856339656633446675>()) {
                                    case 289728575:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ItemStack var6 = ad.player.getOffhandItem();
                        if (!var2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2e5nxe21a4iua","Q6jdC2wJhHIEvTPFsaBYDxcriyz0Gtbm2H2CGxvGR9U=",1371809142557374878,7231466947327645271,-9075066795766661832,-5838500582275377694>()) {
                              case -1516778117:
                                 if (var6 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"spiuhxmpmsqa8","oQyc4Tw8DpL90qQpGxH6tHkrC/f7dJG8+Nqg971Xkf0=",6824010340148635178,6831649727760399109,5533611600977588234,-145667446526401033>()) {
                                       case -662628540:
                                          if (!var6.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sdyn1skkjbgc4","8/EfjtgiJXEAnv11IKh5wbn+M96w8IgcX0RU2tvQ6dE=",7111190112198927566,7669968245749588532,-6099701789255692462,-931096729746597559>()) {
                                                case 2050663422:
                                                   if (var6.is(var1)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3df4jpxw31gnu","d2ruiFr9eHpJGxKvTQ6ydoSqNIO28lvRLhnzKWjIFxg=",6661966644357870376,-4477105554798833296,7545606854086902861,2716885754520049850>()) {
                                                         case 972802363:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2b58lygv724ko","Uo3rT63gKJ+gbtDUZfiblhgIK8qQq4Pv53mj9EV9Rvc=",1731361997330976248,-5618196551878371074,-4184890298198177783,1965908042975878306>()) {
                                                               case 1832626900:
                                                                  return 40;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s39u3r9wukyq53","cmzgAfBqLqXoXEo7RFHf+RIsxmdCtZ3gRPcDGaBIvtw=",3572727469915203551,9170234360784983300,-6811275415573194169,6172232322618381358>()) {
                           case -406153907:
                              return -1;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xqxrikg040vb","JuL7mpWis5Z0gTQMNekky3XI+FEcoyNyNti+LetsUHE=",5775079682053323673,-708887058403760559,-1224204195888451270,9047868527197613200>()) {
                     case 536356139:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   public int a(Item var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xigh97pgw7ia","JYDJvOzswsB2rUq7JgGzxh3L4WVvTmDhRVP4SRw9scw=",8288992983455230093,-3022403067029855205,-5200655434964593106,8308323081621698078>()) {
            case -2144438493:
               if (ad.player != null) {
                  int var2 = 0;
                  int var3 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2agxs6qtixcfj","VmVIIlqHcau8AlKcDqbzd/3oh+vJrN19xbDUx6OANfM=",-425225266737899381,4619928428873721141,-3203249987220700255,8787144704512770355>()) {
                     case -364312541:
                        while (var3 < 36) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2oc7ivfyes619","mN01dXaB4LXuagc1WVIHgwN3ympI6tkFDD8yjIgYzvg=",3873889355211248057,-9109455169728677186,6307715480867059878,6610991201310326429>()) {
                              case -1976597946:
                                 ItemStack var4 = ad.player.getInventory().getItem(var3);
                                 if (var4 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s25jcq62lghr56","Aj5zSqeYS4a9wBS1+eQZCbvZmhfUfQLr1tEydquwAXY=",-9138940909620866010,-938702984643384897,5944959525936643317,-7274023815384126790>()) {
                                       case 1354973950:
                                          if (!var4.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sj565s61v0qld","4GsAmEInFO+3c1TGqqqYB/bdxplG6HpUEUpX3ALUVEA=",-4427180904816087241,5613879389410454652,-3956399357285799367,904885860112593401>()) {
                                                case 1403516264:
                                                   if (var4.is(var1)) {
                                                      label60:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2fukymfdfbz2o","Hu5195qIJA+sFUkNZit/Rq1Sp9RvjdmniJ9vSwcPGNk=",1169347061592076284,1258783400864333810,-2432999384657256944,-283533692987197480>()) {
                                                         case 286308268:
                                                            var2 += var4.getCount();
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2gsnpo4j88eb0","8qjCdFscLv11Vu/QcAJfa9LFpFBGil2vV5cm2GP7A1M=",-6502008028244665905,-713113612619543539,7484129705458432988,-5297881617584818782>()) {
                                                               case 1921353244:
                                                                  break label60;
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

                                 var3++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jg91wh8nltsd","jM1tZLXz44WRjghZXiq6M58IbGI0Xi4Pv4v7RN36pXU=",-4173813535286149190,792481163962435656,-8902568925004194719,5526019572421641674>()) {
                                    case -212335596:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        ItemStack var5 = ad.player.getOffhandItem();
                        if (var5 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3eiqvr99b5gey","m1PwudOhjtx+db85x7GtDXhNtSDWyd9BmH5TWjnojvI=",4628515227348187485,-473343179391694345,120721267888450557,-4310891484376589465>()) {
                              case -1914661438:
                                 if (!var5.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1fza53p8z8rtr","3yAKEebeSr5b1quMbfW03ByCfxfK2twqd4uiW2O5cEI=",-2293674099495642089,1792676278163391950,-1730580951399724564,478042839400705517>()) {
                                       case -708987919:
                                          if (var5.is(var1)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1c7qz3bvgvftu","nbEyVo4mfHE8ZTlTCcWR+fyVSkejeHq2IdCr5M3yva0=",9090418571828800314,-2866719145992419795,-9138018961161924199,823857642326940417>()) {
                                                case 1181208869:
                                                   var2 += var5.getCount();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1v2dxcw04kf6e","W2Fh9viO6CE4c/XgsocWXROrIZbMws0t+roCSAoRkEQ=",-6350077988320626154,3964783764156382550,4851163079309166145,-6658352080815061994>()) {
                                                      case 690214114:
                                                         return var2;
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

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1r14kxvhs0d0e","FboR2iNB1mJX/zxlOf4SS4yLpNHqgUBZAhM3fRBi2jE=",-8348364028308080747,-6111704652700311644,-8892816612594827030,-5215239403658605950>()) {
                     case 156528164:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }

   public static Integer a(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39468renv20fs","itzyz7QAoRD7D9j7Ax+PyeJAbrq8aJF6O8i/QctD7Xo=",-3373699084321200021,6943771221113392313,4057321874012800715,4378133676873120142>()) {
            case 620458271:
               if (!var0.isEmpty()) {
                  CustomData var1 = var0.get(DataComponents.CUSTOM_DATA);
                  if (var1 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ebsxvtmf0c0f","qNA/WbTSz+Gt0PzkHHB6XE7Uo2vWAF/IByoxedgwp64=",8872266081147492484,9166100835716324083,6204094859791120103,1856228240473615015>()) {
                        case -1345202642:
                           if (!var1.isEmpty()) {
                              CompoundTag var2 = var1.copyTag();
                              if (var2 != null) {
                                 label36:
                                 switch ((int)com.yiyiaddon.m.b.a<"sb3x766m72zp6","DpWIBod31Pya2GUikVwYKQDsO0k9DluvGgt3vRkl2+4=",-6889246345141210846,9118819453015639323,1139568952884966515,-3835290988812718579>()) {
                                    case -622735929:
                                       if (!var2.isEmpty()) {
                                          Integer var10000 = com.yiyiaddon.i.b.c.b(var2);
                                          switch ((int)com.yiyiaddon.m.b.a<"sznb4ik8lu54","4uAKam85ZrRmbNGUawL4P1+CB4s8GtOFCpt954St4ls=",-469843066557822449,-7451702497865348516,4941712804499848233,-90391987651850422>()) {
                                             case 589255875:
                                                return var10000;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"srcciafnn6lqf","i+fZTym7niKjwbhAFTMF3D1jyeOKnGK44lnDx5wn1oE=",-4941386000839002315,4221235673960801248,8126609415743308595,123150236088993866>()) {
                                          case -186843778:
                                             break label36;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1xi9cvgo60ii1","711qgMwNqBRqAAkTxwAVioXKftk9qkFG9jwnIDLjxP0=",2995655837123503742,-8103296454256075156,6091705612619022044,8052579993373406446>()) {
                                 case -958170111:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"siebgrl755bgw","o4jue3BUsLHYLK6pdNrS9yqwaHIu6GVLy3AZ97e51wA=",2182122586331854721,3437380745002939952,229218369147465325,-52557114375150491>()) {
                              case -787312641:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2jxd1rn2a6ui6","j6FnBafI1MHWwogeBmxW5C1EA4FM5otBmz+QHS1RSHs=",468320458127778737,-2723967404986286438,337940336701149533,8836930414985200592>()) {
                     case 1335006961:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static Integer b(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ak0cd79jpnuk","vYsqQxx64anBdeLh5agrQDzTaXxphkxv8hMa5KoaiUc=",-5320883598815078897,-6808269139722730011,72825949791579067,-688038995749319440>()) {
            case -1114349938:
               if (!var0.isEmpty()) {
                  Integer var1 = a(var0);
                  List var2 = a(var0);
                  Integer var3 = a(var2);
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1l6aqtctwxlwt","npN0Yd0oTDwwPcNbYkuR3zRzljE/LfKkC3TdspfEKEk=",4955219335053489292,7662053805240232704,-6363921401721204306,-9005424260886101215>()) {
                        case -1660943938:
                           return var3;
                        default:
                           throw null;
                     }
                  } else {
                     ItemLore var4 = var0.get(DataComponents.LORE);
                     List var10000;
                     if (var4 == null) {
                        label79:
                        switch ((int)com.yiyiaddon.m.b.a<"s1hcbi2kq2421m","lfc+Vf/vTFEpTwkKWko2RY6dZ0Yjfq6gRkbT4c5gHdE=",-8249904056124479020,-3798610625538324475,-4020107963355083811,-650995791285704120>()) {
                           case 743127880:
                              var10000 = List.of();
                              switch ((int)com.yiyiaddon.m.b.a<"s32fnn5w1hfzwr","oPThvNPdUfkgawH1fEX92Z4oQIcIVN4Ll9QkLBAtQvY=",-4900854472009855290,1126175767506115520,6665727663206148979,5650734829173546843>()) {
                                 case -315923427:
                                    break label79;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = var4.lines();
                        switch ((int)com.yiyiaddon.m.b.a<"svwze0eukhtem","iPtQn1M4X5Ev+wL2YkL8TsboLs7pzQbcB5f1l2Garsg=",-5350370183294075727,1083365512052701381,7848536261886760029,6030633613431259492>()) {
                           case -360008917:
                              break;
                           default:
                              throw null;
                        }
                     }

                     List var5 = var10000;
                     Integer var6 = a(var5);
                     if (var6 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3tm11fv7ryykn","kKnflXhfhTOOsPaCmvEOKax1WkJ9uX/Fg8uMgXcNty0=",-7127550607165857295,7073989493243922245,-5565697928830306237,1380756493307947575>()) {
                           case 534255920:
                              return var6;
                           default:
                              throw null;
                        }
                     } else {
                        label93: {
                           CustomData var7 = var0.get(DataComponents.CUSTOM_DATA);
                           if (var7 != null) {
                              label74:
                              switch ((int)com.yiyiaddon.m.b.a<"s5l5hfg1smxd3","CtbnQA9RlwXKBKdYclFU4DhnuHBlI3nxbIUmbpFTXi0=",-4536673017080228011,-3729508540131429972,-8364311574651098011,-6314713429373216031>()) {
                                 case -14799544:
                                    if (!var7.isEmpty()) {
                                       var11 = a(var7.copyTag());
                                       switch ((int)com.yiyiaddon.m.b.a<"s1qjjkyvxy3wd4","M6v4gn/mnNeWO4kh34Rd/Q732MSrOq6VaSS6rwCIwl8=",-3472229995809383000,-8687834790590298225,7943164493966810351,-8701491408991418263>()) {
                                          case -222537840:
                                             break label93;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1yb3e65rr3cyk","ooNTSiFg+OLf0pCF02HObjtgG9ktqDbDTZr+oNqgKp4=",6266134659482870299,7778894961967960292,7819418328927081233,5004668489833991110>()) {
                                       case -317228390:
                                          break label74;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var11 = null;
                           switch ((int)com.yiyiaddon.m.b.a<"s2xtthrr14aqdf","pOUYFXB+g/FZOGyNnXzwEUb33+FXH04iSpbY5fBA5AA=",-701387985594749054,3772368906574335589,1423367095620351537,-7146913057806472431>()) {
                              case 1675733715:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        Integer var8 = var11;
                        if (var8 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"stxn3ika5iozn","bW01fqIt0xePXequ7WOwfs/Aoc9FZRJUwBCOHcfyw/4=",-1321044902911254229,2908971312612035432,1082709084467063567,-7904820625837719391>()) {
                              case -453863793:
                                 return var8;
                              default:
                                 throw null;
                           }
                        } else {
                           Integer var9 = a(var0, var1);
                           if (var9 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3phhor53a2ord","85O1SkJe9R8mSExXD0hhfn9ET4Nf3Eg2D76nCqQ4ixg=",866349431178196960,-1972267040277334118,1078208844314607445,3790169781139595121>()) {
                                 case -1338105645:
                                    return var9;
                                 default:
                                    throw null;
                              }
                           } else {
                              Integer var10 = a(var2, var1);
                              if (var10 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ztld9mm0onq2","KlR61qL9CrScqqPkrpM3vCvJ0nLrpfmplwVZxjwQYAM=",7516303746886667840,-5903962114781372326,1073226104287823556,8903119994814400866>()) {
                                    case -214903065:
                                       switch ((int)com.yiyiaddon.m.b.a<"suu5tqzd38cgu","ZTh0qpzMyHZ13+euOxxJaeeY2NG/yNFbPiwCtBnzci0=",8624952926969518006,-4928931236571354979,-801912310512361423,2593957232506615036>()) {
                                          case -874891318:
                                             return var10;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 Integer var12 = a(var5, var1);
                                 switch ((int)com.yiyiaddon.m.b.a<"syto37bklep5d","y+dESttYO/s8xJjW7qri0eq39T2HRyTvQRPU4xw7KlI=",-2556052404821209818,-5783309505989673373,-7599673220862889141,2767450291724344044>()) {
                                    case -80773339:
                                       return var12;
                                    default:
                                       throw null;
                                 }
                              }
                           }
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s160wgxmmias3e","FaEbGmOX2P1KvxpZbVITJQ5TJ4alQrBVC1qEWl+Ih1Y=",1619683862505158809,8640385609905397317,8856985118622367034,-7671515093777996143>()) {
                     case 1842350580:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static Integer a(ItemStack var0, Integer var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fiet0l27ymh0","aloAYIfMUuzDOPPcRy4xlZyZOtKD4vQFTZShSxyCdiU=",-5140534965525148616,-8675942824851627480,-3699012654554274714,2934226774539093336>()) {
            case -1880336502:
               if (var0.isDamageableItem()) {
                  int var2 = var0.getMaxDamage();
                  if (var2 <= 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1x1fpjg33q8rp","+UOPLnQgQ6CbX0RMfX3uXAx6JqaSesWF7JJOyFyiyfA=",6212170589574302215,5478508543940205394,2583200116220918877,-6937864329880979354>()) {
                        case 990363830:
                           return null;
                        default:
                           throw null;
                     }
                  } else if (var1 == var2 - var0.getDamageValue()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3bj9y6hcj2zsn","Qllik8OWaHK+sQRDxXgTOlCbYoPrVNSfaAa8DigaMrA=",-1127078163298303764,2235180934719219371,-8174322538817084924,7932895102594987897>()) {
                        case 1696740758:
                           Integer var10000 = var2;
                           switch ((int)com.yiyiaddon.m.b.a<"soexy4614izwf","q8arV+lmbUMgGYHAzEYnJEqv319dDynE7QzPJmUWojc=",-7584063037357245108,242036229388291113,5593458318609136231,8186187131166276345>()) {
                              case -865114840:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s13fmj5ia39nxp","Fcjz8N/S8DUBy/CADUlknv+/WtgSMA+VWYqVJmwaca8=",-2266462593423221977,-667209837923338285,9211484861769459075,-837181493039492713>()) {
                        case -361724138:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sa0k2y6kp1xs","HoKzof/zkAu3m2uQi7N1aQAglKfzS/ak9BLrZhD0yGQ=",6497011416514592457,-1578204826985898564,-7712833155692096511,5077865424564501299>()) {
                     case 73855542:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static Integer a(List<Component> var0, Integer var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q7i8431v04vz","fkzkvL/oY3DhJJ/9OaoWu65iMDXL7sRKhQ7d7NFV+Lw=",5636669158969146995,-5085893910476712764,394272255930003648,2668114504494011217>()) {
            case -514859668:
               if (!var0.isEmpty()) {
                  f var2 = f.a();
                  int var3 = 0;
                  int var4 = 0;
                  Iterator var5 = var0.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"sykpyzi4pqayz","MY1v+d/m6ANY2evuaynmNyZpAFG3ya2NP9Ur1bF6aDg=",9064602740794960732,-5149081761761952561,7150076988520602517,-220221283073460571>()) {
                     case 1150659452:
                        while (var5.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1h7ic8zox0h23","Jy3ub1quZHh+8I5vGjlNwuRUKF76ob1M8STaIpCBZak=",-12327072255024148,3457285036142538143,-6417798952861967814,860327206081664590>()) {
                              case 519950826:
                                 f.a var8;
                                 label112: {
                                    Component var6 = (Component)var5.next();
                                    String var7 = var6.getString();
                                    if (var2.dG()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2o9il3a962hf0","zhb16ZtOzlO6Yq9Y51IKF/Xea17o9R1bywl4vlBYBjQ=",-5450226993724919689,7213341486830037233,-5665301368535571747,-2037148328818689656>()) {
                                          case 236423763:
                                             var8 = var2.a(var7);
                                             if (var8.dH()) {
                                                label71:
                                                switch ((int)com.yiyiaddon.m.b.a<"sfp4cm5wccnps","UStnJR+Q6MAffb3CJeZKliUpoaO5W6J4cNGK37JpFsM=",1270870923130975210,-4973941027215802446,-7567469300664684509,5931801213624646140>()) {
                                                   case -1353820284:
                                                      if (var1 == null) {
                                                         break label112;
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s21u9brg0x5g1u","kSQhVBE5Xh5s+5uprSzB0RYiTdUOrBvQmoQlLcKxd/8=",7264208728111708134,3631260281983381120,3443855334145262117,-1034883008179232329>()) {
                                                         case 1795524436:
                                                            if (var8.cC() >= var1) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s250dpzvtpx3ad","dp3aF0tDIYbfitL2+bwKAeYaVT7woHWN6WCkQ/TDdxc=",-4600195025708854737,-8642125887700759785,4193214874136903044,3146557635400622419>()) {
                                                                  case -813086483:
                                                                     break label112;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }
                                                            break label71;
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

                                    Integer var9 = g.a(var7, var1);
                                    if (var9 != null) {
                                       label65:
                                       switch ((int)com.yiyiaddon.m.b.a<"shjrlszj58ttm","CmJaDtubroqzmtCw6AkWB/9O2+uzaOJwUQFDF99cXjQ=",-6724319719514186168,-7007608229512947394,5160083613413774846,5125171669102369863>()) {
                                          case -655824522:
                                             var4 = Math.max(var4, var9);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1ejbmn3ojfogx","ectNgBRpl8VC+qOxc4CkG1x0jvsPYOg0CEy/O6jSSS4=",-1464592816232831372,-4769694892528204060,-9072994563185629024,5922158268492885954>()) {
                                                case 1489198453:
                                                   break label65;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1f7nulnmh11wd","0buwjBOrQIYRfAm/WlqgSA200Ay6zEqCJ6WYALM5OKw=",3730214937886378762,3141361533673554173,-3830895019356734673,425958350999545030>()) {
                                       case 1996522823:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3 = Math.max(var3, var8.cC());
                                 switch ((int)com.yiyiaddon.m.b.a<"s13y1vxu81wxpy","HSrdfZ7a5pqCLODDHPNYG6i9Obr/NYLQ4JHjYz8pocU=",-5606466937250390247,815264468163925150,-8681269445408018877,7854605555768024663>()) {
                                    case 619122007:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3 > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"stazsc4ltjqi3","Ph8FdcigQE+Ru5Qp92+vGv0zBqu7j/V3FrwA+v1ktT8=",6441285560175670999,2140828165882265548,6937220827203940888,7393236726866727924>()) {
                              case -1291766235:
                                 return var3;
                              default:
                                 throw null;
                           }
                        } else if (var4 > 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1lwgo5rmzvh62","n1m5++HrZ/TnW/9pZi0PArFuSocsUjY4KubbeKKX9N4=",-1295979173969815141,7051183413733617657,1781453522686396621,853407197519432093>()) {
                              case -2011265488:
                                 Integer var10000 = var4;
                                 switch ((int)com.yiyiaddon.m.b.a<"slhaymjsp6i2z","KKnPMpdXGSdqPxag3yhOYhgH6Vwa3oLRdEJcGoINVis=",2797723159915311209,-9197913427645506698,926532811971566592,-7978293071563955203>()) {
                                    case 16853551:
                                       return var10000;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s3towu5o08idie","ZMLwED0AITxgDHkQlSK4pD4R2GH2y8V52AJdnYJoXWs=",-9206580677266699217,-5655347412466660158,2852928456286283731,5251374697772878279>()) {
                              case 992031997:
                                 return null;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3esnfmk0wxnmb","uoCugM+TFlIBmRfrw/wdKK4oHGGY3r1nJRAVGsC/G54=",-5600925893095486795,4067581170408708720,2392781136109842881,-2925557803455572844>()) {
                     case 1211473927:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static List<Component> a(ItemStack var0) {
      if (ad.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wh02i5kgxomk","E0OIB+Teui1sI/X/LbvfNlrh/kxLyA9MzwAnb16NltU=",-2942095976445182275,541853734524390929,-453972301445852102,-6204847569768296642>()) {
            case -1035447552:
               return List.of();
            default:
               throw null;
         }
      } else {
         return var0.getTooltipLines(TooltipContext.of(ad.level), ad.player, TooltipFlag.NORMAL);
      }
   }

   private static Integer a(List<Component> var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n6e0kt834trg","zLNbln4W6KHQByTGLbjLqqVJcUoLMk1AaeAze9o+Tlo=",-8729267544894001934,-3895593298758713989,-3157170843444479156,-1503609065544377789>()) {
            case 1925654292:
               return null;
            default:
               throw null;
         }
      } else {
         Iterator var1 = var0.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3r5p6v95b0gn9","Gn7+GJxboJjvhxJEXUOHZzYvRsPjDFRR8seieifr2Yk=",363293553962248595,-1422222914722736371,7504131987045456976,5606988380071397599>()) {
            case 1043907415:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4xr75s7bw1lm","IXB5mDcr+PDeuIfTFjUhbR75Iqo1MZGOoYI/dc9nlUg=",-1852847605461017221,7598998767031871895,5802152089798396576,396159195860654263>()) {
                     case -757582168:
                        Component var2 = (Component)var1.next();
                        String var3 = var2.getString();
                        if (!ao(var3)) {
                           switch ((int)com.yiyiaddon.m.b.a<"sux35rq8mvd0e","G7uGny+canqFBdGUsJW9Z8HS0l3Bs5t8psUMsN0YzZE=",-5275437905565064969,-3117237860542946884,6399415136180812136,9045630805679774118>()) {
                              case 676627863:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2jj8c7u481tzg","kL84qLWtYUQLxceCF6/azeEHs0ZyuHP/Qd8I6SIcjkY=",1872024929299012354,5747111041017327286,-5863014669688293982,9170768673744583991>()) {
                                    case -1135850310:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           Matcher var4 = i.matcher(var3);
                           switch ((int)com.yiyiaddon.m.b.a<"s1hnzxd1t0ivac","JM+R2/PY2ert4o42WpWKiG5/46YyHoSnnCHEvfeQ3mY=",-6961858216138011225,8871167629807741446,4348271773471382605,-4790541442019904711>()) {
                              case -576828138:
                                 while (var4.find()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"somo05ali6gpd","t5lfr57cBZ8G0VsOsvbtzXIadyQSP168UX+2qyfjRlk=",-1809940124308156382,-6389511556355479672,-8719272408884128806,8318638598018804622>()) {
                                       case -1187546916:
                                          Integer var5 = c(var4.group(2));
                                          if (var5 != null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3gbt3g4jc6hpv","XB+z9T1dQD/j98N/XMAxEEFUrgKsruSnHUxMAz5kfqA=",-3374047653584264415,-6961205118384931941,2595252430732626490,-4271916099898866191>()) {
                                                case 726909062:
                                                   return var5;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s372fecy05vd4c","TRX391jQiXoWGJZ+iEVP9ub7rwrShWD7nN9TzgrD+R0=",-720676316243170352,2687278502295914480,-3396101934717681070,-6893083343900171897>()) {
                                             case 1453700844:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s34k5mhf5uba0v","PhGryLdHgR2yqGtpqG9GDX6U/GQPwONuqsvZZ+slu+s=",5847036208720277084,-5240655772027132445,-8440870157023476108,4890874139981022010>()) {
                                    case -2068183351:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
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

   private static boolean ao(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"siqkjr4eg1s28","Gwf6DEQ2iRLMwCovwiPeR0jqLqC4SeYzmLbWq47a1J8=",-6890841122308507753,4786722593375465056,2244536745202758834,-1291586491206734517>()) {
            case -45380135:
               if (!var0.isBlank()) {
                  if (!var0.contains(
                     (String)com.yiyiaddon.m.b.a<"sxnlf53s4b52q","4N0jmAzAsmxk25GS4FK/pKEC93uMCwmmlNtqHKRn",-6291816350818991453,2253448270294488782,-7485401258723237364,-3219583819472641415>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3koi6w4uxoc6g","S+e5M3wS4zmulPDUIqDJgl2DkwWAmm26hmy/u5YYrBw=",-9145302362097002054,8472771829980347018,7053310317071962308,-6057253137318827761>()) {
                        case -486545392:
                           if (!var0.toLowerCase(Locale.ROOT)
                              .contains(
                                 (String)com.yiyiaddon.m.b.a<"s2hng4djuj2yew","zqmi9iKaT830joZrjtZT9tmqVavmg1F6GmfKOuZ/2mWlWRAY9No=",2393861361421795920,8196128281796915791,1701433037953600920,-305502805188727203>()
                              )) {
                              if (var0.indexOf(9608) < 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sezbns9p3qygn","tyIvzg1HIqptRRz+rI2ba5MqIq+4id9cEVSfS8PPuwM=",-7602772825781091402,-516557416991034871,3646734119270607987,-1926955765341337917>()) {
                                    case 1650154349:
                                       if (var0.indexOf(9612) < 0) {
                                          label39:
                                          switch ((int)com.yiyiaddon.m.b.a<"st7k8oqfuiifv","MMKcRlxsuqyXtyyuiJTZUE5QNCggjLMtfkxQEwAlNX8=",-2421952802275923694,5497434697315086978,-8720485687949751787,-2522305815087993183>()) {
                                             case -600410421:
                                                if (var0.indexOf(124) < 0) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2nj2lvrfqycyy","ggB3wQdJNKL+HKZ0ksDg3FBK2+Z37F7oU8DPGdwYKx8=",-7564457112222695347,-1936912403410339683,-7952537073589766173,-1585129224001761332>()) {
                                                      case 1590662359:
                                                         return false;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s3qbshyd90j42l","1sCvkL6xG6SoQY9r/GmcKKxTOKcjiM8N0iUSm90NBDs=",-5293543513184341069,2061879991912040948,-7046315064292245516,-4498688611083409308>()) {
                                                   case -1883475519:
                                                      break label39;
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

                              switch ((int)com.yiyiaddon.m.b.a<"s3lh8kwramox2u","FjityFG+aqrZ/R+voc4VwWLnyCc0sETONyADRzMH8TQ=",1115970056376039579,-5117023158518915514,4688470642948843108,-1233147440502382163>()) {
                                 case 1097700190:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sz9y0fwlhy1yx","XU24PkYM4PssKSwNRNBL90N4MUH11DThjcK2TSteulU=",-7506374911653896216,-7568281044233089680,-4132322102998413256,696867536649378764>()) {
                              case 686590629:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3igza0hq4crky","ybOJk9IKj7CC59ksfRNy8iFBH2qClUMq8ZzPNaUWNaI=",1608796103288346636,-7716819934504046440,1881046777957732466,-2059478715845291928>()) {
                     case -390249758:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private static Integer a(CompoundTag var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jrsmgoqo1b3k","jVYN2X2pSli/lpA+2L7hMMzH+81q9MDtu2lWP9vMAYw=",-748685162326323506,-9083801738226898474,-3716696031238443040,-252073850528789252>()) {
            case 1094663906:
               if (!var0.isEmpty()) {
                  Iterator var1 = var0.entrySet().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2kwna792xyh8b","tb5ur231PbZP6noHRV7NCGMP6mPveeuxxVJN68qtDJU=",-7371070897049601015,3853360611161658773,315097099912793914,-3188579269660064166>()) {
                     case -1110150524:
                        while (var1.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3d3zyibdidkqk","Dg4artx69fQ1QGNcdpDf5qItumd6psB6LJ7qTx4qD4w=",-3083175779748307986,-1727374457564806211,2099510695476775258,1033567899975003654>()) {
                              case -1971167863:
                                 Entry var2 = (Entry)var1.next();
                                 if (Q.contains(((String)var2.getKey()).toLowerCase(Locale.ROOT))) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2zaju7e5d598j","fSjyykWXVgx89Ydr5DzRT76DEID305NA00YNmazGXLw=",6227795478932146311,-3428320672997060332,-2599282236560336861,-2344591504745864777>()) {
                                       case 176330099:
                                          Object var4 = var2.getValue();
                                          if (var4 instanceof NumericTag) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1jlnja1gmw2jq","60jyjkpLYVWqqatoHQYJ0wjOG5/tNSJbdmtrHdIucdM=",5149903206308613695,-6535123566268818396,1502294645579452027,8960455589253433784>()) {
                                                case -1161416539:
                                                   NumericTag var3 = (NumericTag)var4;
                                                   Integer var11 = c(String.valueOf(var3.intValue()));
                                                   if (var11 != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"szvwp56t8vn5g","+06TNrUQ6wJ3zWlWn2eIfGRsq8TSvSiIbK2GPjSAnes=",-8011359813360797645,2309741411954344915,-8440641127335442016,-1961382687333892727>()) {
                                                         case -5081521:
                                                            return var11;
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s3ausaqcuvjz58","li/ob9vj8q2aLGRjbqHnBz9NygBWFKwWN1qlvnOa52M=",-5997973607169184621,-6523744003782220180,-6315827427148225364,-969108467318766491>()) {
                                    case -1124613766:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        label135: {
                           if (!var0.contains(
                              (String)com.yiyiaddon.m.b.a<"s2hng4djuj2yew","zqmi9iKaT830joZrjtZT9tmqVavmg1F6GmfKOuZ/2mWlWRAY9No=",2393861361421795920,8196128281796915791,1701433037953600920,-305502805188727203>()
                           )) {
                              label93:
                              switch ((int)com.yiyiaddon.m.b.a<"s2l9313323iugk","X9pXMeevWtiyNdISX8QdIPTDvoRscZHTt6CjU0G/yf8=",1032579375362675576,-6873822072191006370,-8819289574033434652,36884333104572764>()) {
                                 case -2062293872:
                                    if (!var0.contains(
                                       (String)com.yiyiaddon.m.b.a<"s2j54ahfw8bi4e","MRKjwqbGw2UPq/+bggyYG2AplMd7i+/Kyuyf1FqDQ6x0hS0hjEA=",-8886996073510909411,218507539485261391,5879394250940053670,-866859510885259727>()
                                    )) {
                                       break label135;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s5egmqaa3282u","G5+fCydJtDLMhaoIvASKwyyczB3rd7idepPBR4nWZJA=",-685883376401873493,-1167548323846332289,8871164418104401033,4058806513418757253>()) {
                                       case 1804791259:
                                          break label93;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var1 = bT.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s1vjbcd3nlsvma","mqNELaf8e/pP8USv0mF3Iw4eN6Q/knljYiwf3w86iB8=",-929170935790298968,-2984637704700664233,-4950050038207973586,7323645066497020712>()) {
                              case 1105186649:
                                 while (var1.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sxjmin39ctznj","r6Gpc1qSchs2834TO0BGs81Emo2+Y4o7NRr4jrj5NMQ=",-926199906383903246,8587213486755780841,-483737451174498191,-8563861312347779163>()) {
                                       case -1975928432:
                                          String var7 = (String)var1.next();
                                          Tag var12 = var0.get(var7);
                                          if (var12 instanceof NumericTag) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3jizemdjr0qwa","JsNpjKJ7NhyA3mhnmcDUBv1pvvzTStxTYO4rNHa2bYM=",-4045017720952264221,-5347728244968899991,-2593677323674406645,3981479804191930441>()) {
                                                case 1704310381:
                                                   NumericTag var9 = (NumericTag)var12;
                                                   Integer var13 = c(String.valueOf(var9.intValue()));
                                                   if (var13 != null) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"svit8h919wji6","vsHBeqTR8M+zrfrBIF2TmGJtymAIrUNJO0V5Onlba9k=",7059523995393553269,-2344731140879333853,1002701899422150352,-1478223688096806129>()) {
                                                         case 99103621:
                                                            return var13;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s9uoibhqv7r5s","S6iTYE34pr1PthxQ8djIUWtLuEx6lU26qSm+YAo+OBQ=",521961928455620767,936463900878530617,-6127120184684344858,2066274877667569502>()) {
                                             case -2018143828:
                                                continue;
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

                        var1 = var0.entrySet().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s13wkrz57ajg5s","Gw3i0d95RZecjdO2fuxoEBkhEjBQG52bSs8O8RAB8HI=",-7352524662007930762,-4552382835437873583,-1646083790690498450,-1243088621380560259>()) {
                           case -1850741121:
                              while (var1.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1g2szfl6w5cpv","jMtcNkrC8s2HyRMK5VnBBCOmdFYNkgHhghVcf5rSD3I=",-8100305412177448808,7734162519727230112,-4635248417847863687,-1256063390238299017>()) {
                                    case -784496174:
                                       Entry var8 = (Entry)var1.next();
                                       Object var14 = var8.getValue();
                                       if (var14 instanceof CompoundTag) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sxqwr888ik97k","qJIZMmcYJMtPynFwGMCFwwAtE9f1K8NInUrEJSrviXY=",-6609007567937928162,6587542913123581117,-102822669307214122,1151576055300044085>()) {
                                             case -2091330038:
                                                CompoundTag var10 = (CompoundTag)var14;
                                                Integer var15 = a(var10);
                                                if (var15 != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1rqd52jrka7tm","ztz6jhNe/XQx9FNGQdZj8mLqN7MkfQ3/QzDewTzEQL4=",-532682538783629000,6386221729944264988,-2221569672360583078,1659447587807001155>()) {
                                                      case -986772303:
                                                         return var15;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2j6tkps689x0j","PjKdkkCbw28+4qPEXOq3YpRZaiJBTFkQT2u2PLFnmLI=",6099481515778086698,-261104260669250759,-4136999032178029280,4061362841837784351>()) {
                                          case 93010557:
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
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2gbvem2fs3949","KdwiA13BtND+PdMpUi+k07AflHlbvbQUhXQSEaWKk3g=",2602131365724570309,622496524995952068,-8235394701051138132,-8634422786349813096>()) {
                     case -1487101079:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static Integer c(String var0) {
      try {
         int var1 = Integer.parseInt(var0);
         return var1 > 0 && var1 <= 10000 ? var1 : null;
      } catch (NumberFormatException var2) {
         return null;
      }
   }
}

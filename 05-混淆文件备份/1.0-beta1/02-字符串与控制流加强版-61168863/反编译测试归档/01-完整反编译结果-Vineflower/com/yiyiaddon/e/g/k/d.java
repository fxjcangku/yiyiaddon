package com.yiyiaddon.e.g.k;

import com.mojang.logging.LogUtils;
import com.yiyiaddon.e.g.d.o;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;

public final class d {
   private static final Logger j = LogUtils.getLogger();

   private d() {
   }

   public static List<String> Z() {
      ArrayList var0 = new ArrayList();
      c var1 = c.a();
      var0.addAll(var1.X());
      Iterator var2 = var1.W().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3qkkg0zn29aep","4OCoJvPO7dvFC2vK7N1DLramBlT0X6PB+Tw24Y1JnNs=",3284013812528068206,-1576461587082013266,-2198184860108639946,9115501984765394623>()) {
         case 677918800:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1oeylue12rlsc","4owc0kC6hDc8eSip5giTyLk3WjHSx5VhfhTXrghaJy4=",-6669370302977441631,-7427567820611823551,508753617266817318,-3999089204679863314>()) {
                  case -832459653:
                     c.f var3 = (c.f)var2.next();
                     Set var4 = var1.b(var3.s());
                     Iterator var5 = var4.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1kja9sfs2evuq","M8FtZo0NDHcUObMdS1tRNpzgt+MKhwOI1+UCfcLmQhU=",-8475422466828667982,-1085029462536762476,-3301882330219811088,-1474853495129111499>()) {
                        case 1324764086:
                           while (var5.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s18txbjt24a0mo","ETBUaIOHHLu3OJwZNFlUY1/4rMOvyKktYtArrhkgLmk=",5011417099858087423,1242869446325155984,-3119307570274183952,-3204344254964337530>()) {
                                 case -1597033654:
                                    String var6 = (String)var5.next();
                                    if (!var1.b(var6).contains(var3.s())) {
                                       label124:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3om8aduk7vaor","TQoCpUKzWNJdRu/CQejU+a14k7uvUlH12WN5jXE+Srw=",8705933630778764585,-6706205031324355720,-1262268641900848680,-4163178538079501495>()) {
                                          case 79754821:
                                             var0.add(var3.s() + var6);
                                             switch ((int)com.yiyiaddon.m.b.a<"s1qr80ch969tlr","rnLSaYQIabX0AzNQM4QJuAjdI4Wy6mk+9Jx05qO75Qo=",-2719926088322050869,8775471863952974342,8997373089797901231,-6675048170187370331>()) {
                                                case 473265522:
                                                   break label124;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3l59ympem521r","SbgXhpE4QnE/oVDNVkSI0koOI9qsPXh9xJaLa0m9d3E=",4227136080030728288,-8785799576373459317,-6383775670737399988,-5037519862169627177>()) {
                                       case -1070718879:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s114y8g36m9fta","SI8xTJP0J00+HIF8zca1O3uU9Tf4cmzuQ69jtwrI1pQ=",1599472118924468840,-2503878858120255778,-7016975878393918430,-1118921255164728789>()) {
                              case 1396168275:
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

            var2 = var1.N().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s3hhg17vg26fw5","7BxXqI/JRlMn54kp4K9l2LRQEaSNml0kMVTjkCVNvt0=",-2520522116205513967,-8638426601568854410,4046836930845638924,-788647042386331773>()) {
               case 173972712:
                  while (var2.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s22kqbxmx0bhz4","7kr3or1Ze/FvRD859/8jZL/j291sHvzVLl+MktvbLPs=",-9135994452131095004,-7623311182100366400,-6513985158510350710,8562646222298985253>()) {
                        case 1309801156:
                           c.g var11 = (c.g)var2.next();
                           Iterator var13 = var11.k().keySet().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"sf8q4239wtt8j","tFQmR33t8nk/Un4KVfMi/F85N8kY0nLayq4kPLZMFBg=",-3922975954561810037,3390311864196248632,-8320295531105264479,-3991283705726534825>()) {
                              case 341837762:
                                 while (var13.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1sd34eo60ert1","nC7iyuOmvJg+/DOvQH7Baf+K/zNhAzXD3HPcDQG+PFM=",4567475482698574759,7824043999015983573,2555331175907763254,1820666853000448488>()) {
                                       case 1605534626:
                                          String var14 = (String)var13.next();
                                          int var15 = var1.e(var14);
                                          if (var15 < 0) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sb0fgdecpk2qz","fqRbKgHx7uBihjPkFY4eUTq0TdhKGME35zlumY1fF+A=",-8184228694803304320,-3975026273070055656,-4150357203539667915,-3759461484352241407>()) {
                                                case 589854045:
                                                   var0.add(var11.be() + var14);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s12e0cshfh5j7s","7GYRgsKl4vpGhsu/Ng1ua2bC+QHR37OfoNYC6VknEog=",-5612218062722679029,-8266198957654903917,-4435081409001659069,3676527823228883988>()) {
                                                      case -278241565:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             Iterator var7 = var11.c(var14).iterator();
                                             switch ((int)com.yiyiaddon.m.b.a<"s2mfrjsh47f5fc","2km8y5REPrOqCnjeeTv2hSECtIIX0KO/D3ntxXC7nVY=",3845411328710251419,-6484029095111955489,-2342468267255289431,6602454479108907128>()) {
                                                case 485741699:
                                                   while (var7.hasNext()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sq10q7r78zhyp","HqngfnZPyD+149kDu4KhJ2gAdhdzYmMsUxPVZHZtCkY=",8972831799213827498,2614148931866980297,2894107422654540767,-4471606355286503637>()) {
                                                         case 237725568:
                                                            int var8 = (Integer)var7.next();
                                                            if (var8 > var15) {
                                                               label101:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3hs29k8d08yih","0SvnVyUjlWMzcmCKVKfelymAz3BT8o75vkl2MdSSsG4=",8160628581093323347,3697060526082832556,7815627281687745848,8188257706764335312>()) {
                                                                  case -495681494:
                                                                     var0.add(var11.be() + var14 + var8);
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1i7f3p7jnvw6t","5YpItNRvwNvbIUVg+z/W9JSTxY3DO49S8WsJFkxtG3c=",8564636431322685801,-5346776632861944724,-2485759549922861033,3270959248841946848>()) {
                                                                        case 638935517:
                                                                           break label101;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s1z519g05vh2bu","vG8Cyj5CC8B8SU2ybJWO2Aa9Zu5/l5jFXR1bGweTeDg=",-5595513370052912094,1831168131982669585,-7658902333721884565,-8550597919446066251>()) {
                                                               case 29597927:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s1dbjpmbboasg0","He8lgJITTMeL061TwTP4QrlyBHQDXpXUdJyvdDeHx6o=",-8274681346683937329,-6467960646684345034,8179471444671265770,8335988048342397707>()) {
                                                      case -692055254:
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s730e791qyjpl","5We3aIszr7D83V1YfUJJqqS9FGtkX0bE7xyRcY0zSsI=",-1784572825327843120,-3935202823476804523,6646824376194621622,8627326852355405640>()) {
                                    case 885446545:
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

                  var2 = var1.W().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2ghvfusslekal","M0OS4T2jtF9vpRAJi9qIW5HTpBPs95/UZYmmxe5sRD0=",1231725517137432455,-8444232184957210311,1465416566274174795,-7284268453370761539>()) {
                     case 1970369761:
                        while (var2.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"se294muld7aut","C65dP/UK2BoCBkvNYzR9OZfBUoHG+Tluwnb0iLrt13w=",-3909080601668747820,662590063140724311,5067843324559283448,2780887805793130725>()) {
                              case 1925663159:
                                 c.f var12 = (c.f)var2.next();
                                 if (var12.aB() < 1) {
                                    label73:
                                    switch ((int)com.yiyiaddon.m.b.a<"sfzpa8vma7ckq","ogemqxhMdFZYAwaJUKcfKJbv/QTNgkswoRIwoiZXaro=",-2714412887830727880,964009450590288232,1283856816523482960,897963260173013320>()) {
                                       case -309383672:
                                          var0.add(var12.s() + "");
                                          switch ((int)com.yiyiaddon.m.b.a<"s1zft4ucvs08og","/jTVEESRQsQF5i8DgDVY/rX86haY6N98XfaCe6BrlD0=",5678275814171841327,-6507403007569116120,-1833842786024907415,-3328172820828885803>()) {
                                             case -105580529:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1ca1m963ocnc7","lHvYECrv8DDM1iOEFG16jyt7WycjQFOOJPJSSRIfHec=",-518693621409040399,-591434517148876553,7092244205252456638,-1919553688260969935>()) {
                                    case 1866365487:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var0;
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

   public static List<String> a(o var0) {
      ArrayList var1 = new ArrayList();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3c9i6k12ivh45","HEweUDJt41j+CnXnkWMaIrjXSzAD9kHaHcv/mjwLoFI=",1311463378405194503,7118211178830675696,3771509103714089423,3485531926948682356>()) {
            case -903134632:
               var1.add(
                  (String)com.yiyiaddon.m.b.a<"s1dp82mxjis620","1zr2x6sZjBH5FDvrTQSUAHORhq0WJTnmgpW5EIjhePX1sG2m+nhQqA==",-5092214288998629152,1218104269056790760,-4770317487476343695,-2303119790279985067>()
               );
               return var1;
            default:
               throw null;
         }
      } else {
         c var2 = c.a();
         String var3 = var0.aF();
         c.g var4 = var2.a(var3);
         if (var4 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1ktfgcvzkf67r","n7x+ZDpaK4FsIeFiJ4nKzPrctk6RpebAMCodu1mWPlQ=",-4294531766621950713,650709577939036991,39059906073611480,5737020068348074587>()) {
               case -1077585184:
                  var1.add(var3 + "");
                  return var1;
               default:
                  throw null;
            }
         } else {
            HashSet var5 = new HashSet();
            Iterator var6 = var0.Q().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s321tf943kan8l","wwOiv93lwGxXRlxIGkCJynI7+Qn4HAzoYj7V44z56xs=",911563855158979624,2678149858805709016,6200537240092912010,-3391717419677903150>()) {
               case 375732426:
                  while (var6.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1kuccz6fy2xs1","ZShSoMxTIkPEK2N6bR7XglIh7WZxQBGLNFgXhJjaa2o=",8444345409471089055,-499694895197148218,-1722504731150581973,-1066984347595170795>()) {
                        case -975071997:
                           o.a var7 = (o.a)var6.next();
                           var5.add(var7.s());
                           c.f var8 = var2.a(var7.s());
                           if (var8 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1szy89m1zdaus","TwZP0Upw3GE9Go1FbksBZmgVRmUFPdUSop6+Fx2vq0U=",-1517806581958781287,3030082764482239751,7270391459694203755,253827965137300535>()) {
                                 case 491193092:
                                    var1.add(var7.s() + "");
                                    switch ((int)com.yiyiaddon.m.b.a<"s2g019uny2wk7b","1Vis2PW6+zSqLlO8Pe7Xmfl6vqle0tjVKgL3tyKREnM=",-9004900975928938039,1839179004710341455,5523940099391094081,-6137930213239845473>()) {
                                       case -577952071:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              label168: {
                                 if (var7.ai() >= 1) {
                                    label140:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2kdhq8v28h225","PqMsdTJxsN38FiOXRw6cYQ5bSiW/OCaDzZVM056OjG0=",3394069123223702165,8319498632748116354,4056602441958603444,2622720639313242086>()) {
                                       case -1068003122:
                                          if (var7.ai() <= var8.aB()) {
                                             break label168;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2cqifp7wj5bld","zFOwvyAc9Q8t0G9QKrcdMa/SPW6IWl/EGT5SKa4i8Ec=",-8990071102579243729,3423592636822971,-5330834807631515955,-2348145251823907853>()) {
                                             case -852228004:
                                                break label140;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var1.add(var7.s() + var7.ai() + var8.aB());
                                 switch ((int)com.yiyiaddon.m.b.a<"stnx1hxnkoya1","uHrboEV8wS8FN4aFJs9Shl72T/k95FCXNFzQeWrokYY=",-5218593558567138867,-8362483743289807884,5897508127841410470,-1446292322394338242>()) {
                                    case -100991769:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              if (var7.aR()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s26t8krxdku3s7","lur1dVGrnhSNcLcLe0O7HaQBxa/XgUkUcmlhXzO4luo=",-7025753417731836226,-254369559720060854,2151816639166595673,6711377207320249377>()) {
                                    case 602972711:
                                       if (!var4.B(var7.s())) {
                                          label131:
                                          switch ((int)com.yiyiaddon.m.b.a<"sbb4ppshsqdny","BR0WCmAL/Ct+UPRj01SFJyi7ZzwaC6CqcfcoP5KuRDY=",4660319892741607515,-1769212990725582627,5416987741439119843,-5979786066820257391>()) {
                                             case 233749655:
                                                var1.add(var7.s() + var0.aJ());
                                                switch ((int)com.yiyiaddon.m.b.a<"s3l2wt06jyny5g","DfQEZM6ZS4jBXmCLtdTlI7CJtHhqhoiaOurT9t4JQsI=",-6518556217053565162,-5203304098838304966,8694977731415810366,-1509629109603333163>()) {
                                                   case 817827945:
                                                      break label131;
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

                              switch ((int)com.yiyiaddon.m.b.a<"s2sl7ax6huxdhl","7g8GxdcF5CruiYnCV7ytQ8fr65oPImg//RDtXKwWOCA=",2068843528855052468,5401950667057604017,2852598236418762608,2654893853486014178>()) {
                                 case -1070687902:
                                    continue;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  String[] var9 = var5.toArray(new String[0]);
                  int var10 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3jpcfdy3abslm","OnnOsC/0QFUvPC61Z6DKy4mVUguMu/o8HZXw5rLrcNw=",-5654037052382763083,6188757619783251725,1478770553224270799,-7792113838029187179>()) {
                     case 2144423455:
                        while (var10 < var9.length) {
                           switch ((int)com.yiyiaddon.m.b.a<"s18zmojniqxr6w","mlGLM7MnxGu9QYK11ycnzoEgbKTFHz6RSqfxQhidUkE=",-6495158230189372576,4972427913375993462,-6293015846110266547,-4916162507690873530>()) {
                              case 1493664612:
                                 int var13 = var10 + 1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1w35lusxovtmd","KpWZ4F4EXABJKSUuGE8JWa+s01FwGWXHOTioqjt1lkI=",-3435743723499275169,-1319346547213359439,6465979139006136404,2704199588699802207>()) {
                                    case 1944042875:
                                       while (var13 < var9.length) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1dzyfjcxynf5b","BXgjmUl3DZN2Dy4zyd26JEnBYcalhihtVhta7EYFFJ0=",146188878988993922,3204084247271719072,3494972284131050975,-934358074914970534>()) {
                                             case -87819564:
                                                if (var2.c(var9[var10], var9[var13])) {
                                                   label112:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1pl4mebfxh54f","5bSlbXQKjSl6Ip76HJT7D7C+uGQ4vJYoeU30zXgY79E=",-4901963692756745045,-8180743534369731334,467346687541729486,7692768445499000059>()) {
                                                      case -435392249:
                                                         var1.add(var9[var10] + var9[var13]);
                                                         switch ((int)com.yiyiaddon.m.b.a<"sro2rks8335mw","ez9GVd1EFb0KZxpRBFsLuOSCztz79YGAFrkj6WiyCiQ=",-7423619262293268465,749480360417646511,4675138725894535061,6192239339940421423>()) {
                                                            case 1916666414:
                                                               break label112;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var13++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2qcz1sj5uupg","it4/ZcUKF/3gXXsd9xW7ce4BubZxogk44hHHoNTD9FU=",-118908336197717014,161867594371000310,7809899343404457354,-4706490529163110930>()) {
                                                   case -1002894253:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var10++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3m1tpbgayur4i","fDCwTc8zIxfe+IhkBciXA5+Zt9eT9BV6caCcZHWDT2Q=",4850143798208716224,8758363681367528795,2284866121020556613,-3415087437188798466>()) {
                                          case 315552079:
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

                        Iterator var11 = var0.V().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"szceyrl63fk7y","tULpoIcLjBgoESvQHRcKK1+hqNh/Sn+XN3DIkkL+m4Q=",-3162392206784744410,3071291316799000251,-968864819051064226,6553496663158986868>()) {
                           case -1987804166:
                              while (var11.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sm0g0uq8iumkn","QZvvMtcjG4tRxLtxH3N5EjwZATClKgprrCoXcumBm6Y=",-5907197116540479846,-6553352051359032571,8211527842800535791,8744734563094541223>()) {
                                    case 1788429622:
                                       String var14 = (String)var11.next();
                                       if (var2.a(var14) == null) {
                                          label90:
                                          switch ((int)com.yiyiaddon.m.b.a<"sq36vizo3c6sv","KO08s3p2/4vDdQn/EycYfLJ5o1Hx+1IDSNWvddvvLR0=",-8995239185364990035,1317832097904928080,9131769938376503838,8594925506346710435>()) {
                                             case 1569852598:
                                                var1.add(var14 + "");
                                                switch ((int)com.yiyiaddon.m.b.a<"s3gwyvcsm7vub7","8ir13VE/n9yxmczTRHjsGlFlpvFy/ta7gvvcTSSl240=",-8615943856098893394,-4008879482417914592,8606418311130545743,-2919831891011314131>()) {
                                                   case -1167833039:
                                                      break label90;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2p5r4zic2kffa","yWH3EJMaCKAXOGSghAFC81mInbJf7K41JZUPcSB0ErQ=",3955420096445091548,2933659351715976649,193453895536672972,-6507625333240781671>()) {
                                          case -45415936:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              Minecraft var12 = Minecraft.getInstance();
                              if (var12.level != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s19j2gmau6oajo","pn/DSJnFk22Zn7ZZ/Mrl90vjIbS1bE0aiBbUP1AruC0=",320005484217273501,7257949591781648954,-8649572417979785685,7201263157043038668>()) {
                                    case 2001359251:
                                       a(var12, var0, var4, var1);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2u8irpd05e6oh","KbhhD+HgHcdg9A+2rK1YDCf5UhdhNn4lN/uLR6/Tfjg=",5505130142334657450,2573419253183255322,7398039086557595243,-7920394706249899028>()) {
                                          case -459548214:
                                             return var1;
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
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static void a(Minecraft var0, o var1, c.g var2, List<String> var3) {
      Identifier var4 = Identifier.tryParse(var1.aF());
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s8dnm0qrpka3z","90SrkMBVa71Q0RFjxw8TpSGM0IHu/OvPEfKHEVJCnYM=",-1059650750955973363,4490967063785456410,2870062478444212319,-4151072413086858810>()) {
            case 1010561061:
               return;
            default:
               throw null;
         }
      } else {
         Item var5 = var0.level
            .registryAccess()
            .lookupOrThrow(Registries.ITEM)
            .get(ResourceKey.create(Registries.ITEM, var4))
            .map(var0x -> var0x.value())
            .orElse(null);
         if (var5 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s25hvyk3sffmfw","mNpc3Y4IQC1DQxmeo/w/f3lSpkuHS4Swj2k3fmSrK7I=",2695191855566730516,-1086784602767424897,3604869640266082076,303801969371791231>()) {
               case -1561600925:
                  var3.add(var1.aF() + "");
                  return;
               default:
                  throw null;
            }
         } else {
            ItemStack var6 = var5.getDefaultInstance();
            Iterator var7 = var1.Q().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s1ixtu0nrgcupc","lTEioWFSTVUXF5SejHDFzADlG6/FVhSacbdrtRQnhGM=",-776819892093017301,-1451719482401196007,495538116522244185,-7185601476366584114>()) {
               case -370606322:
                  while (var7.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1t90s00lw9al1","xecGLRMen4NAzW9jeJIsMU5KsdINxBGKkEabSGb5s4s=",-6545560247059899045,-1890606558311621123,-4475195013793186638,8878861924498415502>()) {
                        case 1554936153:
                           o.a var8 = (o.a)var7.next();
                           Identifier var9 = Identifier.tryParse(var8.s());
                           if (var9 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"si13wglclskm2","6x3gPdqcL1bNi1EczVdmoaRFapqNF3uxW5x3mTHOnx8=",8207004885125726858,-1460971384175633497,170726367326520889,9138967476749323098>()) {
                                 case -1934640836:
                                    switch ((int)com.yiyiaddon.m.b.a<"sur9koqm470z7","NpgGma4tfqwxmzyzRQNRZsq3MqN6IaPIJcooFtlL7jc=",2456326821873890147,-1874134501892668151,3661040476959057584,3198139075370321825>()) {
                                       case -631372316:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              Enchantment var10 = var0.level
                                 .registryAccess()
                                 .lookupOrThrow(Registries.ENCHANTMENT)
                                 .get(ResourceKey.create(Registries.ENCHANTMENT, var9))
                                 .map(var0x -> var0x.value())
                                 .orElse(null);
                              if (var10 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"so5s1tl5lb63h","1Ews5MXhSRP4gs5juPTnJpBSaeU2msnLQ0ziFxIxc8k=",5073605394610581395,-86119857002934282,-2029159184630205216,4491726869516308028>()) {
                                    case -1279773507:
                                       var3.add(var8.s() + "");
                                       switch ((int)com.yiyiaddon.m.b.a<"s1co0hnzshezta","emWE7f4/rcsKjTDYLSURJa4SZn262kiWiNgPBL6Or1s=",3345856105089498040,-3257417578617754773,6568028244127797155,8085103763433434833>()) {
                                          case -93683686:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (!var10.isSupportedItem(var6)) {
                                    label43:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1pkco9bmlr660","ZQ3Rgo7GOYx1/IFyWKMx72Ehttd2AtLY3xzZaGJzOdM=",2218715098608186499,-6054806506638412088,-1061418613863651987,2850945362477277959>()) {
                                       case 1793289627:
                                          var3.add(var6.getHoverName().getString() + var10.description().getString() + var1.aF() + var8.s());
                                          switch ((int)com.yiyiaddon.m.b.a<"s1fwfv6vzp58ln","VUkKW5zGOM3D/PpFRVnGF3Qaqy1U2STew6HENld3aMk=",-5399641736893325797,-9031685773660178781,3011403116310679806,-8583363503736698688>()) {
                                             case -1915064669:
                                                break label43;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1rucjkdg8ezv0","XJsPGGncqEX8lY21bJvsAKw8XlnznPlnXxjQM9Bb0wg=",8813474708775821228,4335521601054883735,-7834695741792639366,2915861819189336001>()) {
                                    case 1338252504:
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

                  return;
               default:
                  throw null;
            }
         }
      }
   }

   public static boolean bc() {
      List var0 = Z();
      if (!var0.isEmpty()) {
         label18:
         switch ((int)com.yiyiaddon.m.b.a<"s30fs8qglmcbu4","78wan2ANhBTVfYAKY9zCegmibZDNJPGuXbBYC0BTE0s=",5046517523227624779,-3426033174809320457,5104271366369065207,-2881487298132960431>()) {
            case 181199640:
               j.error(
                  (String)com.yiyiaddon.m.b.a<"s2rlerjmsqayv4","70cjDbnkc3JYSa1juJsap8pDzmD4fKfpz3UOI3DE+m33gvEyGW+jIUVm9J+1nYJfU1RMjBglJSblW8g+VU1bCyq9MYz809aPG4M40g8AwQAMhW/4",5683792666572144459,-1127754646830347196,-4925654118729522363,1871340882658720388>()
               );
               Iterator var1 = var0.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s23pmz3uk4zo81","YVpcQSMKXbpOU69zTid3WeXwN05w+zn2zDZs2vpMUYc=",1787139810728377389,8659643755838761316,6732193036226373541,7496963992015879427>()) {
                  case 976953063:
                     while (var1.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2wtuzbohmgxrd","sdW1Wajb8q/kRyI6MNyw0DpzxvX7IP8E8sjf6XxeT6g=",7608696265215205793,-2950491198681203536,6229596841501605772,8500283156117540892>()) {
                           case 1596467203:
                              String var2 = (String)var1.next();
                              j.error(var2 + "");
                              switch ((int)com.yiyiaddon.m.b.a<"stpk5j2gnufcm","U1vZhRZcr2fW47DRyynG1/Oj/Hl1yEZcP34VqdPgwz8=",-7129536238779082602,8400356947951835627,-2969114461569408803,6858012573338249549>()) {
                                 case 879020846:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      return var0.isEmpty();
   }
}

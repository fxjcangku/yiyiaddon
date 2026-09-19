package com.yiyiaddon.i.b;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class c {
   private static final Pattern n = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"s1l24k93a9j4ra","8P9ldPoNkYjsNnX0sv3mLwv1zh8qTGVZX1Pxwbxm302/1HhpbNUU6wfFcLkQ7tZ15RH7ckRv2cKpxxrafu0eDU4m5zsHRAy4RlxUh3J2cpdJNiPf",-8314961187805851465,7915079372962982430,5835261064571756815,-5987038599449756365>()
   );

   private c() {
   }

   public static String g(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2no27mnuaso3","XIbsEo9M9Y1ir7/CzXCsmSHlrpakisqhZ+Cg2C5b7v8=",7849152409950093305,-7431728081872277862,9035203867580487278,2012678234418907924>()) {
            case -630765064:
               if (!var0.isEmpty()) {
                  return BuiltInRegistries.ITEM.getKey(var0.getItem()).toString();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3t981znbryh7v","hPwtOYB1TrEwUWQCyujUEdKn4NG/VWC8OPS6jmG81Fs=",-2931275976755943082,3026802311355865044,6594396245926184480,-6372178803434320639>()) {
                     case 81326909:
                        return (String)com.yiyiaddon.m.b.a<"s2ou2v2tfqm99r","3UNleGYFgCVElXUUeIqu6RepjkKB3c2o8i9/kIz045xDN7ohI9okpkx7B1U230kTFoeV8Vri",3000339509624438754,-1604928870935755563,-2147730143291610891,1002618202858022639>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2ou2v2tfqm99r","3UNleGYFgCVElXUUeIqu6RepjkKB3c2o8i9/kIz045xDN7ohI9okpkx7B1U230kTFoeV8Vri",3000339509624438754,-1604928870935755563,-2147730143291610891,1002618202858022639>();
      }
   }

   public static boolean E(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"ssdkygfhwrw17","ukt3llMZXk3r7LssEsjibkeaZ0tLCujMuzIgBlr2xg8=",-2962065286889403688,-4967541767942908682,-1613203670925072837,-1464618510474062346>()) {
            case 460586359:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2if3ww2iamrld","dpIMwiA35skqtAQ5jyD2BI0iKTRp0Ar3WtGC9SgR+x4=",1993097976131705019,-5287125290688965195,3658167518480832747,-3359782979642046711>()) {
                        case 836789003:
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     Item var2 = BuiltInRegistries.ITEM.getValue(var1);
                     if (var2 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s36yce3n5i87m1","ZnQ6oZxBowKBOAELchYyTGxxRSW37BGSBS+Xgi1+6I0=",-6962232271492632162,4637909391289563243,4720561751484290714,-2514319585793442990>()) {
                           case -452001713:
                              if (var2 != Items.AIR) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ygiwr2om68m5","ttbn5kTIwnVVc6T/BJY+Ox+jHNIuyd3xHY8D2yfbKq0=",-9162258776696163078,-7901705227497629784,2128499449567349359,-4782291696289534696>()) {
                                    case -1682371400:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3kq3buql7ai79","GcQlKhbTO/71N3u6T32eTXOFz178XOdFSanUkUhHP7A=",-7374232777428142035,-6625177836357193903,-7978315715351787454,9087632305357013918>()) {
                                          case 2117967057:
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

                     switch ((int)com.yiyiaddon.m.b.a<"sf4juk5bc4197","mBIF9G5vVid3x7NNn3uwJ485OQFw1RMmoxUTQx5Sr9o=",6224361338423728238,304754259674087622,-9071213032878753309,-62026108451674874>()) {
                        case 547569030:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s17ip0y0tel4r6","hozHNFFgk3YLsmtNG8/IaNEcWpnNe+1xSm24EIJ5qXs=",4868881947845963895,5322535620738537415,5270897619235930302,6695146635907983331>()) {
                     case 297032187:
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

   public static com.yiyiaddon.g.c.d a(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sovkw36bp71uk","KhWIGBKTmxotlvTNzGXXG4oZmnwQ1Y7OSMIu2GO9Dk0=",4312465410463242381,8442722322174774785,-2624857398959952937,-516314643558798129>()) {
            case -1728595045:
               if (!var0.isEmpty()) {
                  Item var1 = var0.getItem();
                  String var2 = BuiltInRegistries.ITEM.getKey(var1).toString();
                  String var3 = aj(var1.getDefaultInstance().getHoverName().getString());
                  String var4 = a(var0, DataComponents.CUSTOM_NAME);
                  String var5 = a(var0, DataComponents.ITEM_NAME);
                  Identifier var6 = var0.get(DataComponents.ITEM_MODEL);
                  String var10000;
                  if (var6 == null) {
                     label39:
                     switch ((int)com.yiyiaddon.m.b.a<"s3qxsu517p1piq","HnCKwVMDoc3aG6RSXk/zUBi8mSpUSlhogYHOWWJVzi8=",8946593369034338865,-8498468055269694057,2482763415651916883,-6213404819538728195>()) {
                        case -994432687:
                           var10000 = null;
                           switch ((int)com.yiyiaddon.m.b.a<"s1ye2jflvild98","Z4PYObt62pZCLdD7MmjAUk/fbXItCLnhlorJ26MYC+4=",4883142398155110605,-7387519532624578689,-5585261037881623937,-4912217383847596044>()) {
                              case -1678096603:
                                 break label39;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var6.toString();
                     switch ((int)com.yiyiaddon.m.b.a<"s3o3rqhltrcsu0","R9dosY2HjmgQ9lfYwTAchTGD8wLVPTU684q/guJUSOs=",2341124493340337726,-6696897103755773287,6150041268845677867,1947681213969787008>()) {
                        case -449440612:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var7;
                  CompoundTag var8;
                  label51: {
                     var7 = var10000;
                     var8 = b(var0);
                     if (var8 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2z6fpifswpihd","0uGV9YrGV8m7/vmr3bXZRiSnO8ptd6TfwWq0xZeQ+PI=",4020440560514018192,-7123537277665025961,-25751601044534788,-8031368761492857227>()) {
                           case 1904047112:
                              if (!var8.isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3rduz0hnc7vdq","wOvtveqBZhUo9ub/q3NLa0LVLx8W9UAmUepHMJeDoY8=",-785865652216802334,-1612129661237518295,1281170579072669758,5570051138309890265>()) {
                                    case 365238540:
                                       var10000 = var8.toString();
                                       switch ((int)com.yiyiaddon.m.b.a<"s1h601f0flmbhf","+ldFTGiXw8PzWR4f56ySW4bkzgD9KY5IXsIKdbgXJxY=",3373231516561480455,-7977127659939426652,6561447172693234539,3631106842126264532>()) {
                                          case -48845627:
                                             break label51;
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

                     var10000 = null;
                     switch ((int)com.yiyiaddon.m.b.a<"s2d9udha6dbvtm","oL00696JAJvGpjqrySPXCglV/W0jDgkvyjP5+fLkkp8=",-4948938773674841061,3149986967142956995,-4772941560548604414,6392520426443246340>()) {
                        case -558931982:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var9 = var10000;
                  String var10 = a(var7, var8);
                  Integer var11 = b(var8);
                  String var12 = bR(var10);
                  String var13 = a(var4, var5, var12, var3);
                  List var14 = b(var0);
                  String var15 = a(var0, a());
                  int var16 = dz();
                  return new com.yiyiaddon.g.c.d(var2, var13, var3, var4, var5, var10, var7, var9, var16, var14, var15, var0.getCount(), var11);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3q423hrsvy512","1QPlUC1Fms7KGDK8Sr0sASqMKnuYYHy0PeSx4WIAr90=",1967534499303088331,-6347169193137117124,-1052276417331376284,-9150805200928232484>()) {
                     case -1058998780:
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

   public static String h(ItemStack var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37eufm5164tje","eonoRB6jTQbhkbEPefyuzX0ciJsfAeniv0OviQfknnw=",-1957024828261751267,7300918741496251553,-7753135310705081843,-7216665807686111365>()) {
            case 1121185056:
               if (!var0.isEmpty()) {
                  String var1 = BuiltInRegistries.ITEM.getKey(var0.getItem()).toString();
                  String var2 = a(var0, DataComponents.CUSTOM_NAME);
                  String var3 = a(var0, DataComponents.ITEM_NAME);
                  String var10000;
                  if (var2 != null) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"sovnjoanatair","8qz8plepd3AtQxazwnx9FPhyBLKNxpY9eeXAwdoqkX8=",-7485141260142095190,6665901238567224923,5330671185453716232,-4621378608039591625>()) {
                        case -1014211225:
                           var10000 = var2;
                           switch ((int)com.yiyiaddon.m.b.a<"s1dl4i376ttaoi","yC6oYG6Zot2FUXIQuuqqIcmuuD+0oGtHqYo4e003xWs=",-1818041054016942063,-3501761503028964178,3087777579899545382,-3742515800048800473>()) {
                              case 1009434311:
                                 break label22;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var3;
                     switch ((int)com.yiyiaddon.m.b.a<"s3lphdkgg3wpj8","n1TKK0v0cRfXlAKdqshaY5le9IHwpncXO03vECSNokU=",1147822523567678210,2717798724921244476,-5322519546584345447,-7305756606655751021>()) {
                        case 247500946:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var4 = var10000;
                  CompoundTag var5 = b(var0);
                  String var6 = a(i(var0), var5);
                  List var7 = b(var0);
                  return com.yiyiaddon.g.c.d.a(var1, var6, var4, var7);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3bkpy2ft6xcfm","IJIZLCc9NiVcBXsBejmGbHUPIFxa8Xwm1nVbmJEtjMU=",8881719546070455059,202623197340915270,-7834894135549286515,-2732398309293679440>()) {
                     case 570216440:
                        return (String)com.yiyiaddon.m.b.a<"s2mlujze6dx255","aykMdyCEPj+eMxi6TCx9RtiGY4HgIdBq7dMWMw==",2553252738891519003,-8825933787838238040,-3246982462113377452,4049016391241338441>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2mlujze6dx255","aykMdyCEPj+eMxi6TCx9RtiGY4HgIdBq7dMWMw==",2553252738891519003,-8825933787838238040,-3246982462113377452,4049016391241338441>();
      }
   }

   public static com.yiyiaddon.g.c.d a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mkkk3vdhfucf","OSV3TjyL2i/3PwZ4e5x9o+acwzCtYshpSl9cuhjJeyg=",5586811732136559687,-5560598378655322566,-2011796424776520048,-5598985174819493670>()) {
            case 878154147:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s34ab669k8ykzi","Ar28FW/uuid9pT769OosrwgOrh7H9PcN4P4ziOQ+o9U=",8757888178108099222,-6622713417699682788,3364069608987379282,-186834141926553047>()) {
                        case 352708672:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Item var2 = BuiltInRegistries.ITEM.getValue(var1);
                     if (var2 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1hyo69d1dy8lq","eDSB3LuwUxg89ovExikjrQ18kJRqbbUSRzs3yvBES7o=",-8377083326989970339,-4832115041500742852,-4424693604413515500,-4050228084800116911>()) {
                           case 1721276231:
                              if (var2 != Items.AIR) {
                                 return a(var2.getDefaultInstance());
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1o7i3gthjzh1e","sQ19NPePw/r935l1N1ysBS7u8kS906r91vp6AANoI30=",-1200979653992641434,8041327087719147441,126173927020760073,6431485957163806241>()) {
                                 case -399139429:
                                    return null;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2cu1i0aqw9qw","o7xDlS9kTWieLdC474wt+nOpOUxyLh7juIf7TRUA0Tw=",5651521474440423401,-7830273385196978555,8966071848968377320,2687155166707451244>()) {
                     case 892616405:
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

   public static com.yiyiaddon.g.c.d a(Item var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tkvdxqd5j0hg","eATGfhBVei4wEo4tMyl5gAlHfVI0n5fusH4tn4mpdPc=",-8661491847649105121,3355102563600403026,-5637064294139462397,-8821581634084873254>()) {
            case 748872013:
               if (var0 != Items.AIR) {
                  String var2 = aj(var1);
                  if (var2.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sg72ktluti59i","KeSlp/UDVrucitCNeeuDrYu1qzQ6z1P5TvtN+cghGSA=",7603027618465966135,-312797244895638846,4624397557415055778,-3823053598426469962>()) {
                        case -1616222782:
                           return null;
                        default:
                           throw null;
                     }
                  }

                  return new com.yiyiaddon.g.c.d(d(var0), var2, c(var0), var2, null, null, null, null, dz(), Collections.emptyList(), null, 1, null);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1fkop1mnlfjtg","DO+5knTo/7VQ38xuxCe1WraFrblJp84EEuAygINhOXA=",3473567287025876040,3624410541658938146,518707256560619413,6249178872581658146>()) {
                     case -2008485622:
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

   public static com.yiyiaddon.g.c.d a(Item var0, String var1, String var2, String var3) {
      if (var0 != null && var0 != Items.AIR) {
         String var4 = aj(var1);
         if (var4.isBlank()) {
            return null;
         }

         String var5 = aj(var2);
         String var6 = aj(var3);
         return new com.yiyiaddon.g.c.d(
            d(var0), var4, c(var0), null, var4, var5.isBlank() ? null : var5, var6.isBlank() ? null : var6, null, dz(), Collections.emptyList(), null, 1, null
         );
      } else {
         return null;
      }
   }

   public static List<Item> l(String var0) {
      ArrayList var1 = new ArrayList();
      String var2 = aj(var0);
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2oy288figuivo","PCJ0rmmW9JjWwD7sufmPl9W2dRtIizPY3ePyjH/Kxks=",3439890029665146669,-4719043962643317908,-4018449023562258970,4640882733253338623>()) {
            case 1447614641:
               return var1;
            default:
               throw null;
         }
      } else {
         Iterator var3 = BuiltInRegistries.ITEM.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3bzks6ps086xf","rTgL19LW8JVEBWzEltMu2+d/rvS5LXUGiES+kLNM6gw=",-4173902194019564972,-8880648350126515865,1680455080393175100,-1904886805542450457>()) {
            case -365931844:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ems2u7v0s05l","QMWrClZOUnwRM8AVRZkoli1GmiIvBB6WA+gye/cnlNM=",4991124204642090134,2408234530482778320,-2013396472104779255,-7442531188781603080>()) {
                     case 188723608:
                        Item var4 = (Item)var3.next();
                        if (var4 == Items.AIR) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2vdi1zh2b7mne","D95ums0wl/mNVAOfWgeJJoAJEaH//VgzXyyDgHZLfPU=",1004294505855363220,3198101342589233674,-6749609341521570689,3628035900954367818>()) {
                              case -1796431289:
                                 switch ((int)com.yiyiaddon.m.b.a<"s8w9ysxhu8ea0","Uu1QY2rUIGrZdzlKI8qIIMg/SdnhVv0oFk5qZx7Pa80=",-6906407613296482664,2438686471945106992,-7423854733158047040,7498245904190078485>()) {
                                    case 980035184:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (aj(var4.getDefaultInstance().getHoverName().getString()).equals(var2)) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"s1a0fkm354r56y","Ac7wHtfyUz1YAuhoIq+kBr1692bgtcufjnnKshztiTc=",3462924421131786455,4839923778708590081,-5620948569788686538,8727720197975497986>()) {
                                 case -1467193669:
                                    var1.add(var4);
                                    switch ((int)com.yiyiaddon.m.b.a<"sz9ray7u1uff4","d3DoqB2CdAs0JWYeIDAm5WqsHEXR+HPaHDT+zYcu/w4=",-607711166862696435,-1755648757242842456,-1175130557419096767,-2784570682778879395>()) {
                                       case 235743908:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1ielrfg7wtuvc","PTWWaoZLH7CXVMSHMHQXCuiE+vXxNHvSdAEdC+rhBK0=",-1897991155576220767,-7536503609173305971,-963549656543030729,-7329946406201141174>()) {
                              case 304698576:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               }

               return var1;
            default:
               throw null;
         }
      }
   }

   public static String a(String var0, CompoundTag var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"shapom65r12i9","MYqxdh8Jw2LWIPrwRquZg0JPh/KOibP638/2NolL/Sg=",1177218695498141124,-1373859872980476608,-4106857058075973967,2496061084410705596>()) {
            case 266639827:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s11fbtjkjyvpgc","oWRsbzUabtuZSZ0ia52PUtF8xP05V2VqaYHkORUpLQw=",-2004649828931369177,6330272860268317639,5541283240556790150,5863131759723287080>()) {
                     case 556907611:
                        String var2 = a(var1);
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sqfx9708u2bue","2FLv97yH8TzhhT8VG7HiA37CbNDGeNg0pIU/hYDjisk=",-5457042901395215590,3573076205959385050,113552335267173423,645537846042858438>()) {
                              case 751093391:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        String var3 = b(var1);
                        if (var3 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3idn2rqt9ypl8","RyQlpf8wf5sqDgL6NjqVWoRmz7yXlu+gBwhRad/I/vw=",-1607886603889951294,-4964569003963300329,2017900340922608809,-5060521150819460241>()) {
                              case 66001898:
                                 return var3;
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

      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sofu1bzb67rl","0H9QURxOaFX/EBBMgegEngj91/3Wys57ekPosHVK+uA=",3607102276881113831,-1761025434973073641,-8452242452228734152,-382279664161295207>()) {
            case 1940275962:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sj7w5dd1ur0zl","5VR0jlGLGf0Uwvz5OrbA5E6GTECQ18BSUyDZKsbfe0M=",398141077074736977,8227972261472048455,-5867482461403337250,-1331895068449291107>()) {
                     case -1876463762:
                        if (!aI(var0)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1zgqft1iml8p4","esxi9v5eOvdLAUKgAPuKFQmgJ5ex8Vb6Lm3UHvAipZY=",8202372299924000697,-3713882494654092929,-3462174272172105213,2555904900545873737>()) {
                              case 1555032910:
                                 if (n.matcher(var0).matches()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ydepp2ej2k7w","vofSMoMcUky2LF3oD87PFxXiKm6t6KDmpIXc0fIfpC8=",-7352297192412539963,7520676868589826052,1529717762094902848,573393450110905236>()) {
                                       case -839440953:
                                          return var0;
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

      return null;
   }

   private static String a(CompoundTag var0) {
      Tag var1 = var0.get(
         (String)com.yiyiaddon.m.b.a<"s3mpwfjywvkpeg","qx3dLLpWjDilr4cYzrhPJFQuMseuvgXKRVAa4gro9LqvsnAjsBJtjbDTBFvNtMe10iJpyIZ7EXI=",-1350550577724053766,4678204519006122652,-4203380748211635305,5116540389134713251>()
      );
      if (var1 instanceof StringTag) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qfu0rige0oqy","rdrapyHho+a8VXAT18KgiBW1Aw1j39qIUi5fEFpZ3eU=",4200796103379807964,6297981190459840318,-2284238962032329474,6957563921321127721>()) {
            case 1761423845:
               StringTag var2 = (StringTag)var1;
               String var3 = var2.value().trim();
               if (n.matcher(var3).matches()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sl77il4jw9zjr","OaLCD4uzT6HijWsnX/x8qe9pF1USfe+59oChkcKpwAg=",-4170432221884735130,-6987535663974371118,8558704062369400366,-7880153185583442036>()) {
                     case -1388716258:
                        return var3;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      Tag var6 = var0.get(
         (String)com.yiyiaddon.m.b.a<"skfyp38knf3f1","A16Ihyb70SbrAYZhnt3t51P7VJrVuMLokG8G2/+k2+rg/VsRnFYlAQOVu3sk6QQvypI=",4117546484746217179,-4691413349465094677,-3349370840508611865,7459386984221458280>()
      );
      if (var6 instanceof CompoundTag) {
         switch ((int)com.yiyiaddon.m.b.a<"s2oaza79ba86pg","6K/fdMYdSRYiPQpozxW8jknVK2dFlX+H1Kw32t4a1mE=",7811047451184600682,1294295880367406297,-7707626025540808825,-4060260797309867203>()) {
            case -1045914942:
               CompoundTag var7 = (CompoundTag)var6;
               Tag var5 = var7.get(
                  (String)com.yiyiaddon.m.b.a<"s2ka5jxlj7d95x","sTxjYTFrQdBcxNZBauQ0K360R8Sb/O6PnU61IIhhyEI=",8768154458819980412,4565234065135292961,6692043722260274757,-8743024909761569333>()
               );
               if (var5 instanceof StringTag) {
                  switch ((int)com.yiyiaddon.m.b.a<"s23mwn0atm3wf8","yvkH1nEaLQR3jK90KcdUYVhy0og51rKTEiMU9vchJlw=",6666058877160339691,3138951951412925984,7315309588700973493,834427198863774339>()) {
                     case -520406800:
                        StringTag var4 = (StringTag)var5;
                        String var8 = var4.value().trim();
                        if (n.matcher(var8).matches()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s335nbjd0u5omz","LC51rYcpjXEWke1a5+xm6SMh2i/xrjmhYptTKm2SOD8=",-5960484741268073432,-2643076740517538967,-4297039220917333527,5943601243267126533>()) {
                              case 580251400:
                                 return var8;
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

      return null;
   }

   private static String b(CompoundTag var0) {
      Iterator var1 = var0.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1otyhm1ktto5w","jZHU/VuKPDYnDuaUPXutJ/tBoPVaLtKwuXmdVDlK2gE=",-6605195964620846631,5190349947607678473,-1349662294205572462,8824498635001875215>()) {
         case 1809835430:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"stik5zj9d2mmy","oon2JDkUl3UODK80n8147l6vVdCGMajvc6fqOuJHHxc=",5809608019116427959,-1424874914772038739,1689862952340766162,-7679304160357595259>()) {
                  case -1835839322:
                     Entry var2 = (Entry)var1.next();
                     Tag var3 = (Tag)var2.getValue();
                     if (var3 instanceof StringTag) {
                        label42:
                        switch ((int)com.yiyiaddon.m.b.a<"s1v4xzc1xikk47","s2xntahkI29uVmzmCfEsq9MY9H8TBYwaKPpOmVi3WDY=",5461154881216267099,-7134470489700756460,4368355983838592534,8122494229035117836>()) {
                           case -388845316:
                              StringTag var4 = (StringTag)var3;
                              String var6 = var4.value().trim();
                              if (n.matcher(var6).matches()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2mqdmofu12x88","dCU0mZCE4F8sY/DCJh5RWb4l/UJ0/1XfCvbCkb/bhk4=",-6437595440832242113,8748973432606152412,981800219650301594,-7078665437186859046>()) {
                                    case -39250458:
                                       if (!aI(var6)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1lkgpvw8pf31z","s1i7WGqzHyibcK/MDH8aMZIJS4xkc5sE79h7hHBk6m8=",4904784475119003137,-3530196521033825513,4693384973582952686,3237919580111704634>()) {
                                             case 590989103:
                                                return var6;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s4mxthwrrb12k","9xCG7RBIynO2ZunSOHOvQ1zzpnw0sR/9IctENd4nVx8=",-2436479296794431459,6014457847551602859,7005987357716011346,6851855813204299336>()) {
                                 case -1264606552:
                                    break label42;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (var3 instanceof CompoundTag) {
                        switch ((int)com.yiyiaddon.m.b.a<"sxy2czyq200k2","2oW5SF3CdmkSaBRr+9CwliyLyCknunhUdppG0N3fNGU=",-4410863603539095710,1156386105368179102,-8418430136444132398,1237147403904330236>()) {
                           case -503694540:
                              CompoundTag var5 = (CompoundTag)var3;
                              String var7 = b(var5);
                              if (var7 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s654n8zsde07s","AKhwWikMFMZRbJpY6fCzK6SKIQVoz/WuNxof8fcoM+o=",6040607353752742672,-8274332547845766084,-7285213548235673631,6879660054545722664>()) {
                                    case -588222423:
                                       return var7;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2ct18rjcg44bx","1eEwmCiDc2kWxdF8qWsdUYXE3Ee8M0AadgCV502thPg=",3053081851277902728,-2078942772889968247,-7491372882384201077,3379885849150771900>()) {
                        case 410925116:
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

   public static Integer b(CompoundTag var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s38tlmncp0yb8l","CsTxhNWDR+rtRX4IbYj8bZAUfo9aCbAC2irU11DUgfI=",-405777285468996176,640521659816232846,-4500420178225146669,-9174086206037986615>()) {
            case 830710590:
               if (!var0.isEmpty()) {
                  return a((Tag)var0);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1b1h9h4fnybkc","7E+H0Jils6RmW6+QEc9NA8QHKRpe3CHUavXZ1pwCS74=",354224362500691087,8876419486698301230,-8161366359893708619,4973703272364483300>()) {
                     case 1691719701:
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

   private static Integer a(Tag var0) {
      if (var0 instanceof CompoundTag) {
         label52:
         switch ((int)com.yiyiaddon.m.b.a<"s1h6pwolzygf79","s1WKfaKubWclxDvXnkxBHhMHIdByWNaI58kKHqvFKGQ=",2559605576009268602,-970691250374701789,-7965755702609177718,1798084742595164451>()) {
            case -355219830:
               CompoundTag var1 = (CompoundTag)var0;
               Tag var2 = var1.get(
                  (String)com.yiyiaddon.m.b.a<"sybjsa59si7g6","GS3LGolaR2pZm8Zu4RYq9ur62WbZhZCSDUgfE+dPFCQTX326HlA=",-3961584434781665172,-307517277719218502,78784818037941763,-6171614752270191599>()
               );
               if (var2 instanceof NumericTag) {
                  switch ((int)com.yiyiaddon.m.b.a<"sqlhalhzi31ua","kVLhLDHhfYOsqQwE9tCSyZGVooJnvmc3at7SQvuth5o=",-8421647232670710898,-7377759847690257078,4203176545400246534,6200386613876666005>()) {
                     case -458697083:
                        NumericTag var8 = (NumericTag)var2;
                        return var8.intValue();
                     default:
                        throw null;
                  }
               }

               Tag var5 = var1.get(
                  (String)com.yiyiaddon.m.b.a<"svy9e2ba763q5","RRfZ2JOdhmpDQ9hlCaQFxGDlKjr8uoplGb7JQR/0DJVfT67LyU2wIYWsIpbTV2zNGY0=",-6432650274947986998,-3422545563323630441,81186355224835800,621443898180271284>()
               );
               if (var5 instanceof CompoundTag) {
                  switch ((int)com.yiyiaddon.m.b.a<"s12furd854as3z","1p4LxOa0gc+UqfVkZ72sDvDgpm2OenvObe95U6P168A=",-7229967936535789852,9164756088695324877,1141455442597533383,1351209219011299083>()) {
                     case 2089181489:
                        CompoundTag var3 = (CompoundTag)var5;
                        var5 = var3.get(
                           (String)com.yiyiaddon.m.b.a<"sybjsa59si7g6","GS3LGolaR2pZm8Zu4RYq9ur62WbZhZCSDUgfE+dPFCQTX326HlA=",-3961584434781665172,-307517277719218502,78784818037941763,-6171614752270191599>()
                        );
                        if (var5 instanceof NumericTag) {
                           switch ((int)com.yiyiaddon.m.b.a<"s5v075dnbu0sd","ScIRTkh2hp4X33bfTkbfub3kF3XKKeveoNV45kRIpkE=",-3758909977237145863,9167286490076319728,-541520893347412381,6057508302799780661>()) {
                              case 612554988:
                                 NumericTag var9 = (NumericTag)var5;
                                 return var9.intValue();
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }

               Iterator var7 = var1.entrySet().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s1554rsicmzs46","MFwRs58GA1mrrU+0t3D9mbItbpQEuRypZ4/lEJshJUY=",5704721499601590046,843828049759841422,-7173579214791922291,-5465925282053515039>()) {
                  case -429628122:
                     while (var7.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"ss5mlmgi5fj5x","LN4rkzJxnk+5FduE5oeV/1tr6wJbGhpWeAo/+kNIps4=",978143726854135830,1315056046507371241,-4162179833009018600,-4757031632295869887>()) {
                           case 1950848936:
                              Entry var4 = (Entry)var7.next();
                              Object var6 = var4.getValue();
                              if (var6 instanceof CompoundTag) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1jfefedy0wxea","geNJLR9+jhKG4LR9DhTNw4khrRvKsg890e5Ew070Mk8=",-606504035111057108,1706342332150331968,-2270887691260825112,5896125737818635016>()) {
                                    case -2088936702:
                                       CompoundTag var11 = (CompoundTag)var6;
                                       Integer var12 = a((Tag)var11);
                                       if (var12 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s19xn6kkgln2hv","MpMggShjT+ty022iTfRmUhX+t6SD4OpzJb0EASkfj1o=",7541784910058895940,-3064251209039235893,6719967546772800032,-7614093269995988551>()) {
                                             case 1212391117:
                                                return var12;
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s13m8j7xta8nur","MjJA0qSkHuokjEjcZe60xqQLLqII3oDGlF9pq7wK214=",-5512137046946703795,-4298374857787012328,-778323611442473750,-4880738435458978269>()) {
                                 case 1343217487:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break label52;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      return null;
   }

   public static String bR(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tpqs78pqdg2o","wsnaNXqat+Sw9o7DrQcf+ecEJz59Yl5DivXEBXsVCbk=",5240232326820543125,3263988281186972856,-1131479777531643568,6357895842375566978>()) {
            case -1621319657:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1kp5f2cfqss49","OJJhphfWyC01GOf6GN0z2eMRxXWJWQY1gIdcNE5Mph8=",-4848622219770927059,4436581864388265147,-4548082637492576655,-895928714808013284>()) {
                        case 1377150606:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     String var2 = var1.getNamespace() + var1.getPath();
                     Language var3 = Language.getInstance();
                     if (var3 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2y8jiji4uidtz","5Xy+zAjEneIetjyq13+8EP3PHBZiYgcUELUjSdExuxg=",1741855345305886554,7947733410169696261,6962227746716807069,6537439314538843833>()) {
                           case -1059537597:
                              if (var3.has(var2)) {
                                 return aj(var3.getOrDefault(var2));
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3ted9rn299dxk","vCQQjSGPb9g8DYJ4will3PBe1fMdDKhKYg5Umu2kUrY=",-2162802090510466239,-417666731716562218,-3864705218529299429,3891427024155300463>()) {
                                 case 1172354588:
                                    return null;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1r9jckqvmql5f","JbDQF5aTsZeunuu4FM0aR5AVkgTHfi0qGWqlkl6/x6M=",8762166998887263532,-7608934636539123162,2065909808037333719,8997540599441552210>()) {
                     case -752949664:
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

   private static String c(Item var0) {
      return aj(var0.getDefaultInstance().getHoverName().getString());
   }

   private static String d(Item var0) {
      return BuiltInRegistries.ITEM.getKey(var0).toString();
   }

   private static String a(String var0, String var1, String var2, String var3) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33mcnlfoftka6","9YT/Tj/5cqv+na6NHB/A1++rVXgKaftMwS9jGdDdXKI=",-8104116652820114795,-6200834930273715335,8119050451877431938,542706173624896368>()) {
            case 1301474612:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1kpsjgkhhmbmz","VW2M0ngYJP7Cy19/diUf/DQflxKelniw+aTo270B630=",-7695155759298402712,7056158418209899495,5808760960337795424,4921829792457196125>()) {
                     case 1236833881:
                        return var0;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3d28oetiohtq4","4+9mkFqJX/ijwI21QLKle4Bva2xfOvv1H6t/ZmrrPX8=",6531974765377748258,-7363589538187160214,-4345110013770678321,-3962184975567139345>()) {
            case -114886041:
               if (!var1.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qtvwyicvn3q","qz6SBfAEpLNjBKeXor1dgbKVOZGz7mJSc0af3jXwc+4=",-3979969622541422523,8068242326393116815,1327382091367208710,-4706081552614689485>()) {
                     case 939923486:
                        return var1;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1woahwld31n9j","Kh/iEC+wkMGOg1vmvR+1XW852ppGZItM5fI5uEkWWNY=",-3261535935753831183,-4178905742495490649,-4240348105438102263,-938006594608842486>()) {
            case -822136245:
               if (!var2.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s20bcif16xmlpy","zYR/R7M0ha/6OA/qNDTjo39a/VL8X2G9MjniSjQFf0k=",5342381016764159912,-4182376343167515005,2593235521837843032,8997418791729312738>()) {
                     case 1677428713:
                        return var2;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return var3;
   }

   private static boolean aI(String var0) {
      return var0.startsWith(
         (String)com.yiyiaddon.m.b.a<"sz706rb0tigiz","VxCNYT4B5dSOzXHhlmOOpHtiJ+H9vxfD8WEzhWoPKLo1Yf9zx1SJd/9miDlBy/pL",7945956772302892050,-6116838070552627247,9216529482760472270,-1886716276010644996>()
      );
   }

   private static CompoundTag b(ItemStack var0) {
      CustomData var1 = var0.get(DataComponents.CUSTOM_DATA);
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"syqcxohsdpji8","BEPpXEjPUck++egsKzvC4tASABLhnzOM9YkBeKnYtI0=",6131870736531217118,3091437716898230435,6158567271214264549,-5615669813633902094>()) {
            case 58916780:
               if (!var1.isEmpty()) {
                  return var1.copyTag();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2rvpp3j0domvs","vHj2ORl7HQ2X6uj3r0UNuQeEJ5dRMNpoEhThwq/KlkE=",-3220577590290990512,8391665372778416825,7393456458410550306,-6800418524859743695>()) {
                     case -239690401:
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

   private static String i(ItemStack var0) {
      Identifier var1 = var0.get(DataComponents.ITEM_MODEL);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yes1xujqfp49","0WQ1lXVxfH/JeAE0L57o2dtsBuoirkTPfRUD1coFsXE=",6512489865783787602,6089615196408448850,-7231323242097561426,-3652140850336145517>()) {
            case -192333696:
               switch ((int)com.yiyiaddon.m.b.a<"s3r9mf8lviwqnd","j6aeNVK6S7NPsd4/ksm/bwgLXf7oXtHdnssJdv4n8+o=",-6903960032794405652,-6060556768171325666,6182043683825779870,1124914001467679641>()) {
                  case 1391831047:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var1.toString();
         switch ((int)com.yiyiaddon.m.b.a<"s1xamxqscgglzx","eWJ39MyxPzrhWzJE30kbhIRld8CbUEQCTUv2vVq3jvY=",-1390759020025421341,8758292623795798035,-8179258496702530154,8059196624647699671>()) {
            case 1896078261:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static String a(ItemStack var0, DataComponentType<Component> var1) {
      Component var2 = var0.get(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7b5ehcpne8ay","X1A8Gr/shg1OslvD2XUnvcC1pN8SreNl6ati0samtys=",297979162829090652,-2469612570980118924,-7243528493621290927,8618944635016529598>()) {
            case 1440257297:
               return null;
            default:
               throw null;
         }
      } else {
         String var3 = aj(var2.getString());
         if (var3.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2j79z27mlm1ro","LDdp+FVfbCWVlQlO67IwLjtldH7qrV27+1UQDPniEY8=",1206860305725547253,-2756722297728329553,3217884220743719860,-6979708325960815320>()) {
               case -1824839151:
                  switch ((int)com.yiyiaddon.m.b.a<"s2sqxglcx9hgc3","gDgKZMhouxE/Zj3FseH7oCMyLXbK/O5SQEoS1TYaQ6E=",9149661190338012953,-1308384940047787126,-5198294668439519723,-2092135979195352610>()) {
                     case 1260942764:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3uo4s8dfxgaqf","tj1JPScO94hW2WEgyt/zVB1uFADd8TF3BWNqETewzpU=",-3341191491219230424,2418509693289328515,-9066489854096584510,5816897901660882680>()) {
               case -258159051:
                  return var3;
               default:
                  throw null;
            }
         }
      }
   }

   private static List<com.yiyiaddon.g.c.d.a> b(ItemStack var0) {
      ArrayList var1 = new ArrayList();
      ItemEnchantments var2 = var0.get(DataComponents.ENCHANTMENTS);
      if (var2 != null) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s2kifutymnkmh","K54NSZKtxbAE5zKIhlDRR0aOyVoONWMMZqYQwZ1255I=",-3895694132289551758,-1836793793970129088,-798522501018437639,-327157233544693174>()) {
            case -1630888077:
               a(var2, var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2p8knsx19atmo","+nXCbMe1NEuhHrP1jbN2AhfYcp5DRl1P2P2e3TKqiGc=",-73427089554485822,2390848286393719617,6083314743410052093,-890266169484339152>()) {
                  case -141611214:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      ItemEnchantments var3 = var0.get(DataComponents.STORED_ENCHANTMENTS);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2t1oelxdnsgt3","RSnar76ZZL+qtJFPZDPEYmXy2I+y8ABCGzEKk1wx7aA=",-51203049719432693,-8231193174893949414,-6815426572402772811,-8871762587580699205>()) {
            case 1797630423:
               a(var3, var1);
               switch ((int)com.yiyiaddon.m.b.a<"s1uict7l7bepbf","VMq5FudGmloX6ayKjR+GgICgGIaxwYksxAqJrSDH/AM=",-4784429803823299306,-2207354766523557524,-9150126569895177819,631866020234610795>()) {
                  case 1023935200:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   private static void a(ItemEnchantments var0, List<com.yiyiaddon.g.c.d.a> var1) {
      Iterator var2 = var0.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s55dvvqlam7th","IUH2BeYPrw21xiAGV8shlOnm2aVfEXiNNxTaBhcp0IA=",-2447693253938241984,-6208352707625030545,-7734234744380754733,-7667444670957717464>()) {
         case -745686679:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s10pa5puamjq4k","DsOGX+8o+RYbUyyvaxPdaqYm48NtuVYhFixVF0DLSvM=",-9159726844880614932,-3524381460165371942,-5160171614115405588,2576792741218378929>()) {
                  case -301870848:
                     it.unimi.dsi.fastutil.objects.Object2IntMap.Entry var3 = (it.unimi.dsi.fastutil.objects.Object2IntMap.Entry)var2.next();
                     Holder var4 = (Holder)var3.getKey();
                     int var5 = var3.getIntValue();
                     String var6 = var4.unwrapKey()
                        .map(var0x -> var0x.identifier().toString())
                        .orElse(
                           (String)com.yiyiaddon.m.b.a<"s2mlujze6dx255","aykMdyCEPj+eMxi6TCx9RtiGY4HgIdBq7dMWMw==",2553252738891519003,-8825933787838238040,-3246982462113377452,4049016391241338441>()
                        );
                     String var7 = aj(((Enchantment)var4.value()).description().getString());
                     String var8 = aj(Enchantment.getFullname(var4, var5).getString());
                     var1.add(new com.yiyiaddon.g.c.d.a(var6, var7, var5, var8));
                     switch ((int)com.yiyiaddon.m.b.a<"s3rwipkrk790c6","d1aF3N5PD9yaEa49yalN/5NqmatG8RopqTCnDN31Lx8=",5206252259249270583,1842595509028285788,-668093555296282725,-1084222281632603638>()) {
                        case -1468451582:
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

   private static String a(ItemStack var0, RegistryAccess var1) {
      if (var1 == null) {
         return null;
      }

      try {
         DataComponentPatch var2 = var0.getComponentsPatch();
         if (var2 != null && !var2.isEmpty()) {
            RegistryOps var3 = RegistryOps.create(JsonOps.INSTANCE, var1);
            DataResult var4 = DataComponentPatch.CODEC.encodeStart(var3, var2);
            JsonElement var5 = (JsonElement)var4.result().orElse(null);
            return var5 == null ? null : var5.toString();
         } else {
            return null;
         }
      } catch (Exception var6) {
         return null;
      }
   }

   private static RegistryAccess a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5591sl910m2t","hWMNJ9UMiunjdkONWKDjGTjXNnkZWJPxJr8LevvBRy4=",7180001724286928099,252965879431756990,-5910745359693040085,-1976424011370874949>()) {
            case -1002974050:
               return var0.player.registryAccess();
            default:
               throw null;
         }
      } else if (var0.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1s1yiphwmixro","nZTvWG4GFqT6+5pw5kBNocqresLeUh+edkAy6kocpRI=",-6385315837137762495,-4992027109348408135,-6404364584993599276,-9131279994272465904>()) {
            case -601735953:
               return var0.level.registryAccess();
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static int dz() {
      try {
         return SharedConstants.getCurrentVersion().dataVersion().version();
      } catch (Exception var1) {
         return 0;
      }
   }

   private static String aj(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nrg49um1xn2z","G0ZNP8uLvr7kVm0aYRhVZ227Ah3Ozt08wmTcLwekHNk=",-2637144015689693966,-5013327660526502976,8425093685345462169,-3412094037209816025>()) {
            case 493871903:
               return (String)com.yiyiaddon.m.b.a<"s2mlujze6dx255","aykMdyCEPj+eMxi6TCx9RtiGY4HgIdBq7dMWMw==",2553252738891519003,-8825933787838238040,-3246982462113377452,4049016391241338441>();
            default:
               throw null;
         }
      } else {
         return var0.replaceAll(
               (String)com.yiyiaddon.m.b.a<"srbov28veze7i","qZVdl9DDPlj0BdG2zjKSxoQJmfyiozmLbqCOB4tAZhs6hVrOEWS5f3HXT/ESlmacK5NrhV2TGR5+E2hbirJ/AYFKAhJbfQ==",-1527998898720380878,-219370840931954341,2522260176673526308,7198278896492760094>(),
               (String)com.yiyiaddon.m.b.a<"s2mlujze6dx255","aykMdyCEPj+eMxi6TCx9RtiGY4HgIdBq7dMWMw==",2553252738891519003,-8825933787838238040,-3246982462113377452,4049016391241338441>()
            )
            .trim();
      }
   }
}

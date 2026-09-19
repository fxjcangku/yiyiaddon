package com.yiyiaddon.e.c.i;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class f {
   private boolean Q;
   private int bm = -1;
   private boolean R;
   private int bn;

   public boolean Q() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sudiq52ya8ido","scClRvhb/sNtz0J4Gt7U40xbcFWRalasbzz+qgHnNIE=",1011769103604788380,-5937074303736750797,4501130590327781354,8301768001900929875>()) {
            case 1980920852:
               return true;
            default:
               throw null;
         }
      } else if (this.a(var1.player.getMainHandItem()) > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dpmgi9bb2jqq","yU97I252U0Mo/wJGBcm1N3ldwgnRtIWzzREGP4qZJIY=",-16031457613736068,-6766287958505882214,-3322224755559878234,-9163951421540158208>()) {
            case 1267899792:
               this.R = false;
               this.bn = 0;
               return true;
            default:
               throw null;
         }
      } else {
         int var2 = this.w();
         if (var2 >= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"sh1y9x1dunc5f","1osLC/rmqwj3+skT5HUkmlhh5BsgmTW4zXjMRttKs+8=",-5156308725408771436,-2395329621653286964,-2586615544230979216,-2930054778999484184>()) {
               case -61674093:
                  this.bm = var1.player.getInventory().getSelectedSlot();
                  h.e(var2);
                  this.Q = true;
                  this.R = false;
                  this.bn = 0;
                  return true;
               default:
                  throw null;
            }
         } else {
            int var3 = this.x();
            if (var3 != -1) {
               switch ((int)com.yiyiaddon.m.b.a<"s6q3jtq9zmr2r","MEVp4ZDR7p19B203y7rkpzu0n1SwdOCN/LiA1fzEa50=",4877711901949541363,-8682907909059077057,-5939993056062384172,916365155126578886>()) {
                  case -2012154428:
                     this.bn++;
                     if (this.R) {
                        label37:
                        switch ((int)com.yiyiaddon.m.b.a<"s3pfsxpbs20hk2","ULMeVZntmp8HJDpol9sMJ//gBanpiSaWIYx0rlxFJFc=",1563283393625145999,-7590253187078767147,-6036414030511045858,-9217284197370744335>()) {
                           case -61701777:
                              if (this.bn <= 8) {
                                 return false;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2s0i73x0e7jyo","+wtq1/WFo37h76G2kuD7cObObLw6FQ9YoB3Va3w+e7w=",8363372088268969391,2287161594914620946,4356135802430527646,-3411263262392103826>()) {
                                 case -1185458163:
                                    break label37;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     h.d(var3);
                     this.R = true;
                     this.bn = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s3jw1wu0lmxg5l","aqSfk5jNMuILR8GJ3Z8U4FVRv5KNskO13gRLyh8FD9s=",9004204019986901460,5504837069228618979,7960906851605897080,-1098490723842999181>()) {
                        case 1286954229:
                           return false;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               this.R = false;
               this.bn = 0;
               return true;
            }
         }
      }
   }

   public void ax() {
      if (this.Q) {
         switch ((int)com.yiyiaddon.m.b.a<"s2s73k2ze2xk6i","a3Tx80xVHBsrrNQCMfGcMil9cXyzb7Htm18AIh5yp4c=",-6807046168086723310,-6011119817103941666,-1411211077637377984,-4933687291530411388>()) {
            case 1922522480:
               h.e(this.bm);
               this.Q = false;
               switch ((int)com.yiyiaddon.m.b.a<"sbktd8d0ceney","KTHl6VaVbLsTlcV0LTQ5wnk9WfO4MJYy4rCWkdsGJw8=",5335157800825827007,3583645457926803925,7353504895879955566,-5427998490427617397>()) {
                  case -258355830:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private int w() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zxo8lo0egza3","OuIVu1ZOmRvN76qJVlL57cMr2lFFMj8S2MZMdqzXZo8=",-8472234589999697990,-8604297059688163282,5775576872573245204,-2973352358610944035>()) {
            case -1043374921:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = -1;
         int var3 = 0;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1e94k9kvqpm5o","tcUFjrCykrio3PZGqbLfYn2gUk2nAQQoqdoddUzSVKg=",-5464563708162648418,3073637231778034546,-4542844890400048711,7759310246634636573>()) {
            case 1880760686:
               while (var4 < 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1pzmbqwegupdi","aNNQiINmH0mqm+CmATWfs/6O5o6q2UGDrhKrntYaXEI=",-6170283303078039534,1692025102734614323,-8656944088833208227,-662030410115391685>()) {
                     case 173869931:
                        int var5 = this.a(var1.player.getInventory().getItem(var4));
                        if (var5 > var3) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s3vnh0007i6lbc","BvSNRN478T3PNydiTf7Crk5oEm2TX0dgNQH7nlYnzdM=",-576687757902779047,2731604446524222906,1505808537925866618,3532778240205619154>()) {
                              case 965522236:
                                 var3 = var5;
                                 var2 = var4;
                                 switch ((int)com.yiyiaddon.m.b.a<"s12t0mp3k5zfw9","PTX48XXxzvqOW4R7/8KweQY8ZCEjVVvPhCsj7lr0ml0=",-177047410930915838,7161585658438646438,-7187715847857235057,-8594653928775492155>()) {
                                    case 1985874461:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3al4qdqurbnr6","5gLY5qDXANMLf1wCcGeBj45iCfUMoEGgQVDAh/FnXR8=",-2849402420013517030,-2291688779519298235,7310756447674137749,-129372538231785275>()) {
                           case 1675354366:
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
   }

   private int x() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3taxkkf9op21y","77KaWv4F4JKGTbCSXRDpbERFTpFoJJQKgPruKyc1RoI=",-6537722289974281605,6661020394245235307,-5689525618845462764,7607106047275826920>()) {
            case -1889815879:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = -1;
         int var3 = 0;
         int var4 = var1.player.getInventory().getContainerSize();
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sjm5yw5d080cp","uQik1+D+QLYYid/8aZb369MS0UMGL0a1jHjpT3OZbwI=",-4505816897264573337,7664793229055768423,-9137305190872971230,4733307218885590299>()) {
            case -833691477:
               while (var5 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2vxtk66s1ypgz","1157GY5JgIumhJ12Fhb0bPxyRaUM8jut6L5etv8X69M=",-4238712024313587727,-4896136730890982583,4228616175246117465,-7077444902811109337>()) {
                     case 1231517681:
                        int var6 = this.a(var1.player.getInventory().getItem(var5));
                        if (var6 > var3) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s2ng25hdz393pd","yFYJES45hFOkxsTXszJsio14NDsR86dDKzzKmPylq7M=",6703898766106565010,7371379628062553436,6590751033566535602,-1555652436054007973>()) {
                              case -1082692272:
                                 var3 = var6;
                                 var2 = var5;
                                 switch ((int)com.yiyiaddon.m.b.a<"s22i6qyu6xinf1","5PyhV/zoNP6/u1qAJ0Tg51YDOaYWPQY0QPL/p9uH2OA=",-7052138811971918348,-8446104486235109835,8271310036179915173,8708972353503665298>()) {
                                    case 2071292975:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"sdommrbkeie3k","AEBB9Ffg6jV/0InepLwSkZFaCLAyzSg9Pam6iTqBvjs=",-6633723928968459842,5501604810493617858,-4306535365781217311,754268311033040605>()) {
                           case 956617497:
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
   }

   private int a(ItemStack var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (!var1.isEmpty() && var2.level != null) {
         ItemEnchantments var3 = var1.get(DataComponents.ENCHANTMENTS);
         if (var3 != null && !var3.isEmpty()) {
            try {
               Registry var4 = var2.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
               Reference var5 = var4.get(Enchantments.FORTUNE).orElse(null);
               return var5 == null ? 0 : var3.getLevel(var5);
            } catch (Exception var6) {
               return 0;
            }
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }
}

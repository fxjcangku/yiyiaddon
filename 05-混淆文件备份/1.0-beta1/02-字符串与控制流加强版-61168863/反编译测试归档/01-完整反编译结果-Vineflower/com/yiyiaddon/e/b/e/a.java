package com.yiyiaddon.e.b.e;

import com.yiyiaddon.g.a.c;
import com.yiyiaddon.g.a.d;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public final class a {
   private static final int aj = 512;
   private static final int ak = 4;
   private final Minecraft g;
   private final Supplier<List<c>> a;
   private int al = 16;
   private int am;
   private int an;
   private int ao;
   private int ap;
   private int aq;
   private int ar;
   private int as;
   private int at;
   private int au;
   private boolean y;
   private BlockPos c;
   private final List<com.yiyiaddon.g.a.a> h = new ArrayList<>();
   private List<com.yiyiaddon.g.a.a> i = List.of();

   public a(Minecraft var1, Supplier<List<c>> var2) {
      this.g = var1;
      this.a = var2;
   }

   public void a(int var1) {
      this.al = Math.max(1, var1);
      this.c(BlockPos.ZERO);
   }

   public void b(BlockPos var1) {
      if (this.g.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aaglmaqxgfcl","DDcI3zWXsGbi7wreMTaN5/XwIfpDw20ubuV3hvOm4TA=",-8051246184678190285,667714104103704900,3554197070496393215,6405436717621678236>()) {
            case -181979010:
               return;
            default:
               throw null;
         }
      } else {
         if (this.y) {
            switch ((int)com.yiyiaddon.m.b.a<"s2632go862l5kd","tBrbezqG1m1TE3hwDEcyUsLq/lVIfuOA50kmVhWbNMU=",-6644427749294925775,-8987675798563502185,-7587980756497204158,-710814905831734545>()) {
               case 1922550928:
                  if (this.c != null) {
                     label26:
                     switch ((int)com.yiyiaddon.m.b.a<"s2496u7brspg7u","iYzNMJs0EcVVKgRerSptCpvLK6QK0ezfJjqwTTTx1z0=",7666052081087856456,-8016748271904222093,-462388519943333654,2598528775051003134>()) {
                        case 1138309349:
                           if (!(var1.distSqr(this.c) > 4.0)) {
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"smixjsj34fswn","HArjfyQXrExW6LAjvp+99LPJcSTuvqcJmPBUMiiEtkA=",-8269745350112558305,2478637777481726811,-8585450869077098129,5525038935291983736>()) {
                              case 411350417:
                                 break label26;
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

         this.c(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s3id28796l097o","Ud0djHT/sl/xzgChl6CKA6/2szdzcH3BZC747Gn68E4=",5990775133736732743,8836763203321227171,7488115051068310184,-6230949006687269119>()) {
            case -361586168:
               return;
            default:
               throw null;
         }
      }
   }

   public boolean f(BlockPos var1) {
      if (this.g.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39szhdjux7jyz","XlN6LcYGjWvADRe8igFTdnn7Asv8h7tXAwRSJirGOlc=",-5643669200060730209,4330456193806309374,-1652855381184025560,-4251003593608561857>()) {
            case 2129486736:
               this.y = false;
               return false;
            default:
               throw null;
         }
      } else if (!this.y) {
         switch ((int)com.yiyiaddon.m.b.a<"s3oluuru5n2v9l","PB3WY8HGR4iv+NDl1couof2k6Z2+Nw5KHXB06KhVumw=",-1435536046602876818,-6451581778884360184,-8369500562697794671,2619230649742463351>()) {
            case -1485631893:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = 512;
         switch ((int)com.yiyiaddon.m.b.a<"s1vdbka3y92tg5","n459kJC+C5Pt8JLvfrPE3A76GywlYQkc1CWKSpwk+KA=",-5276229265063130702,1561973044155232506,-1382236439539466945,-4563957461555543607>()) {
            case 730810141:
               while (var2-- > 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s30n7nbqbe2go1","9+zNxFCL8DjhMhr86JaYjvJTnm1S+awCy6/kTGBgDrM=",-5360168912910202678,-9119875416502716176,-536032544031805752,-7433273530856213468>()) {
                     case -747827606:
                        if (this.at > this.aq) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2mpyaklg6h6t4","+Or+7O8IfHvufzQ5KMpgsG50CYDilwZRwwFBm3JSNmg=",3149498630411970614,6519106013275185343,-550084141719810249,-79203350796304585>()) {
                              case -1439332195:
                                 this.i = new ArrayList<>(this.h);
                                 this.h.clear();
                                 this.y = false;
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        BlockPos var3 = new BlockPos(this.as, this.at, this.au);
                        String var4 = this.a(var3);
                        if (var4 != null) {
                           label56:
                           switch ((int)com.yiyiaddon.m.b.a<"s3jdf48tacdckm","PpL4eto/ZCY4pxj8KcwLdac9K/sDt+U9TERPXXCEtYk=",3624806097813289718,571417679650842318,4495080355531071055,-2493328609939888136>()) {
                              case -1615021191:
                                 this.h.add(new com.yiyiaddon.g.a.a(var3, com.yiyiaddon.i.g.c.bU(), com.yiyiaddon.i.g.c.fG(), var4));
                                 switch ((int)com.yiyiaddon.m.b.a<"s354cx7c7x0rmz","gTFZW084qka4hZ9RE0bhcUuH73SajS76toya3Gi7U7Y=",-5822626583582676563,3503730614415846766,2309346871673013005,-8187794646680049773>()) {
                                    case -1688655109:
                                       break label56;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.as++;
                        if (this.as > this.ap) {
                           label52:
                           switch ((int)com.yiyiaddon.m.b.a<"s2hlqvo17vk8yr","aQHbzCIQnB+tqYGdnh6Kji0baKfNlT4eRTfxuCHBSNM=",2681947629876304734,2094052792488220555,-222655964877963726,2550292712460333762>()) {
                              case -1197957957:
                                 this.as = this.am;
                                 this.au++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2n1mzq8qwqivl","l4c3KHzQYLik3GtrrtxTFWjdzHFLVK+6G88S9SKEq4g=",2109347161087931379,5153140163643391575,-4185387321179953627,832392099640868908>()) {
                                    case 579057690:
                                       break label52;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (this.au > this.ar) {
                           label48:
                           switch ((int)com.yiyiaddon.m.b.a<"sjdhyijdsdedn","/H+k9//N5IWIup8rHtMkqWf9bMZjBL4BZUoSDbnojg4=",-1156097106325066324,-3565872967356223251,-4255002427675701542,2195363075247513287>()) {
                              case 343907386:
                                 this.au = this.ao;
                                 this.at++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3uwj0g9e2y0rw","ZZ10NoS8BCpgfVEDKhicgP33gGY/W1U5aH3kazxfa40=",-111985768775206321,-6788786561919135260,356791991056865035,3019180649151194088>()) {
                                    case -1823565841:
                                       break label48;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1b0vv4uzawr3b","QPJDPo3CighlPk6cbWmY91Alp9AdhivhNZX9Urak/mk=",8719441370042470082,4997559256437433547,6135252670001181746,5975682047280042439>()) {
                           case -300684816:
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

   public List<com.yiyiaddon.g.a.a> k() {
      return this.i;
   }

   public Set<BlockPos> f() {
      HashSet var1 = new HashSet();
      Iterator var2 = this.i.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s6m43hcps51tg","jAtfzaA4YXRlBNRJlz9+ivorTgVy00jOZqdG6yYF0Iw=",-6417234937177871232,8141264107135787570,-2041654992817375281,4296127313272711218>()) {
         case -1052069665:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1tmpvaan1m308","kfVomM/YFvjjU3yNXtVRo3iA8oB/Ur0Wx3vhDYga+Oo=",6612679662682897736,-4753184149653091007,-5201518821562875174,-9028302498235581059>()) {
                  case 2127121847:
                     com.yiyiaddon.g.a.a var3 = (com.yiyiaddon.g.a.a)var2.next();
                     if (var3.G()) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"sjhbc6zfyq4un","S8lKZw66Vzcu1yZiUjx5Eu1gCAK0XokLH5vsZF4hZK4=",-8716068102942209194,-5189777980107787906,-4253268653530423269,1818463084625807369>()) {
                           case 71086216:
                              var1.add(var3.a());
                              switch ((int)com.yiyiaddon.m.b.a<"s2t1a8mjlu3zgt","77evqV+csmVD32MK1fGPdishBf8v3/LnFprn5DnXTzk=",-8087765271590097745,-7580071787418725663,-7026852603590686093,-5960812648630573688>()) {
                                 case -103777686:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"scrnnn6mk812p","RboQBvGlCcyd78ot0MHadjYo/NcBSyx3yz4o1JVYKDQ=",-5622811994283606500,-62929067783830230,-9197401009788892502,6447299170476672273>()) {
                        case 1686440056:
                           continue;
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
   }

   public boolean t() {
      return this.y;
   }

   public void f() {
      this.i = List.of();
      this.h.clear();
      this.y = false;
      this.c = null;
   }

   private void c(BlockPos var1) {
      this.am = var1.getX() - this.al;
      this.an = var1.getY() - this.al;
      this.ao = var1.getZ() - this.al;
      this.ap = var1.getX() + this.al;
      this.aq = var1.getY() + this.al;
      this.ar = var1.getZ() + this.al;
      this.as = this.am;
      this.at = this.an;
      this.au = this.ao;
      this.y = true;
      this.c = var1;
   }

   private String a(BlockPos var1) {
      if (this.g.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3p70k10wzhv2r","73asRamUX9jM+bwMRPinth/JYj626uDrzoLww4RQek0=",1224619620608993860,8299631392360575409,-3264697974267082157,2805943418205217743>()) {
            case 698793067:
               return null;
            default:
               throw null;
         }
      } else {
         Block var2 = this.g.level.getBlockState(var1).getBlock();
         c var3 = d.a(var2, this.a.get());
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s266csc3l7bciq","aooVTa087hFN05SVB3/h4HMyYtGs7zLl2rZfXe6LIwQ=",-7272285731390261678,149361641378530863,-6703645551155442744,1712181301938532353>()) {
               case 238274653:
                  switch ((int)com.yiyiaddon.m.b.a<"s38pb6h4lbqicm","AxsPq0hpBS6IaM3QaYCoowMAerOBGIT1qK829++S4MA=",-7549825674247704924,-6938025345525103651,6783700337665239256,-3979654706634691452>()) {
                     case -1983077576:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var3.s();
            switch ((int)com.yiyiaddon.m.b.a<"s2x1fvhg85olma","Ws/yy0sAYQAICOpRsgBTZN15d0NJNmFIzJ2sHoPeExU=",-3130541751928187222,1872853023111659415,-62256424808388968,-8327896703242189386>()) {
               case -1341360908:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }
}

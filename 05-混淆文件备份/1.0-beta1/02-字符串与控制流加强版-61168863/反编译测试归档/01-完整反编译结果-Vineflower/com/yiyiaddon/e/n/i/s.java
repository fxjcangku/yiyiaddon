package com.yiyiaddon.e.n.i;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public sealed interface s permits b, t, f, d, e {
   com.yiyiaddon.e.n.o.d a();

   String L();

   String m();

   String dE();

   String dF();

   c b();

   String dG();

   default boolean dm() {
      if (this.dF() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dih8hfsadcs7","DKp+ZGRie+5jDXpX8flFnV8S3BO+jLpW+gR06Ny6TfM=",-5186755705705161130,7966809247865887163,-3646204866610661705,-198399840789215358>()) {
            case 944798558:
               if (!this.dF().isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1nqs9fe6prepk","KF8iwiW7Olfg9uI1U2qu/B6EJkRtHrMMJgx5si6IAQA=",-1256860533165908075,-61721555362311414,7196297008670876564,-4743177694102954770>()) {
                     case -742633304:
                        switch ((int)com.yiyiaddon.m.b.a<"s3iuf6zbmiww4u","SJSsIspLXNxFd72j/FHUckb0MX4RwjqHtrSCU1+9xPM=",6694798498558336033,-7396055628416616769,-5619811723159744585,-576169902928337072>()) {
                           case -59918471:
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

      switch ((int)com.yiyiaddon.m.b.a<"sd6by2lpyalz3","9upxvL/OjWBo9h7CAOuUC3tPR+gnUriSm7Er75ePn+8=",3890421669037112484,-2309525749410608578,-5468670542548107086,6305495668369346792>()) {
         case -1328828457:
            return false;
         default:
            throw null;
      }
   }

   default boolean dc() {
      if (this.b() != c.VERIFIED) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s2iyvbn319dx0u","vmTlTmgQpaTKGyFTl7LsjNwF7Me9kXYG4yNEIzq3ISM=",797357965023637964,-6456874162589537845,-6179532086336296762,1423248038346137664>()) {
            case -2074552890:
               if (this.b() != c.DOCUMENTED) {
                  switch ((int)com.yiyiaddon.m.b.a<"skqfd8n7yfw0m","nqu3FPeyRXoR9iOEy/nYOr3Vr+T4H5jEuC0CEmCJznI=",-6066101748067842015,-284084797536763938,5532397538644202782,-5004516776167672307>()) {
                     case -131792991:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2933wd94qlx7i","rVLV4Jvwb4QAKm+KDXXNixdov1TqZnY5KCLU5Z++3zc=",1092418761454105457,1670178949739021533,-2672963912787498472,8899667844237895097>()) {
                  case -989588317:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s10cssopgxjoni","p0ILaZHtFlMtXmCBM0v24GYpIVMILu2DZC1X+glxxu8=",8706135195993584263,-2358232500501695129,-4815291847057524837,-7780990258619460823>()) {
         case -1581562586:
            return true;
         default:
            throw null;
      }
   }

   default String dS() {
      switch (this.b()) {
         case VERIFIED:
            String var3 = (String)com.yiyiaddon.m.b.a<"s3tofosaafr65k","X27+p0uHBqefJk6xf6zj1oyHFDHcp+t67rDC3pPwFQPkVg==",1528706602794723907,8743562684901360946,-6383459832645645114,-1026412075214003025>();
            switch ((int)com.yiyiaddon.m.b.a<"s2szn22nb4d9bf","lFIhCOJceRKy2f2WvD+14M3SLAR0DmnVhYUpdFAfQP4=",-5708385950235981019,5406252914431787650,5597187943522300540,-3774213025033241538>()) {
               case -577322491:
                  return var3;
               default:
                  throw null;
            }
         case DOCUMENTED:
            String var2 = (String)com.yiyiaddon.m.b.a<"szzi2u4bxd45x","7zf4LtFfKEnB5dpTwAe+t6DUTYK7Ne5Nq94IXvjBlyY2cE3G",1283360372583628916,-911145942246142963,-5698709792809875894,4545656507601328586>();
            switch ((int)com.yiyiaddon.m.b.a<"s1m7m8r5abai0u","w47lgSEla1edzB9QfCX1sA9G6j2qjM/4QII2KOcNXt0=",-3724826260330742249,8160680489855891754,-5069403348217326029,5988257900691266788>()) {
               case 954231602:
                  return var2;
               default:
                  throw null;
            }
         case CANDIDATE:
            String var1 = (String)com.yiyiaddon.m.b.a<"s32l6xml68exh8","llpUXrUR2+rkHbs1An2cqXoY24iCO2EPlli+VJIDdOCRTJjN",6334468582248187072,-4464516797338854263,-3167411739884800094,48523999457755021>();
            switch ((int)com.yiyiaddon.m.b.a<"sg84uvm5w90pw","FesPkleaRK15MBIc9ocW17xUSwPLcxe5X+j6Eiut06E=",-8040218522761221046,1328700645309953913,8252147754587770310,-7168480110299759069>()) {
               case -1624851182:
                  return var1;
               default:
                  throw null;
            }
         case UNKNOWN:
            String var10000 = (String)com.yiyiaddon.m.b.a<"s3un760lbv3kry","brq59FCXzrvzal+ECCouODNFKUUWCtdSq/6cyGVQ1nY=",3204086123227754597,2773862206287868443,-7505236721930382314,357273578948415257>();
            switch ((int)com.yiyiaddon.m.b.a<"s16uot1crcyvc0","syi4YVP53teocTEIOTWK97riwbVhbqWFgqvqoylaK+Y=",2549239920719070580,-6106027578537242231,-6455512883733667412,7103951499587412246>()) {
               case 146485907:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   default boolean A(ItemStack var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e68fpfak0cnm","qi/5rj09Nq0BrZD2P0N5Xjmao+5ZLIkkws2PvGt25rg=",2085204093045091697,5165501157845362315,-4516982839290718326,4047732713133006709>()) {
            case 1605329678:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3igkzqscnkzl5","f/x5Ldvk0eb9CAdD07K3NJ2z8Nco07tidNAI632VsUw=",-4858538170344734272,-3279970314035776749,-6025503282301556005,7822587450138040806>()) {
                     case 406571207:
                        if (this.dE() != null) {
                           Identifier var2 = var1.get(DataComponents.ITEM_MODEL);
                           if (var2 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s96qkn4o3glg8","/P8XWod+Yo6YZh7hWCLsIUh+8jwqhW54xccEE++VCLg=",51849494072790601,-67504109410327173,-8587812171922264420,7482241827288057543>()) {
                                 case 747310056:
                                    if (var2.toString().equals(this.dE())) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sbimurwjzr255","rMxcC7ENyRJ6A99qPIUDdKafOzfAf7FYIeai/CxjABE=",3259306216473675135,5086265045100007952,-1171361956249132924,-3111842243866539666>()) {
                                          case 1019928094:
                                             switch ((int)com.yiyiaddon.m.b.a<"s15lv4gj8cy97w","lTpBBg2zZYW1FfDsXbJUBw5x6pxuYvxBFd+vFUOZc9U=",-8247877711441520865,2506510551220886542,-2431720351208275708,6665805749979057897>()) {
                                                case -1554184098:
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

                           switch ((int)com.yiyiaddon.m.b.a<"s1yn9v1gkqs6ox","0LNlH8+BfHDiQsrPvgwWh0nMdgRe8OUXmybinYQbxnw=",-3631800922278001930,3903899531240647649,8345368211113268779,90356017179465435>()) {
                              case 1879665067:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s31pbsor6ti4em","NY6o79gC9UGwyT3WHLilkOR4EMv1gnWynHlhk48VllE=",52223745292603656,1437688976950137798,5864377236765537287,3342904015775200450>()) {
                           case 158172493:
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

   default ItemStack p() {
      return com.yiyiaddon.e.n.o.a.c(this.dE());
   }
}

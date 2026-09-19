package com.yiyiaddon.e.n.r;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

final class a {
   private final b d;

   a(b var1) {
      this.d = var1;
   }

   void gW() {
      if (this.d.L != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mlqk0bd49x5f","rVZJ4nDI5WSn23VuldDyRaEryir1uXAma3AiqwhHWK8=",-7844736720890254980,6137816425514889481,7363780280425974935,5802506818249738636>()) {
            case 357248721:
               if (this.d.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s8wdxd563p7qp","LESTcL/lI+Jlwi8O/bf5Fuf6jZkE9fP3gple2qaX5Pk=",4603144116570749697,-1508680192087644664,-5019145718587881068,4908574564797198703>()) {
                     case 82449452:
                        if (this.d.a != null) {
                           label195:
                           switch ((int)com.yiyiaddon.m.b.a<"s1p1zgje59ny3v","ELkKQB93+F24NJp9sjjAWymFQmS1cVtY4EAvVradt7k=",-42447367541043945,5014385740644941507,-6484401672104786571,4899224339022564348>()) {
                              case 1616130795:
                                 if (this.d.by.contains(this.d.a.dk())) {
                                    com.yiyiaddon.e.n.h.d var10000;
                                    if (this.d.a == h.UNLOAD) {
                                       label188:
                                       switch ((int)com.yiyiaddon.m.b.a<"s343p3xvhbv30d","ni9wjcQ+Aa5ZkYXOUIMWyPZJvZjpN1wOBJvOEvBXYTo=",3419415260447187733,-7078914688813292021,-2870099463400165981,4881803470520601585>()) {
                                          case 1464178408:
                                             var10000 = com.yiyiaddon.e.n.h.d.OUTPUT_BOX;
                                             switch ((int)com.yiyiaddon.m.b.a<"s111x29te8eawf","4FAH9wetoYs71E/Mu1droleP04yDmW5cF98GLlmbmk8=",6427799074467904593,-7050230103350742398,3737251158072234954,112105901192696487>()) {
                                                case -1326921997:
                                                   break label188;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10000 = com.yiyiaddon.e.n.h.d.SEED_BOX;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3qxz7fn0qfcpz","D1TknHstUohdm0ljEIneWPdWPWhc1fidogBpFW9ddkA=",8884323065797490623,-5796440934656726672,952093788644542491,-7643575386070885635>()) {
                                          case 791040106:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    com.yiyiaddon.e.n.h.d var1 = var10000;
                                    Serializable var2 = this.d.d.a(var1, this.d.d.a(var1), this.d.a);
                                    if (var2 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2av46bqsbptgk","cnAVDRIIt+eH2Lu4VLe/ZqJSYbKAADWJr7nw1FT6oZI=",6577924949098894624,2682844540307647464,-4067257445245845289,7764628485090835950>()) {
                                          case -977782450:
                                             if (com.yiyiaddon.e.n.h.c.W(var2)) {
                                                label113:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3ug5bjko7hfto","vnpn0DbKPNBmsegT+X85hFzuEPXkmxe7JZMy6ioyv/c=",-7678491583806547603,-8232636978884463282,9220709805917352004,-8870095886151449373>()) {
                                                   case 1275789928:
                                                      this.d
                                                         .c
                                                         .c(
                                                            var1 + "",
                                                            var1.D() + "",
                                                            (String)com.yiyiaddon.m.b.a<"s1rv9nxjzjxryh","HlAgXd7OhxMMax5oNNAa/N9RGuiUiMJD6FngkK4VuVW4RsAkVFx3eA==",5880211245754531114,-947996924086767002,544231858918160455,741574715401673154>()
                                                         );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3rq4gwf4oc5re","Cye12qrc6+zENqLJmbHjp+J2imLvlfClvmp/1O0F/GY=",-7632598978438092237,-5003394881722405473,1436543833598424833,536446451069367582>()) {
                                                         case 129996265:
                                                            break label113;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                this.d
                                                   .c
                                                   .d(
                                                      var1 + var2,
                                                      var1.D() + "",
                                                      (String)com.yiyiaddon.m.b.a<"s3h3stmsrjkfr","2PfXCYTEpBw8hFFwj0fJKelL2PNI/WIgg33HjSnmBQEXUe+2CcIyF6FatlxyIA==",5212717717752349221,2439845480042912527,-2870285602710473984,8283946124857630423>()
                                                   );
                                                switch ((int)com.yiyiaddon.m.b.a<"s2f6nfje32cbq","jtir5HFngsxCBpvcuvIgM6X+gDgPmOa7oX8Eud2afGs=",6254200107442567802,-7313048916561236214,1340676219711157905,8459194821730541192>()) {
                                                   case 832701351:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.d.a = b.c.REPLAN;
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch (this.d.nI) {
                                       case 0:
                                          if (!com.yiyiaddon.e.n.f.a.a(this.d.a, 0.9, this.d.lA)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3c3eksm2lkn43","xmB6QdfWO/mvVrrQg5diF+eUKgByt6eYDRBBVcdyjKI=",-4248225358162454280,4846278100054886694,5841773989781842887,-7302681245250837534>()) {
                                                case -1807718454:
                                                   this.d.a = b.c.NAVIGATE;
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          this.d.f.f();
                                          BlockPos var5 = this.d.a.n();
                                          if (this.d.b.v(var5)) {
                                             label129:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1fi37mzsp5gnb","V2rZpmVSPzBPZ7qW5Bwqe20ox5PVExNp+pS+WiUBHoQ=",-1688650880921449917,-379297240516961246,-1057654902315435967,7654278258808451701>()) {
                                                case 1111940684:
                                                   if (this.d.b.a(InteractionHand.MAIN_HAND, var5, Direction.UP)) {
                                                      this.d.nJ = 0;
                                                      this.d.nI = 1;
                                                      switch ((int)com.yiyiaddon.m.b.a<"sksh6k084fmyy","dBL91oBcZH7ZZAZaNYLVL/19CTMMS2Gg1BqHqNOSAEc=",255494616211476354,5343672801892639503,5879602308306104087,2973530508968866861>()) {
                                                         case -2113931560:
                                                            return;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s202e32x18chpr","+LQtbwSako4Sa8vau4VsuZi2J20IRkjyyF6pqv7+cvk=",5833136604954287353,8643478891367012301,3892844161209935883,1927356700530233052>()) {
                                                      case 848475047:
                                                         break label129;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          this.d.ac++;
                                          if (this.d.ac >= 3) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1r2df8hj8bgf3","IUfxmtoOeoOGLkQQWam9wvjNIaYU4s0sANcBW/UTh78=",4497055469923918446,-7592908486194525460,-217238430804446306,-8471287744129166864>()) {
                                                case 1580812510:
                                                   this.d
                                                      .a
                                                      .aA(
                                                         (String)com.yiyiaddon.m.b.a<"s12s0yoe5768sg","BYHiD4KlWSiwcg9pNicgvZCOnHdhd22m/jJNUQ6YrQsfFfPlKvcoF1RkCOE=",-3741297122615490842,8687011126608812815,1504642210076348917,-2447168861615707618>()
                                                      );
                                                   this.d.a = b.c.REPLAN;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1mv1ba03eg7gi","1zFErjAFxXO4lOuqpcXYn2swvnK+7Sc88O8e6cZ4r90=",-5076531996055743836,-2586983712972370678,-5765183529158625924,-5944439618164033774>()) {
                                                      case -599054628:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return;
                                       case 1:
                                          this.d.f.ae();
                                          this.d.nJ++;
                                          if (this.d.f.fr()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3bq91ycqh048d","TulwXzXq83IbhDYDCjz/36I6Q4YWUf3CZhT+T7g4UDY=",-5885573959543061125,-2301039102443637451,6754563856167570877,6740505318726140404>()) {
                                                case -1897450170:
                                                   if (this.d.a == h.RESTOCK) {
                                                      label180:
                                                      switch ((int)com.yiyiaddon.m.b.a<"sgu44xzc9as82","QJ2grcx8vF5LGpLLGzHlKMehSEc4zGXvICQDuY5y/ew=",-7560637433583410223,771950043034620315,-5820459842908369896,4476350004444580976>()) {
                                                         case -1219409251:
                                                            this.gY();
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1fw4pda3oqp68","3w3lALyVdk5XitTFNDIXWAHFDrJ8+W7Zzy6FUOdkWhw=",7845832852502962868,-7140054063073172052,-8020452188743025920,-5624625216486424997>()) {
                                                               case -132043227:
                                                                  break label180;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   this.d.nI = 2;
                                                   this.d.nJ = 0;
                                                   switch ((int)com.yiyiaddon.m.b.a<"sw2w0drjc94l4","XzPvdwFF+Q9r0aNMukkNfnkgFfNOTl3BOX3ztqe1iYw=",-7619991706894186888,1416359170536837492,6512016223489117065,1595301609936585642>()) {
                                                      case 862199495:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             if (this.d.nJ > 40) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2z8rtj7scz593","WAS7DsvDg5n4MsrpJcr+JU3BfSEbqnN36Uc75yiHs7g=",-8732832431713251988,1274934444512514525,2091397048505283171,-7083538981222088800>()) {
                                                   case 1937344928:
                                                      com.yiyiaddon.i.a.a.cD();
                                                      this.d.f.f();
                                                      this.d.ac++;
                                                      if (this.d.ac >= 3) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s20b7j98lbisn3","2ZFyTCWqdpmBM7m3LLAUe2OXtT65Yb4KpBl0wjGlB1w=",-8004876615065092268,6487418911229559641,-2592774532189631336,7213727647125986691>()) {
                                                            case -1071502117:
                                                               this.d
                                                                  .a
                                                                  .aA(
                                                                     (String)com.yiyiaddon.m.b.a<"s2uczffcier9lj","IuWH7lvx7hHFvC1nl2HV7fnIUXYexiF0ugiy0OuWqw3c5+/bx5TdgEEfthOA8A==",4692898369101490102,-1912922057168991802,-3489976244518822098,1966167727808984672>()
                                                                  );
                                                               this.d.a = b.c.REPLAN;
                                                               switch ((int)com.yiyiaddon.m.b.a<"sffr4neiojqje","rnFsI+zPeBmB5yE39J/Inydzxw8s2/+NVejxcTa5okk=",-8073809614509195498,-786225898722875476,7128964384481822886,-5511815379872011670>()) {
                                                                  case 1455032224:
                                                                     return;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         this.d.nI = 0;
                                                         this.d.nJ = 0;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3aso3lakeqo5u","/chTn7+kzGe4WniRoeZu00DKaaYD5yugXtDmRcI4hB4=",-6058644796521549373,-2181486579137629686,672182736282394492,7484836121492619622>()) {
                                                            case -1936093286:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          }
                                       case 2:
                                          label164:
                                          switch (this.d.a) {
                                             case RESTOCK:
                                                var6 = this.a(this.d.a);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2mluyilnbd4uw","L7SveDnR44XtKCc7Tc/jfNHZ2DeldLX3MuZb5KU25AI=",-4889084347706602475,-2108836044346491292,-3948370887698926512,-8461529585074153930>()) {
                                                   case -2036110544:
                                                      break label164;
                                                   default:
                                                      throw null;
                                                }
                                             case SEED_RETURN:
                                                var6 = this.c(this.d.a);
                                                switch ((int)com.yiyiaddon.m.b.a<"sgof2f09vxlom","Bd5c9fPx70KB4yGiTXCDkgP4IwR5DPwee5o1p0NlVI4=",6757171289078351390,5275874231077718449,-2630770115784065957,4473081388502062338>()) {
                                                   case -867749590:
                                                      break label164;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                var6 = this.dN();
                                                switch ((int)com.yiyiaddon.m.b.a<"s2av7ajbgd21xe","MUmPw+ll1ZhGe8P3drvLGimLOW6SBfdGbHPl013/2nc=",8972598257864756703,-4395041749005048133,-9219581986091186358,7545815442954515715>()) {
                                                   case -344128042:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                          }

                                          boolean var3 = var6;
                                          if (var3) {
                                             label155:
                                             switch ((int)com.yiyiaddon.m.b.a<"s306z70frjqi9e","v4mRodUG6QfwW5SHAl0Qu227YoYwXBCz7sSY0GOev2Q=",-952092124279296676,-5195632893882732767,6893560046386629286,-8998010813414679688>()) {
                                                case 938225080:
                                                   this.d.nJ = 0;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3ehjbwnh92uz1","0kV45gdBxYiq2eL3nLW5xeD8G4Q+5M8W2RPn1olxfCI=",2444977148681246665,-8084462552361947680,-5351814100260892642,1763469497867881992>()) {
                                                      case -603454004:
                                                         break label155;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             label151:
                                             switch (this.d.a) {
                                                case RESTOCK:
                                                   if (this.d.a.a(this.d.a) >= this.d.b(this.d.a.dk()).bS()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"slvr2jp0vv64v","fCtmONx5Zh3EMY7I1ukKAkprhAiPwI+1x1mPWV/d37I=",2876765049817506766,7122776664093757593,-3038812780704023352,-3388474380045888221>()) {
                                                         case -2040333561:
                                                            var7 = true;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s16yeu201qdlah","VaSaHqIJWaghVkg7g61m4ZkpWvM13xGI042buoEIFNM=",2196489936594764289,6024325437607346976,7981338057613498657,3464978750870034708>()) {
                                                               case -1081972004:
                                                                  break label151;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var7 = false;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s328huqv3tmkco","lkS0jNIqVWbKUxw/HtewmU/CSs5wqKjtW7oGhMmipv8=",-8084985739096522398,-9176295242844257988,4366221033071716198,2554196595898669796>()) {
                                                         case 232856387:
                                                            break label151;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                case SEED_RETURN:
                                                   var7 = this.d.a.h(this.d.a);
                                                   switch ((int)com.yiyiaddon.m.b.a<"swkmjs670kymp","C3zM4W5W8GmyIpzRdx9pATz/Eu1b6qmOkIhG3E8Agp4=",-8316208931629662911,-4187411272218306138,333431525692263035,-4228923938490885746>()) {
                                                      case 1756573445:
                                                         break label151;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   var7 = this.dO();
                                                   switch ((int)com.yiyiaddon.m.b.a<"sn08153ofa98p","N38l/jue7TDTNiQ/gikqpoJb7pqRuU4sfFJtbVCRqZE=",1930898263775938223,-6185324880567968789,6605038925569433287,-8427857780467340964>()) {
                                                      case 1897516809:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                             }

                                             boolean var4 = var7;
                                             if (!var4) {
                                                label138:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2xbohgoxw77wc","R1/+noeOEyaHK0wp9rRvfy6oJK+o+wdXeZUjOv231yo=",-7994928020695104177,7870557897438708757,1782954179977502351,8615694343586675828>()) {
                                                   case -1952332577:
                                                      this.d.a.hA();
                                                      switch ((int)com.yiyiaddon.m.b.a<"sdfmrt013ge0z","J4qecogjs/ZYVjrAG0qUMMIrJhLmwWFBlnHeLccEWOM=",-4723628436845974762,-6371501239723462725,-1067564747582033039,-6892288440043546428>()) {
                                                         case -1755438837:
                                                            break label138;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.d.nI = 3;
                                             this.d.nJ = 0;
                                             switch ((int)com.yiyiaddon.m.b.a<"s28sohtgdpr5wf","f45h7O8Hi2t9werX5QJ/vziSUy3A/hZSPvpCdeenRXw=",-5146050784118655729,-5351560804445229543,4502829617133027547,5359560414219735477>()) {
                                                case -176921458:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2mnv592r708t","PkrpIlVp7fwGSUubKWrm+oaKbI36vPCVwWHpTd/MJH8=",6451191862755519377,2611320793987817815,7784967700469479576,487289874783972021>()) {
                                             case -1391749827:
                                                return;
                                             default:
                                                throw null;
                                          }
                                       case 3:
                                          com.yiyiaddon.i.a.a.cD();
                                          this.d.f.f();
                                          this.d.a = b.c.VERIFY;
                                          this.d.nE = 0;
                                          switch ((int)com.yiyiaddon.m.b.a<"s24mqw4fukrij7","g/2vDGWEpV1b9rwjT4EFwioDykIaL1ybOj9heLuhSMU=",6609456810544784319,-3908579375468170186,705142142688714832,1662522940770747337>()) {
                                             case -553949538:
                                                break;
                                             default:
                                                throw null;
                                          }
                                    }

                                    return;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"sokn5dncij0vz","s1k9c9aW6Ihi3oy8s4DqIizR8otkXPkG9cvIvqWnp4s=",-61518367296047321,4885065074608908690,1654861351976333325,5511729269556257028>()) {
                                    case -222451668:
                                       break label195;
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

      this.d.a = b.c.REPLAN;
   }

   private boolean a(com.yiyiaddon.e.n.i.a var1) {
      if (!this.d.f.fr()) {
         switch ((int)com.yiyiaddon.m.b.a<"stmqonxbkcl0y","QFOSaEd5+PV2INiMCGJlNkpRkOGv4VUejPSFhU0LsQw=",-5562924089997040798,-7350456910059989037,2412493250429111478,8053694195391002930>()) {
            case -85549247:
               return false;
            default:
               throw null;
         }
      } else {
         Minecraft var2 = Minecraft.getInstance();
         if (var2.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sgxg7ccvlxxbb","8iP7jrQVgevscl5cin5U5F4on0olA6lkeVcCTcgJ9Lo=",4479229694094050112,-857160350419964165,7234647207603268646,-8060387800748548736>()) {
               case -1448852732:
                  if (var2.gameMode != null) {
                     if (this.d.a.a(var1) >= this.d.b(var1.dk()).bS()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s30yjqtqqqnk65","VWv1SXRXvG0dkYJqLCPd0mLQ5iXtjBWDHSbkhf8DgPQ=",5925605426612247956,-3567105241627011301,5385591098543442720,-8398562605332994017>()) {
                           case 1435287945:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        AbstractContainerMenu var3 = com.yiyiaddon.i.a.a.b();
                        if (var3 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2gltbombo86zm","CktRdah6IgM9X7M646OF8PvcEJXRyuSMTTp++2OpZwI=",-6855159215247521483,-1064267582603999090,-2148883931917383562,5465679349871792215>()) {
                              case -1944716002:
                                 return false;
                              default:
                                 throw null;
                           }
                        } else {
                           Inventory var4 = var2.player.getInventory();
                           Iterator var5 = var3.slots.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s13i0l8e707e44","XraBWl98KOK0iRIa+gBfVOec6eNpZYHmZP//50UUjZA=",6685472379731504905,-5121992364097094727,-9046303055973343411,-831510094525558586>()) {
                              case -1641268200:
                                 while (var5.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sf68jazvtwh61","5Zo/Qc8XHueHlEGKGu0zVYARqxVi/oC/HF4KfcJjSuI=",7161067466702797538,4089382395591948359,-2783282597877997743,-3823050670896186043>()) {
                                       case 779683527:
                                          Slot var6 = (Slot)var5.next();
                                          if (var6.container == var4) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sflmr967nyvzs","lQttVvfEaLgyCIoRU3S5F+IUutSJIHSi+hytZLQMOvw=",-2962705630632665114,-8754089062602261610,-2422527269624242149,1997626735341976508>()) {
                                                case -1297822038:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s6qaz1eavmydh","Z+xrzqziIHRqnmRLj0Bt3WV2/4RJMBEMVuXyQCxQ5ng=",8627797531358997871,-984728501745356326,-4108866460457152816,7775108593833513420>()) {
                                                      case -433580404:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             ItemStack var7 = var6.getItem();
                                             if (var7.isEmpty()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"swr8w30zkax6z","liJffHJg87ENICzShHJrZ4yU+U6TygZefBvJ0Ob6njM=",7543955599508986015,2904067489499426345,8125783756992249590,-4733156450083611324>()) {
                                                   case -1323557856:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1b4b7x7cubywi","noapbT/plJhVqM/z9ILGrY8PeNeY9PE7oKVST6a+2hI=",-1732131282259278186,-7418681364496838858,-6452640591658077372,116339917631774479>()) {
                                                         case -782931428:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                if (this.d.a.a(var7, var1)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2i4poxr5mp24q","gV5NsBfKRJKBXUHQRndZOUGuJcmH84T05+n4NyDqmrg=",-4928367511448800706,-3323514353573439343,6671503137221368690,-8758065693720644262>()) {
                                                      case -172564614:
                                                         int var8 = this.d.b(var1.dk()).bS() - this.d.a.a(var1);
                                                         return this.a(var3, var6, var8, true);
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s35krymx4drpkm","KQ/eyFoPDrs6G1JG4TuIvK6rIEOtHj7RWAQ6lMRCTYI=",8253085442281821207,-1320547026787851820,2915778310311124759,-8921638332822594171>()) {
                                                   case 1762667355:
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

                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s11my3juacqe2g","k0vyi8gWTeD23+7rSzz0oFi20IGhIJ33sjCC8gfIlmk=",7970421628650260131,-1086934272617296188,-8437609037885366418,-2667858562563409466>()) {
                        case -1307749760:
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
   }

   private boolean b(com.yiyiaddon.e.n.i.a var1) {
      if (this.d.a.b(var1) <= this.d.b(var1.dk()).bU()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1j9ff7ucsjwrx","qshRLOV9axwFtEoQXsUS3LhDbue4KFhPiu0bSS+0i3M=",-2717316220232414736,945632007716115070,4432000028312515332,273217050604192092>()) {
            case 654529659:
               return false;
            default:
               throw null;
         }
      } else {
         Minecraft var2 = Minecraft.getInstance();
         AbstractContainerMenu var3 = com.yiyiaddon.i.a.a.b();
         if (var3 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2l8d3ojbx40vj","rCmeRfs2gj0ShOxMsrxXopveTUuKlZ8iQi7CYuqdlOA=",4574172788471659340,-318259097540118505,-5032265420905599404,3732837264690775883>()) {
               case -1522328661:
                  if (var2.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"st0xa9coz1vrv","yEyCHxYb0fXyy2FH6Dct237PMGvFtyQxVi53/fWiLec=",7473954180766374180,4728873603689557744,-1648792138403195760,-1712417462824583116>()) {
                        case 1116708152:
                           if (this.d.f.fr()) {
                              Iterator var4 = var3.slots.iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"sot7gj2ahawee","yk6AHXkUeSd1hmWBQ+DgULad6lZ9791DFQz9qjxfhSA=",-8990726789450106328,5199430383200504166,5542494692726255521,-7435457250222216968>()) {
                                 case -4727312:
                                    while (var4.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1toc50qri5xjm","/4M4iNdN4TJZ1dKBUcHUaBqsp5cd+LTLLiaPnQ03EX4=",7567652190678449504,-9073420587003768733,7393419739749969980,-807569669720666434>()) {
                                          case 79174596:
                                             Slot var5 = (Slot)var4.next();
                                             ItemStack var6 = var5.getItem();
                                             if (var5.container == var2.player.getInventory()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1xofgktx1k7co","+ZR9YgPBdHbSkFhsEgP4rU/HePOxyyEgUZVVQX4I8Gw=",-4866275138079138550,5626559571867218604,-1295532145970457305,-6846153363870518681>()) {
                                                   case -816707277:
                                                      if (!this.d.a.b(var6, var1)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s33jvfog4r2k0y","82tfdKy4r7BSW4gWO+kmjeZ/Tf4uNlAIkRIcxIVACh4=",-3450740014709811084,-8316317432782216064,-2864921088463269603,-5300140027083298402>()) {
                                                            case -710285431:
                                                               switch ((int)com.yiyiaddon.m.b.a<"ses7q6ztdjnqi","SXh86Q+fPP6jQ471rmgEk19w7UG8c2GZQtb8dVfUyMw=",-3200277998708710079,3679157103814381714,-8513627830390560758,-1532131407723706171>()) {
                                                                  case 194237846:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         if (!this.d.a.aV().stream().anyMatch(var2x -> this.d.a.a(var6, var2x))) {
                                                            int var7 = this.d.a.b(var1) - this.d.b(var1.dk()).bU();
                                                            return this.a(var3, var5, var7, false);
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s1ne0mde50z92u","fWCnlZjicOG82LIttcTdEAzMpPIum02kBqFgvYslef4=",5432563006736252776,-6141519142832449498,5059269847212228368,-5712276240709765924>()) {
                                                            case 184219807:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2wyub9508k00x","s7KFb3ofVW10VZ2fJBfppv3q08Vs1lLLloHDlZxSaTc=",5211657854621236531,1367177411640258742,3747952127063194010,-4800695777743658100>()) {
                                                                  case 442962860:
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
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s28a3hn4v4fws3","tSI5x3zrKExOdcrERBnc+cHARCULy1KzsGWKpUgVLHw=",3257494990092306179,-1554204839936556193,-2222608098171078167,-288003042448510406>()) {
                              case 340222654:
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
   }

   private boolean c(com.yiyiaddon.e.n.i.a var1) {
      int var2 = this.d.a.a(var1) - this.d.a.c(var1);
      if (var2 <= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1c65i8e8c293c","oOZ/RzFAtxkocZPeiveOJK9NvMkjR0SCVL2Pxz0MEEQ=",5764914752642235501,-739533916854070426,-7178007044338708799,811343307312861487>()) {
            case 571425438:
               return false;
            default:
               throw null;
         }
      } else {
         Minecraft var3 = Minecraft.getInstance();
         AbstractContainerMenu var4 = com.yiyiaddon.i.a.a.b();
         if (var4 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2s9cw2bvrcf80","PLol7S2YZbi+aZ2tE2YZpAm0ppaZ2lNrmC4YcTJlybQ=",5178394339401417883,8850598336966092202,4169998113008751721,-6853270415799762894>()) {
               case -1818676373:
                  if (var3.player != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sexbbi53056de","U563oxl/fKe0g4q2W2ZC2zVpJYlnIDuifRGuN4bh7WQ=",4467201785651041804,8537883063974973180,-6004062374093590800,8841412566811994562>()) {
                        case 137632591:
                           if (this.d.f.fr()) {
                              Iterator var5 = var4.slots.iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s3ccuvu0dx44x8","qF7cfnsKe7/oGYwzxLVHNAE+QHMIhTa56dJ/JBE7nsM=",-7297948848944457862,-2165817790633558776,-6543994502110166427,6713319872995042241>()) {
                                 case 2016407708:
                                    while (var5.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sdffzo87g9loo","1geQ2lHtzTzF9MN2LMx5f7SNkb9Czv0AhUwJo83Aw1o=",-7581779206977228460,-2778041688478163176,-8179649435836456023,2370622172671411585>()) {
                                          case -1339496629:
                                             Slot var6 = (Slot)var5.next();
                                             if (var6.container != var3.player.getInventory()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s19mfy8blu854g","R9bm69qVZFYSuEyuplLjju3oepQaSrS4kfob0gVb+vY=",3143519205054608666,3576329359999301181,1762409887821242112,-1080028383398220685>()) {
                                                   case -788305394:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s28xhqdwpk9pfb","NJLVKdX9oQ8Pk2sRKi9noxhW4xiBZKJKGNq1P3mJxCs=",-1905137887167390094,-7388399597487210387,4430001619289142997,-1159769251602561623>()) {
                                                         case -19562839:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                ItemStack var7 = var6.getItem();
                                                if (!var7.isEmpty()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2zvg05ky5gjua","iNFG2ny1QgAGWeU+RAxgb0BvpE+tq6jYyNtZRbsXNpY=",628431775904347105,-933953376787585500,615319181143866615,-3817231658373026562>()) {
                                                      case -2010072191:
                                                         if (this.d.a.a(var7, var1)) {
                                                            return this.a(var4, var6, var2, false);
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s33c6i6g1gj68w","k8zZIcJZRxP+kMqsx7C95OSjDvm/dUbZIq8MZNoidEA=",2143041281949152661,-6030921722441570537,3413970851121067372,-8574828530811349677>()) {
                                                            case 872483389:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2j03lgtkq9sf3","im2Z3Old+t2M4fgHwEG0PF+X93bg/lFni1bYMtQL+jI=",4605386092485346531,7444801927909944756,-5125873420587041648,-2049013605783709708>()) {
                                                                  case 1903601876:
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
                                                break;
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

                           switch ((int)com.yiyiaddon.m.b.a<"s3t6t3384wuleg","uai0HunEbZ7cxPXDrYsAHhUN3EgyidzT3kEbckCPnDU=",-1309772877315751246,-2612975847423699744,-8024306766359499740,-7474222485313646762>()) {
                              case 1357421687:
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
   }

   boolean a(Item var1, int var2) {
      Minecraft var3 = Minecraft.getInstance();
      if (var3.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fsbulnp7nsic","r4z8xe95ICPKrhK9acZdw2rdrnxdUeygitbMgihTmU8=",-5635313473124931925,2221014682013371633,-2511830841690861935,1678546926189968049>()) {
            case 637059408:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2uc7szj5vqlsd","ZsOgn9MkhIrgV17J0Km+5rz2f/8TeCf55lpsO7nxqYQ=",7259158463674530720,-3351474431828772935,-11076734940079886,-2798635678092009759>()) {
                     case -1992306876:
                        if (var2 > 0) {
                           AbstractContainerMenu var4 = var3.player.containerMenu;
                           Iterator var5 = var4.slots.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3iphy1zencau","fPhMS6ovGvjfIZ+7kZPtSM1gPzqeltiLdKhJLRnjI9c=",-2652722099239334584,11824557606501570,-4601191160666186023,-6298321528415230652>()) {
                              case -1414849274:
                                 while (var5.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2nla3jrnprit8","n22mIgEXqh9UkzdKStmHUnYTRhcFUU6fF2Q4qxCtqfM=",-8723445418169397223,-6528723135011295836,3520457275222603853,847097517641288934>()) {
                                       case -1748976148:
                                          Slot var6 = (Slot)var5.next();
                                          if (var6.container == var3.player.getInventory()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2gkbc86pkfein","YX0M6WsFp8lZm0UyhcNJqVz3Y7wz30WarySfogQpgaU=",-2831149046963147753,238125106542711406,9136778330425978970,-8316783611048598133>()) {
                                                case 1228740532:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1w29nau8ypgl3","moNniJsHzMWDzQwLhSWPlJy/A7PDevL9kCsRf5OSGAU=",-2192950272987699021,-3001901018483786785,7688616653197238097,938454056448569684>()) {
                                                      case 39983345:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             ItemStack var7 = var6.getItem();
                                             if (!var7.isEmpty()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s21hqfofc5l16g","UbqkK7R747/ahzLA5bQXzazdkyn8U57FhENabnGruIo=",4641536828955359417,-9101642540227427186,4349892946455044592,8688100918126234927>()) {
                                                   case -967931868:
                                                      if (var7.is(var1)) {
                                                         return this.a(var4, var6, Math.min(var2, var7.getCount()), true);
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s265g85zvs19ys","imUCgdWe8yn39o/lZF99ie8IwsdMBWMB1KEFB6aA4cU=",-7109946196230568116,-2022484562932672367,8777966545706402060,-5566580227212568501>()) {
                                                         case -733135006:
                                                            switch ((int)com.yiyiaddon.m.b.a<"sdyqxo8kvofzo","K9EuHTrU+5fN7PWZxHxjLJ51ZGEYzgk027qFj2ohPco=",3090488358064953038,4786799993045545480,2677305383649624697,2687733449047785243>()) {
                                                               case -224916615:
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
                                             break;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s1t8frh4ctbbuq","RWxR4Q0PQgnkAXm7W7DVMMwTyIlkP8mF5/RbU6fYRtI=",-3467338806050420544,4914465470306995365,5344321052257200196,3012881459322866781>()) {
                           case 1086968361:
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

   boolean d(Item var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s34cmwb36f3c3w","E0lfSq8Z6terZHYx30phPf6CeD8eAVfqoNMI9dSdJHA=",-1652110315483382828,-4933887878501220858,-8398617508410229963,-4118883750532187486>()) {
            case 1623080928:
               if (var1 != null) {
                  AbstractContainerMenu var3 = var2.player.containerMenu;
                  Iterator var4 = var3.slots.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s11y9mzsj1xbxx","l3pnakYbQ7qBCmg/Zq1yzS+06ViYSXOdAV2Bs4RZYpQ=",5453020784706099227,847801356612859877,6230654231382723884,4758019608412413067>()) {
                     case 1403448341:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"slb7hp3x2hr39","J57tc7ciem4lBXrnj/YWZdM8CwRvUkMQE8ThBEqSvkY=",4762497488714962713,5467546942946840222,-3931998714454867996,-692177291423110172>()) {
                              case -764357093:
                                 Slot var5 = (Slot)var4.next();
                                 if (var5.container != var2.player.getInventory()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1l62dpsddf1q7","ARYSKlGVCu63EAWmiYeuy104cd4xUpsdLINZl4wprY4=",-2908965111770244860,5460040761341285923,-4802622142987984023,-3268694636446702005>()) {
                                       case 1096229610:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1275dy4te4sg4","waleEYJ+L5naXjW2Zk/VB3ouK0n/FB6zxKwOuHPxjy0=",1258039477168474904,-6019917366745109769,-7551011011975126723,-793411338680093395>()) {
                                             case -274925065:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    ItemStack var6 = var5.getItem();
                                    if (!var6.isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s313w8uxlu24w9","qsLlVsNyVNW1seTznbHYLVqXyFPpgN4QXrHXC9uXXsc=",107578828175567237,-5036491252173106600,4768353802755885759,2350577040198455650>()) {
                                          case 1163282328:
                                             if (var6.is(var1)) {
                                                return this.a(var3, var5, var6.getCount(), false);
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1uyy887xfnjw2","c4VQsu3YlOasPkUlWD5DO5rL5Ye9bxcEzCxLvW627do=",7056509970051481011,3256639308861840163,-498957800211283888,-2915660440312004780>()) {
                                                case 1212715764:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s31kc9djraa5xz","OXB4jKuOIcr6MT6pslRqC40i3LiyN3RMl1rZYH+3y3E=",-5871170331380442756,1657690060592348335,5367478110546887325,5027253085541501026>()) {
                                                      case 1076400257:
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
                                    break;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sbrqof4j2v4fg","6LmX9iWc9cd8gig24yyidH5BwJYkYjbqGdUKLBHeQo0=",2283634045479115683,3365178300357672420,-2415973337420318558,5513367243782486503>()) {
                     case 588184662:
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

   private boolean a(AbstractContainerMenu var1, Slot var2, int var3, boolean var4) {
      Minecraft var5 = Minecraft.getInstance();
      if (var5.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1phv8tay5gyqd","b5YQbkDT6pfJy3ww+dMSNnU+LqlwWjbUHCBWgAc6Do0=",472179718886231005,3510559761923772564,-6617412959768105301,7721841748728303920>()) {
            case 794805427:
               if (var5.gameMode != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2slffsba5c39n","VXg2GTnhhxwOYb4ntCTBQqmt38jUy5ncS5GWzd7D9Jk=",1520126161253785180,-2393479462599782887,5485989305375057040,-7811316472114874073>()) {
                     case -972759737:
                        if (var1.getCarried().isEmpty()) {
                           ItemStack var6 = var2.getItem();
                           if (!var6.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3afe0hii61rzn","OjfSAcaztfD/JoAD+dyYk4PKTxNtodkmv+OUqJiibyk=",-8371066550686798330,-6263678327889840392,6047549402112788276,-5119502385930993754>()) {
                                 case 1313744156:
                                    if (var3 > 0) {
                                       Slot var7 = null;
                                       Slot var8 = null;
                                       Iterator var9 = var1.slots.iterator();
                                       switch ((int)com.yiyiaddon.m.b.a<"s8r8gumt9kemd","kW1G+qJhqIvyKQ2NjMCY9rrGr31js9O+iEn8wgXyH1E=",7503400049013721729,6767021057010533443,7125962062716183664,-1696226533441235043>()) {
                                          case -346646937:
                                             while (true) {
                                                if (var9.hasNext()) {
                                                   label179:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s12uyfbx1822h4","Q0mmrqFEUhlYMKsS/DYeTzNH73kpGQk7RM+X1ksUotQ=",-6258848122459341945,-2589817523996752186,-9007604726166699188,8062710132057677374>()) {
                                                      case 1379780091:
                                                         Slot var10 = (Slot)var9.next();
                                                         boolean var10000;
                                                         if (var10.container == var5.player.getInventory()) {
                                                            label141:
                                                            switch ((int)com.yiyiaddon.m.b.a<"syhrls7n0cykl","7oMaYNItzplLbweSuS7iFLyhcv85CbZPZ8HcDrWqFZg=",-5657151129132195243,-6896735384878824402,9118703430916021476,-5885209962700567443>()) {
                                                               case -103518650:
                                                                  var10000 = true;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3n13kkq98p1rc","AgTV8JtPndRjeRR+oxUFih+GftmaqqNbIPF3g5MYdgU=",7571224286309673323,4478384182006236118,-6569432532162362781,-959676357799672592>()) {
                                                                     case 1753654612:
                                                                        break label141;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            var10000 = false;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2f2zskidl2tkn","XXLDzu1zkGkIDrBVSYOSwleBsoM4GS+s97ecsKiQOiU=",5200994458260553971,8655606243611659061,991358948013337832,-8534560506902543356>()) {
                                                               case -448054635:
                                                                  break;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         if (var10000 != var4) {
                                                            continue;
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"so65vvv0ghsve","yHvYA6aogIZiBzj5hnhs5n1SV7mFqG8Gaz6GHaJCrPg=",-8456165353577897472,879071306324438821,3583935369473561880,-4059188221072275429>()) {
                                                            case 18791413:
                                                               if (!var10.mayPlace(var6)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s12pzc6wj2eb80","H9+WFTeZvYDVwyzraYi1BNcwyK2R1pKv1RmefsDYjhI=",-6923261442874053231,-818888087490160722,-4527837257054252890,-3153497580814652630>()) {
                                                                     case 1196451936:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s32zgbur9el9qs","TQtIDyEw8NwnHxSbIInW9Oys1JxDH/y8ur1rnxXVkjg=",-5046345345229889226,3125587354006550246,8940463271678035580,-2605189808782645761>()) {
                                                                           case -838761037:
                                                                              continue;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               ItemStack var11 = var10.getItem();
                                                               if (var11.isEmpty()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1qosjeoyztlf8","NeFUwpHGLKVkttTHTNchn89RAVfVRrG2Z5ymf2BsgrQ=",-49097083197903439,-406836360303969292,2554546149977343317,1278350433119061292>()) {
                                                                     case -1267445235:
                                                                        if (var8 == null) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s22xvdb8kt10mm","LtTlaF2qH8HN5AybBG3RWIXbnvAx9M1pIQdrOLT4qvE=",-5927220796783009248,-2010855769409608013,-9188499427277456937,-876181208268292487>()) {
                                                                              case 658969991:
                                                                                 var8 = var10;
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s1izkhf7h2pzji","5RbHrwJS9LVJIdArm4cmwCw4BzfBzXOSn0ZdYWhr8b4=",3014712319438736583,7918056175910096008,-7265149888466228126,1395221558277401292>()) {
                                                                                    case 2033264472:
                                                                                       continue;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               if (ItemStack.isSameItemSameComponents(var11, var6)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s29jjhxr9sb0kg","PhOtn1Lip4XloEG461L+kJIq5N7IaCOikIWc6PR95O4=",-6060274823488480284,5058252316283038558,4365082655849726941,985330655834739077>()) {
                                                                     case 208158986:
                                                                        if (var11.getCount() < var10.getMaxStackSize(var6)) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1337g6dcip2w6","sMZUkCr/M2PMwl/uDp3S+gXULJSMxEtE70Cam83w1xo=",-3348794330479150874,5687793703582353914,-729047833174831411,3990954256330361398>()) {
                                                                              case 1727289871:
                                                                                 var7 = var10;
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s38zzccj6915vt","QTV8uV/Sw4WGKJRHc2iD5xUpGL02tshXLsWZ3zSRO4w=",-7353488984108742684,2577108240987303159,8896524108934378988,-6790375544453895745>()) {
                                                                                    case 515258816:
                                                                                       break label179;
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

                                                               switch ((int)com.yiyiaddon.m.b.a<"s152i14o0jdyhn","haqmmyg0v3zAfkcrUiv+vzrZqQwqt6lJUivEhl7Br/o=",-7318381688863526986,-2421247528167388827,-2119697946485857950,2811951214700010679>()) {
                                                                  case 492041549:
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

                                                Slot var15;
                                                if (var7 != null) {
                                                   label115:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2sq4aimwcp342","uSsx/i/JIAwNmdxooVTKJHnwFgE9cfqkQCOZaarCaeg=",-1294910152708876205,1130552088694461651,-6633986964345507932,-4200847473117055841>()) {
                                                      case -1452863435:
                                                         var15 = var7;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2nr55c6ua1kxf","ZvyY7EX0u2aNBAez9ZNM0y+X6bDwGcobtT2EaetWXq0=",-5391030953074537956,-4043457089549185357,-7563757737743008462,8401970191171442774>()) {
                                                            case -388191488:
                                                               break label115;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var15 = var8;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1gsr2h87wyx52","/Sqq0XXFRehOokv3Gz8ORIRzKvrtOrxVyBojjL5FB5U=",-1655243631923164267,3968028229118278700,2197216346538267590,6139300849870551934>()) {
                                                      case -665945993:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                Slot var12 = var15;
                                                if (var12 == null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1v7q8v0y3e9nk","AxX1ZMKQnn0hmp050mv50H/6jpTFq9eVa12L4TRj9K0=",-3756909439635441346,2499723549177912110,5649613257584747541,1809407482257725753>()) {
                                                      case 1798397126:
                                                         return false;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                if (var3 >= var6.getCount()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1yog6zyu1wkxo","j5OGKZEm84B6qCEmSyXfmw5yOz1tAlb5pdya4lLHZVo=",4437223005876059071,-6764336613630869988,-921126294658317171,-3112944207098554761>()) {
                                                      case 619761030:
                                                         var5.gameMode
                                                            .handleContainerInput(var1.containerId, var2.index, 0, ContainerInput.QUICK_MOVE, var5.player);
                                                         return true;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                int var13 = Math.min(var3, 16);
                                                var5.gameMode.handleContainerInput(var1.containerId, var2.index, 0, ContainerInput.PICKUP, var5.player);
                                                int var14 = 0;
                                                switch ((int)com.yiyiaddon.m.b.a<"s1wvcd4t8zij6s","gSEDJQIoW2aS1P5jP6C0HLCNuFsJb89nSEfcJCnafc0=",-3947643540077756434,-6155543300232842591,6391276973890187214,-6234978609523978442>()) {
                                                   case -1467216926:
                                                      label161:
                                                      while (var14 < var13) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s114utsdjv5957","n8AT3Zs2Zzpmwu6zoQ/Xb6TK9y8Gpat1esQYvGP6ZpU=",-5622105613727993662,6499895551943909645,1812656118990594604,-324541497760044508>()) {
                                                            case 1608353801:
                                                               if (var1.getCarried().isEmpty()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1ba3ceokkousq","vdoM/wvOICdG+v7Rlg7exUwTQa7EmPJNCir0jLSl+fA=",2959739461589031648,5207750798429459398,8861331403301607136,1819029258263801334>()) {
                                                                     case 320978254:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s25458xdjvyop1","KzKL8JHWqA0GVUYYgeWNMetJ7UmXHpGD+f0MX4uaB+I=",-4455953140380869939,-6720280429106909498,-7362874892129876833,-2469192078413035351>()) {
                                                                           case -1357054968:
                                                                              break label161;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               var5.gameMode
                                                                  .handleContainerInput(var1.containerId, var12.index, 1, ContainerInput.PICKUP, var5.player);
                                                               var14++;
                                                               switch ((int)com.yiyiaddon.m.b.a<"sw2mq2w6g9ssw","1xutsy4ZH4iq3gn0XimNnMvxUfBJpUf7++56F1a5EMk=",3427629975177377903,-7035080585923137064,-274889028773273760,-7635126471056688351>()) {
                                                                  case -80145178:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (!var1.getCarried().isEmpty()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3ajz0k29thh16","KxBRnN8ZRvbHAB7R6dftBOh56QuKuWU3xOz7VAxrOAc=",-2167961162806170942,-8692749140082166553,-1166214433587595865,107187738977427>()) {
                                                            case -2106483165:
                                                               var5.gameMode
                                                                  .handleContainerInput(var1.containerId, var2.index, 0, ContainerInput.PICKUP, var5.player);
                                                               switch ((int)com.yiyiaddon.m.b.a<"s109oaggx5ut2q","AF25NqVmA8WdnQj7Icry9JIWB/jHloxBszqSfijxhCI=",8645622723990896303,-2907635247252837946,-693778905025912499,5833551530077591051>()) {
                                                                  case 948862362:
                                                                     return true;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      return true;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1tww2rjc3g71e","6iWLXwSjK8RTXFglXOSq2YhF2uW1b4wzF8+jIjjF4H8=",-6785051209016015840,3930663370835095149,5339842048380258839,-2924017100801689766>()) {
                                       case 52061834:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s28k0ggjb1oia2","9MXV1J+t28L4Ufg1TYS9fbRBEFu+6tSP8FffyTU+kdc=",4148323261057376639,9087434417112287150,477895564695201669,5586178272780263198>()) {
                           case -1736814840:
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

   boolean a(Map<String, Long> var1, String var2) {
      Long var3 = (Long)var1.get(var2);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27btv04w2vxef","OmdkHMjFYGDuuARaAc6X0AfXKlJ7MdIomeeolLcdeeU=",-4718871404574360886,6154874092349823166,8381194604516389012,-7041993081547042392>()) {
            case -1611131780:
               return false;
            default:
               throw null;
         }
      } else if (System.currentTimeMillis() >= var3) {
         switch ((int)com.yiyiaddon.m.b.a<"s2iy2ezzy2gxzf","gIid9zPsBoXis4e3Anp26YJT/zlpKx6HBVBzL8tQJ9c=",9097833921480038641,7838274036718541011,-1828845347019666447,-9142063856097673345>()) {
            case 1395337370:
               var1.remove(var2);
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   private boolean dN() {
      Iterator var1 = this.d.a.bk().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3vn9ga10m7ap8","Qj3ylHMszbLUDbFubAIx2ch4/uwwbefMDyWWyoY2y2c=",-4964817451428946828,-580247666022801194,982130903496949135,7719192710771587798>()) {
         case -1537981869:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3gwlia2vg7eia","FAvHW9oECyd2t1jUlMXwpFxgDbVXA0kqmRPmKVelGqg=",-4236799134519639943,2535395935015620870,-4874278159371759155,9104420633093663630>()) {
                  case -1422809955:
                     com.yiyiaddon.e.n.i.a var2 = (com.yiyiaddon.e.n.i.a)var1.next();
                     if (this.b(var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3u4zlezy9q3yt","sHYcGayRufjiLuKtrfyZe3g4B+wD5amAIrm1UKLPV6Q=",2477502259513269546,189422942977879128,7013029598357814337,2572443781964745538>()) {
                           case 1836282499:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"szr5vwxauq0jw","o33pP2rMvLvLGc4lVC+yrO8fChYk/0nWi1gkacRxRMw=",759976241322053442,-2590616719005452858,5458882435920158030,-6107957319022234015>()) {
                        case -1492954715:
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

   boolean dO() {
      Iterator var1 = this.d.a.bk().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2g76fn8o6tuub","blWtMYmefM2D8fC+t2AoEY47t9yQzwADhJfA0qTTMHE=",-5497413731989751887,-6155075234008191103,-4251005797013937501,-8720635025711263653>()) {
         case 1628642722:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3mz5eqwszeadt","39mER4NPYI/EFUcCGIkXn8IaBUTdgduSYiSRwRrCwic=",3717216608112343796,-5279506576981700605,-4325666042747732412,-4794861362404929967>()) {
                  case 1882156539:
                     com.yiyiaddon.e.n.i.a var2 = (com.yiyiaddon.e.n.i.a)var1.next();
                     if (this.d.a.b(var2) > this.d.b(var2.dk()).bU()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3biqvbam89kfc","EswzKhTtZCYkMW45OwfsepdrzkHaxs5Z7KsZNl4byPA=",-5077337269201817490,111360051787013851,-7046555272464928104,5759954954904484673>()) {
                           case -1059008071:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s38etglcz7mpd1","INRcPx5OiBHWuWSboxCUziY5A1iBlmQc0QD8CKF+0lQ=",3241720833539425975,-2104447878179029561,8846414695008225388,-4422657300780466417>()) {
                        case 764589656:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return true;
         default:
            throw null;
      }
   }

   boolean d(com.yiyiaddon.e.n.i.a var1) {
      b.e var2 = this.d.ap.get(var1.dk());
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hq52zsrd63q2","sWOMAVScY6dw0t8RzEyG8+edKwe+GBm6ekQfERT3gvw=",-4627264649171414094,2190577061096825784,6218889114010885446,6167746098220162900>()) {
            case 42292484:
               return false;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.h.c.a var3 = this.d.d.a(com.yiyiaddon.e.n.h.d.SEED_BOX);
         BlockPos var10000;
         if (var3 == null) {
            label55:
            switch ((int)com.yiyiaddon.m.b.a<"s3nvytwqx1zk78","b/TSYjlZNpGtTLKuAWA2YYJfmNzzG9sV9KS0siOftGo=",4800728339906317872,113671551992050740,451350243518494625,-8580546810949423922>()) {
               case 1045119711:
                  var10000 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"sv5b8hzpfds39","ZDd8/HZDTxqWN5gNCbO6eQk3Yc+f1MHEEZuKHt8m+jY=",1025761721174553896,342206859034750838,5204155678674611186,-3244016557878523837>()) {
                     case 897606312:
                        break label55;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var3.a();
            switch ((int)com.yiyiaddon.m.b.a<"s3cuui5klz5mzg","OrExbHYguyyDd6rf+jxB+f62SwY/ZWY3PkD0HSvsJYI=",-4055258031901504229,-1845767697220890614,-7675718428840463097,-4474882908744681135>()) {
               case 1602100725:
                  break;
               default:
                  throw null;
            }
         }

         label68: {
            BlockPos var4 = var10000;
            if (this.d.a.a(var1) <= var2.cI()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ncbs80zc6opf","0D+Fwj1v7NUfN4KQztXW9D1lFSM+Ahvwz3CAHv49hKE=",-837198113979157591,3258392279484944333,547595041224222026,-8407846283493747104>()) {
                  case -1095614992:
                     if (!this.e(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1k31m3ppofvdx","mL816RkDquGLToThO7zhNa+KYC7Ive+cMEjoD8wRs50=",5500885864390995402,2732507834171058682,2518291568572395564,3671132058388174512>()) {
                           case 495243733:
                              if (Objects.equals(var4, var2.u())) {
                                 label47:
                                 switch ((int)com.yiyiaddon.m.b.a<"sgdh6tnsgt2th","wo5DH4wc0uEJ2B463oRXMKVQo2LD58uVEnUUrtY+EzE=",-6588622692123077061,1015336108021137966,2485483444626430247,-1596427605764818522>()) {
                                    case 2107611791:
                                       if (System.currentTimeMillis() < var2.p()) {
                                          var6 = false;
                                          switch ((int)com.yiyiaddon.m.b.a<"srjxsu3fqoqor","rLQ69gJ67qnkwoCGQmVRGjIVxp1BB+cf4fsUto4PZdo=",-4917967241665972635,-5390380940507400138,4195234380854682285,673301302778397596>()) {
                                             case -692738178:
                                                break label68;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s20ak5pm8ltb01","o+CrK7f2uk/R2PIms5BtekakpsJv9dt54jv0t+E8uhQ=",8952207700558067647,2714811612211581909,3645274744694113547,-7259690951136963176>()) {
                                          case 1018944048:
                                             break label47;
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

            var6 = true;
            switch ((int)com.yiyiaddon.m.b.a<"smnrdr76940or","ri9BdFH4Dx9ZkA36/+NUK2ungGCel6QSnErh3BA6nJE=",2017793136719040478,4880078419872434959,4749731308120010371,-6901000465248832863>()) {
               case -550064328:
                  break;
               default:
                  throw null;
            }
         }

         boolean var5 = var6;
         if (var5) {
            switch ((int)com.yiyiaddon.m.b.a<"sjodsdndvekb1","lnUOxhMQqUCXPY6l729lon4S1gjKF385hT58dv7FV1k=",-1577311913310127725,-8212138313422565049,-2024573463223745232,-4369046558357760321>()) {
               case -228510593:
                  this.d.ap.remove(var1.dk());
                  this.d.aq.remove(var1.dk());
                  return false;
               default:
                  throw null;
            }
         } else {
            return true;
         }
      }
   }

   void gX() {
      if (!this.d.ap.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s38tfe4lkhgp9i","oqz9IWGDu+pQm/7uC0BaFp/YwdyZpAGVi5b2th6A6RI=",-6905068564104754358,-6849376541832962521,5025920329265574722,-5325028835899580610>()) {
            case -741928509:
               if (this.d.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2lzdwwys5lirm","m9+MxjwvytAliZ5ATxzd5zXUtd2sAZbQvtbeIXHMUCE=",6979105465201403284,7832726941304947655,-7940435391264172647,-9195356608953630900>()) {
                     case 1042830844:
                        if (this.d.a != null) {
                           Iterator var1 = List.copyOf(this.d.ap.keySet()).iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3w0i71eltbquf","87UuCsPFfsBqq+MHZtfckjSoOqIEF1FaOv+NhKKBgD8=",2252924554406034158,2356646145349245187,2867358628570514488,7140924493132802548>()) {
                              case -1498245139:
                                 while (var1.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sv73flme6nd72","ZyyGLVbwWWKOrNLiOzgrKs554UQ1qklhNrdma2Vp4uw=",-741011403171392307,-143101709902830114,-837400799034755683,-6023002733558777241>()) {
                                       case -885599170:
                                          label81: {
                                             String var2 = (String)var1.next();
                                             com.yiyiaddon.e.n.i.a var3 = this.d.a.a(var2);
                                             if (var3 != null) {
                                                label48:
                                                switch ((int)com.yiyiaddon.m.b.a<"s16a88c7p6u2m3","WoeAgru32REEXLb4wwWoywOCp0GE6gsd1opSRBZPBdc=",7830172843579909058,-7668988236184871263,9055465675024149009,-8750401298032941434>()) {
                                                   case -1218079388:
                                                      if (this.d.by.contains(var2)) {
                                                         int var4 = this.d.a.a(var3);
                                                         Integer var5 = this.d.aq.put(var2, var4);
                                                         if (var5 != null) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"sbj0srvs2vmkp","3F/K0s8UOm3UokJgGQk3a/NIOBvElLtqZlczhk8OmX0=",-2532613830901708775,9074863226278971409,-8595802545477800878,3844343080794888966>()) {
                                                               case -1215218123:
                                                                  if (var4 > var5) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3he13095hzfi1","8E5TiaDiUX+FnJAPUjc68MP4Cyf7F5Shf8XVY86Oti0=",5058874385372649087,9099963585347956339,1773607870925264148,-2407268225609088673>()) {
                                                                        case 1450163310:
                                                                           this.d.ap.remove(var2);
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s1d00oy1fvxlgx","EsNYFSfT5hsrxjneFs4xhg3QXBlN4VPHfZHivBKL2Bo=",-6474602784264230225,4437848558742274249,2901413970661847981,1751025297788696520>()) {
                                                                              case 39577397:
                                                                                 break label81;
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

                                                         this.d(var3);
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3oz9v2i068sth","TYQr+exnA0bdMsTrBtV8QS+t2efpWgxd/Pfwbpr7TSQ=",3988710127112123654,9135102586402590495,5998250497248532322,-1353006025972616502>()) {
                                                            case 622822949:
                                                               break label81;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s26ti6d89r1dg3","j9D1GYxUEu6djW9bmWVT9NflWfgu+mUjWvvaZ+QhlNE=",9120555698708579293,2563257440324395317,-7469264803152615548,9018366477464205052>()) {
                                                         case 1226648243:
                                                            break label48;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             this.d.ap.remove(var2);
                                             this.d.aq.remove(var2);
                                             switch ((int)com.yiyiaddon.m.b.a<"s2bp9dyneqrcyh","xXKBVcpmh7qcvTqk0TZHGHgkTdm+jZ4fTl9X+GdcEqU=",3611208958875753769,8947333944481940639,-5480220677616799715,-7082508439600366808>()) {
                                                case -575047488:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s22usd61aznrwz","WH7nL9ujpzkccHpUNF9B9qBBVtF0gWa8ect2d2E8m3k=",7789433408494108572,-3789670468943878785,5437296773476285425,1400944175196305255>()) {
                                             case -1430188653:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s36hqe51dp11xy","NpDwK1YwmwdCSmP2YMH238vi6j88SEITWjqD0obhw60=",-4392845203010469099,-5001620073067930098,-83466173202803941,-8736780839663771004>()) {
                           case 2127639499:
                              return;
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
   }

   private boolean e(com.yiyiaddon.e.n.i.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ov4k8hmqjh2e","wdR/QDihVGZq8TYfvmeiJ5KPkLWv2DGH2gPsEvltw/w=",6148487857033670851,-3308150884444454979,826994340629322872,-5334994650830651839>()) {
            case 1971800087:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var2.level.entitiesForRendering().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s236a88ky6ynmz","9YencahoWF3OHMUtWIEPNyfCQ4T1mbzNP63Jz515FnM=",9020922498453611220,7886069818879617670,-5736194056452502713,4035112521041193143>()) {
            case -1250952485:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1w58nnk2spl3q","sTgIWmxcahln71Od/TwDfEvFG+xdG+6NcDyStAQ9IAU=",-4059672759365989185,-8122503036264277267,2883257072045584053,-6004809362603112560>()) {
                     case 1322807811:
                        Entity var4 = (Entity)var3.next();
                        if (var4 instanceof ItemEntity) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2sidt5e4hqbth","5lEs+zyokz8rZW894sbEEXQjoAY0M/2OIkH52iRX2dY=",4655599966263407464,5448201958203960047,2787535678397858651,3874547239253021497>()) {
                              case -142241918:
                                 ItemEntity var5 = (ItemEntity)var4;
                                 if (this.d.a.a(var5.getItem(), var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1opk6tosbvigi","WK2KJ9+qq340R8WCnHOsq/dPR4+KBifU3ZqycDIbNRc=",74665030547826844,-6679699204734053392,-5499193358540365289,-6836460028908829088>()) {
                                       case -1804025907:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s3jra7nn3q2oj2","IH/J+PTXpnel2DUnQnjHfoiVq2ooEVvAw4HGXPvFquk=",-7618939266064432122,-4893167026358913770,-1941882509545413299,9056335376294371843>()) {
                           case -320236967:
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

   private void gY() {
      AbstractContainerMenu var1 = com.yiyiaddon.i.a.a.b();
      Minecraft var2 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"savn6xfghzyku","GojQpnHd9gp+vLGVv3AKsTU3TC/VtltAeExwnuXEo+s=",8635508134667418948,-63588729010928824,-7936284940432220559,-4014181583654998813>()) {
            case 213233747:
               if (var2.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2h7nx0j7eawrj","taDQjiCMADI0p77EEkT1NvPoNcz7Ix7ldxOY9YMGxAA=",142757762942311828,-7341703806830581655,-6445777746352041690,-3493856098066760690>()) {
                     case -2040223513:
                        if (!this.d.ap.isEmpty()) {
                           Inventory var3 = var2.player.getInventory();
                           Iterator var4 = List.copyOf(this.d.ap.keySet()).iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s1ueugih2419kv","ot9AEnNcXVbG7Jj62Vf028os8tFL5t6u8F8FQnlJiqU=",2681329401467151548,3610065860615728650,-2981734216016263675,7213662924245872461>()) {
                              case 2106897516:
                                 label96:
                                 while (var4.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s346b1488w9xzm","gBv42IAekgYleHkr3jTcHnAtjcQyD6AoNYkWy0WqR6I=",-5620063458100965918,2027378358642118593,4453901206500488166,5564119388057891471>()) {
                                       case -569475434:
                                          String var5 = (String)var4.next();
                                          com.yiyiaddon.e.n.i.a var6 = this.d.a.a(var5);
                                          if (var6 == null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sqa9ubuhzst6","zO+p+JveZGLj31YG6NIVFOuq9ws+TxXX9hBNOHIGtYw=",6086110689777426314,1154810115767880163,6408402246846050789,968467103415280361>()) {
                                                case -1378637275:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1uzpsuzt8mw64","TQ3BcuMXDeRBczhna3Ne3asm0xWsyUXkWhwG6rSRiCg=",-2560099028526606047,-2886459477972509504,-7590809781277280104,5799417187809303921>()) {
                                                      case 1655380906:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             Iterator var7 = var1.slots.iterator();
                                             switch ((int)com.yiyiaddon.m.b.a<"s2nlfkqkg7yooo","1lBdXaKSc3KDY37+kDIDTGySyhU/faB9ohUYoQTl6xw=",5572610780842581784,6437778375419897914,-6860965935585400775,7173920400612978387>()) {
                                                case -663753709:
                                                   while (true) {
                                                      if (var7.hasNext()) {
                                                         label52:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1ux4kmlotx493","Vla+mPoqErbz6577s/hzonZ+2zrP9blSzBR40+0uEhI=",-3781955241232741308,4766192339960341851,-1499284919750144502,3997578776192084929>()) {
                                                            case 926968557:
                                                               Slot var8 = (Slot)var7.next();
                                                               if (var8.container == var3) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"szlevb7xb1op9","0XQa5IWa5z21NrcPPS7bE/d33Elwrb0rnh2y5ZmQutU=",-1057320546028915215,4977487186842014375,5568682391856957511,5698668443358109868>()) {
                                                                     case -67761019:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s5ejqxxppou1f","IyQormb6WSZzOWhcXZ/XcvtT0JI34WG0NHtXvXP0l/E=",-5916109834021004392,-3575843541055549029,8980468669176154592,4819635244409792893>()) {
                                                                           case -1694961608:
                                                                              continue;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               if (!this.d.a.a(var8.getItem(), var6)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1ilmuqk13d361","AVWB8QZVLp52GW1cbkkfH1FdAZOv00M5LiRv/d7hwf0=",-7097914217775762101,1270230937078860318,-3092191715971384869,-7109029158088928837>()) {
                                                                     case -917485986:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               switch ((int)com.yiyiaddon.m.b.a<"s26dsteac53niw","0LqS6hTJ9947XhPwOkniDlPH5r0loiQNvVAMWGaQ/9M=",-8178461314977717053,-9007128408287829415,-3312287297502236585,5942812961167950454>()) {
                                                                  case -604084048:
                                                                     this.d.ap.remove(var5);
                                                                     this.d.aq.remove(var5);
                                                                     switch ((int)com.yiyiaddon.m.b.a<"syh6grrdroy5v","yjbxtAe6VlHO5yM/P8PKd2TEXd0ip6NHJJNdkhJFudg=",-3774489490312098878,173576149763461666,-6243225182108851025,1476618978532687382>()) {
                                                                        case -1039665543:
                                                                           break label52;
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

                                                      switch ((int)com.yiyiaddon.m.b.a<"s27nyfmaxl52wf","y0rVqHciDycfGN+Px3CBc8rwNHef+lbVsznjH8kHC+U=",-4894253269741042170,4017220512269125065,3324360799330054045,-2840020667831831545>()) {
                                                         case 1980514679:
                                                            continue label96;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                default:
                                                   throw null;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s3j5ycqn7xiaby","tRYkXiRv8kpAygTy32kim+dFxBxMXfAPAuUWxZ6Upas=",-366110995450333720,-3014747532108443711,4854713316106082,5109259806513802777>()) {
                           case 2092893855:
                              return;
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
   }
}

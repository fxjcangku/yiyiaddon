package com.yiyiaddon.d.c;

import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundKeepAlivePacket;
import net.minecraft.network.protocol.game.ServerboundAcceptTeleportationPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.world.entity.player.Input;

public final class g {
   private g() {
   }

   public static boolean b(Connection var0, Packet<?> var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ifwn7caswsmm","jExsxlIbhvjk3J5SG2cVgP+NEumwj5tAo85CtkTbs7Y=",-3636165314616231840,-2367546491451262765,7444611782187891251,1699134186803160003>()) {
            case 804791973:
               return false;
            default:
               throw null;
         }
      } else if (c.k()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jan4cvmn6b57","ldp5hpAdYZXtETf8A0mSdRpYc4hprFXuuydXvAAbb+U=",3705801250741061964,1881442889744633978,-6855735593967157736,-3030621613410743119>()) {
            case -1466682757:
               return false;
            default:
               throw null;
         }
      } else if (f.n() == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lqkij1yr2d4v","kW9q94VhneL1e9lUOPQ/9lCAE7jnpdWanvqBpBryh/A=",7182665626723425330,-5107643460786814371,-5387022090821989192,-5045419183276912020>()) {
            case 1159672430:
               com.yiyiaddon.d.a.d.d(var1);
               return false;
            default:
               throw null;
         }
      } else {
         i var2 = a(var1);
         d var3 = f.a(var2);
         switch (var3.a()) {
            case CANCEL:
               if (var2.a() == i.a.CHAT) {
                  switch ((int)com.yiyiaddon.m.b.a<"sykyeta9gptst","6KZtXM1ii80Ea9F82jjeHxKqtCkZ27HAFdd8TfclUPs=",-9155171480739489133,-886868645457403830,1341593906420032392,-6920719339199330241>()) {
                     case -73384064:
                        com.yiyiaddon.d.a.d.i(var2.A());
                        switch ((int)com.yiyiaddon.m.b.a<"s7mljzd4mjnuw","nBKoGfjQknRxqsE2k0AIWQqViC/zNzqmhMKGCf+V4H0=",6977639627161182922,7169028576066103117,1940864976473695170,3404018674624348803>()) {
                           case -1540380329:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return true;
            case REPLACE_INPUT:
               b.a(var0, new ServerboundPlayerInputPacket(a(var3.l())));
               return true;
            case DELAY:
               e.a(var0, var1, var3.d());
               return true;
            default:
               com.yiyiaddon.d.a.d.d(var1);
               return false;
         }
      }
   }

   private static i a(Packet<?> var0) {
      if (var0 instanceof ServerboundChatPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gchv6niaeay1","ERqS8U2nRdP1CVTK9wZN0f7ee2B4MDaiCGbAlHlTMjY=",-4103215874202413714,-1592196183003728840,5249828042606029855,-3215136204873743328>()) {
            case -967736313:
               ServerboundChatPacket var6 = (ServerboundChatPacket)var0;
               return i.a(var6.message());
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundPlayerInputPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2f1uqvfl9lllx","ErMpd9iw8jFnfvjnUBgAvUqsqNZefumpF1TwOpdvPsw=",-8819128147914964532,-3456804409655680571,-4716191958813858352,-836365231253571462>()) {
            case 213687581:
               ServerboundPlayerInputPacket var5 = (ServerboundPlayerInputPacket)var0;
               return i.a(a(var5.input()));
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundCustomPayloadPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zyo5lu5hrpfg","TNEfQJoUWzAfsxA8brHenMoiBRGB6oYuLAshRVvG3Ns=",-5254885733106606694,-1327408120374649696,3305156758429076863,-1672943560085414865>()) {
            case -1454806686:
               ServerboundCustomPayloadPacket var4 = (ServerboundCustomPayloadPacket)var0;
               return i.b(var4.payload().type().id().toString().toLowerCase(Locale.ROOT));
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundPlayerActionPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ugx3bfladwok","LyWBz6dPQDofvke8gWnBOYA8nQK2sGBHzRNxZXC+N3c=",-4219876929428304104,4650412396034674812,-4065066584002979251,-1781992429004652047>()) {
            case -2127506081:
               ServerboundPlayerActionPacket var3 = (ServerboundPlayerActionPacket)var0;
               return i.a(var3.getAction().name(), var3.getPos());
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundUseItemOnPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s346nstmqlrbd0","6yoah+fuHRV9kYMSLINRMHqlugNI1tjSlO+4mmrRakc=",6274468717099761605,-2375186257340511842,-6927962432105100172,8765558497676834702>()) {
            case 119088044:
               ServerboundUseItemOnPacket var1 = (ServerboundUseItemOnPacket)var0;
               BlockPos var2 = var1.getHitResult().getBlockPos();
               return i.a(var2);
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundUseItemPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s6jkbftz6tel6","+gRku0G7enTrdDSo1HPKNdWHzQ5Sy9grt9Yzt/LC8HM=",7192798999634629853,1761584678469480446,-7984018603977971689,4888910926904042297>()) {
            case 1681740129:
               return i.a(i.a.USE_ITEM);
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundMovePlayerPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ilc5s26a92aw","NSIfGdR6yqiWoIjyUZ2pHCk6uqN9svYXfCdkDDNVnSY=",-1580915324763690076,681864248998380032,7618046192935387801,6913568274308183445>()) {
            case 259040864:
               return i.a(i.a.MOVE_PLAYER);
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundMoveVehiclePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s1itzn9dcys35f","Oa6mU+OJAwwSC7OBVQqApvBaJS3ppXfbZVJdji+qIj4=",4540912345122962377,452236032636859710,-8371136569593476208,-2427497782536688968>()) {
            case -665620993:
               return i.a(i.a.MOVE_VEHICLE);
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundKeepAlivePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uai6ub5cna0l","3JJDca6CEaRkHeIQqhfR2pfkDFepuCNmoEX02AKsZog=",-3211026329195483408,-5087571768601144538,8975461792418168544,5692566429052383053>()) {
            case -856798028:
               return i.a(i.a.KEEP_ALIVE);
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundAcceptTeleportationPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s15x5pg3v8qpli","zo2b6MDZ4NbwlGd0hYsmxII1+UD1jmYq9AZ3qJl/YxQ=",3928361367745825256,-168551035485695208,-1916384721607812664,-7698200636696734692>()) {
            case 1527088841:
               return i.a(i.a.ACCEPT_TELEPORT);
            default:
               throw null;
         }
      } else if (var0 instanceof ServerboundPlayerAbilitiesPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s4aop5oaxugxc","+EyN9jtViEfxFi5jeSQAgl+bROIZeFn4/ld+7tBn8u0=",-5794126441298217534,832654493909583167,-4358913431668610024,-8000817110523977815>()) {
            case 2016859483:
               return i.a(i.a.ABILITIES);
            default:
               throw null;
         }
      } else {
         return i.a(i.a.OTHER);
      }
   }

   private static int a(Input var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11rrpsym2btbs","vBXh0TOx7RsBJKCdFODznDfo8uLeanwcwxpgKf73+C0=",-701184674222377808,-4825872618893685301,-7854472728657382503,4368155632090747484>()) {
            case -305503642:
               return 0;
            default:
               throw null;
         }
      } else {
         byte var1 = 0;
         if (var0.forward()) {
            label78:
            switch ((int)com.yiyiaddon.m.b.a<"s7b0owxzl7pe","j6/+6I5jjK5AlQNtmO1FJzR5YEJ0eQ4x84h6EI72qOk=",9062337495195001545,4717241447175411116,8930091325097439990,-4307148470677497730>()) {
               case 2095051900:
                  var1 |= 1;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ad2m77228jln","CDQaI8fh/+NTL0zNpyOPN9aGcF979lrvzDi85f8/AP8=",3368653161442806641,5824766493785159528,1528251459009034182,-4727572937939371131>()) {
                     case 1590507534:
                        break label78;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0.backward()) {
            label73:
            switch ((int)com.yiyiaddon.m.b.a<"s38xm0azxh2ge1","w8W5Jjb/ICsq+qHguWc9V4ykkDtBYluiVtD3B5tkfOw=",-8171520541999908950,-6918452430176715082,-7895157495474029116,-6103440316075815026>()) {
               case 835781495:
                  var1 |= 2;
                  switch ((int)com.yiyiaddon.m.b.a<"s1k0tmvyjbmsx3","FEkvqZMDlsRH3bzBztihng8kKYwcjYq3rl2BhkmLWso=",3802518315507129840,6123102087023296827,574261804900782356,-6964248837792493945>()) {
                     case -1295301806:
                        break label73;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0.left()) {
            label68:
            switch ((int)com.yiyiaddon.m.b.a<"s3de628o63j9pt","Xmn+zV2iILGKZzZ+6KIPM4RDEEQBB0rxf/VcE4Nq6RM=",5774309397854682721,150453610918159655,1114587410525878945,275999030971838157>()) {
               case 1198631373:
                  var1 |= 4;
                  switch ((int)com.yiyiaddon.m.b.a<"s15wxy4soeh2g0","DIH6Olnx5VUyeREHHmrVF64wVoMAcwWNaJ+yh/UFV5M=",-5592666286027317079,-4686060292086776107,-6265997387641306413,-2869857856800828575>()) {
                     case 1480433788:
                        break label68;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0.right()) {
            label63:
            switch ((int)com.yiyiaddon.m.b.a<"shqmi43lh44wj","d63pTyFNaZ0yz/lhgptXYCRnnbbaz+7hs5GylUAEfAc=",2236060487899562293,-2562306588995127293,-754258684641916968,-6150777527163531184>()) {
               case 1927546028:
                  var1 |= 8;
                  switch ((int)com.yiyiaddon.m.b.a<"s3rxns5925jdad","zP/FTeuxb+u9sB+5yte0YRNDaaSkm9s0NxgI0brtOpk=",5065881069949789779,6066208791624831244,-1552939935858181502,-1876185968662924447>()) {
                     case 2121031413:
                        break label63;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0.jump()) {
            label58:
            switch ((int)com.yiyiaddon.m.b.a<"s1nnjprh62t9jk","l5nuGrOURdQKU9nOomXnzrxOQ1O9i5kTcvjT37pReOE=",2916905674609147948,114365051342394967,-1567591755163068907,-5887776048078282788>()) {
               case -303157686:
                  var1 |= 16;
                  switch ((int)com.yiyiaddon.m.b.a<"srw2t10qgy4a2","/YePynrFWrfOGsczpSlAp0QWQh2f33ZEp3J9MIzKnFk=",1999742973753415466,2192862568431514796,3004232558301688263,-1605710188490067245>()) {
                     case 636535094:
                        break label58;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0.shift()) {
            label53:
            switch ((int)com.yiyiaddon.m.b.a<"s3h5j35xppgygk","oNSDLg/U3/8T3+RKSG0l46g/ue2vntyvXjSWFba14xQ=",-5563410858515008944,-2678443858413206622,111951618870358990,2921135778538903613>()) {
               case -720668528:
                  var1 |= 32;
                  switch ((int)com.yiyiaddon.m.b.a<"s18vi0w1pmp301","uI1dFmodOKvlR87jTWU/wJgkbpqNAEFUJ3Sh4r3e/cc=",-585356373197037855,-8251719431684769027,-4429657622269430840,-238680451432567001>()) {
                     case 315652887:
                        break label53;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0.sprint()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3d5d8c1c8smx1","kS/wM153RndM53u8oGQXlyP7ABHNzi0GY0xseJW0754=",-7757179565494659670,-8304456856334803070,-6160355590751083889,-1767823857336469029>()) {
               case 754614029:
                  var1 |= 64;
                  switch ((int)com.yiyiaddon.m.b.a<"s15ro38x9yht57","KqDfDSgWzkIHxWgQpnBTawmgBfssflG6S73M6afjtjQ=",65923091575944013,5588747081562555845,-8388770589054604660,1911170266772143267>()) {
                     case 1921063033:
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
   }

   private static Input a(int var0) {
      return new Input((var0 & 1) != 0, (var0 & 2) != 0, (var0 & 4) != 0, (var0 & 8) != 0, (var0 & 16) != 0, (var0 & 32) != 0, (var0 & 64) != 0);
   }
}

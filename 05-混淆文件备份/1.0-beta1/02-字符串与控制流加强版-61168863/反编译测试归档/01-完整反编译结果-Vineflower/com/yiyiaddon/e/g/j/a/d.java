package com.yiyiaddon.e.g.j.a;

import com.yiyiaddon.e.g.d.j;
import com.yiyiaddon.e.g.d.o;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.j.l;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class d {
   private static final String kf = (String)com.yiyiaddon.m.b.a<"s33y4xy3u9yrtu","FC3FnzEgv0WmcdNNqdIhX9xkuYKjs4TjYwfFgPU68D0mKQ==",6135606916734867672,7557437472842445795,-6600254671330502760,8206228634962026889>();
   private static final String kg = (String)com.yiyiaddon.m.b.a<"s1gghfzufzq7fi","V7Moypi/vlXihwxo1tHPWW0sRxgBjVFM2RVNBxPlQPJ3jNFzX8rPARMs9ritpFQQdWbl9IAF",-2657762678645187033,-1208024179211951728,2718163131221356045,7825352322883477462>();
   private static final float aK = 220.0F;
   private static final List<String> aS = Arrays.stream(com.yiyiaddon.e.g.d.e.values()).map(com.yiyiaddon.e.g.d.e::D).toList();
   private static final com.yiyiaddon.e.g.b.a d = new com.yiyiaddon.e.g.b.a();
   private final com.yiyiaddon.e.g.j.a e;
   private final com.yiyiaddon.e.g.a i;
   private String kh;
   private ItemStack g = ItemStack.EMPTY;

   public d(com.yiyiaddon.e.g.j.a var1, com.yiyiaddon.e.g.a var2) {
      this.e = var1;
      this.i = var2;
   }

   public void d(i var1) {
      com.yiyiaddon.e.g.b.a var2 = this.i.a();
      var1.a(
         new com.yiyiaddon.l.c.f.b(
            this.e,
            () -> (String)com.yiyiaddon.m.b.a<"s2u8utr406jvx7","tQEMew8BatTXrxXHWFvZEpa113wsNSpTkxV/SZyZAKElFtmV",1613438759072267237,-8542815562717350212,3559671761634296021,-4893425038069120736>(),
            (String)com.yiyiaddon.m.b.a<"s1iwdmp6rary8w","IPfTpHhrSHbaNsoBeJMvstJ3nnw/ksJw2c/swLh3ohxPOPkgXLqtmFslOYCIj8ilN+6kTc7MFGRgO2L3D03BbOaSEzupIcNFdjjdWInCB5BjOnORg/Q+4SfGuruYSyhofGlr0dNpZ8a42sHwUTrUbSTzpuseAMtTqlgY/gQqW5puTwkeWB5qzA==",-5328346766373939126,-1748834812771193755,-2344455478029304300,-144117047992758295>(),
            (String)com.yiyiaddon.m.b.a<"s2vbs84y6a0gcy","7yi7hWWGooxq3QbwK16X38SOi1NL0npXA3w0HwpweL3ABIX2SMET0g==",-2232983083209767661,3204775373625159426,-2647919480452777390,156994774768666190>(),
            List.of(
               new com.yiyiaddon.l.c.f.c(new com.yiyiaddon.l.j.e(aS, () -> var2.a.ordinal(), var2x -> {
                  var2.a = com.yiyiaddon.e.g.d.e.values()[var2x];
                  this.i.L();
               })),
               com.yiyiaddon.l.c.f.b(
                  () -> {
                     var2.a = d.a;
                     this.i.L();
                     this.e.C();
                  },
                  (String)com.yiyiaddon.m.b.a<"s2u8utr406jvx7","tQEMew8BatTXrxXHWFvZEpa113wsNSpTkxV/SZyZAKElFtmV",1613438759072267237,-8542815562717350212,3559671761634296021,-4893425038069120736>()
               )
            )
         )
      );
      var1.a(
         new com.yiyiaddon.l.c.f.b(
               this.e,
               () -> (String)com.yiyiaddon.m.b.a<"s16bfybgx9kk2y","dif3z5vLCLcBchoMlgBGf20HAUXgh28blFH7TH30yoZCYHPAs7B3TA==",-1858394691490544884,-7921310653377339444,5043044978969720469,-7384117768265090671>(),
               (String)com.yiyiaddon.m.b.a<"s1gghfzufzq7fi","V7Moypi/vlXihwxo1tHPWW0sRxgBjVFM2RVNBxPlQPJ3jNFzX8rPARMs9ritpFQQdWbl9IAF",-2657762678645187033,-1208024179211951728,2718163131221356045,7825352322883477462>(),
               null,
               List.of(
                  new com.yiyiaddon.l.c.f.c(new l(this::aW, 220.0F).a()),
                  new com.yiyiaddon.l.c.f.c(
                     new com.yiyiaddon.l.j.a(
                        (String)com.yiyiaddon.m.b.a<"s33y4xy3u9yrtu","FC3FnzEgv0WmcdNNqdIhX9xkuYKjs4TjYwfFgPU68D0mKQ==",6135606916734867672,7557437472842445795,-6600254671330502760,8206228634962026889>(),
                        this::cH
                     )
                  ),
                  com.yiyiaddon.l.c.f.b(
                     () -> {
                        var2.aw.clear();
                        this.i.L();
                        this.e.C();
                     },
                     (String)com.yiyiaddon.m.b.a<"s16bfybgx9kk2y","dif3z5vLCLcBchoMlgBGf20HAUXgh28blFH7TH30yoZCYHPAs7B3TA==",-1858394691490544884,-7921310653377339444,5043044978969720469,-7384117768265090671>()
                  )
               )
            )
            .a(this::g)
      );
   }

   private ItemStack g() {
      String var1 = new com.yiyiaddon.e.g.d.i(this.i.a().aw).aF();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sdpthzlxars8h","W9xJVW9cNZUcCxqebVA/FZN0n6sJUVyn8yTnKEmG1sU=",3588671379391509074,8582031303468649088,2879749683393973053,-1455359105707578334>()) {
            case 1007733935:
               if (var1.equals(this.kh)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3pfkosy3gmhya","v2Kns6JAK6rUOmjxXZddaYRKP7soYzjW7v6TEKWXALY=",-7809265129412364400,-5690446861041244776,-3830851745302753136,4239483615424297785>()) {
                     case -155343766:
                        return this.g;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.kh = var1;
      this.g = b(var1);
      return this.g;
   }

   private static ItemStack b(String var0) {
      if (var0 != null && !var0.isEmpty()) {
         Identifier var1 = Identifier.tryParse(var0);
         if (var1 == null) {
            return ItemStack.EMPTY;
         }

         Item var2 = BuiltInRegistries.ITEM.getValue(var1);
         if (var2 != null && var2 != Items.AIR) {
            try {
               return var2.getDefaultInstance();
            } catch (NullPointerException var4) {
               return ItemStack.EMPTY;
            }
         } else {
            return ItemStack.EMPTY;
         }
      } else {
         return ItemStack.EMPTY;
      }
   }

   private void cH() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jj2ybfak4cfz","58//dKh3POhKFBBeYrzF5F9ZyvcaH59kCsEqhzf61Ow=",7768751500996125096,-5836016760038926781,-6290580973548372801,8325700216534542670>()) {
            case -843752170:
               return;
            default:
               throw null;
         }
      } else {
         var1.setScreen(new h(this.e, this.i));
      }
   }

   private String aW() {
      com.yiyiaddon.e.g.d.i var1 = new com.yiyiaddon.e.g.d.i(this.i.a().aw);
      String var2 = var1.aF();
      j var10000 = j.a();
      String var10001;
      if (var2 == null) {
         label50:
         switch ((int)com.yiyiaddon.m.b.a<"s1srygjsz01nlh","SWzJziPe/Z809sF2JLBCJ93t7bzFdp1K8+W4mdWn8Ok=",-1494525392525003007,-2972538798101018923,-5777410178200730043,4958566396221494255>()) {
            case 2021754790:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1hkmje4dbxfa7","UJISg6NdzNv3IFjFkpNGr6Hj608q2WZVnRdOow==",5874567525526604480,-1830044402149927150,-2607677576589716811,5074607251923671542>();
               switch ((int)com.yiyiaddon.m.b.a<"s2hap9hv03vx7k","sqElZkuPPH0JBlst1h5ITV9Pwhik0VhGDH7uJD2DrAA=",-2784156269965757727,3734134015892899493,3202153743528492050,8245458707045729676>()) {
                  case 1714944673:
                     break label50;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var2;
         switch ((int)com.yiyiaddon.m.b.a<"s2mqur3hz56laz","0mre+D3fyNtlqDcEIav/lwYc6xJoz3CLRyBfDt3zf40=",1289197652065638912,8250476641154628673,5597695447566942437,-7309854413002741359>()) {
            case 1714386644:
               break;
            default:
               throw null;
         }
      }

      j.a var3 = var10000.a(var10001);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"seeg67704mh43","cL/mLFGq1gRu/HA1HaEfqVe/a2GrTkI0g3lVIX598Fc=",-6121834004250228689,-9065558677337654160,-4310295222190847711,-832779950760105575>()) {
            case -534219473:
               return (String)com.yiyiaddon.m.b.a<"s2znhpijwdvgfy","2hk9N2b3Tus4RJZoFF9VEVUN74GU5IGe17U412QcGk1ctunT5cY=",-8208298455422911602,3376576286720642828,-4316427065899466819,-5610948967282337745>();
            default:
               throw null;
         }
      } else {
         j.b var4 = this.b(var3);
         String var8;
         if (var4 == null) {
            label41:
            switch ((int)com.yiyiaddon.m.b.a<"s1p1egi1l7zeoy","SJnLRTRU4xAP5Soeh38vOY9f1RGT5zpcjyF8/s29aDw=",-1905815188021487342,8113559673519723937,-8120297803722991071,2239354020613535786>()) {
               case -725045026:
                  var8 = (String)com.yiyiaddon.m.b.a<"s22s9s43ei6w2o","SOHxYN3VNWpG/IKor3/oh873epdFxUMm2Hetb4xfvYjVXw/pg7Q=",2952078869137184225,-3764676734233865801,-7850780749742301939,585607003013262256>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1toarp7l097dn","VmtMQsx6AryC68ag6DY983aUCEKhxWrUSXJOyv07MXM=",-7960768597597880847,189493263842042270,-4066947874362282110,-1371352386828702869>()) {
                     case -1462006503:
                        break label41;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var8 = var4.name;
            switch ((int)com.yiyiaddon.m.b.a<"s3u9t5yfe46aoi","fstWcWnV/joSo5/+nWocT2lsRQa9dWfBZbpzUI5ytmI=",2665145081276199065,2164421494529650904,-2071835371297172970,5993637555303507338>()) {
               case 2117093984:
                  break;
               default:
                  throw null;
            }
         }

         String var5 = var8;
         o var6 = var1.b();
         int var9;
         if (var6 == null) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"swysq4kzb3xy5","Sy8PZ0tMp2H8QJFSilBjPTsxx7G0qozxxNFWkzqsAo4=",-7013105023857970502,-9055625439474311224,6265485284901507241,6253889776694215730>()) {
               case 713971803:
                  var9 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s194vxk22ozarm","AyIu2sYuJpCoH84AF/AF4g+sX3eMNKaGHYvdnrmcqls=",-5323338723528771446,3943930607643148447,8361787644600774151,5086900697548455292>()) {
                     case -1888034915:
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var9 = var6.Q().size();
            switch ((int)com.yiyiaddon.m.b.a<"s3t3ujcqxmgivc","9+EbWyL+GKRpvadV+z6TVuiUJhSBs0ARVCozjdFmsPQ=",-7683570383062763195,1174708992096680303,-6558517068066690258,8155860659667248569>()) {
               case 615811343:
                  break;
               default:
                  throw null;
            }
         }

         int var7 = var9;
         return var3.name + var5 + var7;
      }
   }

   private j.b b(j.a var1) {
      String var2 = new com.yiyiaddon.e.g.d.i(this.i.a().aw).aG();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zdh0cooavzyr","QhbE6KQJA8l3qsW3Z0fqufDWzg6S1WtFC+T73Js1BYg=",6840791914116636416,6665545339172632123,-4660719790091385922,-806808776388586942>()) {
            case -572792721:
               return null;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var1.profiles.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1nlxq5kgvossz","KK3tKxNX9C3c1aUSyqRTgoZZ8/Jo8qr1gchdL+Zw0IY=",7288207739366771683,7792482472844979970,1848028999002370909,-1343615058918684277>()) {
            case -2145911434:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2erai3mtdijsq","AjWIb3MtJBQgxbv48neftaXCMDn6Qj13kboFeWG1uAA=",-8023965906159971878,3395857771222367232,582047332098081825,-6071100957147678749>()) {
                     case 488878082:
                        j.b var4 = (j.b)var3.next();
                        if (var4.id.equals(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1lrgs37plrkwz","NbyCbFeosFG7G+Ad7rhbuMrXRLC3SmJIFXkLlbHC0TY=",-741354550404211982,-1381206093057987523,4564046036146037455,-4334452575895225822>()) {
                              case 562878165:
                                 return var4;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1wt4my8uj7ust","Fpabc2HMLpBq3Fuyao90U1d0RAxZeBR6t+PTvDZf0vk=",8096155448755902584,1197991114903945986,8426250075576022347,-8502104178231713947>()) {
                           case -606620038:
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
   }
}

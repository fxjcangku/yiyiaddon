package com.yiyiaddon.d.c;

import net.minecraft.core.BlockPos;

public record i(i.a a, String aq, String ar, BlockPos b, int q) {
   public static final int r = 1;
   public static final int s = 2;
   public static final int t = 4;
   public static final int u = 8;
   public static final int v = 16;
   public static final int w = 32;
   public static final int x = 64;

   public boolean b(int var1) {
      if ((this.q & var1) != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kie61u398cqu","071Z4+1mod0EdaWGXOHm9B5EisYVyfUfY/8r+rpXxc4=",6878323692675011763,1755246982358534849,-7212444452433151745,1256886575952363333>()) {
            case 63254604:
               switch ((int)com.yiyiaddon.m.b.a<"s2ccogw1wuhxke","lIL5ZitBme4w3YekXE1mxC40Wda1QFLmH2wNXF24PcY=",-7646477316893371360,-3616940604290256472,1670058519377753156,-5028815184366799835>()) {
                  case -1771330533:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s38ja3nkqf33v5","MXboiLV/v0Gudx9aOdNdyd8XZ+ds9VaAMus3e13fd40=",3856937396822078897,-9104843172167765499,-8732689312305173780,7258442903900837325>()) {
            case -748599971:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean m() {
      if (!this.b(1)) {
         switch ((int)com.yiyiaddon.m.b.a<"sd3vwkks3t6uz","ZueDp/XV6yaTLvnRLK3ygoUDG1mYdTH8J21m58p2h1c=",5293744610871278469,-5366947722260502980,-115108586236811363,7682968115947979519>()) {
            case -2041073306:
               if (!this.b(2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3kqdglnyfhwjh","hq8OF0DO5VLu2g6xQCxSBuvq1kcY2hHicIDM+lmJouI=",4739908291740000130,6860690857877202788,5771367497647767526,2634963856614989552>()) {
                     case 1114701843:
                        if (!this.b(4)) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s13v1d6cr213ft","xp9oEN05n5OXq7DJh1VPRqlB0DVMvdLUzz2zX5rdJ3M=",2753374450950754905,-8157068948705162316,-1925343019141955580,-5757258848107147869>()) {
                              case -1451217569:
                                 if (!this.b(8)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s299z1jgxl4xkp","0K0ErrJ8TenjyCqisPhZ9SJkRc8TysRwq4aXXbPFAu8=",4621693221857628715,-9160268667132137175,-6738014314565536189,4650346633253160142>()) {
                                       case 1593933145:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1f6vxr942zfmf","TOdY/hciOpKn43mL6vAxUSe6ftisgDfoDQo2VKJ6rZc=",-7835332955210831991,-5110331523130377571,7140907661679704621,-5375655815142104301>()) {
                                    case -1328463433:
                                       break label28;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2jo44ord8s537","UNN3e/0hprAfQiW5qo9LcfdeOil+YxsWXZxqzP2eb34=",-4155324063789814790,-1380043267976280205,5929440299367153812,-832778146059822364>()) {
         case -1488787652:
            return true;
         default:
            throw null;
      }
   }

   static i a(i.a var0) {
      return new i(
         var0,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         null,
         0
      );
   }

   static i a(String var0) {
      return new i(
         i.a.CHAT,
         var0 == null
            ? (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>()
            : var0,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         null,
         0
      );
   }

   static i a(int var0) {
      return new i(
         i.a.PLAYER_INPUT,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         null,
         var0
      );
   }

   static i b(String var0) {
      return new i(
         i.a.CUSTOM_PAYLOAD,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         var0 == null
            ? (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>()
            : var0,
         null,
         0
      );
   }

   static i a(String var0, BlockPos var1) {
      return new i(
         i.a.PLAYER_ACTION,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         var0 == null
            ? (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>()
            : var0,
         var1,
         0
      );
   }

   static i a(BlockPos var0) {
      return new i(
         i.a.USE_ITEM_ON,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         var0,
         0
      );
   }

   static i a(i.a var0, String var1) {
      return new i(
         var0,
         (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>(),
         var1 == null
            ? (String)com.yiyiaddon.m.b.a<"s25fbnkxohf154","0xHM+YBTNh3bZ5NTHm7Tt9XagpEBGb8bT3Nt7g==",2641311504184258353,-5124976097974389553,-9142288574524588517,-5442428438157899014>()
            : var1,
         null,
         0
      );
   }

   public String A() {
      return this.aq;
   }

   public String B() {
      return this.ar;
   }

   public BlockPos a() {
      return this.b;
   }

   public int l() {
      return this.q;
   }

   public enum a {
      CHAT,
      PLAYER_INPUT,
      CUSTOM_PAYLOAD,
      PLAYER_ACTION,
      USE_ITEM,
      USE_ITEM_ON,
      MOVE_PLAYER,
      MOVE_VEHICLE,
      KEEP_ALIVE,
      ACCEPT_TELEPORT,
      ABILITIES,
      PLAYER_COMMAND,
      RESOURCE_PACK,
      OTHER;
   }
}

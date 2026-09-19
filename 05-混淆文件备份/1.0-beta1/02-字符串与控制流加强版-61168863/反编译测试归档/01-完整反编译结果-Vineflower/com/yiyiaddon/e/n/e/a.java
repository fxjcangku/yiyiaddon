package com.yiyiaddon.e.n.e;

import net.minecraft.core.BlockPos;

public record a(int lZ, int ma, int mb, String rG, boolean dF) {
   public BlockPos a() {
      return new BlockPos(this.lZ, this.ma, this.mb);
   }

   public static a a(int var0, int var1, int var2) {
      return new a(
         var0,
         var1,
         var2,
         (String)com.yiyiaddon.m.b.a<"s2xre7xdecfq6n","4R2m+3zqWKa5Q1j/htxgUAyaQt8laxU5nLdLTQ==",-1261998186130698219,4427201226521124492,-2290063913914084865,6563804982168474400>(),
         true
      );
   }

   public boolean da() {
      if (this.rG != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ne0s184vc23q","zeh0XY9qC2pVdaBxxQb7GAKwLi4V/Lc8FXqGY8f4Ieg=",7641075178216305586,-1536673792951450146,-8631175600099689669,-6111577447058468657>()) {
            case -364588423:
               if (!this.rG.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2rg81l46wdj","9tUmAgfn6a8HTq9j9tM5LDtBSYoQgNdYEertsMEJsb0=",585244705791811168,708251688793145097,7651293106448529592,3547950049658485894>()) {
                     case -345755021:
                        switch ((int)com.yiyiaddon.m.b.a<"s1f1lri1fz704u","Q1owEwwNX/5FIATDT/EraSbL1L1uEj8SFwQDdsJjrb4=",5497504650016622229,3742214779878439870,8204127038300134825,2882818736784214119>()) {
                           case 1999771386:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3hcd570uu6yfn","EMAb7x3PkkjkoFBkAlb/M1tJPLNkOBVmTFXActIxTzk=",338635078937964564,7374496590753880449,-526861969537352981,3994910974692152090>()) {
         case 1976551779:
            return false;
         default:
            throw null;
      }
   }

   public int aj() {
      return this.lZ;
   }

   public int ak() {
      return this.ma;
   }

   public int al() {
      return this.mb;
   }

   public String dk() {
      return this.rG;
   }

   public boolean ar() {
      return this.dF;
   }
}

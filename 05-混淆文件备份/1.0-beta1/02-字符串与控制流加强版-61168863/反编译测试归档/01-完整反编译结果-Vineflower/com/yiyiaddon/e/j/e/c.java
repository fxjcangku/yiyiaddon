package com.yiyiaddon.e.j.e;

import net.minecraft.core.BlockPos;

public record c(int iV, int iW, int iX, String mV, float bf, float bg) {
   public BlockPos a() {
      return new BlockPos(this.iV, this.iW, this.iX);
   }

   public boolean G() {
      String var1 = com.yiyiaddon.i.g.c.bU();
      if (!var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qbs2mvrn41fv","mwtvrzQNwyx1qMMeBjL+W94+Vhoa94CJspdBomgu8a0=",7171898733598741125,3991372255241209697,698626136207390906,-3017197261558027470>()) {
            case -577706642:
               if (var1.equals(this.mV)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sxjabzr5ta69m","ZdeNtqnLGFnpnZP2/CtEzeNVQoXZ7U5Ff53VwO5YDt8=",6325545093078164733,1660444903677240070,3041587413433046405,2746079872855889093>()) {
                     case 1842113935:
                        switch ((int)com.yiyiaddon.m.b.a<"sezrdvnf01dl9","9eIXWH60T8wJ9PEruDPtKLGghfE8kLfJl0d3hR+Xz80=",-6230443586004593327,408871942274398059,-4566583390436467469,5751938081819934254>()) {
                           case 378145476:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3b2vh7uiwd4tu","MaMkzF1A9gMtYFvMJJzWYY0it/OK7aaj00zYfBN8J2c=",3295143636286948258,-6911167081619127209,3249969224910806662,-2577023740636345617>()) {
         case -1075564150:
            return false;
         default:
            throw null;
      }
   }

   public int aj() {
      return this.iV;
   }

   public int ak() {
      return this.iW;
   }

   public int al() {
      return this.iX;
   }

   public String bU() {
      return this.mV;
   }

   public float g() {
      return this.bf;
   }

   public float h() {
      return this.bg;
   }
}

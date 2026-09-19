package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;

public final class q {
   public static final float gT = 740.0F;
   public static final float gU = 500.0F;
   public static final float gV = 22.0F;
   private static final float gW = 24.0F;
   private static final float gX = 0.16F;
   private static final float gY = 0.88F;
   private static final float gZ = 12.0F;
   private static final float[] b = new float[]{0.75F, 1.0F, 1.25F};
   private float ha = 740.0F;
   private float hb = 500.0F;
   private float hc = 1.0F;
   private float hd = 1.0F;
   private float he;
   private float hf;
   private float hg = 1.0F;
   private float hh = 1.0F;
   private float hi = -1.0F;
   private float hj;
   private boolean fL;
   private static q a;
   private int tk;
   private int tl;

   public q() {
   }

   public q(float var1, float var2) {
      this.d(var1, var2);
   }

   public void d(float var1, float var2) {
      this.ha = Math.max(1.0F, var1);
      this.hb = Math.max(1.0F, var2);
   }

   public float v() {
      return this.ha;
   }

   public float w() {
      return this.hb;
   }

   public void jN() {
      this.fL = true;
   }

   public boolean a(Minecraft var1, float var2) {
      float var3 = Math.max(0.0F, var2) / 0.16F;
      float var10001;
      if (this.fL) {
         label90:
         switch ((int)com.yiyiaddon.m.b.a<"s3c5yjd6uga9tk","+AWURyh4bWqHqp4zLpgQEZALCeVRdw40eoiKeJK8w58=",5142565048590636584,-8543564383513461761,-3876850255619559154,4958669228293805746>()) {
            case 1918167618:
               var10001 = this.hj - var3;
               switch ((int)com.yiyiaddon.m.b.a<"s2q53ncklrk1i4","vfbDGGqYxbxl3t/NsLGVX1wSi07bxJdR2fmeUvu350Y=",8220637800796882343,-2544875691610281761,-8727105031495920044,-6075180148778155387>()) {
                  case -342703552:
                     break label90;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = this.hj + var3;
         switch ((int)com.yiyiaddon.m.b.a<"s17n6td4womrg9","zSs4UMKtKjA09BrKZpKcJgd+azHiRKU1lzdGu8kJJdg=",6096853866284212987,5915137236639770302,8107090587266454293,-5549933144703046978>()) {
            case -854121255:
               break;
            default:
               throw null;
         }
      }

      this.hj = com.yiyiaddon.l.a.a.d(var10001);
      this.hh = 0.88F + 0.120000005F * this.z();
      float var4 = b[Math.max(0, Math.min(com.yiyiaddon.c.a.b, b.length - 1))];
      if (this.hi < 0.0F) {
         label86:
         switch ((int)com.yiyiaddon.m.b.a<"s3fidhnq2t9teg","3c9ydToQvtF64kgWeoQuL+TNhJ6187Wh8fH6Fq1n4mM=",6112735525288973488,-6882097568426911312,4459028484720911600,-6674272810473280859>()) {
            case -1887992617:
               this.hi = var4;
               switch ((int)com.yiyiaddon.m.b.a<"s4k56ohbahn3a","5HicS4lL1QO7+QeeHpXNztMIiW343BpGC2c+uEkaoXg=",-317053018167171141,-5969705488759701940,-814659604217333476,-4366849689538603874>()) {
                  case 787700869:
                     break label86;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.hi = this.hi + (var4 - this.hi) * Math.min(1.0F, Math.max(0.0F, var2) * 12.0F);
      if (Math.abs(this.hi - var4) < 0.001F) {
         label83:
         switch ((int)com.yiyiaddon.m.b.a<"sww3tw63guyyg","clbUeP7Sw5o6JsXayTsnEgIj281pT/KMGqTKAjB79o0=",-1224559373199080902,213217968461117480,-7677440510773680755,-5038120652963625663>()) {
            case -1594057771:
               this.hi = var4;
               switch ((int)com.yiyiaddon.m.b.a<"s364r6yqymr26k","8qSeW9jG0zBv1dL0yhn3pSJg4v3rGmnXY7FHS6ygQ1Y=",981767306355764377,-86767056017260558,-8624818665486737124,8534657853683258971>()) {
                  case 851015358:
                     break label83;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      int var10;
      if (var1 == null) {
         label76:
         switch ((int)com.yiyiaddon.m.b.a<"s1t2rctueqypmk","TiuIOVbZa7+T2nK2J7c0Whc79NiAY48OG1GZmkeJg50=",-2462831683430835882,-4614737729452871654,-2962888938357531559,1877950159602096226>()) {
            case -517925343:
               var10 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3811324mhjrh1","dE5Vbt/m4vXt6RiN+dH3NPfCnz/nDuVonVPmgNSU6xo=",-2159426149557372160,9091111614202758970,-2458686080436520572,-7057044921548238514>()) {
                  case -1194073435:
                     break label76;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10 = var1.getWindow().getWidth();
         switch ((int)com.yiyiaddon.m.b.a<"s1618gfycds5hf","0Qg/D2jEZiOZSp4WC1dOxgQmvyivl7A1JrGt2OvQMW8=",-7894808012590097542,-2790594986465139920,-1043000718080301384,-3143194453334477459>()) {
            case 1958929120:
               break;
            default:
               throw null;
         }
      }

      this.hc = b(var10);
      int var11;
      if (var1 == null) {
         label69:
         switch ((int)com.yiyiaddon.m.b.a<"s2l6ds6jtabfls","xgX9L6mMoimQVnOFHysX6SrZDYBeCHEwxFWvhmAkCzE=",-6355971746712728394,-7525726278013527314,1110561875896034519,8327916820240398057>()) {
            case -2092700396:
               var11 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3j4wcb38w5bv8","XIbhVrkwx9r6RsjvJY9Oj7IK5BCnRvf0RAIWKR6zpdY=",4577014959644597762,-4443649525631064878,6436302283120450704,5193323731831703462>()) {
                  case 847917389:
                     break label69;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var11 = var1.getWindow().getHeight();
         switch ((int)com.yiyiaddon.m.b.a<"sb3w2q0gbg9x3","eM9p1H6zoiEWxFNyFtEGL9kQ7+qLF9XIcLY52ofad2M=",4219446526806579700,-2331538476850084412,6349029724749604110,-2499056844940125477>()) {
            case 469096614:
               break;
            default:
               throw null;
         }
      }

      this.hd = b(var11);
      this.he = (this.hc - this.ha) / 2.0F;
      this.hf = (this.hd - this.hb) / 2.0F;
      float var5 = (this.hc - 24.0F) / (this.ha * this.hi);
      float var6 = (this.hd - 24.0F) / (this.hb * this.hi);
      float var7 = Math.max(0.05F, Math.min(1.0F, Math.min(var5, var6)));
      float var10000;
      if (var1 == null) {
         label63:
         switch ((int)com.yiyiaddon.m.b.a<"sso3ondp2t6l7","zJDYzCBbpuYauWddYiua40GQTA+fI8zlcPQShrcFwig=",-3128544995163618637,-467290373971259598,-5767295579039226929,6486465657723945292>()) {
            case 1082702479:
               var10000 = 2.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s1veh55dpir1n7","ZO/ligM++KzYwlBu1iUWxCAXq8H9aJk23833lhtUDiE=",-5201824593919016951,6398251725991479811,2493079451421429623,6220869707262485213>()) {
                  case -1762013935:
                     break label63;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = Math.max(1.0F, var1.getWindow().getGuiScale());
         switch ((int)com.yiyiaddon.m.b.a<"sxa465yu543ut","p9y7tvfK4CEiQibCBX7QPNJbY1E3pLqIOTo9ccu1glA=",6150899911598033280,-520713350570090776,-2733486981374154119,-4505194943317770145>()) {
            case 1265106584:
               break;
            default:
               throw null;
         }
      }

      float var8 = var10000;
      this.hg = var7 * 2.0F / var8 * this.hi * this.hh;
      if (this.fL) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ez6ouynpnxsj","BqY4cob9pf3oOpu1/Vw2lh0ImgK7Yh0Dnjj/qJMiJeA=",7120265095397126614,6437140845125335275,-3339287889071520581,-3209765653481467894>()) {
            case 1265030895:
               if (this.hj <= 0.005F) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bdilnrt5u4ir","2RCK+sqte1GjC+EC/UF28pV5Y+9WnKnxC5rZArviodM=",608877941153162880,6550361162418046254,80415221647208142,8169509837178715648>()) {
                     case 1275038571:
                        switch ((int)com.yiyiaddon.m.b.a<"s3jlkikt8c9z2m","TBaaRc8G9oIV+FjlbP3uXm50BNikwdiXc8yd7p/5ZBI=",4456410495516621590,-8491265778701764533,-1055907372077409568,8652067199577104170>()) {
                           case -448434345:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1d9bozu3kvso3","Vl9pxq6NS//e8HJ/E8SPkDY67FrDLouP3Wjkf4Ev7oc=",4780249063312229523,4251993704914150081,-1024941190385579965,-4387076385639583967>()) {
         case 511869080:
            return false;
         default:
            throw null;
      }
   }

   public float x() {
      return this.he;
   }

   public float y() {
      return this.hf;
   }

   public float p() {
      return this.hg;
   }

   public float z() {
      return com.yiyiaddon.l.a.a.e(this.hj);
   }

   public float A() {
      return 22.0F / this.hh;
   }

   private static float b(int var0) {
      return Math.max(1.0F, Math.round(var0 * 0.5F));
   }

   public void a(Canvas var1, int var2, int var3) {
      this.tk = var2;
      this.tl = var3;
      a = this;
      var1.translate(var2 / 2.0F, var3 / 2.0F);
      var1.scale(this.hg, this.hg);
      var1.translate(-this.hc * 0.5F, -this.hd * 0.5F);
   }

   public static q a() {
      return a;
   }

   public float i(float var1) {
      return this.b(var1, this.tk);
   }

   public float j(float var1) {
      return this.c(var1, this.tl);
   }

   public float a(double var1, int var3) {
      return this.hc * 0.5F + ((float)var1 - var3 * 0.5F) / this.hg;
   }

   public float b(double var1, int var3) {
      return this.hd * 0.5F + ((float)var1 - var3 * 0.5F) / this.hg;
   }

   public float b(float var1, int var2) {
      return var2 * 0.5F + (var1 - this.hc * 0.5F) * this.hg;
   }

   public float c(float var1, int var2) {
      return var2 * 0.5F + (var1 - this.hd * 0.5F) * this.hg;
   }

   public float k(float var1) {
      return var1 * this.hg;
   }
}

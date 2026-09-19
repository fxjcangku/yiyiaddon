package com.yiyiaddon.e.o.b;

public final class a {
   private a() {
   }

   public record a(com.yiyiaddon.e.o.b.a.b c, com.yiyiaddon.e.o.b.a.c b) {
      public boolean eE() {
         if (this.b != com.yiyiaddon.e.o.b.a.c.COOLDOWN) {
            switch ((int)com.yiyiaddon.m.b.a<"s16t5v6sljzjv2","UOvO8n/AWnDGxeoml2EoOOFCKd7Ac4K8egv/7zjj/HA=",7308417681325507315,-3653032039631406697,-7878560914733438373,-5558810379749580596>()) {
               case 1296712445:
                  switch ((int)com.yiyiaddon.m.b.a<"s3iq7behwic9xe","jWbPf9jAbCvYe/MmXV+gLOp3lGP9IEtVKdfInCBrn7w=",2107521855304294355,-8247286150336003329,5893894099833259059,3766452318855456427>()) {
                     case 606328536:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3tis1sbud4ri4","0edHvLLsDHMioD+8MgbPLjNqREWGuCwYWS6Euz190/o=",-8507248083170222873,905797194978905533,4087917638914018168,-456058391682624193>()) {
               case 1610501195:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public com.yiyiaddon.e.o.b.a.b a() {
         return this.c;
      }

      public com.yiyiaddon.e.o.b.a.c a() {
         return this.b;
      }
   }

   public enum b {
      PACKET_FLY(
         (String)com.yiyiaddon.m.b.a<"s3p54yrbvlts1g","XakFqoha615KEVjj0BZwka9Xvar8kWJjUPpGqDMGDmVT2l9W",-2291218503763906768,7596957771777612839,-7952030045036873559,-4306173663483348365>()
      ),
      VANILLA_MIMIC(
         (String)com.yiyiaddon.m.b.a<"s17xw2syl1bo5m","vrNBGI9ct4jPRPVvcXmGUH9tcfvmXRpQcL1IjEmS5lAJAKkqiBeCi5foU07MlQ==",3136169681270974162,3998373590438161150,-8167542885179666339,6535973568809906020>()
      ),
      SAFE_GLIDE(
         (String)com.yiyiaddon.m.b.a<"s104emn4y8e3rr","7lgD2GkN5+aldNeCLD9tlI4vKRtlKLl4DnZ4m7MvHBFknFzo",4105968513017357364,7121004253700930128,8364372613935757971,3820247014603160075>()
      ),
      FIREWORK_BOOST(
         (String)com.yiyiaddon.m.b.a<"s3sgeg2c2osk0k","e/ehHLhAYblie7nE0ghpfRHIXHi5yNlZHZWZj4p62YPUosSe",7559875926831137605,-3958808073713713775,-7305129213557792839,-2056286966265702961>()
      ),
      SEQUENCE_SCAFFOLD(
         (String)com.yiyiaddon.m.b.a<"s2nfa123b681mg","ZOI7pkdj3ruhxLGxaeO7PuZknf8wYcDyEaTlatXrW7MZAf7p",218893199180015013,-4980062042860539480,-7166390492250954718,-4730369024992436443>()
      );

      public final String wH;

      b(String var3) {
         this.wH = var3;
      }

      @Override
      public String toString() {
         return this.wH;
      }
   }

   public enum c {
      GRANTED,
      COOLDOWN,
      HIGH_RISK_AC,
      NO_FLY_ABILITY,
      DEGRADED;
   }
}

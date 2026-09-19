package com.yiyiaddon;

import com.yiyiaddon.c.a;
import com.yiyiaddon.e.o.b.c;
import com.yiyiaddon.k.e;
import com.yiyiaddon.k.f;
import com.yiyiaddon.k.g;
import com.yiyiaddon.l.g.a.l;
import com.yiyiaddon.l.i.d;
import com.yiyiaddon.m.b;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class YiyiAddonClient implements ClientModInitializer {
   private static final String d = (String)b.a<"s1ncyplr3clij2","L4gKoTjZeUqr/VoK53wO9+VZis/FIA2TjJWxYz/KS/DxxbS29paBEzTxl/+0KY11PGyLaZMb",8996972578883844597,-5114179793464050591,2165830244295581899,7103635254351338572>();

   @Override
   public void onInitializeClient() {
      a.d();
      com.yiyiaddon.l.i.d.kV();
      com.yiyiaddon.h.a.a();
      c.init();
      com.yiyiaddon.l.d.d.jO();
      ClientTickEvents.END_CLIENT_TICK.register(com.yiyiaddon.l.d.d::j);
      ClientTickEvents.END_CLIENT_TICK.register(com.yiyiaddon.l.d.b::j);
      com.yiyiaddon.c.a.a.d();
      com.yiyiaddon.k.b.a.a().d();
      com.yiyiaddon.k.e.c.init();
      g.aR();
      com.yiyiaddon.k.d.aR();
      e.aR();
      com.yiyiaddon.d.a.b.a(
         (String)b.a<"s1ncyplr3clij2","L4gKoTjZeUqr/VoK53wO9+VZis/FIA2TjJWxYz/KS/DxxbS29paBEzTxl/+0KY11PGyLaZMb",8996972578883844597,-5114179793464050591,2165830244295581899,7103635254351338572>(),
         com.yiyiaddon.d.a.c.JOIN_SERVER,
         var0 -> f.jn()
      );
      com.yiyiaddon.d.a.b.a(
         (String)b.a<"s1ncyplr3clij2","L4gKoTjZeUqr/VoK53wO9+VZis/FIA2TjJWxYz/KS/DxxbS29paBEzTxl/+0KY11PGyLaZMb",8996972578883844597,-5114179793464050591,2165830244295581899,7103635254351338572>(),
         com.yiyiaddon.d.a.c.DISCONNECT,
         var0 -> {
            com.yiyiaddon.k.d.jl();
            f.f();
         }
      );
      com.yiyiaddon.e.n.n.f.init();
      l.a(
         (String)b.a<"s2ak173m4i7fpp","b+R2s3fYqZ9CUz8pFfGVY1bQyOFyjlZ3jtr7zt8tsWob/t6mHs83Ca2EfUVmBqZvDRrVLP/d7sNwSFbubV/CXqxDlfo=",-5868926478643467489,2005049390719446232,2153298013700645569,8562393551036252408>(),
         com.yiyiaddon.l.g.a.a::render
      );
   }
}

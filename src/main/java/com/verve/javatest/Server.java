package com.verve.javatest;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import com.verve.javatest.controller.AdController;
import com.verve.javatest.controller.DealController;
import com.verve.javatest.controller.SupplyController;
import com.verve.javatest.controller.TagsController;

public class Server {
  public static void main(String[] args) {
    Javalin app = Javalin.create().start(9000);

    app.routes(() -> {
      path("0", () -> {
        path("supply", () -> {
          post( SupplyController.createSupply ); // Save a supply
          get( SupplyController.fetchAllSupplies ); // Retrieve all supplies
          path( ":id", () -> {
        	  get( SupplyController.fetchById ); // Retrieve a supply
        	  put( SupplyController.updateSupply ); // Update a supply
          });
        });
        path("deals", () -> {
        	post( DealController.createDeal ); // Save a Deal
          path( ":id", () -> {
        	  get( DealController.fetchDealById ); // Retrieve a deal
          });
        });
        path("tags", () -> {
        	post( TagsController.createTags ); // Save a tag
          path( ":id", () -> {
        	  get( TagsController.fetchById ); // Retrieve a tag
        	  put( TagsController.updateTags ); // Update a tag
          });
        });
        path("ad", () -> {
        	get( AdController.fetchAdBySupplyId ); // Retrieve an ad
        });
      });
    });
  }
}

package main

import (
	"BlockChainService"
	"BlockChainService/pkg/handler"
	"BlockChainService/pkg/service"

	"github.com/joho/godotenv"
	"github.com/sirupsen/logrus"
)

func main() {

	if err := godotenv.Load(); err != nil {
		logrus.Fatalf("error loading env: %s", err.Error())
	}

	services := service.NewService()
	handlers := handler.NewHandler(services)

	srv := new(BlockChainService.Server)
	if err := srv.Run("9090", handlers.InitRoutes()); err != nil {
		logrus.Panic("error occured while running http server: &s", err.Error())
	}
}

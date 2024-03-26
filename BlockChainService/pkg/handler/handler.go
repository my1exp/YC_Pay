package handler

import (
	"BlockChainService/pkg/service"
	"github.com/gin-gonic/gin"
)

type Handler struct {
	services *service.Service
}

func NewHandler(services *service.Service) *Handler {
	return &Handler{services: services}
}

func (h *Handler) InitRoutes() *gin.Engine {
	router := gin.New()

	xrp := router.Group("/xrp")
	{
		xrp.GET("/wallet", h.generateWallet)
		xrp.GET("/payments", h.getPayments)
	}
	return router
}

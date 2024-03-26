package handler

import (
	"BlockChainService"
	"github.com/gin-gonic/gin"
	"net/http"
)

func (h *Handler) generateWallet(c *gin.Context) {
	var input BlockChainService.RequestWallet

	if err := c.BindJSON(&input); err != nil {
		newErrorResponse(c, http.StatusBadRequest, err.Error())
		return
	}
	print(input.Currency, input.Network)

	addr, secret := h.services.Wallet.CreateWallet(input)

	c.JSON(http.StatusOK, map[string]interface{}{
		"addr":   addr,
		"secret": secret,
	})

}

func (h *Handler) getPayments(c *gin.Context) {

}

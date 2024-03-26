package service

import (
	"BlockChainService"
	"hg.sr.ht/~dchapes/ripple/crypto/rkey"
)

type Wallet interface {
	CreateWallet(requestWallet BlockChainService.RequestWallet) (*rkey.FamilySeed, string)
}

type Payments interface {
}

type Service struct {
	Wallet
	Payments
}

func NewService() *Service {
	return &Service{}
}
